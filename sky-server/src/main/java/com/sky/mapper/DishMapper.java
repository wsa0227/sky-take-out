package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotion.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 添加新的菜品
     *
     * @param dish
     */
//    @Options(useGeneratedKeys = true,keyProperty = "id")
    @AutoFill(OperationType.INSERT)
//    @Insert("insert into dish values(#{name}, #{categoryId}, #{price}, #{image}, #{description}, #{createTime}, #{updateTime}, #{createUser},\n" +
//            "                #{updateUser}, #{status})")
    void add(Dish dish);

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品数据
     *
     * @param id
     * @return
     */
    @Select("select * from dish where id =#{id} ")
    Dish selectId(Long id);

    /**
     * 根据id删除菜品
     *
     * @param id
     */
    @Delete("delete from dish where id =#{id}")
    void deleteById(Long id);

    /**
     * 修改菜品
     *
     * @param dish
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
}
