package com.huawei.apartment.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 公寓实体类
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Data
@TableName("apartment")
public class ApartmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公寓ID,主键
     */
    @TableId(value = "apartment_id", type = IdType.AUTO)
    private Long apartmentId;

    /**
     * 公寓名称
     */
    @TableField("name")
    private String name;

    /**
     * 公寓编码,唯一
     */
    @TableField("code")
    private String code;

    /**
     * 所在城市
     */
    @TableField("city")
    private String city;

    /**
     * 详细地址
     */
    @TableField("address")
    private String address;

    /**
     * 经度
     */
    @TableField("longitude")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @TableField("latitude")
    private BigDecimal latitude;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 负责人姓名
     */
    @TableField("manager_name")
    private String managerName;

    /**
     * 负责人电话
     */
    @TableField("manager_phone")
    private String managerPhone;

    /**
     * 状态: 0=停用 1=启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

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
