package com.rkgn.entity;

import com.rkgn.gui.OperationFrame;
import com.rkgn.utils.Role;

public class Browser extends User {

    public Browser(String name, String password) {
        super(name, password, Role.BROWSER);
    }

    @Override
    public void showMenu() {
        OperationFrame operationFrame = new OperationFrame();
        operationFrame.setVisible(true);
    }
}
