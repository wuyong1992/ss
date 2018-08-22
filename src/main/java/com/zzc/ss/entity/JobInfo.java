package com.zzc.ss.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author JianGuo
 * on 2018/8/8
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "职位信息")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class JobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Integer jobId;

    @ApiModelProperty(value = "发布的企业ID")
    private Integer enterpriseId;

    @ApiModelProperty(value = "职位名称")
    private String jobTitle;

    @ApiModelProperty(value = "职位类别ID")
    private Integer jobCategoryId;

    @ApiModelProperty(value = "招聘人数")
    private Integer recruitNum;

    @ApiModelProperty(value = "招聘有效期类型，1短期；2长期；短期时间段")
    private Integer timelinessType;

    @ApiModelProperty(value = "开始时间，当timelinessType为1时，有值")
    private Date startTime;

    @ApiModelProperty(value = "结束时间，当timelinessType为1时，有值")
    private Date endTime;

    @ApiModelProperty(value = "结算周期类型,1日结：2周结；3月结")
    private Integer payPeriodType;

    @ApiModelProperty(value = "薪资，单位：元，具体是每天还是每周根据payPeriodType来")
    private BigDecimal salary;

    @ApiModelProperty(value = "工作时间段")
    private String workTime;

    @ApiModelProperty(value = "工作地点")
    private String workAddress;

    @ApiModelProperty(value = "工作描述")
    private String jobDetail;

    @ApiModelProperty(value = "真实的浏览人数")
    private Integer browseNum;

    @ApiModelProperty(value = "预设的浏览人数基数")
    private Integer baseBrowseNum;

    @ApiModelProperty(value = "真实的申请人数")
    private Integer applyNum;

    @ApiModelProperty(value = "预设的申请人数基数")
    private Integer baseApplyNum;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "状态字段")
    private Integer status;

    @ApiModelProperty(value = "状态原因")
    private String statusReason;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
