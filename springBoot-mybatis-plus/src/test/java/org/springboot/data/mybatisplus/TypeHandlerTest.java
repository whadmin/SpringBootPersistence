package org.springboot.data.mybatisplus;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springboot.data.mybatisplus.entity.typeHandler.Currency;
import org.springboot.data.mybatisplus.entity.typeHandler.OtherInfo;
import org.springboot.data.mybatisplus.entity.User;
import org.springboot.data.mybatisplus.entity.typeHandler.Wallet;
import org.springboot.data.mybatisplus.enums.AgeEnum;
import org.springboot.data.mybatisplus.enums.GenderEnum;
import org.springboot.data.mybatisplus.enums.GradeEnum;
import org.springboot.data.mybatisplus.enums.UserState;
import org.springboot.data.mybatisplus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 21:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeHandlerTest {

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

        Wallet wallet = new Wallet();
        wallet.setName("qianbao");

        Currency currency1 = new Currency();
        currency1.setAmount(10d);
        currency1.setType("RMB");

        Currency currency2 = new Currency();
        currency2.setAmount(10d);
        currency2.setType("USD");

        ArrayList<Currency> lists = Lists.newArrayList(currency1,currency2);
        wallet.setCurrencyList(lists);

        user.setWallet(wallet);

        OtherInfo info = new OtherInfo();
        info.setCity("beijin");
        info.setSex("nan");
        user.setOtherInfo(info);

        assertThat(mapper.insert(user)).isGreaterThan(0);
        Long id = user.getId();

        User s_user = mapper.selectById(id);
        System.err.println(s_user);
    }
}
