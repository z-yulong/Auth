package com.zyl.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


import com.zyl.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 姓名
     */
    @TableField("name")
    private String name;
    /**
     * 手机
     */
    @TableField("phone")
    private String phone;
    /**
     * 头像地址
     */
    @TableField("head_url")
    private String headUrl;
    /**
     * 部门id
     */
    @TableField("dept_id")
    private Long deptId;
    /**
     * 岗位id
     */
    @TableField("post_id")
    private Long postId;
    /**
     * 描述
     */
    @TableField("description")
    private String description;
    /**
     * 状态（1：正常 0：停用）
     */
    @TableField("status")
    private Integer status;
    /**
     * roleList
     */
    @TableField(exist = false)
    private List<SysRole> roleList;

    /**
     * 岗位
     */
    @TableField(exist = false)
    private String postName;

    /**
     * 部门
     */
    @TableField(exist = false)
    private String deptName;
    /**
     * userPermsList
     */
    @TableField(exist = false)
    List<String> userPermsList;
}

