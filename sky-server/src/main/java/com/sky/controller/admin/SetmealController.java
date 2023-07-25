package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

@RestController
@Api("套餐管理")
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     *
     * @param setmealDTO
     * @return
     */
    @ApiOperation("新增套餐")
    @PostMapping
    public Result add(@RequestBody SetmealDTO setmealDTO) {

        setmealService.add(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     *
     * @return
     */
    @ApiOperation("删除菜品（批量）")
    @DeleteMapping
    public Result deleteByIds(@RequestParam List<Integer> ids) {
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 回显数据
     *
     * @return
     */
    @ApiOperation("回显数据")
    @GetMapping("/{id}")
    public Result<SetmealVO> selectById(@PathVariable Integer id) {
        SetmealVO setmealVO = setmealService.selectById(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐
     *
     * @return
     */
    @ApiOperation("修改套餐")
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {

        setmealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * 修改售卖状态
     * @param status
     * @param id
     * @return
     */
    @ApiOperation("修改起售停售")
    @PostMapping("/status/{status}")
    public Result setStatus(@PathVariable Integer status ,long id) {

        setmealService.setStatus(status,id);
        return Result.success();
    }

}
