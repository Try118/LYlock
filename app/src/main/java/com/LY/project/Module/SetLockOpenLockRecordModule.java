package com.LY.project.Module;

/**
 * Created by YX_PC on 2018/4/19.
 */

public class SetLockOpenLockRecordModule {

    /**
     * account : 13823839265
     * lockid : 234
     * type : 1
     * name : 13823839265
     * time : 1516506463
     * detial :
     */

    public String account;
    public String lockid;
    public String type;
    public String name;
    public String time;
    public String detial;

    public SetLockOpenLockRecordModule(String time) {
        this.time = time;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLockid() {
        return lockid;
    }

    public void setLockid(String lockid) {
        this.lockid = lockid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetial() {
        return detial;
    }

    public void setDetial(String detial) {
        this.detial = detial;
    }
}
