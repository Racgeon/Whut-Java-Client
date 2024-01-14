package com.rkgn.utils;

import cn.hutool.json.JSONUtil;
import com.rkgn.dto.Result;

import java.io.*;
import java.net.Socket;

public class SocketUtils {
    public static Result parseResult(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String response;
        response = br.readLine();
        return JSONUtil.toBean(response, Result.class);
    }

    public static void send(Socket socket, String data) throws IOException {
        OutputStream out = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(out);
        pw.println(data);
        pw.flush();
    }

    public static void sendBytes(Socket socket, String filename) throws IOException {
        OutputStream out = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(filename));
        br.transferTo(bos);
        bos.flush();
    }
}
