package fpt.lhlvb.softskillcommunity.model.response.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatisticRankUserResponse {
    private float completedTime;
    private int completedTask;
    private int conditionRank;
    private String rank;
}
