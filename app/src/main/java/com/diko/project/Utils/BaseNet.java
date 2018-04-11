package com.diko.project.Utils;

/**
 * Created by YX_PC on 2018/4/11.
 */

public class BaseNet implements Net,Runnable {
    protected NetCallback callback= new NetCallback() {
        @Override
        public void execute(String result) {

        }

        @Override
        public void error(String result) {

        }
    };
    @Override
    public void setCallback(NetCallback callback) {

    }

    @Override
    public void run() {

    }
}
