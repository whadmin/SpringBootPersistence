package org.springboot.data.mybatisplus.entity.resultMap;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 19:35
 */
@Data
@TableName(resultMap = "m_b") // 对应xml里的 id
public class Man {

    private Long id;

    private String name;

    private Long laoPoId;

    @TableField(exist = false)
    private Woman laoPo;

    @TableField(exist = false)
    private List<Child> waWa;
}