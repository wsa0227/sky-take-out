package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishesMapper {
    @Insert("insert into setmeal_dish values(null,#{setmealId},#{dishId},#{name},#{price},#{copies})  ")
    void add(SetmealDish setmealDish);

    @Select("select * from setmeal_dish where setmeal_id =#{id} ")
    List<SetmealDish> selectById(Integer id);

    @Delete("delete from setmeal_dish where setmeal_id=#{id}")
    void deleteById(Long id);
}
