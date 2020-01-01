package org.springboot.data.mybatisplus.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuhao.w
 * @since 2019-12-32
 *
 * 配置MybatisPlus 自定义扩展功能工具
 */
@Configuration
@MapperScan("org.springboot.data.mybatisplus.mapper")
public class MybatisPlusConfig {

//    /**
//     * 该插件 3.1.2 后版本废弃，推荐使用第三方SPY扩展 执行SQL分析打印 功能
//     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor(){
//        //启用性能分析插件
//        return new PerformanceInterceptor();
//    }


    /**
     * SQL 执行分析拦截器
     *   拦截处理 DELETE UPDATE 语句， 防止小白或者恶意 delete update 全表操作！
     *
     *   示例  SqlExplainInterceptorTest
     *
     *
     * 声明为Spring Bean 对象 自动注入到 MybatisSqlSessionFactoryBean.plugins 数组中
     */
    @Bean
    @Profile({"dev"}) // 设置 dev 环境开启
    public SqlExplainInterceptor sqlExplainInterceptor(){
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        //设置BlockAttackSqlParser，执行语句不存在where则报异常
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);
        return sqlExplainInterceptor;
    }


    /**
     * 乐观锁析拦截器
     *   配合地方:
     *        1  实体中选择乐观锁属性添加 @Version 注解 （支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime）
     *   需要注意的地方：
     *        1 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     *        2 在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
     *   示例  OptLockerTest
     *        整数类型下 newVersion = oldVersion + 1
     *        newVersion 会回写到 entity 中
     *
     * 声明为Spring Bean 对象 自动注入到 MybatisSqlSessionFactoryBean.plugins 数组中
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


    /**
     * 分页插件拦截器
     *    示例  OptLockerTest
     *
     *
     * 声明为Spring Bean 对象 自动注入到 MybatisSqlSessionFactoryBean.plugins 数组中
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 开启 count 的 join 优化,只针对 left join !!!
        return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
    }
}
