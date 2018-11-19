package com.travel.deepblue.mocker;


import com.travel.deepblue.adexchange.ExchangeData;
import com.travel.deepblue.adexchange.MobileUser;
import org.apache.kafka.clients.producer.*;

import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MobileExchangeMokingStream {

    private static  String TOPIC = "adexchange_3";
    private static String BOOTSTRAP_SERVERS =  "localhost:9092";


    private static Producer<Long, String> createProducer() {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        if(args.length !=3){
            System.out.println("Invalid params. Required <exchange data file> <bootstrap servers> <kafka-topic>");
            System.out.println("Exchange data file required in the following format:");
            System.out.println("deviceid_encoded    lat,lon     City    App Timestamp");
            System.exit(0);
        }

        String file_name = args[0];
        BOOTSTRAP_SERVERS = args[1];
        TOPIC = args[2];
        final Producer<Long, String> producer = createProducer();

        BufferedReader br = new BufferedReader(new FileReader(file_name));
        String str = null;

        ExchangeData exchangeData = new ExchangeData();
        while((str = br.readLine())!=null){
            String str_arr[] = str.split("\\t");
            String device_id = str_arr[0];
            String location = str_arr[1];
            String city = str_arr[2];
            String date_time = str_arr[4];
            MobileUser mu = new MobileUser(date_time,device_id,location,city);
            exchangeData.addMobileUser(mu);

        }

        while(true){
            Iterator<MobileUser> itr = exchangeData.getExchangeData().iterator();
            while(itr.hasNext()){
                MobileUser mu = itr.next();
                Thread.sleep(1000);
                final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC,mu.toString());
                RecordMetadata metadata = producer.send(record).get();
            }
        }

    }
}
