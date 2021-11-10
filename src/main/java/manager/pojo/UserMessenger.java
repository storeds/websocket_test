package manager.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMessenger implements Serializable  {

    private Integer userId;
    private String user;
    private String msg;

    @Override
    public String toString() {
        return "UserMessenger{" +
                "userId=" + userId +
                ", user='" + user + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
