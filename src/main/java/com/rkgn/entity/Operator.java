package com.rkgn.entity;

import com.rkgn.gui.OperationFrame;
import com.rkgn.utils.Role;

public class Operator extends User {
    public Operator(String name, String password) {
        super(name, password, Role.OPERATOR);
    }

    @Override
    public void showMenu() {
        OperationFrame operationFrame = new OperationFrame();
        operationFrame.setVisible(true);
    }
}
