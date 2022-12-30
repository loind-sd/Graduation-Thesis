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
@Table(name = "rooms_task", schema = "public", catalog = "softskill")
public class RoomsTask extends BaseEntity {
    @Basic
    @Column(name = "room_id")
    private long roomId;
    @Basic
    @Column(name = "task_id")
    private long taskId;
    @Basic
    @Column(name = "softskill_id")
    private long softSkillId;
    @Basic
    @Column(name = "status_id")
    private long statusId;
    @Basic
    @Column(name = "task_no")
    private Integer taskNo;
    @Basic
    @Column(name = "count_submit")
    private Integer countSubmit;
    @Basic
    @Column(name = "count_completed")
    private Integer countCompleted;
    @Basic
    @Column(name = "count_cancel")
    private Integer countCancel;
    @Basic
    @Column(name = "start_time")
    private Timestamp startTime;
}