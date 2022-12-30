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
@Table(name = "feedback", schema = "public", catalog = "softskill")
public class Feedback extends BaseEntity {
    @Basic
    @Column(name = "title_id")
    private Long titleId;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "rate_star")
    private Integer rateStar;
    @Basic
    @Column(name = "content_response")
    private String contentResponse;
    @Basic
    @Column(name = "status")
    private Boolean status;

    public Feedback(Integer rateStar, Long titleId, String content) {
        this.titleId = titleId;
        this.content = content;
        this.rateStar = rateStar;
    }
}
