package com.rkgn.entity;

import com.rkgn.gui.OperationFrame;
import com.rkgn.utils.Role;

public class Administrator extends User {
    public Administrator(String name, String password) {
        super(name, password, Role.ADMINISTRATOR);
    }

    @Override
    public void showMenu() {
        OperationFrame operationFrame = new OperationFrame();
        operationFrame.setVisible(true);
    }
}
