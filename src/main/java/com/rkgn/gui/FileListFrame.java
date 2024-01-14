package com.rkgn.gui;

import cn.hutool.json.JSONArray;
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
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Vector;


public class FileListFrame extends JFrame {
    private List<Doc> docList;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JTable docTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public FileListFrame() {
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
                com.rkgn.utils.Dialog.error(this, "获取全部文档错误：" + result.getErrorMsg());
                return;
            }

            docList = ((JSONArray) result.getData()).stream().map(JSONObjectUtils::parseDoc).toList();
        } catch (IOException ex) {
            Dialog.error(this, "获取全部文档错误：" + ex.getMessage());
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        var subtitlePanel = new JPanel();
        var subtitle = new JLabel();
        var scrollPane = new JScrollPane();
        docTable = new JTable();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        //======== subtitlePanel ========
        {
            subtitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));

            //---- subtitle ----
            subtitle.setText("\u6587\u4ef6\u5217\u8868");
            subtitle.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
            subtitlePanel.add(subtitle);
        }
        contentPane.add(subtitlePanel);

        //======== scrollPane ========
        {
            scrollPane.setViewportView(docTable);
        }
        contentPane.add(scrollPane);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}
