package org.springboot.data.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.data.mybatisplus.entity.User;
import org.springboot.data.mybatisplus.mapper.UserMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 15:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicDeleteTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testLogicDeleteById() {
        userMapper.deleteById(1);
    }

    @Test
    public void testLogicDeleteBatchIds() {
        userMapper.deleteBatchIds(Arrays.asList(1, 2, 3));
    }

    @Test
    public void testLogicDelete() {
        userMapper.delete(new QueryWrapper<User>().eq("age", 1));
    }
}
