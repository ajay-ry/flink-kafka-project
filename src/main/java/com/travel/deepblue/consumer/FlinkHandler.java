package com.travel.deepblue.consumer;


import com.travel.deepblue.adexchange.MobileUser;
import com.travel.deepblue.config.YamlConfiguration;
import com.travel.deepblue.utilities.ActionHandler;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.io.Serializable;
import java.util.Properties;

public class FlinkHandler implements Serializable {


    private Properties properties;
    private transient FlinkKafkaConsumer010 flinkKafkaConsumer011;
    private transient Jedis jedis;
    private transient JSONParser jsonParser;
    private transient String parallilism;
    private ActionHandler actionHandler;

    public FlinkHandler(Properties properties){
        this.properties = properties;
        Properties consumer_properties = new Properties();
        consumer_properties.setProperty("bootstrap.servers",properties.getProperty("consumer.servers"));
        String consumer_topic = properties.getProperty("consumer.topic");
        consumer_properties.setProperty("group.id", properties.getProperty("group.id"));
        consumer_properties.setProperty("auto.offset.reset",properties.getProperty("auto.offset.reset"));
        this.flinkKafkaConsumer011 = new FlinkKafkaConsumer010<>(consumer_topic,new SimpleStringSchema(),consumer_properties);

        this.parallilism=properties.getProperty("parallelism");
    }
    public void execute() throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(Integer.parseInt(parallilism));
        env.addSource(flinkKafkaConsumer011).map(new RichMapFunction<String, String>() {

            @Override
            public void open(org.apache.flink.configuration.Configuration params){
                GenericObjectPoolConfig gopo = new GenericObjectPoolConfig();
                JedisPool jedis_pool = new JedisPool(gopo,properties.getProperty("redis.host"),Integer.parseInt(properties.getProperty("redis.port")),Integer.parseInt(properties.getProperty("redis.timeout")));
                jedis = jedis_pool.getResource();
                actionHandler = new ActionHandler(jedis);
                jsonParser = new JSONParser();
            }

            @Override
            public String map(String s) throws Exception {
                handle_record(s,jedis);
                return null;
            }
        });
        env.execute();
    }


    private void handle_record(String s, Jedis jedis) throws ParseException, java.text.ParseException, InterruptedException {
        JSONObject jobj = (JSONObject) jsonParser.parse(s);
        String device_id = jobj.get("device_id").toString();
        String location = jobj.get("location").toString();
        String date_time = jobj.get("time_stamp").toString();
        String city = jobj.get("city").toString();

        MobileUser mu = new MobileUser(date_time,device_id,location,city);
        Thread.sleep(1000);
        actionHandler.perform_action(mu);

    }

    public static void main(String[] args) throws Exception {

        if(args.length!=1){
            System.out.println("Config file required");
            System.exit(0);
        }
        YamlConfiguration yc = new YamlConfiguration(args[0]);


        Properties properties = new Properties();
        properties.setProperty("consumer.servers",yc.getValue("consumer.servers"));
        properties.setProperty("consumer.topic",yc.getValue("consumer.topic"));
        properties.setProperty("group.id", yc.getValue("group.id"));
        properties.setProperty("auto.offset.reset",yc.getValue("auto.offset.reset") );
        properties.setProperty("redis.host",yc.getValue("redis.host"));
        properties.setProperty("redis.port",yc.getValue("redis.port"));
        properties.setProperty("redis.timeout",yc.getValue("redis.timeout"));
        properties.setProperty("parallelism",yc.getValue("parallelism"));

        /*Properties properties = new Properties();
        properties.setProperty("consumer.servers",yc.getValue("localhost:9092"));
        properties.setProperty("consumer.topic",yc.getValue("adexchange"));
        properties.setProperty("group.id", yc.getValue("test1"));
        //properties.setProperty("auto.offset.reset", yc.getValue("auto.offset.reset"));
        properties.setProperty("redis.host",yc.getValue("localhost"));
        properties.setProperty("redis.port",yc.getValue("6379"));
        properties.setProperty("redis.timeout",yc.getValue("6000"));
        properties.setProperty("parallelism",yc.getValue("4"));*/


        FlinkHandler flinkHandler = new FlinkHandler(properties);
        flinkHandler.execute();
    }
}
