package com.yao.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by root on 15-3-20.
 */
public class EchoHandler implements Runnable {
    private Socket socket;
    public EchoHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream outputStream=null;
        InputStream inputStream=null;
        try {
            outputStream=socket.getOutputStream();
            inputStream=socket.getInputStream();
            byte[]bytes=new byte[1024];
            while (inputStream.read(bytes)>0){
                outputStream.write(bytes);
                bytes=new byte[1024];
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
