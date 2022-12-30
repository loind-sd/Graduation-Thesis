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
@Table(name = "notification_type", schema = "public", catalog = "softskill")
public class NotificationType extends BaseEntity {
    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "notificationType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @Fetch(value = FetchMode.SELECT)
    private List<Notification> notifications;
}
