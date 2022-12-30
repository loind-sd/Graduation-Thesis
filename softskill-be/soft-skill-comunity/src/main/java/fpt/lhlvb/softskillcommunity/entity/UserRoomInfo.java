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
@Table(name = "user_room_info", schema = "public", catalog = "softskill")
public class UserRoomInfo extends BaseEntity {
    @Basic
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "room_id")
    private Long roomId;
    @Basic
    @Column(name = "join_status")
    private Boolean joinStatus;
    @Basic
    @Column(name = "is_joined")
    private Boolean isJoined;
    @Basic
    @Column(name = "is_old_member")
    private Boolean isOldMember;
}
