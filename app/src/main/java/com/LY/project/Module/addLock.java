package com.LY.project.Module;

/**
 * Created by YX on 2018/5/2.
 */

public class addLock {

    /**
     * code : 1
     *lockid:513
     * error : 账号不存在
     */

    private int code;
    private String error;
    private String lockid;

    public String getLockid() {
        return lockid;
    }

    public void setLockid(String lockid) {
        this.lockid = lockid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
