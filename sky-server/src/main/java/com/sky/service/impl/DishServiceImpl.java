package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 增加菜品
     * @param dishDTO
     */
    @Override
    @Transactional
    public void add(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.add(dish);
//        判断是否有口味
        List<DishFlavor> flavors = dishDTO.getFlavors();

        if (flavors != null) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dish.getId());
                dishFlavorMapper.add(flavor);
            }
        }
    }


    /**
     * 分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page1 = dishMapper.page(dishPageQueryDTO);

        PageResult pageResult = new PageResult(page1.getTotal(), page1.getResult());
        return pageResult;
    }

    /**
     * 删除菜品
     *
     * @param ids
     */
    @Override
    @Transactional
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.selectId(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
            List<Long> setmailIds = setmealMapper.selectById(ids);
            if (setmailIds != null && setmailIds.size() > 0) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }

        }
        for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteById(id);
        }

    }

    /**
     * 根据Id查询数据
     * @param id
     * @return
     */
    @Override
    public DishVO selectById(Long id) {
        Dish dish = dishMapper.selectId(id);
        List<DishFlavor> list = dishFlavorMapper.selectById(id);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        if (list != null && list.size() > 0) {
            dishVO.setFlavors(list);
            return dishVO;
        }
        return dishVO;
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        dishMapper.update(dish);

        dishFlavorMapper.deleteById(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishDTO.getId());
                dishFlavorMapper.add(flavor);
            }


        }
    }
}
