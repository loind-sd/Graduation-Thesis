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
@Table(name = "world_chat", schema = "public", catalog = "softskill")
public class WorldChat extends BaseEntity {
    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "channel_id")
    private Long channelId;
}
