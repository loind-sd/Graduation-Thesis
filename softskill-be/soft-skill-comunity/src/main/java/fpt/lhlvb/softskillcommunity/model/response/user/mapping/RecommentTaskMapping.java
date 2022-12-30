package fpt.lhlvb.softskillcommunity.model.response.user.mapping;


public interface RecommentTaskMapping {
    Long getId();
    String getContent();
    String getTypeName();
    String getName();
    String getNickName();
    String getMail();
    String getSoftSkillName();
    Long getStatusId();
    String getStatusName();
    String getDateCreate();
    String getGuideline();
    byte[] getTaskImage();
    byte[] getGuidelineImage();
}
