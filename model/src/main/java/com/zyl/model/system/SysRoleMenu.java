package com.zyl.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyl.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色菜单
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableField("role_id")
	private Long roleId;
	/**
	 * 菜单id
	 */
	@TableField("menu_id")
	private Long menuId;

}

