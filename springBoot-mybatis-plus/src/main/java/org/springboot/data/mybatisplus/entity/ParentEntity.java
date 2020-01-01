package org.springboot.data.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 公共字段
 * @Author: wuhao.w
 * @Date: 2019/12/27 16:22
 *
 * 继承 Model 支持 AR模型
 */
@Data
@Accessors(chain = true)
public class ParentEntity<T extends ParentEntity<?>>  extends Model<T> {

    /**
     * 站点ID
     */
    private Integer siteId;

    /**
     * 是否有效 0: 无效 1: 有效
     */
    @TableLogic(value = "1",delval = "0")
    @TableField(select = false,fill = FieldFill.INSERT)
    private Byte isValid;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

    /**
     * 最后更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;



}
