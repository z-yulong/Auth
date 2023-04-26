package com.zyl.model.system;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyl.model.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 部门
 */
@Data
@TableName("sys_dept")
public class SysDept extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门名称
	 */
	@TableField("name")
	private String name;
	/**
	 * 上级部门id
	 */
	@TableField("parent_id")
	private Long parentId;
	/**
	 * 树结构
	 */
	@TableField("tree_path")
	private String treePath;
	/**
	 * 排序
	 */
	@TableField("sort_value")
	private Integer sortValue;
	/**
	 * 负责人
	 */
	@TableField("leader")
	private String leader;
	/**
	 * 电话
	 */
	@TableField("phone")
	private String phone;
	/**
	 * 状态（1正常 0停用）
	 */
	@TableField("status")
	private Integer status;
	/**
	 * 下级部门
	 */
	@TableField(exist = false)
	private List<SysDept> children;

}