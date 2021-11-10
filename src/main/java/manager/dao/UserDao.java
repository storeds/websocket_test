package manager.dao;


import org.springframework.stereotype.Repository;
import manager.pojo.UserMessenger;
import manager.pojo.Users;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    public List<Users> qurreyUser(){

        List<Users> arrayList = new ArrayList<>();
        Users users1 = new Users(1,"aa");
        Users users2 = new Users(2,"bb");
        arrayList.add(users1);
        arrayList.add(users2);
        return arrayList;
    }

    public int saveMsg(UserMessenger userMsg){

        List<UserMessenger> arrayList = new ArrayList<>();

        if (userMsg != null){
            arrayList.add(userMsg);
            System.out.println(userMsg);
            return 1;
        }else {
            System.out.println("存储的值为空");
            return 0;
        }
    }

}
