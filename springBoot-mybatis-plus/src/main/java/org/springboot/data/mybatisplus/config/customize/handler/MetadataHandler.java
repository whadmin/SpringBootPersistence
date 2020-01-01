package org.springboot.data.mybatisplus.config.customize.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springboot.data.mybatisplus.entity.ParentEntity;
import org.springboot.data.mybatisplus.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * MetaObjectHandler 自动填充处理实现类，
 * <p>
 * 在外部配置下面entity的插入和更新时
 * mybatis-plus.type-aliases-package: org.springboot.data.org.springboot.data.mybatisplus.mapper.entity
 * <p>
 * 判断是否设置 @TableField(fill = FieldFill.XXX)填充规则，如果设置则回调接口的实现进行拦截
 * 统一处理createUser,createTime,updateUser,updateTime四个通用字段自动填充
 * <p>
 * 框架中
 * MetadataHandler 作为bean 会自动注入到
 * <bean id="globalConfig" class="com.baomidou.org.springboot.data.mybatisplus.mapper.entity.GlobalConfiguration">
 * <!-- 公共字段填充器 -->
 * <property name="metaObjectHandler">
 * <bean class="com.springdb.org.springboot.data.mybatisplus.mapper.common.CommonMetaObjectHandler" />
 * </property>
 * </bean>
 *
 * @author: wuhao.w
 * @date: 2019-12-31
 * @since: 4.0.0
 */
@Slf4j
@Component
public class MetadataHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (log.isDebugEnabled()) {
            log.debug(">>>>>>>>>>>> start insert fill ....");
        }
        if (metaObject.getOriginalObject() instanceof ParentEntity) {
            String operator = ((ParentEntity) metaObject.getOriginalObject()).getCreateUser();
            if (StringUtils.isBlank(operator)) {
                throw new RuntimeException("save_user is required");
            }
            Integer createTime = ((ParentEntity) metaObject.getOriginalObject()).getCreateTime();
            createTime = createTime != null && createTime > 0 ? createTime : TimeUtil.currentTimeStampInt();
            this.setFieldValByName("createTime", createTime, metaObject);
            this.setFieldValByName("updateUser", operator, metaObject);
            this.setFieldValByName("updateTime", createTime, metaObject);
            this.setFieldValByName("updateUser", operator, metaObject);
            this.setFieldValByName("isValid", (byte) 1, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (log.isDebugEnabled()) {
            log.debug(">>>>>>>>>>>> start update fill ....");
        }
        if (metaObject.getOriginalObject() instanceof Map) {
            Map mapObject = (Map) metaObject.getOriginalObject();
            Object entity = null;
            if (mapObject.containsKey("param_1")) {
                entity = mapObject.get("param_1");
            }
            if (mapObject.containsKey(Constants.ENTITY)) {
                entity = mapObject.get(Constants.ENTITY);
            }
            if (entity instanceof ParentEntity) {
                String operator = ((ParentEntity) entity).getUpdateUser();
                if (StringUtils.isBlank(operator)) {
                    throw new RuntimeException("MetadataHandler error[update_user is required]");
                }

                Integer updateTime = ((ParentEntity) entity).getUpdateTime();
                updateTime = updateTime != null && updateTime > 0 ? updateTime : TimeUtil.currentTimeStampInt();

                this.setFieldValByName("updateTime", updateTime, metaObject);
                this.setFieldValByName("updateUser", operator, metaObject);
            } else if (entity instanceof Map) {

                Object updateUser = ((HashMap) entity).get("updateUser");
                if (Objects.isNull(updateUser)) {
                    throw new RuntimeException("MetadataHandler error[update_user is required]");
                }

                Integer updateTime = null;
                if (((HashMap) entity).containsKey("updateTime")) {
                    updateTime = (Integer) ((HashMap) entity).get("updateTime");
                    updateTime = updateTime != null && updateTime > 0 ? updateTime : TimeUtil.currentTimeStampInt();
                }

                this.setFieldValByName("updateTime", updateTime, metaObject);
                this.setFieldValByName("updateUser", updateUser, metaObject);
            }
        }
    }
}
