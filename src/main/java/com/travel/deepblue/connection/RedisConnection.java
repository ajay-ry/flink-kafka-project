package com.travel.deepblue.connection;

import redis.clients.jedis.Jedis;

public class RedisConnection {
    Jedis redis;

    public RedisConnection(){
        redis = new Jedis("127.0.0.1",6379);
    }

    public RedisConnection(String host, int port){
        redis = new Jedis(host,port);
    }

    public RedisConnection(int port){
        redis = new Jedis("127.0.0.1",port);
    }

    public Jedis getRedis(){
        return this.redis;
    }

    public static void main(String[] args) {
        RedisConnection r = new RedisConnection();
        r.getRedis().sadd("test","1");
    }

}
