package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomSearchRequest{
    private Long softSkillId;
    private String textSearch;
    private Integer roomSizeFrom;
    private Integer roomSizeTo;
    private String fromTime;
    private String toTime;
}
