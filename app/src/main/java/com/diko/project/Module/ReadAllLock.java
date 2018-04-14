package com.diko.project.Module;

/**
 * Created by jie on 2018/4/14.
 */

public class ReadAllLock {

    /**
     * id : 门锁id
     * key : 秘钥
     * bluetooth : 蓝牙地址
     * owerid : 所有者id
     * name : 锁名
     * starttime : 开始时间
     * endtime : 结束时间（0表示永久拥有）
     * motherid : 母锁ID
     * power : 权限
     * givemessage : 授权信息
     * code : 1
     */

    private String id;
    private String key;
    private String bluetooth;
    private String owerid;
    private String name;
    private String starttime;
    private String endtime;
    private String motherid;
    private String power;
    private String givemessage;
    private int code;

    public ReadAllLock() {

    }

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
