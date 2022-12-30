package fpt.lhlvb.softskillcommunity.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendTaskResponse {
    private Long id;
    private String content;
    private String typeName;
    private String name;
    private String nickName;
    private String mail;
    private String softSkillName;
    private Long statusId;
    private String status;
}
