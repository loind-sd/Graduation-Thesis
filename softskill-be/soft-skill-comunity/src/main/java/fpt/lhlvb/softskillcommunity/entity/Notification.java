package fpt.lhlvb.softskillcommunity.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "notification", schema = "public", catalog = "softskill")
public class Notification extends BaseEntity {
    @Basic
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "status")
    private Boolean status;
    @Basic
    @Column(name = "sender_id")
    private Long senderId;
    @Basic
    @Column(name = "delivered")
    private boolean delivered;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", nullable = false)
    @JsonProperty
    private NotificationType notificationType;

    @Basic
    @Column(name = "picture")
    private String picture;
}
