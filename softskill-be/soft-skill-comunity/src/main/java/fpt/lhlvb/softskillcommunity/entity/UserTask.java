package fpt.lhlvb.softskillcommunity.entity;

import fpt.lhlvb.softskillcommunity.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_task", schema = "public", catalog = "softskill")
public class UserTask extends BaseEntity {
    @Basic
    @Column(name = "user_id")
    private long userId;
    @Basic
    @Column(name = "task_id")
    private long taskId;
    @Basic
    @Column(name = "status_id")
    private long statusId;
}
