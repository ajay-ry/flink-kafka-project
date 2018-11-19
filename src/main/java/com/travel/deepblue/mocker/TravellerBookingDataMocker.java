package com.travel.deepblue.mocker;

import com.travel.deepblue.connection.RedisConnection;
import com.travel.deepblue.connection.RedisConnection;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TravellerBookingDataMocker {
    Jedis redis;

    public TravellerBookingDataMocker(Jedis redis){
        this.redis = redis;
    }
    public void load_data_to_redis(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str = null;
        while((str=br.readLine())!=null){
            String str_arr[] = str.split("\\t");
            //key = deviceid
            //value = lat,lon|city|timestamp
            redis.set(str_arr[2],str_arr[4]+"|"+str_arr[6]+"|"+str_arr[7]);
        }
    }

    public static void main(String[] args) throws IOException {
        if(args.length != 1){
            System.out.println("Invalid arguments. Data file required with the following format required:");
            System.out.println("service_type  device device_encoded        source lat,lon     dest lat,lon       source city       dest city       timestamp");
            System.exit(0);
        }

        String file = args[0];
        RedisConnection rc = new RedisConnection();
        Jedis redis = rc.getRedis();
        TravellerBookingDataMocker travellerBookingDataMocker = new TravellerBookingDataMocker(redis);
        travellerBookingDataMocker.load_data_to_redis(file);
    }

}
