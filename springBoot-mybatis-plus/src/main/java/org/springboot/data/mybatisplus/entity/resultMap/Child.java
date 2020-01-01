package org.springboot.data.mybatisplus.entity.resultMap;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 19:35
 */
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springboot.data.mybatisplus.entity.resultMap.Man;
import org.springboot.data.mybatisplus.entity.resultMap.Woman;

/**
 * @author miemie
 * @since 2019-11-27
 */
@Data
@TableName(autoResultMap = true) //不配合 typeHandler 或 numericScale 使用无意义,演示而已
public class Child {

    private Long id;

    private String name;

    private Long laoHanId;

    private Long laoMaId;

    @TableField(exist = false)
    private Man laoHan;

    @TableField(exist = false)
    private Woman laoMa;
}