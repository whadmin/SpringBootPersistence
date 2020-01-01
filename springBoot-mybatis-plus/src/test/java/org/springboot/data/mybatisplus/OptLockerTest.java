package org.springboot.data.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.data.mybatisplus.entity.User;
import org.springboot.data.mybatisplus.enums.AgeEnum;
import org.springboot.data.mybatisplus.enums.GenderEnum;
import org.springboot.data.mybatisplus.enums.GradeEnum;
import org.springboot.data.mybatisplus.enums.UserState;
import org.springboot.data.mybatisplus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 *     仅支持 updateById(id) 与 update(entity, wrapper) 方法
 * </p>
 *
 * @author yuxiaobin
 * @date 2018/8/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OptLockerTest {

    @Autowired
    UserMapper mapper;

    @Test
    public void testUpdateByIdSucc() {
        User user = new User();
        user.setName("小羊");
        user.setRealName("wuhao.w");
        user.setAge(AgeEnum.ONE);
        user.setGrade(GradeEnum.HIGH);
        user.setGender(GenderEnum.FEMALE);
        user.setUserState(UserState.ACTIVE);
        user.setEmail("abc@mp.com");
        user.setCreateUser("wuhao.w");
        user.setVersion(1);
        assertThat(mapper.insert(user)).isGreaterThan(0);
        Long id = user.getId();

        User userUpdate = new User();
        userUpdate.setId(id);
        userUpdate.setAge(AgeEnum.TWO);
        userUpdate.setVersion(1);
        userUpdate.setUpdateUser("xiaoxiao");
        Assert.assertEquals("Should update success", 1, mapper.updateById(userUpdate));
        Assert.assertEquals("Should version = version+1", 2, userUpdate.getVersion().intValue());
    }

    @Test
    public void testUpdateByIdFail() {
        User user = new User();
        user.setName("小羊");
        user.setRealName("wuhao.w");
        user.setAge(AgeEnum.ONE);
        user.setGrade(GradeEnum.HIGH);
        user.setGender(GenderEnum.FEMALE);
        user.setUserState(UserState.ACTIVE);
        user.setEmail("abc@mp.com");
        user.setCreateUser("wuhao.w");
        user.setVersion(1);
        assertThat(mapper.insert(user)).isGreaterThan(0);
        Long id = user.getId();

        User userUpdate = new User();
        userUpdate.setId(id);
        userUpdate.setAge(AgeEnum.TWO);
        userUpdate.setVersion(0);
        userUpdate.setUpdateUser("xiaoxiao");
        Assert.assertEquals("Should update failed due to incorrect version(actually 1, but 0 passed in)", 0, mapper.updateById(userUpdate));
    }

    @Test
    public void testUpdateByIdSuccWithNoVersion() {
        User user = new User();
        user.setName("小羊");
        user.setRealName("wuhao.w");
        user.setAge(AgeEnum.ONE);
        user.setGrade(GradeEnum.HIGH);
        user.setGender(GenderEnum.FEMALE);
        user.setUserState(UserState.ACTIVE);
        user.setEmail("abc@mp.com");
        user.setCreateUser("wuhao.w");
        user.setVersion(1);
        assertThat(mapper.insert(user)).isGreaterThan(0);
        Long id = user.getId();

        User userUpdate = new User();
        userUpdate.setId(id);
        userUpdate.setAge(AgeEnum.TWO);
        userUpdate.setVersion(null);
        userUpdate.setUpdateUser("xiaoxiao");
        Assert.assertEquals("Should update success as no version passed in", 1, mapper.updateById(userUpdate));

        User updated = mapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, id).select(User::getVersion,User::getAge));
        Assert.assertEquals("Version not changed", 1, updated.getVersion().intValue());
        Assert.assertEquals("Age updated", AgeEnum.TWO.getValue(), updated.getAge().getValue());
    }

    /**
     * 批量更新带乐观锁
     * <p>
     * 在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
     */
    @Test
    public void testUpdateByEntitySucc() {

        User user = new User();
        user.setName("小羊");
        user.setRealName("wuhao.w");
        user.setAge(AgeEnum.ONE);
        user.setGrade(GradeEnum.HIGH);
        user.setGender(GenderEnum.FEMALE);
        user.setUserState(UserState.ACTIVE);
        user.setEmail("abc@mp.com");
        user.setCreateUser("wuhao.w");
        user.setVersion(1);
        assertThat(mapper.insert(user)).isGreaterThan(0);
        assertThat(mapper.insert(user)).isGreaterThan(0);

        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.eq("name", "小羊");
        //int count = mapper.selectCount(ew);

        User userUpdate1= new User();
        userUpdate1.setVersion(1);
        userUpdate1.setUpdateUser("xiaoxiao1");

        User userUpdate2 = new User();
        userUpdate2.setVersion(2);
        userUpdate2.setUpdateUser("xiaoxiao2");

        Assert.assertEquals("updated records should be same", 2, mapper.update(userUpdate1, ew));
        Assert.assertEquals("updated records should be same", 0, mapper.update(userUpdate2, ew));
    }

}
