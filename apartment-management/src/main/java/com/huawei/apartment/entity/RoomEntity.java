package com.huawei.apartment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 房间实体类
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Data
@TableName("room")
public class RoomEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房间ID,主键
     */
    @TableId(value = "room_id", type = IdType.AUTO)
    private Long roomId;

    /**
     * 所属楼栋ID
     */
    @TableField("building_id")
    private Long buildingId;

    /**
     * 所属公寓ID(冗余字段)
     */
    @TableField("apartment_id")
    private Long apartmentId;

    /**
     * 所在楼层
     */
    @TableField("floor")
    private Integer floor;

    /**
     * 房间号
     */
    @TableField("room_number")
    private String roomNumber;

    /**
     * 房间类型: 单间/一室一厅/两室一厅等
     */
    @TableField("room_type")
    private String roomType;

    /**
     * 面积(平方米)
     */
    @TableField("area")
    private BigDecimal area;

    /**
     * 月租金(元)
     */
    @TableField("rent")
    private BigDecimal rent;

    /**
     * 房间状态: 0=空置 1=已租 2=维修中 3=预订中
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 删除时间(软删除)
     */
    @TableField("deleted_at")
    @TableLogic
    private LocalDateTime deletedAt;
}
