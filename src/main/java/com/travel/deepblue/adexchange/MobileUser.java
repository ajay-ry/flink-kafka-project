package com.travel.deepblue.adexchange;

import org.json.simple.JSONObject;

public class MobileUser {


    private String date_time;
    private String device_id;
    private String location;
    private String city;

    public MobileUser(String date_time, String device_id, String location, String city){
        this.date_time=date_time;
        this.device_id=device_id;
        this.location=location;
        this.city = city;
    }


    public String getTimestamp(){
        return this.date_time;
    }

    public String getDeviceId(){
        return this.device_id;
    }

    public String getLocation(){
        return this.location;
    }

    public String getCity(){
        return this.city;
    }

    public String toString(){
        JSONObject jobj = new JSONObject();
        jobj.put("device_id",this.device_id);
        jobj.put("location",this.location);
        jobj.put("time_stamp",this.date_time);
        jobj.put("city",this.city);
        return jobj.toString();
    }
}
