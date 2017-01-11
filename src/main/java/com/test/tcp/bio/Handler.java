package com.test.tcp.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2016/8/26.
 */
public class Handler implements Runnable {
    private Socket socket;
    public Handler (Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while (true) {
                String s = in.readLine();
                if (s == null || "bye".equals(s)) {
                    break;
                }
                System.out.println(s);
            }
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println("hello client");
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        BufferedReader in = null;
//        PrintWriter out = null;
//        try {
//            in = new BufferedReader(new InputStreamReader(this.tcp.getInputStream()));
//            out = new PrintWriter(this.tcp.getOutputStream(), true);
//            String line;
//            StringBuilder sb = new StringBuilder();
//            while ((line = in.readLine()) != null) {
//                sb.append(line);
//            }
//            System.out.printf("Receive from client: %s : %s\n", this.tcp.getRemoteSocketAddress().toString(), sb.toString());
//            out.println("Hello Client!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (out != null) {
//                out.close();
//            }
//            if (this.tcp != null) {
//                try {
//                    this.tcp.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
