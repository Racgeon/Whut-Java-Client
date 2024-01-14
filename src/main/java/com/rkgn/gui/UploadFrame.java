package com.rkgn.gui;

import com.rkgn.dto.Result;
import com.rkgn.dto.UploadForm;
import com.rkgn.entity.User;
import com.rkgn.utils.Constants;
import com.rkgn.utils.Dialog;
import com.rkgn.utils.SocketUtils;
import com.rkgn.utils.UserHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;


public class UploadFrame extends JFrame {
    private final User user;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTextField idField;
    private JTextField descriptionField;
    private JTextField filenameField;
    private JLabel label10;
    private JButton selectFileButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    private final FileDialog fileDialog;

    public UploadFrame() {
        this.user = UserHolder.getUser();
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = toolkit.getScreenSize().width / 4;
        int height = (int) (toolkit.getScreenSize().height / 2.5);
        setSize(width, height);
        setLocationRelativeTo(null);
        fileDialog = new FileDialog(this, "选择文件", FileDialog.LOAD);
        File file = new File("src/main/resources/data/client");
        fileDialog.setDirectory(file.getAbsolutePath());
    }


    public void clear() {
        idField.setText("");
        descriptionField.setText("");
        filenameField.setText("");
        File file = new File("src/main/resources/data/client");
        fileDialog.setDirectory(file.getAbsolutePath());
    }

    private void okMouseClicked(MouseEvent e) {
        String id = idField.getText();
        String description = descriptionField.getText();
        String filename = filenameField.getText();
        Thread t = new Thread(() -> {
            try {
                uploadFile(id, description, filename);
            } catch (IOException ex) {
                Dialog.error(this, "上传文件错误：" + ex.getMessage());
            }
        });
        t.start();
    }

    private void uploadFile(String id, String description, String filename) throws IOException {
        File clientFile = new File(filename);

        if (!clientFile.exists()) {
            Dialog.error(this, "上传文件错误：档案未找到！");
            return;
        }

        try (Socket socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT)) {
            UploadForm uploadForm = new UploadForm(user.getName(), description, fileDialog.getFile(), clientFile.length());
            SocketUtils.send(socket, "upload\n" + uploadForm);
            SocketUtils.sendBytes(socket, filename);
            Result result = SocketUtils.parseResult(socket);
            if (!result.getSuccess()) {
                Dialog.error(this, "上传文件错误：" + result.getErrorMsg());
                return;
            }
        } catch (IOException ex) {
            Dialog.error(this, "上传文件异常：" + ex.getMessage());
            return;
        }
        Dialog.info(this, "上传文件成功！");
    }

    private void cancelMouseClicked(MouseEvent e) {
        setVisible(false);
    }

    private void selectFileButtonMouseClicked(MouseEvent e) {
        fileDialog.setVisible(true);
        String filename = fileDialog.getDirectory() + fileDialog.getFile();
        filenameField.setText(filename);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        var subtitlePanel = new JPanel();
        var subtitle = new JLabel();
        var idPanelHolder = new JPanel();
        var placeholder1 = new JPanel();
        var idPanel = new JPanel();
        var label1 = new JLabel();
        var usernameLabel = new JLabel();
        var label2 = new JLabel();
        idField = new JTextField();
        var label3 = new JLabel();
        var placeholder2 = new JPanel();
        var descriptionPanelHolder = new JPanel();
        var placeholder3 = new JPanel();
        var descriptionPanel = new JPanel();
        var label4 = new JLabel();
        var passwordLabel = new JLabel();
        var label5 = new JLabel();
        descriptionField = new JTextField();
        var label6 = new JLabel();
        var placeholder4 = new JPanel();
        var filenamePanelHolder = new JPanel();
        var placeholder5 = new JPanel();
        var panel13 = new JPanel();
        var label7 = new JLabel();
        var roleLabel = new JLabel();
        var label8 = new JLabel();
        filenameField = new JTextField();
        label10 = new JLabel();
        selectFileButton = new JButton();
        var label9 = new JLabel();
        var placeholder6 = new JPanel();
        var buttonPanelHolder = new JPanel();
        var placeholder7 = new JPanel();
        var buttonPanel = new JPanel();
        var ok = new JButton();
        var cancel = new JButton();
        var placeholder8 = new JPanel();

        //======== this ========
        setTitle("\u4e0a\u4f20\u6587\u4ef6");
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(5, 0));

        //======== subtitlePanel ========
        {
            subtitlePanel.setLayout(new GridLayout());

            //---- subtitle ----
            subtitle.setText("\u4e0a\u4f20\u6587\u4ef6");
            subtitle.setHorizontalAlignment(SwingConstants.CENTER);
            subtitle.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
            subtitlePanel.add(subtitle);
        }
        contentPane.add(subtitlePanel);

        //======== idPanelHolder ========
        {
            idPanelHolder.setLayout(new GridLayout(3, 0));

            //======== placeholder1 ========
            {
                placeholder1.setLayout(new FlowLayout());
            }
            idPanelHolder.add(placeholder1);

            //======== idPanel ========
            {
                idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));

                //---- label1 ----
                label1.setText("            ");
                idPanel.add(label1);

                //---- usernameLabel ----
                usernameLabel.setText("ID");
                idPanel.add(usernameLabel);

                //---- label2 ----
                label2.setText("            ");
                idPanel.add(label2);
                idPanel.add(idField);

                //---- label3 ----
                label3.setText("                ");
                idPanel.add(label3);
            }
            idPanelHolder.add(idPanel);

            //======== placeholder2 ========
            {
                placeholder2.setLayout(new FlowLayout());
            }
            idPanelHolder.add(placeholder2);
        }
        contentPane.add(idPanelHolder);

        //======== descriptionPanelHolder ========
        {
            descriptionPanelHolder.setLayout(new GridLayout(3, 0));

            //======== placeholder3 ========
            {
                placeholder3.setLayout(new FlowLayout());
            }
            descriptionPanelHolder.add(placeholder3);

            //======== descriptionPanel ========
            {
                descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.X_AXIS));

                //---- label4 ----
                label4.setText("            ");
                descriptionPanel.add(label4);

                //---- passwordLabel ----
                passwordLabel.setText("\u63cf\u8ff0");
                descriptionPanel.add(passwordLabel);

                //---- label5 ----
                label5.setText("         ");
                descriptionPanel.add(label5);
                descriptionPanel.add(descriptionField);

                //---- label6 ----
                label6.setText("                ");
                descriptionPanel.add(label6);
            }
            descriptionPanelHolder.add(descriptionPanel);

            //======== placeholder4 ========
            {
                placeholder4.setLayout(new FlowLayout());
            }
            descriptionPanelHolder.add(placeholder4);
        }
        contentPane.add(descriptionPanelHolder);

        //======== filenamePanelHolder ========
        {
            filenamePanelHolder.setLayout(new GridLayout(3, 0));

            //======== placeholder5 ========
            {
                placeholder5.setLayout(new FlowLayout());
            }
            filenamePanelHolder.add(placeholder5);

            //======== panel13 ========
            {
                panel13.setLayout(new BoxLayout(panel13, BoxLayout.X_AXIS));

                //---- label7 ----
                label7.setText("            ");
                panel13.add(label7);

                //---- roleLabel ----
                roleLabel.setText("\u6587\u4ef6\u540d");
                panel13.add(roleLabel);

                //---- label8 ----
                label8.setText("      ");
                panel13.add(label8);
                panel13.add(filenameField);

                //---- label10 ----
                label10.setText("    ");
                panel13.add(label10);

                //---- selectFileButton ----
                selectFileButton.setText("\u9009\u62e9");
                selectFileButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        selectFileButtonMouseClicked(e);
                    }
                });
                panel13.add(selectFileButton);

                //---- label9 ----
                label9.setText("                ");
                panel13.add(label9);
            }
            filenamePanelHolder.add(panel13);

            //======== placeholder6 ========
            {
                placeholder6.setLayout(new FlowLayout());
            }
            filenamePanelHolder.add(placeholder6);
        }
        contentPane.add(filenamePanelHolder);

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
