package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

public interface FeedbackMapping {
    Long getNumRow();
    Long getFeedbackId();
    String getFeedbackTitle();
    String getContent();
    String getCreatedName();
    String getCreatedMail();
    Object getCreatedDate();
    Boolean getStatus();
    String getContentResponse();
    String getUpdatedName();
    Integer getRateStar();
}
