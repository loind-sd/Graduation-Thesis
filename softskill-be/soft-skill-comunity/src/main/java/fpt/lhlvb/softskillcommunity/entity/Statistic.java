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
@Table(name = "statistic", schema = "public", catalog = "softskill")
public class Statistic extends BaseEntity {
    @Basic
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "time_room")
    private Float timeRoom;
    @Basic
    @Column(name = "join_time")
    private Timestamp joinTime;
    @Basic
    @Column(name = "task_completed_time")
    private Float taskCompletedTime;
    @Basic
    @Column(name = "count_task_doing")
    private Integer countTaskDoing;
    @Basic
    @Column(name = "count_task_completed")
    private Integer countTaskCompleted;
}
