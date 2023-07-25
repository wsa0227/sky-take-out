package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 插入口味
     * @param flavor
     */
    @Insert("insert into dish_flavor values (null,#{dishId},#{name},#{value})")
    void add(DishFlavor flavor);

    /**
     * 根据菜品id删除口味
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteById(Long id);

    /**
     * 根据Id查询菜品口味
     * @param id
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> selectById(Long id);
}
