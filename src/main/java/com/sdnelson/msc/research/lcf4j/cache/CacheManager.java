package com.sdnelson.msc.research.lcf4j.cache;


import com.sdnelson.msc.research.lcf4j.core.NodeStatus;
import com.sdnelson.msc.research.lcf4j.nodemgmt.NodeRegistry;
import com.sdnelson.msc.research.lcf4j.nodemgmt.websocksts.message.ResponseClusterMessage;
import org.apache.log4j.Logger;

//Cache Resolve
public class CacheManager {

    final static org.apache.log4j.Logger logger = Logger.getLogger(CacheManager.class);

    public static void resolveResponseCacheMessage(final ResponseClusterMessage responseClusterMessage) {
        if(CacheRegistry.getCacheSize() == 0){
            logger.info("Cache sync received : " + responseClusterMessage.getCacheMapData().toString());
            CacheRegistry.updateFullCache(responseClusterMessage.getCacheMapData());
            logger.info("Local cache updated.");
        }
    }

    public static void resolveCacheUpdateMessage(UpdateCacheMessage updateCacheMessage) {
        final CacheData cacheData = updateCacheMessage.getCacheData();
        CacheRegistry.addToCache(cacheData.getKey(), cacheData.getValue());
        logger.info("Cache updated : " + cacheData.toString());
    }

    public static void resolveCacheEvictMessage(EvictCacheMessage evictCacheMessage) {
        final CacheData cacheData = evictCacheMessage.getCacheData();
        CacheRegistry.evictFromCache(cacheData.getKey());
        logger.info("Cache evicted : " + cacheData.toString());
    }
}
