package com.rkgn.gui;

import com.rkgn.entity.User;
import com.rkgn.utils.Dialog;
import com.rkgn.utils.Frames;
import com.rkgn.utils.Role;
import com.rkgn.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OperationFrame extends JFrame {
    private final User user;
    private final AddFrame addFrame = new AddFrame();
    private final DeleteFrame deleteFrame = new DeleteFrame();
    private final UpdateFrame updateFrame = new UpdateFrame();
    private final FileListFrame fileListFrame = new FileListFrame();
    private final UploadFrame uploadFrame;
    private final DownloadFrame downloadFrame = new DownloadFrame();
    private final ChangePasswordFrame changePasswordFrame;

    public OperationFrame() {
        this.user = UserHolder.getUser();
        uploadFrame = new UploadFrame();
        changePasswordFrame = new ChangePasswordFrame();
        String role = user.getRole();
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setSize(toolkit.getScreenSize().width / 2, toolkit.getScreenSize().height / 2);
        setLocationRelativeTo(null);

        subtitle.setText(Role.translateMap.get(role) + subtitle.getText());

        switch (role) {
            case Role.ADMINISTRATOR -> upload.setEnabled(false);
            case Role.OPERATOR -> {
                add.setEnabled(false);
                delete.setEnabled(false);
                update.setEnabled(false);
            }
            case Role.BROWSER -> {
                add.setEnabled(false);
                delete.setEnabled(false);
                update.setEnabled(false);
                upload.setEnabled(false);
            }
        }
    }

    private void addMousePressed(MouseEvent e) {
        if (add.isEnabled()) {
            addFrame.clear();
            addFrame.setVisible(true);
            addFrame.toFront();
        }
    }

    private void deleteMousePressed(MouseEvent e) {
        if (delete.isEnabled()) {
            deleteFrame.refresh();
            deleteFrame.setVisible(true);
            deleteFrame.toFront();
        }
    }

    private void updateMousePressed(MouseEvent e) {
        if (update.isEnabled()) {
            updateFrame.clear();
            updateFrame.setVisible(true);
            updateFrame.toFront();
        }
    }

    private void listMousePressed(MouseEvent e) {
        if (list.isEnabled()) {
            fileListFrame.setVisible(true);
            fileListFrame.refresh();
            fileListFrame.toFront();
        }
    }

    private void uploadMousePressed(MouseEvent e) {
        if (upload.isEnabled()) {
            uploadFrame.clear();
            uploadFrame.setVisible(true);
            uploadFrame.toFront();
        }
    }

    private void downloadMousePressed(MouseEvent e) {
        if (download.isEnabled()) {
            downloadFrame.refresh();
            downloadFrame.setVisible(true);
            downloadFrame.toFront();
        }
    }

    private void changePasswordMousePressed(MouseEvent e) {
        if (changePassword.isEnabled()) {
            changePasswordFrame.clear();
            changePasswordFrame.setVisible(true);
            changePasswordFrame.toFront();
        }
    }

    private void logoutMousePressed(MouseEvent e) {
        int confirm = Dialog.confirm(this, "确认退出登录？");
        if (confirm == JOptionPane.YES_OPTION) {
            LoginFrame loginFrame = Frames.loginFrames.get(user.getName());
            this.setVisible(false);
            loginFrame.setVisible(true);
            UserHolder.removeUser();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        var menuBar = new JMenuBar();
        var userMenu = new JMenu();
        add = new JMenuItem();
        delete = new JMenuItem();
        update = new JMenuItem();
        var fileMenu = new JMenu();
        list = new JMenuItem();
        upload = new JMenuItem();
        download = new JMenuItem();
        var selfMenu = new JMenu();
        changePassword = new JMenuItem();
        logout = new JMenuItem();
        subtitle = new JLabel();

        //======== this ========
        setTitle("\u6863\u6848\u7ba1\u7406\u7cfb\u7edf");
        var contentPane = getContentPane();
        contentPane.setLayout(new CardLayout());

        //======== menuBar ========
        {
            menuBar.setPreferredSize(new Dimension(120, 24));

            //======== userMenu ========
            {
                userMenu.setText("\u7528\u6237");

                //---- add ----
                add.setText("\u65b0\u589e\u7528\u6237");
                add.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        addMousePressed(e);
                    }
                });
                userMenu.add(add);

                //---- delete ----
                delete.setText("\u5220\u9664\u7528\u6237");
                delete.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        deleteMousePressed(e);
                    }
                });
                userMenu.add(delete);

                //---- update ----
                update.setText("\u4fee\u6539\u7528\u6237");
                update.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        updateMousePressed(e);
                    }
                });
                userMenu.add(update);
            }
            menuBar.add(userMenu);

            //======== fileMenu ========
            {
                fileMenu.setText("\u6587\u4ef6");

                //---- list ----
                list.setText("\u6587\u4ef6\u5217\u8868");
                list.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        listMousePressed(e);
                    }
                });
                fileMenu.add(list);

                //---- upload ----
                upload.setText("\u4e0a\u4f20\u6587\u4ef6");
                upload.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        uploadMousePressed(e);
                    }
                });
                fileMenu.add(upload);

                //---- download ----
                download.setText("\u4e0b\u8f7d\u6587\u4ef6");
                download.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        downloadMousePressed(e);
                    }
                });
                fileMenu.add(download);
            }
            menuBar.add(fileMenu);

            //======== selfMenu ========
            {
                selfMenu.setText("\u4e2a\u4eba");

                //---- changePassword ----
                changePassword.setText("\u4fee\u6539\u5bc6\u7801");
                changePassword.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        changePasswordMousePressed(e);
                    }
                });
                selfMenu.add(changePassword);

                //---- logout ----
                logout.setText("\u9000\u51fa\u767b\u5f55");
                logout.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        logoutMousePressed(e);
                    }
                });
                selfMenu.add(logout);
            }
            menuBar.add(selfMenu);
        }
        setJMenuBar(menuBar);

        //---- subtitle ----
        subtitle.setText("\u6863\u6848\u7ba1\u7406\u7cfb\u7edf");
        subtitle.setFont(new Font("\u6977\u4f53", Font.BOLD, 20));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(subtitle, "card1");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuItem add;
    private JMenuItem delete;
    private JMenuItem update;
    private JMenuItem list;
    private JMenuItem upload;
    private JMenuItem download;
    private JMenuItem changePassword;
    private JMenuItem logout;
    private JLabel subtitle;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
