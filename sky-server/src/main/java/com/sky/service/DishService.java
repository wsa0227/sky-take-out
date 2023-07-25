package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void add(DishDTO dishDTO);


    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    void delete(List<Long> ids);

    DishVO selectById(Long id);

    void update(DishDTO dishDTO);

    List<Dish> list(Long category_id);
}
