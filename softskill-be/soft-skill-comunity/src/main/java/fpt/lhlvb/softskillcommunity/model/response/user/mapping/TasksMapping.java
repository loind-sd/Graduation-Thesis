package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

public interface TasksMapping {
    Long getTaskId();
    String getTaskName();
    String getTaskContent();
    Boolean getIsFavorite();
    byte[] getImageData();
    Integer getCountGrDoingOrDone();
}
