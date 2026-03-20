package com.huawei.apartment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.huawei.apartment.common.result.Result;
import com.huawei.apartment.dto.ApartmentAddDTO;
import com.huawei.apartment.dto.ApartmentQueryDTO;
import com.huawei.apartment.service.ApartmentService;
import com.huawei.apartment.vo.ApartmentListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公寓管理控制器
 *
 * @author Huawei
 * @since 2026-03-18
 */
@Slf4j
@Api(tags = "公寓管理")
@RestController
@RequestMapping("/api/apartment")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    /**
     * 分页查询公寓列表
     *
     * @param queryDTO 查询条件
     * @return 公寓列表
     */
    @ApiOperation("分页查询公寓列表")
    @GetMapping("/list")
    public Result<IPage<ApartmentListVO>> list(ApartmentQueryDTO queryDTO) {
        log.info("分页查询公寓列表,参数: {}", queryDTO);
        IPage<ApartmentListVO> result = apartmentService.queryApartmentPage(queryDTO);
        return Result.success(result);
    }

    /**
     * 新增公寓
     *
     * @param addDTO 新增DTO
     * @return 公寓ID
     */
    @ApiOperation("新增公寓")
    @PostMapping("/add")
    public Result<Long> add(@Validated @RequestBody ApartmentAddDTO addDTO) {
        log.info("新增公寓,参数: {}", addDTO);
        Long apartmentId = apartmentService.addApartment(addDTO);
        return Result.success(apartmentId, "新增成功");
    }

    /**
     * 编辑公寓
     *
     * @param addDTO 编辑DTO
     * @return 是否成功
     */
    @ApiOperation("编辑公寓")
    @PutMapping("/update")
    public Result<Boolean> update(@Validated @RequestBody ApartmentAddDTO addDTO) {
        log.info("编辑公寓,参数: {}", addDTO);
        Boolean result = apartmentService.updateApartment(addDTO);
        return Result.success(result, "编辑成功");
    }

    /**
     * 删除公寓
     *
     * @param apartmentId 公寓ID
     * @return 是否成功
     */
    @ApiOperation("删除公寓")
    @DeleteMapping("/delete")
    public Result<Boolean> delete(@RequestParam Long apartmentId) {
        log.info("删除公寓,公寓ID: {}", apartmentId);
        Boolean result = apartmentService.deleteApartment(apartmentId);
        return Result.success(result, "删除成功");
    }

    /**
     * 启用/停用公寓
     *
     * @param apartmentId 公寓ID
     * @param status 状态
     * @return 是否成功
     */
    @ApiOperation("启用/停用公寓")
    @PutMapping("/status")
    public Result<Boolean> updateStatus(@RequestParam Long apartmentId, @RequestParam Integer status) {
        log.info("更新公寓状态,公寓ID: {}, 状态: {}", apartmentId, status);
        Boolean result = apartmentService.updateApartmentStatus(apartmentId, status);
        return Result.success(result, "状态更新成功");
    }
}
