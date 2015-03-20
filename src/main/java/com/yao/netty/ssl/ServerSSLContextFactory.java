package com.yao.netty.ssl;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by root on 15-3-16.
 */
public class ServerSSLContextFactory {

    private static String SERVER_KEY_STORE = "keystore/sChat.jks";
    private static String SERVER_KEY_STORE_PASSWORD = "sNetty";
    private static final String PROTOCOL = "SSL";
    private static SSLContext SERVER_CONTEXT;

    public static SSLContext getSSLContext() {
        if (SERVER_CONTEXT == null) {
            createSSLContext();
        }
        return SERVER_CONTEXT;
    }

    private static void createSSLContext() {
        try {
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(SERVER_KEY_STORE);
            ks.load(is, SERVER_KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
            SERVER_CONTEXT = SSLContext.getInstance(PROTOCOL);
            SERVER_CONTEXT.init(kmf.getKeyManagers(), null, null);
            System.out.println("初始化SSL上下文成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String []args){

        ServerSSLContextFactory.getSSLContext();
    }
}
