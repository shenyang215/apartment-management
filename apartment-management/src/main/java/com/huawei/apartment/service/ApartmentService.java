package com.huawei.apartment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huawei.apartment.dto.ApartmentAddDTO;
import com.huawei.apartment.dto.ApartmentQueryDTO;
import com.huawei.apartment.entity.ApartmentEntity;
import com.huawei.apartment.vo.ApartmentListVO;

/**
 * 公寓业务逻辑层接口
 *
 * @author Huawei
 * @since 2026-03-18
 */
public interface ApartmentService extends IService<ApartmentEntity> {

    /**
     * 分页查询公寓列表
     *
     * @param queryDTO 查询条件
     * @return 公寓列表
     */
    IPage<ApartmentListVO> queryApartmentPage(ApartmentQueryDTO queryDTO);

    /**
     * 新增公寓
     *
     * @param addDTO 新增DTO
     * @return 公寓ID
     */
    Long addApartment(ApartmentAddDTO addDTO);

    /**
     * 编辑公寓
     *
     * @param addDTO 编辑DTO
     * @return 是否成功
     */
    Boolean updateApartment(ApartmentAddDTO addDTO);

    /**
     * 删除公寓
     *
     * @param apartmentId 公寓ID
     * @return 是否成功
     */
    Boolean deleteApartment(Long apartmentId);

    /**
     * 启用/停用公寓
     *
     * @param apartmentId 公寓ID
     * @param status 状态
     * @return 是否成功
     */
    Boolean updateApartmentStatus(Long apartmentId, Integer status);
}
