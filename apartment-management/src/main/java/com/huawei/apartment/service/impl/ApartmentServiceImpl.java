package com.huawei.apartment.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huawei.apartment.common.exception.BusinessException;
import com.huawei.apartment.dto.ApartmentAddDTO;
import com.huawei.apartment.dto.ApartmentQueryDTO;
import com.huawei.apartment.entity.ApartmentEntity;
import com.huawei.apartment.mapper.ApartmentMapper;
import com.huawei.apartment.service.ApartmentService;
import com.huawei.apartment.vo.ApartmentListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公寓业务逻辑层实现
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Slf4j
@Service
public class ApartmentServiceImpl extends ServiceImpl<ApartmentMapper, ApartmentEntity> implements ApartmentService {

    @Override
    public IPage<ApartmentListVO> queryApartmentPage(ApartmentQueryDTO queryDTO) {
        log.info("查询公寓列表,参数: {}", queryDTO);

        Page<ApartmentListVO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<ApartmentListVO> result = baseMapper.selectApartmentPage(
                page,
                queryDTO.getName(),
                queryDTO.getCity(),
                queryDTO.getStatus()
        );

        log.info("查询公寓列表成功,共{}条记录", result.getTotal());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addApartment(ApartmentAddDTO addDTO) {
        log.info("新增公寓,参数: {}", addDTO);

        // 校验公寓编码是否已存在
        ApartmentEntity existEntity = baseMapper.selectByCode(addDTO.getCode());
        if (existEntity != null) {
            throw new BusinessException("公寓编码已存在,请重新输入");
        }

        // 校验公寓名称是否已存在
        Long count = this.lambdaQuery()
                .eq(ApartmentEntity::getName, addDTO.getName())
                .count();
        if (count > 0) {
            throw new BusinessException("公寓名称已存在,请重新输入");
        }

        // 构建实体对象
        ApartmentEntity entity = new ApartmentEntity();
        BeanUtils.copyProperties(addDTO, entity);

        // 保存
        this.save(entity);

        log.info("新增公寓成功,公寓ID: {}", entity.getApartmentId());
        return entity.getApartmentId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateApartment(ApartmentAddDTO addDTO) {
        log.info("编辑公寓,参数: {}", addDTO);

        // 校验公寓是否存在
        ApartmentEntity existEntity = this.getById(addDTO.getApartmentId());
        if (existEntity == null) {
            throw new BusinessException("公寓不存在");
        }

        // 校验公寓名称是否重复
        Long count = this.lambdaQuery()
                .eq(ApartmentEntity::getName, addDTO.getName())
                .ne(ApartmentEntity::getApartmentId, addDTO.getApartmentId())
                .count();
        if (count > 0) {
            throw new BusinessException("公寓名称已存在,请重新输入");
        }

        // 构建实体对象
        ApartmentEntity entity = new ApartmentEntity();
        BeanUtils.copyProperties(addDTO, entity);

        // 更新
        Boolean result = this.updateById(entity);

        log.info("编辑公寓成功,结果: {}", result);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteApartment(Long apartmentId) {
        log.info("删除公寓,公寓ID: {}", apartmentId);

        // 校验公寓是否存在
        ApartmentEntity existEntity = this.getById(apartmentId);
        if (existEntity == null) {
            throw new BusinessException("公寓不存在");
        }

        // 校验公寓下是否存在楼栋
        Integer buildingCount = baseMapper.countBuildingsByApartmentId(apartmentId);
        if (buildingCount > 0) {
            throw new BusinessException("该公寓下存在楼栋,无法删除");
        }

        // 校验公寓下是否存在房间
        Integer roomCount = baseMapper.countRoomsByApartmentId(apartmentId);
        if (roomCount > 0) {
            throw new BusinessException("该公寓下存在房间,无法删除");
        }

        // 删除(软删除)
        Boolean result = this.removeById(apartmentId);

        log.info("删除公寓成功,结果: {}", result);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateApartmentStatus(Long apartmentId, Integer status) {
        log.info("更新公寓状态,公寓ID: {}, 状态: {}", apartmentId, status);

        // 校验公寓是否存在
        ApartmentEntity existEntity = this.getById(apartmentId);
        if (existEntity == null) {
            throw new BusinessException("公寓不存在");
        }

        // 更新状态
        ApartmentEntity entity = new ApartmentEntity();
        entity.setApartmentId(apartmentId);
        entity.setStatus(status);

        Boolean result = this.updateById(entity);

        log.info("更新公寓状态成功,结果: {}", result);
        return result;
    }
}
