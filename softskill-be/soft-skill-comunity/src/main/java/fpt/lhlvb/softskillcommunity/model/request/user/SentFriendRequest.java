package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SentFriendRequest {

    @NotNull
    private Long id;
}
