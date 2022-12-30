package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticForUserRequest {
    private Long userId;
    private String time;
}
