package fpt.lhlvb.softskillcommunity.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendResponse {
    private Long id;
    private String name;
    private String nickname;
    private String picture;
    private String status;

    public FriendResponse(Long id, String name, String nickname, String picture) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.picture = picture;
    }
}
