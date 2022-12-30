package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String nickname;
    private String username;
    private Integer gender;
    private Date birthdate;
}
