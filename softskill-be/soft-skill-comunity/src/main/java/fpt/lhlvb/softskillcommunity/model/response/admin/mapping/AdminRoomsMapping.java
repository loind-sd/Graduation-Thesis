package fpt.lhlvb.softskillcommunity.model.response.admin.mapping;

public interface AdminRoomsMapping {
    Long getNumRow();
    Long getRoomId();
    String getRoomName();
    String getSoftSkill();
    Long getTaskId();
    String getTaskName();
    String getStartTime();
    Integer getUserOnline();
    Integer getRoomSize();
    Boolean getIsLock();
    String getDescription();
}