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
@Table(name = "recomment_task", schema = "public", catalog = "softskill")
public class RecommendTask extends BaseEntity {
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "type_id")
    private Integer typeId;
    @Basic
    @Column(name = "softskill_id")
    private Integer softSkillId;
    @Basic
    @Column(name = "status")
    private Integer status;

    @Basic
    @Column(name = "guideline")
    private String guideline;

    @Basic
    @Column(name = "guideline_image")
    private byte[] guidelineImage;

    @Basic
    @Column(name = "image_data")
    private byte[] imageData;
}
