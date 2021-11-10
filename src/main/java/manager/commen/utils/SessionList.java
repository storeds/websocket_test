package manager.commen.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionList {

    private Integer UserId;
    private Integer ToUserId;
    private String username;

    @Override
    public String toString() {
        return "SessionList{" +
                "UserId=" + UserId +
                ", ToUserId=" + ToUserId +
                ", username='" + username + '\'' +
                '}';
    }
}
