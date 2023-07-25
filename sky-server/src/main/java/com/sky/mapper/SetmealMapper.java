package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotion.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    //    @Select("select setmeal_id from setmeal_dish where dish_id in #{ids} ")
    List<Long> selectById(List<Long> ids);

    /**
     * 新增套餐
     *
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    void add(Setmeal setmeal);

    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据套餐id查询返回状态为一的总数
     * 用于判断该套餐是否是起售
     *
     * @param id
     * @return
     */

    @Select("select * from setmeal where id=#{id} and status=1")
    Setmeal findSetmealById(Integer id);

    /**
     * 删除套餐根据id
     *
     * @param id
     */
    @Delete("delete from setmeal where id=#{id} ")
    void deleteById(Integer id);

    @Select("select *  from setmeal where id = #{id}")
    Setmeal selectSetmealById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
}
