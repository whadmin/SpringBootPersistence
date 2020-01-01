package org.springboot.data.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.springboot.data.mybatisplus.entity.User;
import org.springboot.data.mybatisplus.enums.AgeEnum;
import org.springboot.data.mybatisplus.enums.GenderEnum;
import org.springboot.data.mybatisplus.enums.GradeEnum;
import org.springboot.data.mybatisplus.enums.UserState;
import org.springboot.data.mybatisplus.mapper.UserMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 14:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlExplainInterceptorTest {

    @Resource
    private UserMapper mapper;

    @Test
    public void test(){
        try {
            mapper.deleteUserNoWhere(new User());
        }catch (MyBatisSystemException e){
            System.out.println(e);
        }

        try {
            mapper.updateUserNoWhere(new User().setName("mi").setAge(AgeEnum.ONE));
        }catch (MyBatisSystemException e){
            System.out.println(e);
        }
    }
}
