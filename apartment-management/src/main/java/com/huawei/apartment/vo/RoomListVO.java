package com.huawei.apartment.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 房间列表VO
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Data
public class RoomListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 房间号
     */
    private String roomNumber;

    /**
     * 所在楼层
     */
    private Integer floor;

    /**
     * 房间类型
     */
    private String roomType;

    /**
     * 面积(平方米)
     */
    private BigDecimal area;

    /**
     * 月租金(元)
     */
    private BigDecimal rent;

    /**
     * 房间状态: 0=空置 1=已租 2=维修中 3=预订中
     */
    private Integer status;

    /**
     * 所属楼栋名称
     */
    private String buildingName;

    /**
     * 所属公寓名称
     */
    private String apartmentName;

    /**
     * 城市
     */
    private String city;

    /**
     * 地址
     */
    private String address;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
