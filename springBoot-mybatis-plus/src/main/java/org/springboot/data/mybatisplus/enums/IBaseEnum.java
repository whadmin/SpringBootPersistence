package org.springboot.data.mybatisplus.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

/**
 * <p>
 *     #1500 github
 * </p>
 *
 * @author yuxiaobin
 * @date 2019/8/29
 */
public interface IBaseEnum<T extends Serializable> extends IEnum<T>{

    String getDescription();
}
