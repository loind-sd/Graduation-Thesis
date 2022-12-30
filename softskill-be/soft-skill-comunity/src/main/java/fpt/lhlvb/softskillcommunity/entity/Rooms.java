package fpt.lhlvb.softskillcommunity.entity;

import fpt.lhlvb.softskillcommunity.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms", schema = "public", catalog = "softskill")
public class Rooms extends BaseEntity {
    @Basic
    @Column(name = "room_code")
    private String roomCode;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "softskill_id")
    private Long softskillId;
    @Basic
    @Column(name = "room_size")
    private Integer roomSize;
    @Basic
    @Column(name = "is_lock")
    private Boolean isLock;

    @ManyToMany(mappedBy = "rooms")
    private List<Users> users = new ArrayList<>();
}
