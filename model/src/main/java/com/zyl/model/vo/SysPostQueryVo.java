package com.zyl.model.vo;

import lombok.Data;

@Data
public class SysPostQueryVo {
	/**
	 * 岗位编码
	 */
	private String postCode;
	/**
	 * 岗位名称
	 */
	private String name;
	/**
	 * 状态（1正常 0停用）
	 */
	private Boolean status;


}

