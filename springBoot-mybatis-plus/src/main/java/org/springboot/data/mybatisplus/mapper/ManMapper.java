package org.springboot.data.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springboot.data.mybatisplus.entity.resultMap.Man;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 19:37
 */
public interface ManMapper extends BaseMapper<Man> {

    Man selectLinkById(Long id);
}