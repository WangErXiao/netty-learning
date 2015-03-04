package com.yao.netty.proxy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 15-3-4.
 * Proxy server
 */

public class Server {
    public static void main(String[]args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(9999);
        while (true){
            Socket socket= serverSocket.accept();
            OutputStream outputStream= socket.getOutputStream();
            outputStream.write("hello world\r\n".getBytes());
            outputStream.flush();
            outputStream.close();
            socket.close();
        }
    }
}
