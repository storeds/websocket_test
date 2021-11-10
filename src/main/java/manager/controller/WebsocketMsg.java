package manager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import manager.commen.utils.CurPool;
import manager.commen.utils.SessionList;
import manager.commen.utils.WebsocketSessionPool;


import java.util.*;

@CrossOrigin
@RestController
@Slf4j
public class WebsocketMsg {



    /**
     * 查询所有的可连接对象
     * @param userId
     * @return
     */
    @RequestMapping( value = "/checkAll", method = RequestMethod.GET )
    public Map checkAll(@RequestParam("userId") Integer userId) {
        Iterator<Integer> it = CurPool.sessionPool.keySet().iterator();
        List userlist = new ArrayList();
        List userlistid = new ArrayList();
        Map usermap = new HashMap();
        while (it.hasNext()){
            Integer userID = it.next();

            if (userId.equals(userID)){
               log.info("链接的id有{}", userID);
            }else {
                String username = (String) CurPool.sessionPool.get(userID).get(0);
                userlist.add(username);
                userlistid.add(userID);
            }

        }
        usermap.put("userlist",userlist);
        usermap.put("userlistid",userlistid);
        return usermap;
    }




    /**
     * 建立连接通道并绑定
     * @param username
     * @param UserId
     * @param ToUserId
     * @return
     */
    @RequestMapping("/createSession")
    public @ResponseBody
    Map connectuser(@RequestParam("username") String username,
                    @RequestParam("UserId") Integer UserId,
                    @RequestParam("ToUserId") Integer ToUserId){
        log.info("输入的连接用户有{}{}{}",username,UserId,ToUserId);

        HashMap map = new HashMap();

        //查看自己的连接有无被创建没有则创建
        if (WebsocketSessionPool.linkSession.get(username) == null){
            SessionList sessionList = new SessionList();
            sessionList.setUsername(username);
            sessionList.setUserId(UserId);
            sessionList.setToUserId(ToUserId);
            log.info("{}自己的池子{}",username,sessionList);
            WebsocketSessionPool.linkSession.put(username , sessionList);
        }




        //查看链接对象是否有和自己链接没有则创建连接
        if (WebsocketSessionPool.linkSession.get(CurPool.sessionPool.get(ToUserId).get(0).toString()) == null) {
            SessionList sessionList = new SessionList();
            String usernameTo = CurPool.sessionPool.get(ToUserId).get(0).toString();
            sessionList.setUsername(usernameTo);
            sessionList.setUserId(ToUserId);
            sessionList.setToUserId(UserId);
            log.info("{}连接对象的池子{}",usernameTo,sessionList);
            WebsocketSessionPool.linkSession.put(usernameTo,sessionList);
        }else {
            map.put("msg","faile");
            log.info("链接失败了");
        }

        map.put("msg","sucess");
        return map;
    }


    /**
     * 关闭连接通道
     * @param username
     * @param UserId
     * @param ToUserId
     */
    @RequestMapping("/delete")
    public void disconnect(@RequestParam("username") String username,
                    @RequestParam("UserId") Integer UserId,
                    @RequestParam("ToUserId") Integer ToUserId){

        WebsocketSessionPool.linkSession.remove(username);
        WebsocketSessionPool.linkSession.remove(CurPool.sessionPool.get(ToUserId).get(0).toString());
        log.info("成功删除连接");
    }
}
