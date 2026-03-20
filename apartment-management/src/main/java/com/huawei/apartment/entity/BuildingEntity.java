package com.huawei.apartment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 楼栋实体类
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Data
@TableName("building")
public class BuildingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 楼栋ID,主键
     */
    @TableId(value = "building_id", type = IdType.AUTO)
    private Long buildingId;

    /**
     * 所属公寓ID
     */
    @TableField("apartment_id")
    private Long apartmentId;

    /**
     * 楼栋名称
     */
    @TableField("name")
    private String name;

    /**
     * 楼栋编码
     */
    @TableField("code")
    private String code;

    /**
     * 楼层数
     */
    @TableField("floor_count")
    private Integer floorCount;

    /**
     * 状态: 0=停用 1=启用
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
