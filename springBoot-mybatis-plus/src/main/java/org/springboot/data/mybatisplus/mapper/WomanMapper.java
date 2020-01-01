package org.springboot.data.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springboot.data.mybatisplus.entity.resultMap.Woman;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 19:38
 */
public interface WomanMapper extends BaseMapper<Woman> {

    Woman selectLinkById(Long id);
}
