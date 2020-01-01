package org.springboot.data.mybatisplus.entity.resultMap;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 19:36
 */
@Data
@TableName(autoResultMap = true) //不配合 typeHandler 或 numericScale 使用无意义,演示而已
public class Woman {

    private Long id;

    private String name;

    private Long laoGongId;

    @TableField(exist = false)
    private Man laoGong;

    @TableField(exist = false)
    private List<Child> waWa;
}
