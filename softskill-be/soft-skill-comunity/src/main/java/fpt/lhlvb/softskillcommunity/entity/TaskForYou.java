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
@Table(name = "task_for_you", schema = "public", catalog = "softskill")
public class TaskForYou extends BaseEntity {
    @Basic
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "status")
    private Integer status;
}
