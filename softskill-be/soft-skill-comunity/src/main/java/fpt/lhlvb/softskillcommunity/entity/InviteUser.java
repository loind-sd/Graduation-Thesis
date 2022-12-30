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
@Table(name = "invite_user", schema = "public", catalog = "softskill")
public class InviteUser extends BaseEntity {
    @Basic
    @Column(name = "mail")
    private String mail;
    @Basic
    @Column(name = "role")
    private String role;
}
