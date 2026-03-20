package com.huawei.apartment.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公寓列表VO
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Data
public class ApartmentListVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公寓ID
     */
    private Long apartmentId;

    /**
     * 公寓名称
     */
    private String name;

    /**
     * 公寓编码
     */
    private String code;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 联系电话
     */
    private String phone;

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

    /**
     * 空置房间数
     */
    private Integer vacantCount;

    /**
     * 已租房间数
     */
    private Integer rentedCount;

    /**
     * 维修中房间数
     */
    private Integer maintenanceCount;

    /**
     * 预订中房间数
     */
    private Integer bookedCount;

    /**
     * 房间状态汇总
     */
    public String getRoomStatus() {
        if (roomCount == null || roomCount == 0) {
            return "暂无房间";
        }
        return String.format("空置%d/已租%d/维修中%d/预订中%d",
                vacantCount != null ? vacantCount : 0,
                rentedCount != null ? rentedCount : 0,
                maintenanceCount != null ? maintenanceCount : 0,
                bookedCount != null ? bookedCount : 0);
    }
}
