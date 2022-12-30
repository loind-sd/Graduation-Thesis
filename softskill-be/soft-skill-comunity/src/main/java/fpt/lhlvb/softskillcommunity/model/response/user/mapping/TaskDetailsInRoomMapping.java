package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

public interface TaskDetailsInRoomMapping {
    Long getTaskId();

    String getTaskName();

    String getTaskContent();

    byte[] getImageTask();

    Long getSubTaskId();

    String getSubTaskName();
}
