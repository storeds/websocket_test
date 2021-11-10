package manager.commen.utils;

import manager.commen.WebSockets;
import manager.pojo.Users;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统一管理session、websocket、curUser
 */

public class CurPool {

    /**
     * 存储websocket
     */
    public static Map<Integer, WebSockets> webSocketsMap = new ConcurrentHashMap<>();

    /**
     * 存储session，第一个存令牌token，第二存session
     */
    public static Map<Integer , List<Object>> sessionPool = new ConcurrentHashMap<>();

    /**
     * 存储当前登录的用户
     */
    public static Map<String, Users>curUserPool = new ConcurrentHashMap<>();

}
