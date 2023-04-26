package com.zyl.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分配菜单
 */
@Data
public class AssignMenuVo {

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 菜单id列表
     */
    private List<Long> menuIdList;

}
