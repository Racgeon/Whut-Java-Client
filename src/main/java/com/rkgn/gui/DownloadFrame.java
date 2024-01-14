package com.rkgn.gui;

import cn.hutool.json.JSONArray;
import com.rkgn.dto.DownloadForm;
import com.rkgn.dto.Result;
import com.rkgn.entity.Doc;
import com.rkgn.utils.Constants;
import com.rkgn.utils.Dialog;
import com.rkgn.utils.JSONObjectUtils;
import com.rkgn.utils.SocketUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Vector;


public class DownloadFrame extends JFrame {
    private List<Doc> docList;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTable docTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public DownloadFrame() {
        initComponents();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = (int) (toolkit.getScreenSize().width / 2.5);
        int height = toolkit.getScreenSize().height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);
        refresh();
    }

    public void refresh() {
        try (Socket socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT)) {
            SocketUtils.send(socket, "get all docs\n");
            Result result = SocketUtils.parseResult(socket);
            if (!result.getSuccess()) {
                Dialog.error(this, "获取全部档案错误：" + result.getErrorMsg());
                return;
            }

            docList = ((JSONArray) result.getData()).stream().map(JSONObjectUtils::parseDoc).toList();
        } catch (IOException ex) {
            Dialog.error(this, "获取全部档案错误：" + ex.getMessage());
        }
        Vector<Object> columnNames = new Vector<>();
        columnNames.add("文件id");
        columnNames.add("创建者");
        columnNames.add("创建时间");
        columnNames.add("描述");
        columnNames.add("文件名");
        TableModel tableModel = new DefaultTableModel(columnNames, docList.size());
        for (int i = 0; i < docList.size(); i++) {
            Doc doc = docList.get(i);
            tableModel.setValueAt(doc.getId(), i, 0);
            tableModel.setValueAt(doc.getCreator(), i, 1);
            tableModel.setValueAt(doc.getTimestamp(), i, 2);
            tableModel.setValueAt(doc.getDescription(), i, 3);
            tableModel.setValueAt(doc.getFilename(), i, 4);
        }
        docTable.setModel(tableModel);
    }

    private void okButtonMouseClicked(MouseEvent e) {
        int[] selectedRows = docTable.getSelectedRows();
        for (int selectedRow : selectedRows) {
            Doc doc = docList.get(selectedRow);
            String filename = doc.getFilename();
            Thread t = new Thread(() -> {
                try {
                    downloadFile(filename);
                } catch (IOException ex) {
                    Dialog.error(this, "下载文件异常：" + ex.getMessage());
                }
            });
            t.start();
        }
    }

    public void downloadFile(String filename) throws IOException {
        try (Socket socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT)) {
            DownloadForm downloadForm = new DownloadForm(filename);
            SocketUtils.send(socket, "download\n" + downloadForm);
            InputStream is = socket.getInputStream();
            BufferedReader bis = new BufferedReader(new InputStreamReader(is));
            String bytes = bis.readLine();
            byte[] read = socket.getInputStream().readNBytes(Integer.parseInt(bytes));
            try (FileOutputStream fos = new FileOutputStream("src/main/resources/data/client/" + filename)) {
                fos.write(read);
            }
        } catch (IOException ex) {
            Dialog.error(this, "下载文件异常：" + ex.getMessage());
            return;
        }
        Dialog.info(this, "下载成功！");
    }

    private void cancelButtonMouseClicked(MouseEvent e) {
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        var subtitlePanel = new JPanel();
        var subtitle = new JLabel();
        var scrollPane = new JScrollPane();
        docTable = new JTable();
        var buttonPanel = new JPanel();
        var okButton = new JButton();
        var cancelButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== subtitlePanel ========
        {
            subtitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));

            //---- subtitle ----
            subtitle.setText("\u4e0b\u8f7d\u6587\u4ef6");
            subtitle.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
            subtitlePanel.add(subtitle);
        }
        contentPane.add(subtitlePanel);

        //======== scrollPane ========
        {
            scrollPane.setViewportView(docTable);
        }
        contentPane.add(scrollPane);

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
        contentPane.add(buttonPanel);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}
