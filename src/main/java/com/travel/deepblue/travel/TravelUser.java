package com.travel.deepblue.travel;


import org.json.simple.JSONObject;

public class TravelUser {

    private String device_id;
    private String cur_location;
    private String time_stamp;
    private String city;

    public TravelUser(String device_id, String cur_location, String time_stamp, String city){
        this.device_id = device_id;
        this.cur_location = cur_location;
        this.time_stamp = time_stamp;
        this.city = city;
    }

    public String getDeviceId(){
        return this.device_id;
    }

    public String getCurLocation(){
        return this.cur_location;
    }

    public String getTimeStamp(){
        return this.time_stamp;
    }

    public String getCity(){
        return this.city;
    }

    public String toString(){
        JSONObject jobj = new JSONObject();
        jobj.put("device_id",this.device_id);
        jobj.put("location",this.cur_location);
        jobj.put("time_stamp",this.time_stamp);
        jobj.put("city",this.city);
        return jobj.toString();
    }
}
