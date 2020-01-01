package org.springboot.data.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springboot.data.mybatisplus.entity.typeHandler.OtherInfo;
import org.springboot.data.mybatisplus.entity.typeHandler.Wallet;
import org.springboot.data.mybatisplus.enums.AgeEnum;
import org.springboot.data.mybatisplus.enums.GenderEnum;
import org.springboot.data.mybatisplus.enums.GradeEnum;
import org.springboot.data.mybatisplus.enums.UserState;

import java.io.Serializable;

/**
 * <p>
 * 用户实体对应表 user
 * </p>
 *
 * 相关注解：
 *
 * @Data
 *
 * 注解在类上，将类提供的所有属性都添加get、set方法，
 * 并添加、equals、canEquals、hashCode、toString方法
 *
 * @Accessors(chain = true)
 *
 * 使用链式设置属性，set方法返回的是this对象。
 *
 * @TableName
 *
 * 定义对应的表名称(通常用来针对实体和表明不一致的情况),对应数据库名称
 *
 * ...
 *
 * @TableField
 *
 * value ： 关联数据库字段名称(通常用来针对实体和表明不一致的情况)
 *
 * exist ： 当前字段是否存在对应数据库表字段，如果不存在则该属性值在插入跟新时会忽略。
 *          默认 true 存在，false 不存在
 *
 * update ： 强制设置字段更新方式，会忽略应用的设置值，例如下面：
 * assertThat(mapper.updateById(new User().setId(1L).setEmail("ab@c.c").setUpdateCount(null))).isGreaterThan(0);
 *
 * @fill: 标识属性对应表字段需要自动填充方式，存在三种类型
 *
 *       FieldFill.DEFAULT（不填充 默认 ）
 *       FieldFill.INSERT（插入时填充）
 *       FieldFill.UPDATE(更新时填充)
 *       FieldFill.INSERT_UPDATE(插入和更新时填充)
 *
 *       需要注意的是，这里仅仅只是配置标识，具体填充实现需要实现 MetaObjectHandler接口，并注册Spring Bean中
 *
 * @typeHandler 类型转换
 *     MP 内置Json JacksonTypeHandler，FastjsonTypeHandler 两种类型处理器，在一个属性上二选一 也可以同时存在
 *        相当于mybatis配置：
 *        <result column="other_info" jdbcType="VARCHAR" property="otherInfo" typeHandler="com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler" />
 *
 *    注意！！选择对应的 JSON 处理器也必须存在对应依赖包
 *           @TableName(autoResultMap = true)
 *
 * @TableId
 *
 * 标识表唯一主键对应字段数据,在不设置时,默认会将id属性作为对应数据库的唯一主键
 *
 * value ： 关联数据库字段名称(通常用来针对实体和表明不一致的情况)
 *
 * type ：  IdType（生成ID类型枚举类）
 *
 *
 * @枚举
 *
 * 支持需要配置：
 *    1  mybatis-plus:
 *           type-enums-package: org.springboot.data.mybatisplus.enums
 *    2  原生枚举支持
 *       mybatis-plus:
 *           configuration:
 *              # 设置默认枚举类型处理器
 *               default-enum-type-handler: org.apache.ibatis.type.EnumOrdinalTypeHandler
 *
 * 原生枚举  GenderEnum  默认使用枚举值顺序： 0：MALE， 1：FEMALE
 * 内置枚举1  AgeEnum 实现  IEnum<Integer> 接口，通过T getValue()方法获取枚举数据库存储值
 * 内置枚举2  GradeEnum 通过  @EnumValue注解 标识属性作为数据库存储值
 */
@Data
@Accessors(chain = true)
@TableName(value = "mi_user")
public class User extends ParentEntity<User> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "email")
    private String email;

    @TableField(exist = false)
    private String remark;

    @TableField(value = "real_name", update = "siteId+name")
    private String realName;

    @TableField(value = "age")
    private AgeEnum age;

    @TableField(value = "gender")
    private GenderEnum gender;

    @TableField(value = "grade")
    private GradeEnum grade;

    @TableField(value = "user_state")
    private UserState userState;

    @Version
    @TableField(select = true, fill = FieldFill.INSERT)
    private Integer version;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Wallet wallet;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private OtherInfo otherInfo;


    @Override
    protected Serializable pkVal() {
        /**
         * AR 模式这个必须有，否则 xxById 的方法都将失效！
         * 另外 UserMapper 也必须 AR 依赖该层注入，有可无 XML
         */
        return id;
    }
}
