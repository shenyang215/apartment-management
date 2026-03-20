package com.huawei.apartment.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 楼栋列表VO
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Data
public class BuildingListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 楼栋ID
     */
    private Long buildingId;

    /**
     * 楼栋名称
     */
    private String name;

    /**
     * 楼栋编码
     */
    private String code;

    /**
     * 所属公寓名称
     */
    private String apartmentName;

    /**
     * 楼层数
     */
    private Integer floorCount;

    /**
     * 状态: 0=停用 1=启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 房间总数
     */
    private Integer roomCount;
}
