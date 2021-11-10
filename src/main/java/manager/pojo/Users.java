package manager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {
    private Integer userId;
    private String user;

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", user='" + user + '\'' +
                '}';
    }
}
