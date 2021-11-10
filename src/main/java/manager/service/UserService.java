package manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import manager.dao.UserDao;
import manager.pojo.UserMessenger;
import manager.pojo.Users;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public List<Users> querUsersService(){
        return userDao.qurreyUser();
    }

    public boolean saveMsg(UserMessenger userMessenger){
        return userDao.saveMsg(userMessenger) > 0 ? true : false;
    }

}
