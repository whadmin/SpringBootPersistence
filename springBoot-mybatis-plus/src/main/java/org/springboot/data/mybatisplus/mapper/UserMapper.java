package org.springboot.data.mybatisplus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springboot.data.mybatisplus.entity.page.MyPage;
import org.springboot.data.mybatisplus.entity.param.ParamSome;
import org.springboot.data.mybatisplus.entity.User;
import org.springboot.data.mybatisplus.entity.resultMap.UserAddress;

import java.util.List;
import java.util.Map;

/**
 * 自定义分页MyPage，返回MyPage
 * 1  自定义 page 类必须放在入参第一位
 * 2  返回可以是 IPage<T> 也可以是 MyPage<T> 同时还可以是 List<T>
 * 3  参数可以是 自定义参数对象 ParamSome paramSome 也可以是 Map
 * 返回值可以用 IPage<T> 接收 也可以使用入参的 MyPage<T> 接收
 *
 * 自定义方法
 * 1  实现自定义方法 DeleteAll extends AbstractMethod
 * 2  自定义方法注入
 *
 * @author wuhao.w
 * @since 2020-01-01
 */
public interface UserMapper extends BaseMapper<User> {


    /**
     * 自定义方法
     */
    void deleteAll();

    /**
     * 更新所有用户（测试被SQL 执行分析拦截器 SqlExplainInterceptor 拦截）
     */
    void updateUserNoWhere(User user);

    /**
     * 删除所有用户（测试被SQL 执行分析拦截器 SqlExplainInterceptor 拦截）
     */
    void deleteUserNoWhere(User user);

    MyPage<UserAddress> myPageleftjoin(@Param("pg") MyPage<UserAddress> myPage);

    MyPage<User> myPageParamSomeReturnMyPage(@Param("pg") MyPage<User> myPage, @Param("ps") ParamSome paramSome);

    MyPage<User> myPageMapReturnMyPage(@Param("pg") MyPage<User> myPage, @Param("map") Map param);

    List<User> myPageParamSomeReturnList(@Param("pg") MyPage<User> myPage, @Param("ps") ParamSome paramSome);

    List<User> myPageMapReturnList(@Param("pg") MyPage<User> myPage, @Param("map") Map param);

    List<User> rowBoundList(RowBounds rowBounds,@Param("map")  Map map);
}
