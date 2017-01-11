package com.test.tcp.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/8/26.
 */
public class Server {
    public static final int PORT = 20006;
    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
            Socket client = null;
            System.out.println("Server is listening on " + PORT);
            while (true) {
                client = server.accept();
                //单线程
//                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                System.out.println(in.readLine());
//                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//                out.println("hello client");
//                in.close();
//                out.close();

                //多线程
//                new Thread(new Handler(client)).start();

                //线程池
                HandlerExecutor executor = new HandlerExecutor(10, 100);
                executor.execute(new Handler(client));
//                ExecutorService executor = Executors.newScheduledThreadPool(10);
//                executor.submit(new Handler(client));
            }
        } finally {
            if (server != null) {
                System.out.println("server is closed!");
                server.close();
            }
        }
    }
}
