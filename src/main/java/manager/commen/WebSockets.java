package manager.commen;


import org.springframework.stereotype.Component;
import manager.commen.utils.CurPool;
import manager.commen.utils.JsonUtils;
import manager.commen.utils.WebsocketSessionPool;


import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.List;

@Component
@ServerEndpoint("/websocket/{userId}/{username}")
public class WebSockets {

    private Session session;

    /**
     * 打开连接，并将连接对象存入池中
     * @param session
     * @param userId
     * @param
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Integer userId,
                       @PathParam(value = "username") String username){
        this.session = session;
        //将websocket存入curPool池中方便以后调用
        CurPool.webSocketsMap.put(userId,this);

        //将session存入curPool池中
        List<Object> list = new ArrayList<>();
        list.add(username);
        list.add(session);
        CurPool.sessionPool.put(userId,list);

        //显示连接池中有那些对象可以连接
        System.out.println("【websocket消息】有新的连接,目前连接池中共有："+CurPool.webSocketsMap.size()+"连接对象");

    }



    /**
     * 关闭连接，并删除连接对象
     */
    @OnClose
    public void onClose(){
        Integer userId = Integer.parseInt(this.session.getRequestParameterMap().get("userId").get(0));
        //删除user对象的session
        CurPool.sessionPool.remove(userId);

        //删除user对象的websocket
        CurPool.webSocketsMap.remove(userId);

    }

    /**
     * 发送消息
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println(message);
        //获取当前对象的id
        Integer userID = Integer.parseInt(this.session.getRequestParameterMap().get("userId").get(0));
        String username = CurPool.sessionPool.get(userID).get(0).toString();
        //获取发送消息对象的id
        Integer ToUserId = WebsocketSessionPool.linkSession.get(username).getToUserId();
        System.out.println(username+":"+userID+"==>发送对象的id:"+ToUserId);

        sendTextMessage(ToUserId,JsonUtils.objectToJson(message));

    }

    /**
     * 此为单点消息（发送文本）
     */
    public void sendTextMessage(Integer userID, String message){
        Session session = (Session)CurPool.sessionPool.get(userID).get(1);
        if (session != null){
            try {
                session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }




}
