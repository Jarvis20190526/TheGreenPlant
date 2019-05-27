package com.example.a95795.thegreenplant.table;


/**
 * Created by huang on 2017/11/1.
 */
public class EnvironmentInfo {
    private Integer emId;
    private String emName;//车间名
    private String vel;//收集频率
    private int tmp;//温度
    private int hum;//相对湿度
    private int pm;//pm2.5
    private Boolean isTmp;//温度达标情况
    private Boolean isHum;//相对湿度达标情况
    private Boolean isPM;//pm2.5达标情况
    private String time;
    private Boolean isEvent;//综合指数达标情况

    private ChildData childData;
    private boolean isCheck;
    private String url;

    public EnvironmentInfo(String emName,String time, int tmp, int hum, int pm, Boolean isTmp, Boolean isHum, Boolean isPM,Boolean isEvent) {
        this.emName = emName;
        this.time = time;
        this.tmp = tmp;
        this.hum = hum;
        this.pm = pm;
        this.isTmp = isTmp;
        this.isHum = isHum;
        this.isPM = isPM;
        this.time = time;
        this.isEvent = isEvent;
    }

    public Integer getEmId() {
        return emId;
    }

    public void setEmId(Integer emId) {
        this.emId = emId;
    }

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public String getVel() {
        return vel;
    }

    public void setVel(String vel) {
        this.vel = vel;
    }

    public int getTmp() {
        return tmp;
    }

    public void setTmp(int tmp) {
        this.tmp = tmp;
    }

    public int getHum() {
        return hum;
    }

    public void setHum(int hum) {
        this.hum = hum;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public Boolean getIsTmp() {
        return isTmp;
    }

    public void setIsTmp(Boolean isTmp) {
        this.isTmp = isTmp;
    }

    public Boolean getIsHum() {
        return isHum;
    }

    public void setIsHum(Boolean isHum) {
        this.isHum = isHum;
    }

    public Boolean getIsPM() {
        return isPM;
    }

    public void setIsPM(Boolean isPM) {
        this.isPM = isPM;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(Boolean isEvent) {
        this.isEvent = isEvent;
    }

    public ChildData getChildData() {
        return childData;
    }

    public void setChildData(ChildData childData) {
        this.childData = childData;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

