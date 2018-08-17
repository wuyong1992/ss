package com.zzc.ss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "求职信息VO对象")
public class UserJobVO {

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "用户主键ID")
    private Integer userId;

    @ApiModelProperty(value = "工作主键ID")
    private Integer jobId;

    @ApiModelProperty(value = "发布该工作的企业ID")
    private Integer enterpriseId;

    @ApiModelProperty(value = "状态字段，1：申请中，2：已通过，3：未通过")
    private Integer status;

    @ApiModelProperty(value = "申请次数")
    private Integer applyCount;

    @ApiModelProperty(value = "申请成功次数")
    private Integer applySuccessCount;

    @ApiModelProperty(value = "创建时间")
    private Date crateTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "用户真实姓名")
    private String realName;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "工作标题")
    private String jobTitle;

    @ApiModelProperty(value = "企业名称")
    private String enterpriseFullName;

    @ApiModelProperty(value = "企业联系电话")
    private String enterprisePhone;



}
