package com.rkgn.gui;

import cn.hutool.json.JSONArray;
import com.rkgn.dto.DeleteForm;
import com.rkgn.dto.Result;
import com.rkgn.entity.User;
import com.rkgn.utils.Dialog;
import com.rkgn.utils.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class DeleteFrame extends JFrame {
    private List<User> userList;

    public DeleteFrame() {
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = (int) (toolkit.getScreenSize().height / 3.5);
        setSize(toolkit.getScreenSize().width / 4, height);
        setLocationRelativeTo(null);
        refresh();
    }

    public void refresh() {
        try (Socket socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT)) {
            SocketUtils.send(socket, "get all users\n");
            Result result = SocketUtils.parseResult(socket);
            if (!result.getSuccess()) {
                Dialog.error(this, "获取全部用户错误：" + result.getErrorMsg());
                return;
            }
            userList = ((JSONArray) result.getData()).stream().map(JSONObjectUtils::parseUser).toList();
        } catch (IOException ex) {
            Dialog.error(this, "获取全部用户错误：" + ex.getMessage());
        }
        Vector<Object> columnNames = new Vector<>();
        columnNames.add("用户名");
        columnNames.add("角色");
        TableModel tableModel = new DefaultTableModel(columnNames, userList.size());
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            tableModel.setValueAt(user.getName(), i, 0);
            tableModel.setValueAt(Role.translateMap.get(user.getRole()), i, 1);
        }
        userTable.setModel(tableModel);
    }

    private void deleteMouseClicked(MouseEvent e) {
        int[] selectedRows = userTable.getSelectedRows();
        for (int selectedRow : selectedRows) {
            User user = userList.get(selectedRow);
            String username = user.getName();

            if (username.equals(user.getName())) {
                Dialog.error(this, "您不能删除自己！");
                return;
            }
            int confirm = Dialog.confirm(this, "确认删除用户：" + username + "?");
            switch (confirm) {
                case JOptionPane.YES_NO_OPTION -> {
                    try (Socket socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT)) {
                        DeleteForm deleteForm = new DeleteForm(username);
                        SocketUtils.send(socket, "delete\n" + deleteForm);
                        Result result = SocketUtils.parseResult(socket);
                        if (!result.getSuccess()) {
                            Dialog.error(this, "删除角色错误：" + result.getErrorMsg());
                            return;
                        }
                    } catch (IOException ex) {
                        Dialog.error(this, "删除角色错误：" + ex.getMessage());
                        return;
                    } finally {
                        refresh();
                    }
                    Dialog.info(this, "删除用户成功！");
                }
                case JOptionPane.NO_OPTION -> {
                }
            }
        }
    }

    private void cancelMouseClicked(MouseEvent e) {
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel = new JPanel();
        title = new JLabel();
        scrollPane1 = new JScrollPane();
        userTable = new JTable();
        panel2 = new JPanel();
        panel7 = new JPanel();
        ok = new JButton();
        cancel = new JButton();

        //======== this ========
        setTitle("\u5220\u9664\u7528\u6237");
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== panel ========
        {
            panel.setLayout(new GridLayout());

            //---- title ----
            title.setText("\u5220\u9664\u7528\u6237");
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
            panel.add(title);
        }
        contentPane.add(panel);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(userTable);
        }
        contentPane.add(scrollPane1);

        //======== panel2 ========
        {
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

            //======== panel7 ========
            {
                panel7.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));

                //---- ok ----
                ok.setText("\u786e\u8ba4");
                ok.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        deleteMouseClicked(e);
                    }
                });
                panel7.add(ok);

                //---- cancel ----
                cancel.setText("\u53d6\u6d88");
                cancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelMouseClicked(e);
                        cancelMouseClicked(e);
                    }
                });
                panel7.add(cancel);
            }
            panel2.add(panel7);
        }
        contentPane.add(panel2);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel;
    private JLabel title;
    private JScrollPane scrollPane1;
    private JTable userTable;
    private JPanel panel2;
    private JPanel panel7;
    private JButton ok;
    private JButton cancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
