package org.springboot.data.mybatisplus;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.data.mybatisplus.entity.page.MyPage;
import org.springboot.data.mybatisplus.entity.param.ParamSome;
import org.springboot.data.mybatisplus.entity.User;
import org.springboot.data.mybatisplus.entity.resultMap.UserAddress;
import org.springboot.data.mybatisplus.enums.AgeEnum;
import org.springboot.data.mybatisplus.mapper.UserMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author wuhao.w
 * @since 2020-01-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaginationTest {

    @Resource
    private UserMapper mapper;

    /**
     * 内置分页
     */
    @Test
    public void internalPagination() {
        Page<User> page = new Page<>(1, 2);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().ge(User::getAge, AgeEnum.ONE).orderByAsc(User::getAge);

        IPage<User> result = mapper.selectPage(page, wrapper);

        System.out.println("总条数 ------> " + result.getTotal());
        System.out.println("当前页数 ------> " + result.getCurrent());
        System.out.println("当前每页显示数 ------> " + result.getSize());
        System.out.println("当前查询数量 ------> " + result.getRecords().size());
        System.out.println("当前查询数据第一条 ------> " + result.getRecords().get(0));
        System.out.println("当前查询数据第二条 ------> " + result.getRecords().get(1));


        IPage<Map<String, Object>> resultMap = mapper.selectMapsPage(page, wrapper);
        System.out.println("总条数 ------> " + resultMap.getTotal());
        System.out.println("当前页数 ------> " + resultMap.getCurrent());
        System.out.println("当前每页显示数 ------> " + resultMap.getSize());
        System.out.println("当前查询数量 ------> " + resultMap.getRecords().size());
        System.out.println("当前查询数据第一条 ------> " + resultMap.getRecords().get(1));


        System.out.println("json 正反序列化 begin");
        String json = JSON.toJSONString(page);
        Page<User> page1 = JSON.parseObject(json, Page.class);
        print(page1.getRecords());
        System.out.println("json 正反序列化 end");
    }


    /**
     * 自定义 XML 分页
     */
    @Test
    public void customizePagination() {
        MyPage<User> myPage = new MyPage<User>(1, 5).setSelectInt(1).setSelectStr("小羊");

        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("yihao", 1);
        map.put("erhao", "小羊");

        ParamSome paramSome = new ParamSome();
        paramSome.setErhao("小羊");
        paramSome.setYihao(1);


        System.out.println("----- 自定义 XML myPageParamSomeReturnMyPage分页 ------");
        MyPage<User> userMyPage1 = mapper.myPageParamSomeReturnMyPage(myPage, paramSome);
        assertThat(myPage).isSameAs(userMyPage1);
        System.out.println("总条数 ------> " + userMyPage1.getTotal());
        System.out.println("当前页数 ------> " + userMyPage1.getCurrent());
        System.out.println("当前每页显示数 ------> " + userMyPage1.getSize());
        print(userMyPage1.getRecords());
        System.out.println("----- 自定义 XML myPageParamSomeReturnMyPage分页 ------");


        System.out.println("----- 自定义 XML myPageParamSomeReturnMyPage分页 ------");
        MyPage<User> userMyPage2 = mapper.myPageMapReturnMyPage(myPage, map);
        assertThat(myPage).isSameAs(userMyPage2);
        System.out.println("总条数 ------> " + userMyPage2.getTotal());
        System.out.println("当前页数 ------> " + userMyPage2.getCurrent());
        System.out.println("当前每页显示数 ------> " + userMyPage2.getSize());
        print(userMyPage2.getRecords());
        System.out.println("----- 自定义 XML myPageParamSomeReturnMyPage分页 ------");


        System.out.println("----- 自定义 XML myPageParamSomeReturnList分页 ------");
        ;
        print(mapper.myPageParamSomeReturnList(myPage, paramSome));
        System.out.println("----- 自定义 XML myPageParamSomeReturnList分页 ------");

        System.out.println("----- 自定义 XML myPageMapReturnList分页 ------");
        ;
        print(mapper.myPageMapReturnList(myPage, map));
        System.out.println("----- 自定义 XML myPageMapReturnList分页 ------");
    }

    @Test
    public void rowBoundsTest() {
        RowBounds rowBounds = new RowBounds(0, 5);

        HashMap<Object, Object> map = Maps.newHashMap();
        map.put("yihao", 1);
        map.put("erhao", "小羊");

        List<User> list = mapper.rowBoundList(rowBounds, map);
        System.out.println("list.size=" + list.size());
    }


    @Test
    public void tests2() {
        /* 下面的 left join 不会对 count 进行优化,因为 where 条件里有 join 的表的条件 */
        MyPage<UserAddress> myPage = new MyPage<>(1, 5);
        myPage.setSelectInt(1).setSelectStr("小羊");
        MyPage<UserAddress> userChildrenMyPage = mapper.myPageleftjoin(myPage);
        List<UserAddress> records = userChildrenMyPage.getRecords();
        records.forEach(System.out::println);

        /* 下面的 left join 会对 count 进行优化,因为 where 条件里没有 join 的表的条件 */
        myPage = new MyPage<UserAddress>(1, 5).setSelectInt(1);
        userChildrenMyPage = mapper.myPageleftjoin(myPage);
        records = userChildrenMyPage.getRecords();
        records.forEach(System.out::println);
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }

}
