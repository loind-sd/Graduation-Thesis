package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserRequest {
    private String mail;
    private String nickname;
}
