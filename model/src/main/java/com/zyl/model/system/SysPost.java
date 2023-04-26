package com.zyl.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zyl.model.base.BaseEntity;
import lombok.Data;

/**
 * 岗位
 */
@Data
@TableName("sys_post")
public class SysPost extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 岗位编码
	 */
	@TableField("post_code")
	private String postCode;
	/**
	 * 岗位名称
	 */
	@TableField("name")
	private String name;
	/**
	 * 显示顺序
	 */
	@TableField("description")
	private String description;
	/**
	 * 状态（1正常 0停用）
	 */
	@TableField("status")
	private Integer status;

}