package fpt.lhlvb.softskillcommunity.entity;
import fpt.lhlvb.softskillcommunity.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_channel_info")
public class ChatChannelInfo extends BaseEntity{
    @Column(name = "chat_channel_id")
    private Long chatChannelId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_master")
    private Boolean isMaster;
}
