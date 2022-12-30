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
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks_relation", schema = "public", catalog = "softskill")
public class TasksRelation extends BaseEntity {
    @Basic
    @Column(name = "task_id")
    private long taskId;
    @Basic
    @Column(name = "softskill_id")
    private long softSkillId;
    @Basic
    @Column(name = "start_time")
    private Date startTime;
    @Basic
    @Column(name = "end_time")
    private Date endTime;
    @Basic
    @Column(name = "is_general")
    private boolean isGeneral;
    @Column(name = "sub_task")
    private Long subTask;
}
