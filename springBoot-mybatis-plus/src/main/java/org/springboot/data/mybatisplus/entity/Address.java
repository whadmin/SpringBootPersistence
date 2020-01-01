package org.springboot.data.mybatisplus.entity;

import lombok.Data;

/**
 * @author miemie
 * @since 2019-06-12
 */
@Data
public class Address {
    private Long id;
    private String description;
    private Long userId;
}
