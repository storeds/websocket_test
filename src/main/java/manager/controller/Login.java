package manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import manager.pojo.Users;
import manager.service.UserService;

import java.util.*;

@CrossOrigin
@RestController
public class Login {

    @Autowired
    UserService userService;

    @RequestMapping( value = "/login", method = RequestMethod.GET )
    public Map login(@RequestParam("user") String user){

        System.out.println(user);
        List<Users> usersList = userService.querUsersService();
        System.out.println(usersList.size());
        Map listMap = new HashMap();

        for (int i = 0; i < usersList.size(); i++){
            if (user.equals(usersList.get(i).getUser())){
                Integer userId = usersList.get(i).getUserId();
                listMap.put("user",user);
                listMap.put("msg","登录成功");
                System.out.println("进入");
                if (i > 0){
                    listMap.put("userlist",usersList.get(i - 1 ).getUser());
                    listMap.put("userlistid",usersList.get(i - 1 ).getUserId());
                }else {
                    listMap.put("userlist",usersList.get(i + 1).getUser());
                    listMap.put("userlistid",usersList.get(i + 1 ).getUserId());
                }

                return listMap;

            }
        }
        listMap.put("msg","登录失败");
        System.out.println("失败");
        return listMap;
    }





}
