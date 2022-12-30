package fpt.lhlvb.softskillcommunity.model.response.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupTaskInfoResponse {
    private Long id;
    private int no;
    private String taskName;
    private Long softSkillId;
    private String softSkillName;
    private String status;
    private String startDate;
    private String endDate;
    private String createdDate;
    private String createdName;
    private String taskContent;
    private Boolean guidelineChange;
    private List<GuidelineResponse> guideline;
    private int doingNumber;
    private int doneNumber;
    private int favoriteNumber;
    private Long requiredTask;
    private byte[] taskImage;
    private byte[] guidelineImage;

}
