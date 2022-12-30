package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

public interface TaskLatestInRoomMapping {
    Long getTaskId();
    String getTaskName();
    String getTaskContent();
    Byte[] getImageData();
    Long getSubTaskId();
    String getSubTaskName();
    String getSubTaskContent();
    Integer getStatusSubTask();
}
