package com.yao.nio;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-11.
 */
public class TestSelector {
    private static final  int MAXSIZE=5000;
    public static final  void main(String[]args) {
         try {
             Selector[]sels=new Selector[MAXSIZE];
             for (int i=0;i<MAXSIZE;++i){
                 sels[i]=Selector.open();
             }
             TimeUnit.MINUTES.sleep(10);
         }catch (Exception e){
             e.printStackTrace();
         }
    }
}
