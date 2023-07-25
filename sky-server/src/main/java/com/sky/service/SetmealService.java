package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void add(SetmealDTO setmealDTO);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteByIds(List<Integer> ids);

    SetmealVO selectById(Integer id);

    void update(SetmealDTO setmealDTO);

    void setStatus(Integer status,long id);
}
