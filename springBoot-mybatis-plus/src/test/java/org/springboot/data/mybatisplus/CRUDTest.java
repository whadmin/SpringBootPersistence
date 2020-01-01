package org.springboot.data.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.data.mybatisplus.entity.User;
import org.springboot.data.mybatisplus.enums.AgeEnum;
import org.springboot.data.mybatisplus.enums.GenderEnum;
import org.springboot.data.mybatisplus.enums.GradeEnum;
import org.springboot.data.mybatisplus.enums.UserState;
import org.springboot.data.mybatisplus.mapper.UserMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * 内置 CRUD 演示
 * </p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CRUDTest {

    @Resource
    private UserMapper mapper;

    @Test
    public void insert() {
        User user = new User();
        user.setName("小羊");
        user.setRealName("wuhao.w");
        user.setAge(AgeEnum.ONE);
        user.setGrade(GradeEnum.HIGH);
        user.setGender(GenderEnum.FEMALE);
        user.setUserState(UserState.ACTIVE);
        user.setEmail("abc@mp.com");
        user.setCreateUser("wuhao.w");
        assertThat(mapper.insert(user)).isGreaterThan(0);
        assertThat(user.getId()).isNotNull();

        Assert.assertTrue(mapper.delete(new QueryWrapper<User>()
                .lambda().eq(User::getAge, AgeEnum.ONE)) > 0);
    }

    @Test
    public void deleteById() {
        assertThat(mapper.deleteById(3L)).isGreaterThan(0);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> objectObjectMap = Maps.newHashMap();
        objectObjectMap.put("name", "Billie");
        assertThat(mapper.deleteByMap(objectObjectMap)).isGreaterThan(0);
    }

    @Test
    public void deleteByQueryWrapper() {
        assertThat(mapper.delete(new QueryWrapper<User>()
                .lambda().eq(User::getName, "Sandy"))).isGreaterThan(0);
    }

    @Test
    public void updateById() {
        User user = new User().setId(1L).setEmail("abc@c.c");
        user.setUpdateUser("xiaoxiao");
        user.setVersion(null);
        assertThat(mapper.updateById(user)).isGreaterThan(0);
        assertThat(mapper.selectById(1).getEmail()).isEqualTo("abc@c.c");
    }


    @Test
    public void updateByWrappers() {
        assertThat(
                mapper.update(
                        new User().setName("mp"),
                        Wrappers.<User>lambdaUpdate()
                                .set(User::getAge, 3)
                                .eq(User::getId, 2)
                )
        ).isGreaterThan(0);

        User user = mapper.selectById(2);
        assertThat(user.getAge()).isEqualTo(3);
        assertThat(user.getName()).isEqualTo("mp");

        mapper.update(
                null,
                Wrappers.<User>lambdaUpdate().set(User::getEmail, null).eq(User::getId, 2)
        );
        user = mapper.selectById(2);
        assertThat(user.getEmail()).isNull();
        assertThat(user.getName()).isEqualTo("mp");

        mapper.update(
                new User().setEmail("miemie@baomidou.com"),
                new QueryWrapper<User>()
                        .lambda().eq(User::getId, 2)
        );
        user = mapper.selectById(2);
        assertThat(user.getEmail()).isEqualTo("miemie@baomidou.com");

        mapper.update(
                new User().setEmail("miemie2@baomidou.com"),
                Wrappers.<User>lambdaUpdate()
                        .set(User::getAge, null)
                        .eq(User::getId, 2)
        );
        user = mapper.selectById(2);
        assertThat(user.getEmail()).isEqualTo("miemie2@baomidou.com");
        assertThat(user.getAge()).isNull();
    }


    @Test
    public void selectById() {
        mapper.insert(
                new User().setId(10086L)
                        .setName("miemie")
                        .setEmail("miemie@baomidou.com")
                        .setAge(AgeEnum.THREE));
        assertThat(mapper.selectById(10086L).getEmail()).isEqualTo("miemie@baomidou.com");


    }

    @Test
    public void selectOne() {
        mapper.insert(
                new User().setId(10087L)
                        .setName("miemie")
                        .setEmail("miemie@baomidou.com")
                        .setAge(AgeEnum.THREE));

        User user = mapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 10086));
        assertThat(user.getName()).isEqualTo("miemie");
        assertThat(user.getAge()).isEqualTo(3);
    }


    @Test
    public void selectList() {
        mapper.insert(
                new User().setId(10088L)
                        .setName("miemie")
                        .setEmail("miemie@baomidou.com")
                        .setAge(AgeEnum.THREE));

        mapper.selectList(Wrappers.<User>lambdaQuery().select(User::getId).eq(User::getId, 10088L))
                .forEach(x -> {
                    assertThat(x.getId()).isNotNull();
                    assertThat(x.getEmail()).isNull();
                    assertThat(x.getName()).isNull();
                    assertThat(x.getAge()).isNull();
                    System.out.println(x);
                });
        mapper.selectList(new QueryWrapper<User>().select("id", "name").eq("id", 10088L))
                .forEach(x -> {
                    assertThat(x.getId()).isNotNull();
                    assertThat(x.getEmail()).isNull();
                    assertThat(x.getName()).isNotNull();
                    assertThat(x.getAge()).isNull();
                    System.out.println(x);
                });

        mapper.deleteById(10088L);
    }

    @Test
    public void selectPage() {
        IPage<User> page = mapper.selectPage(new Page<>(1, 5), Wrappers.<User>query().orderByAsc("age"));
        assertThat(page).isNotNull();
        assertThat(page.getRecords()).isNotEmpty();
        assertThat(page.getRecords().get(0)).isNotNull();
        System.out.println(page.getRecords().get(0));
    }


    @Test
    public void selectMaps() {
        List<Map<String, Object>> mapList = mapper.selectMaps(Wrappers.<User>query().orderByAsc("age"));
        assertThat(mapList).isNotEmpty();
        assertThat(mapList.get(0)).isNotEmpty();
        System.out.println(mapList.get(0));
    }

    @Test
    public void selectMapsPage() {
        IPage<Map<String, Object>> page = mapper.selectMapsPage(new Page<>(1, 5), Wrappers.<User>query().orderByAsc("age"));
        assertThat(page).isNotNull();
        assertThat(page.getRecords()).isNotEmpty();
        assertThat(page.getRecords().get(0)).isNotEmpty();
        System.out.println(page.getRecords().get(0));
    }


    @Test
    public void orderBy() {
        List<User> users = mapper.selectList(Wrappers.<User>query().orderByAsc("age"));
        assertThat(users).isNotEmpty();
    }

    @Test
    public void testSelectMaxId() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("max(id) as id");
        User user = mapper.selectOne(wrapper);
        System.out.println("maxId=" + user.getId());
        List<User> users = mapper.selectList(Wrappers.<User>lambdaQuery().orderByDesc(User::getId));
        Assert.assertEquals(user.getId().longValue(), users.get(0).getId().longValue());
    }

    @Test
    public void testGroup() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("age, count(*)")
                .groupBy("age");
        List<Map<String, Object>> maplist = mapper.selectMaps(wrapper);
        for (Map<String, Object> mp : maplist) {
            System.out.println(mp);
        }
        /**
         * lambdaQueryWrapper groupBy orderBy
         */
        LambdaQueryWrapper<User> lambdaQueryWrapper = new QueryWrapper<User>().lambda()
                .select(User::getAge)
                .groupBy(User::getAge)
                .orderByAsc(User::getAge);
        for (User user : mapper.selectList(lambdaQueryWrapper)) {
            System.out.println(user);
        }
    }

    @Test
    public void testTableFieldExistFalse() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("age, count(age) as count")
                .groupBy("age");
        List<User> list = mapper.selectList(wrapper);
        list.forEach(System.out::println);
        list.forEach(x -> {
            Assert.assertNull(x.getId());
            Assert.assertNotNull(x.getAge());
        });
        mapper.insert(
                new User().setId(10088L)
                        .setName("miemie")
                        .setEmail("miemie@baomidou.com")
                        .setAge(AgeEnum.THREE));
        User miemie = mapper.selectById(10088L);
        Assert.assertNotNull(miemie);

    }

}
