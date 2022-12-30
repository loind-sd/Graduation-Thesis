package fpt.lhlvb.softskillcommunity.model.response.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SoftSkillInfoResponse {
    private Long id;
    private int no;
    private String name;
    private int numberRoom;
    private int numberTask;
    private String status;
    private String createdDate;
    private String createdName;
}
