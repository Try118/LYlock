package com.LY.project.Module;

/**
 * Created by jie on 2018/4/11.
 */

public class OpenRecord {
    public OpenRecord(String time, String name, String type, String detail) {
        this.time = time;
        this.name = name;
        this.type = type;
        this.detail = detail;
    }

    public String time;
    public String name;
    public String type;
    public String detail;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
