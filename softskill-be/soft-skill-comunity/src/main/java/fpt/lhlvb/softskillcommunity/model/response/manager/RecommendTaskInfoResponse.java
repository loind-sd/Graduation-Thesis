package fpt.lhlvb.softskillcommunity.model.response.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendTaskInfoResponse {
    private Long id;
    private int no;
    private String createdName;
    private String taskContent;
    private String softSkillName;
    private String taskType;
    private String status;
    private String createdDate;
    private String updateName;
    private String guideline;
    private byte[] taskImage;
    private byte[] guidelineImage;
    private String mail;
}
