package com.rkgn.utils;

import com.rkgn.gui.LoginFrame;

import java.util.concurrent.ConcurrentHashMap;

public class Frames {
    public static ConcurrentHashMap<String, LoginFrame> loginFrames = new ConcurrentHashMap<>();
}
