package com.rkgn.gui;

import com.rkgn.dto.AddForm;
import com.rkgn.dto.Result;
import com.rkgn.entity.Administrator;
import com.rkgn.entity.Browser;
import com.rkgn.entity.Operator;
import com.rkgn.entity.User;
import com.rkgn.exception.DataException;
import com.rkgn.utils.Constants;
import com.rkgn.utils.Dialog;
import com.rkgn.utils.Role;
import com.rkgn.utils.SocketUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;


public class AddFrame extends JFrame {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleSelector;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public AddFrame() {
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = toolkit.getScreenSize().width / 4;
        int height = (int) (toolkit.getScreenSize().height / 2.5);
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    public void clear() {
        usernameField.setText("");
        passwordField.setText("");
        roleSelector.setSelectedItem(null);
    }

    private void okMouseClicked(MouseEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String selectedItem;
        try {
            selectedItem = Objects.requireNonNull(roleSelector.getSelectedItem()).toString();
        } catch (NullPointerException ex) {
            Dialog.error(this, "角色不能为空！");
            return;
        }

        try {
            validateUser(username, password, Role.translateMap.get(selectedItem));
        } catch (DataException ex) {
            return;
        }

        User user = switch (selectedItem) {
            case "档案浏览员" -> new Browser(username, password);
            case "档案录入员" -> new Operator(username, password);
            case "系统管理员" -> new Administrator(username, password);
            default -> null;
        };

        if (user == null) {
            Dialog.error(this, "角色选择错误！");
            return;
        }

        try (Socket socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT)) {
            AddForm addForm = new AddForm(username, password, user.getRole());
            SocketUtils.send(socket, "add\n" + addForm);
            Result result = SocketUtils.parseResult(socket);
            if (!result.getSuccess()) {
                Dialog.error(this, "新增角色错误：" + result.getErrorMsg());
                return;
            }
        } catch (IOException ex) {
            Dialog.error(this, "新增角色错误：" + ex.getMessage());
            return;
        }

        Dialog.info(this, "新增用户成功！");
        setVisible(false);
    }

    private void validateUser(String username, String password, String role) throws DataException {
        if (username.isEmpty() || password.isEmpty()) {
            Dialog.error(this, "用户名或密码不能为空！");
            throw new DataException("录入的用户名或密码或角色错误！");
        }
        if (!Role.translateMap.containsKey(role)) {
            Dialog.error(this, "录入的角色信息错误！");
            throw new DataException("录入的角色信息错误！");
        }
    }

    private void cancelMouseClicked(MouseEvent e) {
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        var subtitlePanel = new JPanel();
        var subtitle = new JLabel();
        var usernamePanelHolder = new JPanel();
        var placeholder1 = new JPanel();
        var usernamePanel = new JPanel();
        var label1 = new JLabel();
        var usernameLabel = new JLabel();
        var label2 = new JLabel();
        usernameField = new JTextField();
        var label3 = new JLabel();
        var placeholder2 = new JPanel();
        var passwordPanelHolder = new JPanel();
        var placeholder3 = new JPanel();
        var passwordPanel = new JPanel();
        var label4 = new JLabel();
        var passwordLabel = new JLabel();
        var label5 = new JLabel();
        passwordField = new JPasswordField();
        var label6 = new JLabel();
        var placeholder4 = new JPanel();
        var rolePanelHolder = new JPanel();
        var placeholder5 = new JPanel();
        var rolePanel = new JPanel();
        var label7 = new JLabel();
        var roleLabel = new JLabel();
        var label8 = new JLabel();
        roleSelector = new JComboBox<>();
        var label9 = new JLabel();
        var placeholder6 = new JPanel();
        var buttonPanelHolder = new JPanel();
        var placeholder7 = new JPanel();
        var buttonPanel = new JPanel();
        var ok = new JButton();
        var cancel = new JButton();
        var placeholder8 = new JPanel();

        //======== this ========
        setTitle("\u65b0\u589e\u7528\u6237");
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(5, 0));

        //======== subtitlePanel ========
        {
            subtitlePanel.setLayout(new GridLayout());

            //---- subtitle ----
            subtitle.setText("\u65b0\u589e\u7528\u6237");
            subtitle.setHorizontalAlignment(SwingConstants.CENTER);
            subtitle.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
            subtitlePanel.add(subtitle);
        }
        contentPane.add(subtitlePanel);

        //======== usernamePanelHolder ========
        {
            usernamePanelHolder.setLayout(new GridLayout(3, 0));

            //======== placeholder1 ========
            {
                placeholder1.setLayout(new FlowLayout());
            }
            usernamePanelHolder.add(placeholder1);

            //======== usernamePanel ========
            {
                usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));

                //---- label1 ----
                label1.setText("            ");
                usernamePanel.add(label1);

                //---- usernameLabel ----
                usernameLabel.setText("\u7528\u6237\u540d");
                usernamePanel.add(usernameLabel);

                //---- label2 ----
                label2.setText("    ");
                usernamePanel.add(label2);
                usernamePanel.add(usernameField);

                //---- label3 ----
                label3.setText("                ");
                usernamePanel.add(label3);
            }
            usernamePanelHolder.add(usernamePanel);

            //======== placeholder2 ========
            {
                placeholder2.setLayout(new FlowLayout());
            }
            usernamePanelHolder.add(placeholder2);
        }
        contentPane.add(usernamePanelHolder);

        //======== passwordPanelHolder ========
        {
            passwordPanelHolder.setLayout(new GridLayout(3, 0));

            //======== placeholder3 ========
            {
                placeholder3.setLayout(new FlowLayout());
            }
            passwordPanelHolder.add(placeholder3);

            //======== passwordPanel ========
            {
                passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));

                //---- label4 ----
                label4.setText("            ");
                passwordPanel.add(label4);

                //---- passwordLabel ----
                passwordLabel.setText("\u5bc6\u7801");
                passwordPanel.add(passwordLabel);

                //---- label5 ----
                label5.setText("       ");
                passwordPanel.add(label5);
                passwordPanel.add(passwordField);

                //---- label6 ----
                label6.setText("                ");
                passwordPanel.add(label6);
            }
            passwordPanelHolder.add(passwordPanel);

            //======== placeholder4 ========
            {
                placeholder4.setLayout(new FlowLayout());
            }
            passwordPanelHolder.add(placeholder4);
        }
        contentPane.add(passwordPanelHolder);

        //======== rolePanelHolder ========
        {
            rolePanelHolder.setLayout(new GridLayout(3, 0));

            //======== placeholder5 ========
            {
                placeholder5.setLayout(new FlowLayout());
            }
            rolePanelHolder.add(placeholder5);

            //======== rolePanel ========
            {
                rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.X_AXIS));

                //---- label7 ----
                label7.setText("            ");
                rolePanel.add(label7);

                //---- roleLabel ----
                roleLabel.setText("\u89d2\u8272");
                rolePanel.add(roleLabel);

                //---- label8 ----
                label8.setText("      ");
                rolePanel.add(label8);

                //---- roleSelector ----
                roleSelector.setModel(new DefaultComboBoxModel<>(new String[] {
                    "\u6863\u6848\u6d4f\u89c8\u5458",
                    "\u6863\u6848\u5f55\u5165\u5458",
                    "\u7cfb\u7edf\u7ba1\u7406\u5458"
                }));
                rolePanel.add(roleSelector);

                //---- label9 ----
                label9.setText("                ");
                rolePanel.add(label9);
            }
            rolePanelHolder.add(rolePanel);

            //======== placeholder6 ========
            {
                placeholder6.setLayout(new FlowLayout());
            }
            rolePanelHolder.add(placeholder6);
        }
        contentPane.add(rolePanelHolder);

        //======== buttonPanelHolder ========
        {
            buttonPanelHolder.setLayout(new GridLayout(3, 0));

            //======== placeholder7 ========
            {
                placeholder7.setLayout(new FlowLayout());
            }
            buttonPanelHolder.add(placeholder7);

            //======== buttonPanel ========
            {
                buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, -2));

                //---- ok ----
                ok.setText("\u786e\u8ba4");
                ok.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okMouseClicked(e);
                    }
                });
                buttonPanel.add(ok);

                //---- cancel ----
                cancel.setText("\u53d6\u6d88");
                cancel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelMouseClicked(e);
                    }
                });
                buttonPanel.add(cancel);
            }
            buttonPanelHolder.add(buttonPanel);

            //======== placeholder8 ========
            {
                placeholder8.setLayout(new FlowLayout());
            }
            buttonPanelHolder.add(placeholder8);
        }
        contentPane.add(buttonPanelHolder);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}
