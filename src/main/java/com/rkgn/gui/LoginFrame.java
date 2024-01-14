package com.rkgn.gui;

import com.rkgn.dto.LoginForm;
import com.rkgn.dto.Result;
import com.rkgn.entity.User;
import com.rkgn.utils.Dialog;
import com.rkgn.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;


public class LoginFrame extends JFrame {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTextField usernameField;
    private JPasswordField passwordField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    public LoginFrame() {
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = toolkit.getScreenSize().width / 4;
        int height = toolkit.getScreenSize().height / 3;
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    private void loginMouseClicked(MouseEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Socket socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT)) {
            LoginForm loginForm = new LoginForm(username, password);
            SocketUtils.send(socket, "login\n" + loginForm);
            Result result = SocketUtils.parseResult(socket);

            if (!result.getSuccess()) {
                Dialog.error(this, "登录错误：" + result.getErrorMsg());
                return;
            }

            User user = JSONObjectUtils.parseUser(result.getData());

            if (user == null) {
                Dialog.error(this, "您输入的账号或密码错误！");
            } else {
                Dialog.info(this, "登录成功！");
                UserHolder.setUser(user);
                user.showMenu();
                Frames.loginFrames.put(user.getName(), this);
                setVisible(false);
            }
        } catch (IOException ex) {
            Dialog.error(this, "登录错误：" + ex.getMessage());
        }
    }

    private void exitMouseClicked(MouseEvent e) {
        int confirm = Dialog.confirm(this, "是否退出系统？");
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        var tabbedPane = new JTabbedPane();
        var panel = new JPanel();
        var subtitlePanel = new JPanel();
        var subtitle = new JLabel();
        var usernamePanelHolder = new JPanel();
        var placeholder1 = new JPanel();
        var usernamePanel = new JPanel();
        var label2 = new JLabel();
        var usernameLabel = new JLabel();
        var label3 = new JLabel();
        usernameField = new JTextField();
        var label4 = new JLabel();
        var placeholder2 = new JPanel();
        var passwordPanelHolder = new JPanel();
        var placeholder3 = new JPanel();
        var passwordPanel = new JPanel();
        var label5 = new JLabel();
        var passwordLabel = new JLabel();
        var label6 = new JLabel();
        passwordField = new JPasswordField();
        var label7 = new JLabel();
        var placeholder4 = new JPanel();
        var buttonPanel = new JPanel();
        var login = new JButton();
        var exit = new JButton();

        //======== this ========
        setTitle("\u6863\u6848\u7ba1\u7406\u7cfb\u7edf");
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        //======== tabbedPane ========
        {

            //======== panel ========
            {
                panel.setLayout(new GridLayout(4, 1));

                //======== subtitlePanel ========
                {
                    subtitlePanel.setLayout(new GridLayout());

                    //---- subtitle ----
                    subtitle.setText("\u6863\u6848\u7ba1\u7406\u7cfb\u7edf");
                    subtitle.setHorizontalAlignment(SwingConstants.CENTER);
                    subtitle.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
                    subtitlePanel.add(subtitle);
                }
                panel.add(subtitlePanel);

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

                        //---- label2 ----
                        label2.setText("            ");
                        usernamePanel.add(label2);

                        //---- usernameLabel ----
                        usernameLabel.setText("\u8d26\u53f7");
                        usernamePanel.add(usernameLabel);

                        //---- label3 ----
                        label3.setText("    ");
                        usernamePanel.add(label3);
                        usernamePanel.add(usernameField);

                        //---- label4 ----
                        label4.setText("                ");
                        usernamePanel.add(label4);
                    }
                    usernamePanelHolder.add(usernamePanel);

                    //======== placeholder2 ========
                    {
                        placeholder2.setLayout(new FlowLayout());
                    }
                    usernamePanelHolder.add(placeholder2);
                }
                panel.add(usernamePanelHolder);

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

                        //---- label5 ----
                        label5.setText("            ");
                        passwordPanel.add(label5);

                        //---- passwordLabel ----
                        passwordLabel.setText("\u5bc6\u7801");
                        passwordPanel.add(passwordLabel);

                        //---- label6 ----
                        label6.setText("    ");
                        passwordPanel.add(label6);
                        passwordPanel.add(passwordField);

                        //---- label7 ----
                        label7.setText("                ");
                        passwordPanel.add(label7);
                    }
                    passwordPanelHolder.add(passwordPanel);

                    //======== placeholder4 ========
                    {
                        placeholder4.setLayout(new FlowLayout());
                    }
                    passwordPanelHolder.add(placeholder4);
                }
                panel.add(passwordPanelHolder);

                //======== buttonPanel ========
                {
                    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));

                    //---- login ----
                    login.setText("\u767b\u5f55");
                    login.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            loginMouseClicked(e);
                        }
                    });
                    buttonPanel.add(login);

                    //---- exit ----
                    exit.setText("\u9000\u51fa");
                    exit.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            exitMouseClicked(e);
                        }
                    });
                    buttonPanel.add(exit);
                }
                panel.add(buttonPanel);
            }
            tabbedPane.addTab("\u767b\u5f55", panel);
        }
        contentPane.add(tabbedPane);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}
