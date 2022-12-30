package fpt.lhlvb.softskillcommunity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fpt.lhlvb.softskillcommunity.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Roles extends BaseEntity {
    @Basic
    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Fetch(value = FetchMode.SELECT)
    private List<Account> accounts;
}
