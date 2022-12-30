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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_status", schema = "public", catalog = "softskill")
public class TaskStatus extends BaseEntity {
    @Basic
    @Column(name = "task_id")
    private long taskId;
    @Basic
    @Column(name = "softskill_id")
    private long softskillId;
    @Basic
    @Column(name = "start_time")
    private long startTime;
    @Basic
    @Column(name = "end_time")
    private long endTime;
    @Basic
    @Column(name = "is_general")
    private boolean isGeneral;

}
