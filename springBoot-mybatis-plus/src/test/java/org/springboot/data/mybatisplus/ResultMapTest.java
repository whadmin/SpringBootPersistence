package org.springboot.data.mybatisplus;

import javax.annotation.Resource;
import static org.assertj.core.api.Assertions.assertThat;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springboot.data.mybatisplus.entity.resultMap.Child;
import org.springboot.data.mybatisplus.entity.resultMap.Man;
import org.springboot.data.mybatisplus.entity.resultMap.Woman;
import org.springboot.data.mybatisplus.mapper.ChildMapper;
import org.springboot.data.mybatisplus.mapper.ManMapper;
import org.springboot.data.mybatisplus.mapper.WomanMapper;

import java.util.List;

/**
 * @Author: wuhao.w
 * @Date: 2019/12/31 19:40
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultMapTest {

    @Resource
    private ChildMapper childMapper;
    @Resource
    private ManMapper manMapper;
    @Resource
    private WomanMapper womanMapper;

    @Test
    public void t_c() {
        final Child child = childMapper.selectLinkById(1L);
        log.info("child: {}", child);
        assertThat(child).isNotNull();
        final Man laoHan = child.getLaoHan();
        assertThat(laoHan).isNotNull();
        assertThat(laoHan.getName()).isNotBlank();
        final Woman laoMa = child.getLaoMa();
        assertThat(laoMa).isNotNull();
        assertThat(laoMa.getName()).isNotBlank();
    }

    @Test
    public void t_m() {
        final Man man = manMapper.selectLinkById(1L);
        log.info("man: {}", man);
        assertThat(man).isNotNull();
        assertThat(man.getName()).isNotBlank();
        final Woman laoPo = man.getLaoPo();
        assertThat(laoPo).isNotNull();
        assertThat(laoPo.getName()).isNotBlank();
        final List<Child> waWa = man.getWaWa();
        assertThat(waWa).isNotEmpty();
        waWa.forEach(i -> assertThat(i.getName()).isNotBlank());
    }

    @Test
    public void t_w() {
        final Woman woman = womanMapper.selectLinkById(1L);
        log.info("woman: {}", woman);
        assertThat(woman).isNotNull();
        assertThat(woman.getName()).isNotBlank();
        final Man laoGong = woman.getLaoGong();
        assertThat(laoGong).isNotNull();
        assertThat(laoGong.getName()).isNotBlank();
        final List<Child> waWa = woman.getWaWa();
        assertThat(waWa).isNotEmpty();
        waWa.forEach(i -> assertThat(i.getName()).isNotBlank());
    }
}