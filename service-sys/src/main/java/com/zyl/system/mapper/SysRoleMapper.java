package com.zyl.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyl.model.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: zyl
 * @date 2023/4/26 15:36
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> selectRoleByUserId(Long id);

    List<Long> selectUserRoleIds(Long id);
}
