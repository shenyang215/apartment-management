package com.huawei.apartment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 公具新增/编辑DTO
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Data
public class ApartmentAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公寓ID(编辑时必填)
     */
    private Long apartmentId;

    /**
     * 公寓名称
     */
    @NotBlank(message = "公寓名称不能为空")
    @Size(min = 1, max = 100, message = "公寓名称长度必须在1-100字符之间")
    private String name;

    /**
     * 公寓编码
     */
    @NotBlank(message = "公寓编码不能为空")
    @Size(min = 1, max = 50, message = "公寓编码长度必须在1-50字符之间")
    private String code;

    /**
     * 所在城市
     */
    @NotBlank(message = "城市不能为空")
    @Size(min = 1, max = 20, message = "城市长度必须在1-20字符之间")
    private String city;

    /**
     * 详细地址
     */
    @NotBlank(message = "地址不能为空")
    @Size(min = 1, max = 500, message = "地址长度必须在1-500字符之间")
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
    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$|^\\d{3,4}-\\d{7,8}$", message = "联系电话格式不正确")
    private String phone;

    /**
     * 负责人姓名
     */
    @Size(max = 50, message = "负责人姓名长度不能超过50字符")
    private String managerName;

    /**
     * 负责人电话
     */
    @Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "负责人电话格式不正确")
    private String managerPhone;

    /**
     * 状态: 0=停用 1=启用
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500字符")
    private String remark;
}
