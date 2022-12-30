package fpt.lhlvb.softskillcommunity.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendTaskUserResponse {
    private Long id;
    private RecommendTaskContentMapping content;
    private String typeName;
    private String softSkillName;
    private String status;
    private String guideline;
    private byte[] taskImage;
    private byte[] guidelineImage;
}

