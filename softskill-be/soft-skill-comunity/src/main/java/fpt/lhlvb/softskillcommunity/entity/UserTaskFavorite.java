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
@Table(name = "user_task_favorite", schema = "public", catalog = "softskill")
public class UserTaskFavorite extends BaseEntity {
    @Basic
    @Column(name = "task_id")
    private Long taskId;
    @Basic
    @Column(name = "favorite")
    private Boolean favorite;
    @Basic
    @Column(name = "status_id")
    private Integer statusId;
}
