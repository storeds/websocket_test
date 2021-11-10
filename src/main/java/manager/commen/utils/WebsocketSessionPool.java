package manager.commen.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存放连接通道
 */
public class WebsocketSessionPool {

    public static Map<String , SessionList> linkSession = new ConcurrentHashMap<>();

}
