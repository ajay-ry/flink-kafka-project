package com.travel.deepblue.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;


public class YamlConfiguration {

    private static String yaml_conf_file;
    private Map<String,Object> map;

    public YamlConfiguration(String conf_file) throws FileNotFoundException {
        this.yaml_conf_file=conf_file;
        Yaml yaml = new Yaml();
        InputStream ios = new FileInputStream(new File(yaml_conf_file));
        map = (Map<String,Object>)yaml.load(ios);
    }

    public String getValue(String key){
        return  map.get(key).toString();
    }

}
