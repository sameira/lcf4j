package com.sdnelson.msc.research.lcf4j.config;


import com.sdnelson.msc.research.lcf4j.Lcf4jCluster;
import com.sdnelson.msc.research.lcf4j.nodemgmt.websocksts.client.WebSocketClient;
import com.sdnelson.msc.research.lcf4j.util.ClusterConfig;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistributedConfigManager {

    final static org.apache.log4j.Logger logger = Logger.getLogger(DistributedConfigManager.class);

    public static void addNewConfigVersion(Map<String, String> config){
        //Set version as 0 here, but in registry it will be check and set for correct value
        final ConfigData configData = new ConfigData(ClusterConfig.getNodeServerName(), 0, new HashMap<>(config));
        // Returned data will contained updated version
        final ConfigData localConfigData = ConfigRegistry.addToLocalConfig(configData);
        final List<WebSocketClient> clientList = Lcf4jCluster.getClientList();
        if(clientList == null || clientList.isEmpty()){
            logger.info("No client nodes found to send config updates.");
            return;
        }
        for (WebSocketClient webSocketClient : clientList){
            webSocketClient.sendConfigUpdateMessage(localConfigData);
        }
        logger.info("Local config registry updated and distributed to the cluster successfully.");
    }

    public static Map<Integer,ConfigData> getAllConfig() {
        return ConfigRegistry.getAllConfigData();
    }

    public static ConfigData getConfigForVersion(int version) {
        return ConfigRegistry.getConfigForVersion(version);
    }

    public static int getConfigSize() {
        return ConfigRegistry.getConfigSize();
    }
}
