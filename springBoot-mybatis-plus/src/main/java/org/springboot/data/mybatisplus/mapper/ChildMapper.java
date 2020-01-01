package org.springboot.data.mybatisplus.mapper;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 19:37
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springboot.data.mybatisplus.entity.resultMap.Child;

import java.util.List;

/**
 * @author miemie
 * @since 2019-11-27
 */
public interface ChildMapper extends BaseMapper<Child> {

    Child selectLinkById(Long id);

    @Select("select * from child where lao_han_id = #{id}")
    List<Child> selectByLaoHanId(Long id);

    @Select("select * from child where lao_ma_id = #{id}")
    List<Child> selectByLaoMaId(Long id);
}
