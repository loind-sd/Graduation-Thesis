package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

public interface RoomDetailMapping {
    Long getRoomId();

    String getRoomCode();

    String getRoomName();

    String getDescription();

    String getPassword();

    Integer getSoftSkillId();

    String getSoftSkillName();

    Integer getRoomSize();

    Long getCreateById();

    String getCreateByName();

    Boolean getIsLock();
}
