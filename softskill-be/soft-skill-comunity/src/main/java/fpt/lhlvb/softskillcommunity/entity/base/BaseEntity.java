package fpt.lhlvb.softskillcommunity.entity.base;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP default NOW()")
    private Timestamp createdAt;

    @Column(name = "updated_date", nullable = false, columnDefinition = "TIMESTAMP default NOW()")
    private Timestamp updatedAt;

    @Column(name = "delete_flag", columnDefinition = "boolean DEFAULT false")
    private boolean deleteFlag;

    @Column(name = "created_by", columnDefinition = "integer DEFAULT 0")
    private Long createdBy;

    @Column(name = "updated_by", columnDefinition = "integer DEFAULT 0")
    private Long updatedBy;

    public void setCommonRegister(Long currentLoginId) {
        Timestamp now = CommonUtils.resultTimestamp();
        this.createdAt = now;
        this.createdBy = currentLoginId;
        this.updatedAt = now;
        this.updatedBy = currentLoginId;
        this.deleteFlag = CommonConstant.DELETE_FLAG_FALSE;
    }

    public void setCommonUpdate(Long currentLoginId) {
        this.updatedAt = CommonUtils.resultTimestamp();
        this.updatedBy = currentLoginId;
    }

    public void setCommonDelete(Long currentLoginId) {
        this.deleteFlag = CommonConstant.DELETE_FLAG_TRUE;
        setCommonUpdate(currentLoginId);
    }
}
