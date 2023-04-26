package com.zyl.model.system;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyl.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单
 */
@Data
@TableName("sys_menu")
public class SysMenu extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 所属上级
	 */
	@TableField("parent_id")
	private Long parentId;
	/**
	 * 名称
	 */
	@TableField("name")
	private String name;
	/**
	 * 类型(1:菜单,2:按钮)
	 */
	@TableField("type")
	private Integer type;
	/**
	 * 路由地址
	 */
	@TableField("path")
	private String path;
	/**
	 * 组件路径
	 */
	@TableField("component")
	private String component;
	/**
	 * 权限标识
	 */
	@TableField("perms")
	private String perms;
	/**
	 * 图标
	 */
	@TableField("icon")
	private String icon;
	/**
	 * 排序
	 */
	@TableField("sort_value")
	private Integer sortValue;
	/**
	 * 状态(0:禁止,1:正常)
	 */
	@TableField("status")
	private Integer status;
	/**
	 * 下级列表
 	 */
	@TableField(exist = false)
	private List<SysMenu> children;
	/**
	 * 是否选中
	 */
	@TableField(exist = false)
	private boolean isSelect;
}

