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
import java.util.Date;

/**
 * @author JianGuo
 * on 2018/8/8
 * description:
 */
@Data
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "企业信息")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class EnterpriseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键ID")
    private Integer enterpriseId;

    @ApiModelProperty(value = "联系电话")
    private String enterprisePhone;

    @ApiModelProperty(value = "全称")
    private String fullName;

    @ApiModelProperty(value = "负责人名称")
    private String manageName;

    @ApiModelProperty(value = "简介")
    private String intro;

    @ApiModelProperty(value = "企业地址")
    private String enterpriseAddress;

    @ApiModelProperty(value = "企业QQ")
    private String enterpriseQq;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "状态字段")
    private Integer status;

    @ApiModelProperty(value = "状态原因")
    private String statusReason;

    @ApiModelProperty(value = "信息是否完善")
    private Integer isInfoComplete;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


    public EnterpriseInfo(String enterprisePhone, String fullName, Integer status) {
        this.enterprisePhone = enterprisePhone;
        this.fullName = fullName;
        this.status = status;
    }

    public EnterpriseInfo() {
    }
}
