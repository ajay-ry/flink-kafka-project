package com.travel.deepblue.utilities;

import com.travel.deepblue.adexchange.MobileUser;
import redis.clients.jedis.Jedis;

import java.text.ParseException;

public class ActionHandler {

    private Jedis jedis;
    private final int THRESHOLD_DISTANCE = 500;
    public ActionHandler(Jedis jedis){
        this.jedis = jedis;
    }

    public void perform_action(MobileUser mu) throws ParseException {
        String mu_device_id = mu.getDeviceId();
        String mu_timestamp = mu.getTimestamp();
        String mu_city = mu.getCity();
        String mu_location = mu.getLocation();

        String travel_data = jedis.get(mu_device_id);
        if(travel_data != null){
            String travel_data_arr[] = travel_data.split("\\|");
            String eu_cur_location = travel_data_arr[0];
            String eu_city = travel_data_arr[1];
            String eu_timestamp = travel_data_arr[2];

            if(com.travel.deepblue.utilities.HaversineDistanceComputer.calculate_distance(mu_location,eu_cur_location)>THRESHOLD_DISTANCE && DateUtility.isGreater(mu_timestamp,eu_timestamp)
                    && !mu_city.equals(eu_city)){
                System.out.println(mu.toString()+"|"+new com.travel.deepblue.travel.TravelUser(mu_device_id,eu_cur_location,eu_timestamp,eu_city).toString()+"\n\n");

            }

        }


    }

}
