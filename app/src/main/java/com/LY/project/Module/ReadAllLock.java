package com.LY.project.Module;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2018/4/14.
 */

public class ReadAllLock {

    /**
     * id : 238
     * key : 00017786
     * bluetooth : 96:B3:85:4B:54:C4
     * owerid : 5
     * name : WL96B3854B54C4
     * lockname : 61402
     * username :
     * starttime : 1516506434
     * endtime : 0
     * motherid : 234
     * power : 1
     * givemessage :
     * state : 1
     * address : 61402
     */
    public static List<ReadAllLock> arrayDemoFromData(String str) {

        Type listType = new TypeToken<ArrayList<ReadAllLock>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
    private String id;
    private String key;
    private String bluetooth;
    private String owerid;
    private String name;
    private String lockname;
    private String username;
    private String starttime;
    private String endtime;
    private String motherid;
    private String power;
    private String givemessage;
    private String state;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getOwerid() {
        return owerid;
    }

    public void setOwerid(String owerid) {
        this.owerid = owerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLockname() {
        return lockname;
    }

    public void setLockname(String lockname) {
        this.lockname = lockname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getMotherid() {
        return motherid;
    }

    public void setMotherid(String motherid) {
        this.motherid = motherid;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getGivemessage() {
        return givemessage;
    }

    public void setGivemessage(String givemessage) {
        this.givemessage = givemessage;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
