package com.huawei.apartment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huawei.apartment.entity.BuildingEntity;
import com.huawei.apartment.vo.BuildingListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 楼栋数据访问层
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Mapper
public interface BuildingMapper extends BaseMapper<BuildingEntity> {

    /**
     * 分页查询楼栋列表(含房间统计)
     *
     * @param page 分页对象
     * @param apartmentId 公寓ID
     * @param name 楼栋名称
     * @param status 状态
     * @return 楼栋列表
     */
    IPage<BuildingListVO> selectBuildingPage(
            Page<BuildingListVO> page,
            @Param("apartmentId") Long apartmentId,
            @Param("name") String name,
            @Param("status") Integer status
    );

    /**
     * 统计楼栋下的房间数量
     *
     * @param buildingId 楼栋ID
     * @return 房间数量
     */
    Integer countRoomsByBuildingId(@Param("buildingId") Long buildingId);
}
