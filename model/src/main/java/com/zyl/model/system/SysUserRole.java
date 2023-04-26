package com.zyl.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyl.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户角色
 */
@Data
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 角色id
	 */
	@TableField("role_id")
	private Long roleId;
	/**
	 * 用户id
	 */
	@TableField("user_id")
	private Long userId;
}

