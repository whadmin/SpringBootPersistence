package org.springboot.data.mybatisplus.entity.resultMap;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springboot.data.mybatisplus.entity.Address;
import org.springboot.data.mybatisplus.entity.User;
import java.util.List;

/**
 * @author miemie
 * @since 2019-06-12
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserAddress extends User {

    private List<Address> c;
}
