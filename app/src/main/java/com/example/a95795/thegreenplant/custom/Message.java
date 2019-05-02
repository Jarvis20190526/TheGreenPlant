package com.example.a95795.thegreenplant.custom;

/**
 * Created by 95795 on 2019-04-28.
 */

public class Message {
    private String Name;
    private String Id;
    private int thisId;
    private String Job_Number;
    private String Phone;

    @Override
    public String toString() {
        return "Message{" +
                "Name='" + Name + '\'' +
                ", Id='" + Id + '\'' +
                ", thisId=" + thisId +
                ", Job_Number='" + Job_Number + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getThisId() {
        return thisId;
    }

    public void setThisId(int thisId) {
        this.thisId = thisId;
    }

    public String getJob_Number() {
        return Job_Number;
    }

    public void setJob_Number(String job_Number) {
        Job_Number = job_Number;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Message(String name, String id, int thisId, String job_Number, String phone) {
        Name = name;
        Id = id;
        this.thisId = thisId;
        Job_Number = job_Number;
        Phone = phone;
    }
}
