package com.wlkg.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
@ApiModel(value = "User",description = "用户对象")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户主键，数据库自增",name = "id",hidden = true)
    private Long id;

    @Length(min = 4,max = 30,message = "用户名只能在4~30位之间")//hibernate-validator 框架验证注解
    @ApiModelProperty(value = "用户注册名",name="username", required = true)
    private String username;// 用户名

    @JsonIgnore
    @Length(min = 4,max = 30,message = "用户名只能在4~30位之间") //hibernate-validator 框架验证注解
    @ApiModelProperty(value = "用户注册密码",name="password", required = true)
    private String password;// 密码

    @Pattern(regexp = "^1[3456789]\\d{9}$",message = "手机号格式不正确")   //正则验证
    @ApiModelProperty(value = "用户注册电话号码",name="phone", required = true)
    private String phone;// 电话

    @ApiModelProperty(hidden = true)
    private Date created;// 创建时间

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String salt;// 密码的盐值



}
