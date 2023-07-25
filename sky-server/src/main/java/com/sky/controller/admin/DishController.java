package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api("菜品管理")
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 添加菜品
     *
     * @param dishDTO
     * @return
     */
    @ApiOperation("添加菜品")
    @PostMapping
    public Result add(@RequestBody DishDTO dishDTO) {

        dishService.add(dishDTO);
        return Result.success();
    }

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result page(DishPageQueryDTO dishPageQueryDTO) {
        PageResult p = dishService.page(dishPageQueryDTO);
        return Result.success(p);
    }

    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @ApiOperation("删除菜品")
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        dishService.delete(ids);
        return Result.success();
    }

    /**
     * 根据Id查询数据
     *
     * @param id
     * @return
     */
    @ApiOperation("根据Id查询数据")
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Long id) {
        DishVO dishVO = dishService.selectById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品数据
     *
     * @param dishDTO
     * @return
     */
    @ApiOperation("修改菜品数据")
    @PutMapping
    public Result update(@RequestBody DishDTO
                                 dishDTO) {
        dishService.update(dishDTO);
        return Result.success();
    }
}
