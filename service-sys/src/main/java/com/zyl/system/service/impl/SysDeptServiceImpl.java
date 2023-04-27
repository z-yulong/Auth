package com.zyl.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyl.model.system.SysDept;
import com.zyl.system.mapper.SysDeptMapper;
import com.zyl.system.service.SysDeptService;
import org.springframework.stereotype.Service;

/**
 * @author: zyl
 * @date 2023/4/27 19:56
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
}
