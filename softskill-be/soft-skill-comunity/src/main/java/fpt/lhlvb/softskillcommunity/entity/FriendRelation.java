package fpt.lhlvb.softskillcommunity.entity;

import fpt.lhlvb.softskillcommunity.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend_relation", schema = "public", catalog = "softskill")
public class FriendRelation extends BaseEntity {
    @Basic
    @Column(name = "user_sent_id")
    private Long userSentId;
    @Basic
    @Column(name = "user_recived_id")
    private Long userRecivedId;
    @Basic
    @Column(name = "status")
    private Integer status;
}
