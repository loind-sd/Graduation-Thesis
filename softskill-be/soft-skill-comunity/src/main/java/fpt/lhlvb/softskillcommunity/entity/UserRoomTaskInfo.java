package fpt.lhlvb.softskillcommunity.entity;

import fpt.lhlvb.softskillcommunity.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_room_task_info", schema = "public", catalog = "softskill")
public class UserRoomTaskInfo extends BaseEntity {
    @Basic
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "room_id")
    private Long roomId;
    @Basic
    @Column(name = "task_id")
    private Long taskId;
    @Basic
    @Column(name = "status_task_id")
    private Integer statusTaskId;
    @Basic
    @Column(name = "is_click_completed")
    private Boolean isClickCompleted;
    @Basic
    @Column(name = "is_click_cancel")
    private Boolean isClickCancel;
}
