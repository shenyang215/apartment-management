package com.huawei.apartment.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 公寓查询DTO
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Data
public class ApartmentQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公寓名称
     */
    private String name;

    /**
     * 城市
     */
    private String city;

    /**
     * 状态: 0=停用 1=启用
     */
    private Integer status;

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;
}
