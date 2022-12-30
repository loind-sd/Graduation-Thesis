package fpt.lhlvb.softskillcommunity.entity;

import fpt.lhlvb.softskillcommunity.entity.base.BaseEntity;
import fpt.lhlvb.softskillcommunity.model.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users extends BaseEntity {
    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "nickname")
    private String nickname;

    @Basic
    @Column(name = "mail")
    private String mail;

    @Basic
    @Column(name = "gender")
    private Integer gender;

    @Basic
    @Column(name = "birthdate")
    private Date birthdate;

    @Basic
    @Column(name = "isonline")
    private Boolean isOnline;

    @Basic
    @Column(name = "rank_id")
    private Long rankId;

    @OneToOne(mappedBy = "user")
    private Account account;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Basic
    @Column(name = "picture")
    private String picture;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable( name = "user_room_info", joinColumns = { @JoinColumn(name = "user_id") },inverseJoinColumns = { @JoinColumn(name = "room_id") })
    private List<Rooms> rooms = new ArrayList<>();
}
