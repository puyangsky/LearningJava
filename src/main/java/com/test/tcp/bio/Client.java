package com.test.tcp.bio;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016/8/26.
 */
public class Client {
    public static final int PORT = 20006;
    public static final String ADDR = "127.0.0.1";
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintStream out = null;
        try {
            socket = new Socket(ADDR, PORT);
            socket.setSoTimeout(2000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            out.println("1:Hello server!");
            out.println("2:Hello server!");
            out.println("bye");
            String line = in.readLine();
            System.out.println("Receive from server: " + line);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            System.out.println("server time out, exit!");
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
