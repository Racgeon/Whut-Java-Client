package com.rkgn.gui;

import com.rkgn.dto.ChangePasswordForm;
import com.rkgn.dto.Result;
import com.rkgn.entity.User;
import com.rkgn.utils.Constants;
import com.rkgn.utils.Dialog;
import com.rkgn.utils.SocketUtils;
import com.rkgn.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;

public class ChangePasswordFrame extends JFrame {
    private final User user;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPasswordField passwordField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public ChangePasswordFrame() {
        this.user = UserHolder.getUser();
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = toolkit.getScreenSize().width / 4;
        int height = toolkit.getScreenSize().height / 4;
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    public void clear() {
        passwordField.setText("");
    }

    private void okButtonMouseClicked(MouseEvent e) {
        String password = new String(passwordField.getPassword());
        if (password.isEmpty()) {
            Dialog.error(this, "密码不能为空！");
        }
        int confirm = Dialog.confirm(this, "确认修改密码？");
        if (confirm == JOptionPane.YES_OPTION) {
            try (Socket socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT)) {
                ChangePasswordForm changePasswordForm = new ChangePasswordForm(user.getName(), password);
                SocketUtils.send(socket, "change password\n" + changePasswordForm);
                Result result = SocketUtils.parseResult(socket);
                if (!result.getSuccess()) {
                    Dialog.error(this, "修改密码错误：" + result.getErrorMsg());
                    return;
                }
            } catch (IOException ex) {
                Dialog.error(this, "修改密码错误：" + ex.getMessage());
                return;
            }
            Dialog.info(this, "修改密码成功！");
        }
    }

    private void cancelButtonMouseClicked(MouseEvent e) {
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        var subtitlePanel = new JPanel();
        var subtitle = new JLabel();
        var passwordPanelHolder = new JPanel();
        var placeholder1 = new JPanel();
        var passwordPanel = new JPanel();
        var label3 = new JLabel();
        var label2 = new JLabel();
        var label4 = new JLabel();
        passwordField = new JPasswordField();
        var label5 = new JLabel();
        var placeholder2 = new JPanel();
        var buttonPanelHolder = new JPanel();
        var placeholder3 = new JPanel();
        var buttonPanel = new JPanel();
        var okButton = new JButton();
        var cancelButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(3, 0));

        //======== subtitlePanel ========
        {
            subtitlePanel.setLayout(new GridLayout());

            //---- subtitle ----
            subtitle.setText("\u4fee\u6539\u5bc6\u7801");
            subtitle.setHorizontalAlignment(SwingConstants.CENTER);
            subtitle.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
            subtitlePanel.add(subtitle);
        }
        contentPane.add(subtitlePanel);

        //======== passwordPanelHolder ========
        {
            passwordPanelHolder.setLayout(new GridLayout(3, 0));

            //======== placeholder1 ========
            {
                placeholder1.setLayout(new BoxLayout(placeholder1, BoxLayout.X_AXIS));
            }
            passwordPanelHolder.add(placeholder1);

            //======== passwordPanel ========
            {
                passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));

                //---- label3 ----
                label3.setText("            ");
                passwordPanel.add(label3);

                //---- label2 ----
                label2.setText("\u5bc6\u7801");
                passwordPanel.add(label2);

                //---- label4 ----
                label4.setText("    ");
                passwordPanel.add(label4);
                passwordPanel.add(passwordField);

                //---- label5 ----
                label5.setText("                ");
                passwordPanel.add(label5);
            }
            passwordPanelHolder.add(passwordPanel);

            //======== placeholder2 ========
            {
                placeholder2.setLayout(new BoxLayout(placeholder2, BoxLayout.X_AXIS));
            }
            passwordPanelHolder.add(placeholder2);
        }
        contentPane.add(passwordPanelHolder);

        //======== buttonPanelHolder ========
        {
            buttonPanelHolder.setLayout(new BoxLayout(buttonPanelHolder, BoxLayout.Y_AXIS));

            //======== placeholder3 ========
            {
                placeholder3.setLayout(new FlowLayout());
            }
            buttonPanelHolder.add(placeholder3);

            //======== buttonPanel ========
            {
                buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));

                //---- okButton ----
                okButton.setText("\u786e\u8ba4");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okButtonMouseClicked(e);
                    }
                });
                buttonPanel.add(okButton);

                //---- cancelButton ----
                cancelButton.setText("\u53d6\u6d88");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked(e);
                    }
                });
                buttonPanel.add(cancelButton);
            }
            buttonPanelHolder.add(buttonPanel);
        }
        contentPane.add(buttonPanelHolder);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}
