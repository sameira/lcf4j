package com.lcf4j.app;

import com.sdnelson.msc.research.lcf4j.Lcf4jCache;
import com.sdnelson.msc.research.lcf4j.Lcf4jCluster;
import com.sdnelson.msc.research.lcf4j.Lcf4jConfig;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Application {

    final static org.apache.log4j.Logger logger = Logger.getLogger(Lcf4jCluster.class);

    public static void main(String[] args) {
        Lcf4jCluster lcf4jCluster = new Lcf4jCluster();
        Lcf4jCache lcf4jCache = new Lcf4jCache();
        Lcf4jConfig lcf4jConfig = new Lcf4jConfig();
        try {
            lcf4jCluster.startCluster();
            Map<String, String> configMap = new HashMap<>();
            while(true) {
                Thread.sleep(5000);
                lcf4jCache.addToCache(UUID.randomUUID().toString().split("-")[2], UUID.randomUUID().toString().split("-")[1]);
                configMap.put(UUID.randomUUID().toString().split("-")[2], UUID.randomUUID().toString().split("-")[1]);
                lcf4jConfig.addNewConfigVersion(configMap);
                logger.info("Cache size : " + lcf4jCache.getCacheSize());
                logger.info("Config size : " + lcf4jConfig.getConfigSize());
            }
        } catch (InterruptedException e) {
            logger.error("Error occurred while starting the node server");
            System.exit(0);
        }
    }
}