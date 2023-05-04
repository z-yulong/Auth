package com.zyl.system.controller;

import com.zyl.common.result.R;
import com.zyl.model.system.SysDept;
import com.zyl.system.service.SysDeptService;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zyl
 * @date 2023/4/27 19:58
 */
@RestController
@RequestMapping("/admin/system/sysDept")
public class SysDeptController {
    private final SysDeptService deptService;

    public SysDeptController(SysDeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 根据id获取部门
     *
     * @param id 部门id
     * @return sysDept
     */
    @GetMapping("/getById/{id}")
    public R<SysDept> getById(@PathVariable Long id) {
        SysDept dept = deptService.getById(id);
        return R.ok(dept);
    }

    /**
     * 新增部门
     * @param sysDept 部门
     */
    @PostMapping("/save")
    public R save(@RequestBody SysDept sysDept) {
        boolean b = deptService.save(sysDept);
        return b ? R.ok() : R.fail();
    }
    /**
     * 修改部门
     * @param sysDept 部门
     */
    @PutMapping("/update")
    public R update(@RequestBody SysDept sysDept) {
        sysDept.setUpdateTime(null);
        boolean b = deptService.updateById(sysDept);
        return b ? R.ok() : R.fail();
    }


    /**
     * 根据id删除部门
     * @param id 部门id
     * @return R
     */
    @DeleteMapping("delete/{id}")
    public R remove(@PathVariable Long id) {
        boolean b = deptService.removeById(id);
        return b ? R.ok() : R.fail();
    }


}
