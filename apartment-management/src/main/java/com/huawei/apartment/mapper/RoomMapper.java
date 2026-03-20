package com.huawei.apartment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huawei.apartment.entity.RoomEntity;
import com.huawei.apartment.vo.RoomListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 房间数据访问层
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Mapper
public interface RoomMapper extends BaseMapper<RoomEntity> {

    /**
     * 分页查询房间列表(关联查询)
     *
     * @param page 分页对象
     * @param apartmentId 公寓ID
     * @param buildingId 楼栋ID
     * @param roomNumber 房间号
     * @param status 状态
     * @return 房间列表
     */
    IPage<RoomListVO> selectRoomPage(
            Page<RoomListVO> page,
            @Param("apartmentId") Long apartmentId,
            @Param("buildingId") Long buildingId,
            @Param("roomNumber") String roomNumber,
            @Param("status") Integer status
    );

    /**
     * 统计公寓下各状态房间数量
     *
     * @param apartmentId 公寓ID
     * @param status 房间状态
     * @return 房间数量
     */
    Integer countByApartmentIdAndStatus(
            @Param("apartmentId") Long apartmentId,
            @Param("status") Integer status
    );
}
