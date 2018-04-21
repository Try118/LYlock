package com.LY.project.Manager;

/**
 * =====作者=====
 * lcj
 * =====时间=====
 * 2018/4/14.
 */

public class InterfaceManger {

    public interface OnRequestListener {
        void onSuccess(Object success);

        void onError(String error);

        void onComplete();
    }
}
