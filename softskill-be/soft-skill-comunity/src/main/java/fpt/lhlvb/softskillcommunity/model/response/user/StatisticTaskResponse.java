package fpt.lhlvb.softskillcommunity.model.response.user;

import fpt.lhlvb.softskillcommunity.model.response.user.mapping.StatisticTaskMapping;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticTaskResponse {
    private StatisticTaskMapping statisticTaskMapping;
    private String fromDate;
    private String toDate;
}
