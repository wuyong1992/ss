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
import javax.persistence.Id;
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
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "用户")
public class UserInfo {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键ID")
    private Integer userId;

    @ApiModelProperty(value = "关联企业主键ID")
    private Integer enterpriseId;

    @ApiModelProperty(value = "微信 OPENID")
    private String openid;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知，默认0")
    private Integer sex;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "头像")
    private String headimgurl;

    @ApiModelProperty(value = "微信特权信息")
    private String privilege;

    @ApiModelProperty(value = "微信unionid")
    private String unionid;

    @ApiModelProperty(value = "真实名称")
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "QQ号")
    private String qq;

    @ApiModelProperty(value = "特长")
    private String speciality;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    @ApiModelProperty(value = "状态字段")
    private Integer status;

    @ApiModelProperty(value = "关注状态")
    private Integer subscribeStatus;

    @ApiModelProperty(value = "信息是否完善")
    private Integer isInfoComplete;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
