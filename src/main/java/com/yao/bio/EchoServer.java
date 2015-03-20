package com.yao.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 15-3-20.
 */
public class EchoServer {

    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
           serverSocket=new ServerSocket(port);
          while (true){
             Socket socket= serverSocket.accept();
             new Thread(new EchoHandler(socket)).start();
          }
    }
    public static void main(String[]args) throws IOException {
        EchoServer echoServer=new EchoServer();
        echoServer.start(9999);
    }
}
