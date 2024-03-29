package com.zyl.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyl.model.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: zyl
 * @date 2023/4/26 15:35
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> selectMenuListByUserId(Long userId);
}
