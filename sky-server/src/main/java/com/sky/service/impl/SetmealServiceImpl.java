package com.sky.service.impl;

import com.aliyun.oss.AliyunossUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotion.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishesMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishesMapper setmealDishesMapper;
    @Autowired
    private AliyunossUtils aliyunossUtils;

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void add(SetmealDTO setmealDTO) {
//        新建vo
        Setmeal setmeal = new Setmeal();
//        拷贝数据
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.add(setmeal);

//        补充数据
//             公共字段mapper加
//                套餐菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmeal.getId());
                setmealDishesMapper.add(setmealDish);
            }


        }

    }

    /**
     * 套餐分页
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> p = setmealMapper.page(setmealPageQueryDTO);
        PageResult pageResult = new PageResult(p.getTotal(), p.getResult());
        return pageResult;
    }

    /**
     * 删除套餐
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteByIds(List<Integer> ids) {
//删除套餐
        for (Integer id : ids) {
            Setmeal setmealById = setmealMapper.findSetmealById(id);
            if (setmealById != null) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
            setmealMapper.deleteById(id);
//            删除套餐关系表中的数据
            setmealDishesMapper.deleteById((long) id);
        }
    }

    /**
     * 根据Id查询套餐
     *
     * @param id
     * @return
     */
    @Override
    public SetmealVO selectById(Integer id) {
        Setmeal setmeal = setmealMapper.selectSetmealById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        List<SetmealDish> list = setmealDishesMapper.selectById(id);
        if (list != null && list.size() > 0) {
            setmealVO.setSetmealDishes(list);
        }
        return setmealVO;
    }

    /**
     * 更新套餐
     *
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            setmealDishesMapper.deleteById(setmealDTO.getId());
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmeal.getId());
                setmealDishesMapper.add(setmealDish);
            }
        }

    }

    /**
     * 修改售卖状态
     *
     * @param status
     * @param id
     */
    @Override
    @ApiOperation("修改启售状态")
    public void setStatus(Integer status, long id) {
        Setmeal s = Setmeal.builder().id(id).status(status).build();
        setmealMapper.update(s);
    }
}
