package com.zzc.ss.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author JianGuo
 * on 2018/8/15
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "用户，求职工作关联表")
public class UserJob {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Integer id;


    @ApiModelProperty(value = "用户主键ID")
    private Integer userId;

    @ApiModelProperty(value = "工作主键ID")
    private Integer jobId;

    @ApiModelProperty(value = "发布该工作的企业主键ID")
    private Integer enterpriseId;

    @ApiModelProperty(value = "状态字段，1：申请中，2：已通过，3：未通过")
    private Integer status;

    @ApiModelProperty(value = "申请次数")
    private Integer applyCount;

    @ApiModelProperty(value = "申请成功次数")
    private Integer applySuccessCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
