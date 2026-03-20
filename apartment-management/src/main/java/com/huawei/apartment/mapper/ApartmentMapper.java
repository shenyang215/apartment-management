package com.huawei.apartment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huawei.apartment.entity.ApartmentEntity;
import com.huawei.apartment.vo.ApartmentListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公寓数据访问层
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Mapper
public interface ApartmentMapper extends BaseMapper<ApartmentEntity> {

    /**
     * 分页查询公寓列表(含房间统计)
     *
     * @param page 分页对象
     * @param name 公寓名称
     * @param city 城市
     * @param status 状态
     * @return 公寓列表
     */
    IPage<ApartmentListVO> selectApartmentPage(
            Page<ApartmentListVO> page,
            @Param("name") String name,
            @Param("city") String city,
            @Param("status") Integer status
    );

    /**
     * 根据公寓编码查询
     *
     * @param code 公寓编码
     * @return 公寓实体
     */
    ApartmentEntity selectByCode(@Param("code") String code);

    /**
     * 统计公寓下的楼栋数量
     *
     * @param apartmentId 公寓ID
     * @return 楼栋数量
     */
    Integer countBuildingsByApartmentId(@Param("apartmentId") Long apartmentId);

    /**
     * 统计公寓下的房间数量
     *
     * @param apartmentId 公寓ID
     * @return 房间数量
     */
    Integer countRoomsByApartmentId(@Param("apartmentId") Long apartmentId);
}
