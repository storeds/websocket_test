package manager.commen.utils;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
@ServerEndpoint("/websocket/{user}")
public class SocketTest {
    private static int onlineCount = 0;
    private static Map<String, SocketTest> clients = new ConcurrentHashMap<String, SocketTest>();
    private Session session;
    private String username;
    private static final Logger log = LoggerFactory.getLogger(SocketTest.class);

    private static CopyOnWriteArraySet<SocketTest> webSocketSet = new CopyOnWriteArraySet<SocketTest>();
//    @OnOpen
//    public void onOpen(@PathParam("username") String username, Session session) throws IOException {
//
//        this.username = username;
//        this.session = session;
//
//        addOnlineCount();
//        clients.put(username, this);
//        System.out.println("已连接");
//    }

    @OnOpen
    public void onOpen(Session session) throws Exception{
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
        System.out.println("连接成功");

    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(username);
        subOnlineCount();
    }

//    @OnMessage
//    public void onMessage(String message) throws IOException {
//
//        JsonUtils jsonTo = JsonUtils.fromObject(message);
//        String mes = (String) jsonTo.get("message");
//
//        if (!jsonTo.get("To").equals("All")){
//            sendMessageTo(mes, jsonTo.get("To").toString());
//        }else{
//            sendMessageAll("给所有人");
//        }
//    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessageTo(String message, String To) throws IOException {
        // session.getBasicRemote().sendText(message);
        //session.getAsyncRemote().sendText(message);
        for (SocketTest item : clients.values()) {
            if (item.username.equals(To)) {
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (SocketTest item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        SocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        SocketTest.onlineCount--;
    }

    public static synchronized Map<String, SocketTest> getClients() {
        return clients;
    }
}