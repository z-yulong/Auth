package com.zyl.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 分配菜单
 */
@Data
public class AssignRoleVo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id列表
     */
    private List<Long> roleIdList;

}
