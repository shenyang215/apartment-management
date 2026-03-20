-- ============================================
-- 公寓管理系统 - 数据库表结构设计
-- 版本: V1.0
-- 创建日期: 2026-03-18
-- 数据库: MySQL 5.7+
-- ============================================

-- ============================================
-- 1. 公寓表 (apartment)
-- ============================================
CREATE TABLE `apartment` (
  `apartment_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '公寓ID,主键',
  `name` VARCHAR(100) NOT NULL COMMENT '公寓名称',
  `code` VARCHAR(50) NOT NULL COMMENT '公寓编码,唯一',
  `city` VARCHAR(20) NOT NULL COMMENT '所在城市',
  `address` VARCHAR(500) NOT NULL COMMENT '详细地址',
  `longitude` DECIMAL(10, 7) DEFAULT NULL COMMENT '经度',
  `latitude` DECIMAL(10, 7) DEFAULT NULL COMMENT '纬度',
  `phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
  `manager_name` VARCHAR(50) DEFAULT NULL COMMENT '负责人姓名',
  `manager_phone` VARCHAR(20) DEFAULT NULL COMMENT '负责人电话',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态: 0=停用 1=启用',
  `remark` TEXT DEFAULT NULL COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` DATETIME DEFAULT NULL COMMENT '删除时间(软删除)',
  PRIMARY KEY (`apartment_id`),
  UNIQUE KEY `uk_code` (`code`),
  KEY `idx_city` (`city`),
  KEY `idx_status` (`status`),
  KEY `idx_deleted_at` (`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公寓信息表';

-- ============================================
-- 2. 楼栋表 (building)
-- ============================================
CREATE TABLE `building` (
  `building_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '楼栋ID,主键',
  `apartment_id` BIGINT(20) NOT NULL COMMENT '所属公寓ID',
  `name` VARCHAR(100) NOT NULL COMMENT '楼栋名称',
  `code` VARCHAR(50) NOT NULL COMMENT '楼栋编码',
  `floor_count` INT(11) NOT NULL DEFAULT 0 COMMENT '楼层数',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态: 0=停用 1=启用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` DATETIME DEFAULT NULL COMMENT '删除时间(软删除)',
  PRIMARY KEY (`building_id`),
  KEY `idx_apartment_id` (`apartment_id`),
  UNIQUE KEY `uk_apartment_code` (`apartment_id`, `code`),
  KEY `idx_deleted_at` (`deleted_at`),
  CONSTRAINT `fk_building_apartment` FOREIGN KEY (`apartment_id`) REFERENCES `apartment` (`apartment_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='楼栋信息表';

-- ============================================
-- 3. 房间表 (room)
-- ============================================
CREATE TABLE `room` (
  `room_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '房间ID,主键',
  `building_id` BIGINT(20) NOT NULL COMMENT '所属楼栋ID',
  `apartment_id` BIGINT(20) NOT NULL COMMENT '所属公寓ID(冗余字段)',
  `floor` INT(11) NOT NULL COMMENT '所在楼层',
  `room_number` VARCHAR(20) NOT NULL COMMENT '房间号',
  `room_type` VARCHAR(50) NOT NULL COMMENT '房间类型: 单间/一室一厅/两室一厅等',
  `area` DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '面积(平方米)',
  `rent` DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '月租金(元)',
  `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '房间状态: 0=空置 1=已租 2=维修中 3=预订中',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` DATETIME DEFAULT NULL COMMENT '删除时间(软删除)',
  PRIMARY KEY (`room_id`),
  KEY `idx_building_id` (`building_id`),
  KEY `idx_apartment_id` (`apartment_id`),
  KEY `idx_status` (`status`),
  KEY `idx_deleted_at` (`deleted_at`),
  UNIQUE KEY `uk_building_room` (`building_id`, `room_number`),
  CONSTRAINT `fk_room_building` FOREIGN KEY (`building_id`) REFERENCES `building` (`building_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_room_apartment` FOREIGN KEY (`apartment_id`) REFERENCES `apartment` (`apartment_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间信息表';

-- ============================================
-- 4. 租户表 (tenant) - 为关联查询预留
-- ============================================
CREATE TABLE `tenant` (
  `tenant_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '租户ID,主键',
  `name` VARCHAR(50) NOT NULL COMMENT '租户姓名',
  `phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
  `id_card` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态: 0=停用 1=启用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` DATETIME DEFAULT NULL COMMENT '删除时间(软删除)',
  PRIMARY KEY (`tenant_id`),
  UNIQUE KEY `uk_phone` (`phone`),
  KEY `idx_status` (`status`),
  KEY `idx_deleted_at` (`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户信息表';

-- ============================================
-- 5. 订单表 (order) - 为关联查询预留
-- ============================================
CREATE TABLE `order` (
  `order_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID,主键',
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
  `tenant_id` BIGINT(20) NOT NULL COMMENT '租户ID',
  `room_id` BIGINT(20) NOT NULL COMMENT '房间ID',
  `apartment_id` BIGINT(20) NOT NULL COMMENT '公寓ID',
  `start_date` DATE NOT NULL COMMENT '起租日期',
  `end_date` DATE NOT NULL COMMENT '结束日期',
  `rent` DECIMAL(10, 2) NOT NULL COMMENT '租金',
  `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '订单状态: 0=待签约 1=已签约 2=已完成 3=已取消',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` DATETIME DEFAULT NULL COMMENT '删除时间(软删除)',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_room_id` (`room_id`),
  KEY `idx_apartment_id` (`apartment_id`),
  KEY `idx_status` (`status`),
  KEY `idx_deleted_at` (`deleted_at`),
  CONSTRAINT `fk_order_tenant` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`tenant_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_order_room` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_order_apartment` FOREIGN KEY (`apartment_id`) REFERENCES `apartment` (`apartment_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租赁订单表';

-- ============================================
-- 6. 操作日志表 (operation_log)
-- ============================================
CREATE TABLE `operation_log` (
  `log_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID,主键',
  `user_id` BIGINT(20) NOT NULL COMMENT '操作用户ID',
  `user_name` VARCHAR(50) NOT NULL COMMENT '操作用户名',
  `module` VARCHAR(50) NOT NULL COMMENT '操作模块',
  `action` VARCHAR(50) NOT NULL COMMENT '操作类型: 新增/编辑/删除/启用/停用等',
  `content` TEXT NOT NULL COMMENT '操作内容',
  `ip` VARCHAR(50) DEFAULT NULL COMMENT '操作IP',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`log_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_module` (`module`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- ============================================
-- 7. 插入测试数据
-- ============================================

-- 插入公寓测试数据
INSERT INTO `apartment` (`name`, `code`, `city`, `address`, `longitude`, `latitude`, `phone`, `manager_name`, `manager_phone`, `status`, `remark`)
VALUES
('阳光公寓', 'AP001', '北京', '朝阳区建国路88号', 116.447794, 39.915063, '13800138000', '张三', '13900139000', 1, '新开公寓,交通便利'),
('星河公寓', 'AP002', '上海', '浦东新区陆家嘴环路1000号', 121.505870, 31.239690, '13800138001', '李四', '13900139001', 1, '高端公寓,设施齐全'),
('蓝天公寓', 'AP003', '广州', '天河区天河路123号', 113.329350, 23.137760, '13800138002', '王五', '13900139002', 1, '性价比高,适合年轻人'),
('海景公寓', 'AP004', '深圳', '南山区深南大道999号', 113.939850, 22.542820, '13800138003', '赵六', '13900139003', 0, '装修中,暂不对外'),
('绿洲公寓', 'AP005', '杭州', '西湖区文三路456号', 120.145250, 30.274080, '13800138004', '孙七', '13900139004', 1, '环境优美,近地铁站');

-- 插入楼栋测试数据
INSERT INTO `building` (`apartment_id`, `name`, `code`, `floor_count`, `status`)
VALUES
(1, 'A栋', 'B001', 6, 1),
(1, 'B栋', 'B002', 8, 1),
(2, '1号楼', 'B003', 12, 1),
(2, '2号楼', 'B004', 15, 1),
(3, '东区', 'B005', 5, 1),
(3, '西区', 'B006', 7, 1),
(5, '主楼', 'B007', 10, 1),
(5, '副楼', 'B008', 6, 1);

-- 插入房间测试数据
INSERT INTO `room` (`building_id`, `apartment_id`, `floor`, `room_number`, `room_type`, `area`, `rent`, `status`)
VALUES
-- 阳光公寓A栋
(1, 1, 1, '101', '单间', 25.50, 2000.00, 0),
(1, 1, 1, '102', '单间', 25.50, 2000.00, 1),
(1, 1, 1, '103', '一室一厅', 45.00, 3500.00, 0),
(1, 1, 2, '201', '单间', 25.50, 2000.00, 2),
(1, 1, 2, '202', '一室一厅', 45.00, 3500.00, 1),
-- 阳光公寓B栋
(2, 1, 1, '101', '两室一厅', 65.00, 5000.00, 0),
(2, 1, 1, '102', '两室一厅', 65.00, 5000.00, 1),
(2, 1, 2, '201', '三室一厅', 85.00, 7000.00, 0),
-- 星河公寓1号楼
(3, 2, 1, '101', '单间', 30.00, 4000.00, 1),
(3, 2, 1, '102', '一室一厅', 55.00, 6000.00, 1),
(3, 2, 2, '201', '两室一厅', 80.00, 9000.00, 0),
-- 星河公寓2号楼
(4, 2, 1, '101', '单间', 30.00, 4000.00, 3),
(4, 2, 1, '102', '一室一厅', 55.00, 6000.00, 0),
-- 蓝天公寓东区
(5, 3, 1, '101', '单间', 20.00, 1500.00, 1),
(5, 3, 1, '102', '单间', 20.00, 1500.00, 0),
(5, 3, 2, '201', '一室一厅', 40.00, 2500.00, 1),
-- 绿洲公寓主楼
(7, 5, 1, '101', '单间', 28.00, 2200.00, 0),
(7, 5, 1, '102', '一室一厅', 50.00, 3800.00, 1),
(7, 5, 2, '201', '两室一厅', 70.00, 5500.00, 0);

-- 插入租户测试数据
INSERT INTO `tenant` (`name`, `phone`, `id_card`, `status`)
VALUES
('小明', '13811112222', '110101199001011234', 1),
('小红', '13811113333', '110101199002025678', 1),
('小李', '13811114444', '110101199003039012', 1),
('小王', '13811115555', '110101199004040345', 1),
('小张', '13811116666', '110101199005050678', 1);

-- 插入订单测试数据
INSERT INTO `order` (`order_no`, `tenant_id`, `room_id`, `apartment_id`, `start_date`, `end_date`, `rent`, `status`)
VALUES
('ORD202603180001', 1, 2, 1, '2026-01-01', '2026-12-31', 2000.00, 1),
('ORD202603180002', 2, 5, 1, '2026-02-01', '2027-01-31', 3500.00, 1),
('ORD202603180003', 3, 7, 1, '2026-03-01', '2027-02-28', 5000.00, 1),
('ORD202603180004', 1, 10, 2, '2026-01-15', '2027-01-14', 4000.00, 1),
('ORD202603180005', 4, 11, 2, '2026-02-15', '2027-02-14', 6000.00, 1),
('ORD202603180006', 5, 16, 3, '2026-03-01', '2027-02-28', 1500.00, 1),
('ORD202603180007', 2, 18, 3, '2026-03-15', '2027-03-14', 2500.00, 1),
('ORD202603180008', 3, 19, 5, '2026-04-01', '2027-03-31', 3800.00, 1);

-- ============================================
-- 8. 常用查询示例
-- ============================================

-- 查询公寓列表(含房间统计)
SELECT
  a.apartment_id,
  a.name,
  a.code,
  a.city,
  a.address,
  a.longitude,
  a.latitude,
  a.phone,
  a.status,
  a.created_at,
  COUNT(r.room_id) AS room_count,
  SUM(CASE WHEN r.status = 0 THEN 1 ELSE 0 END) AS vacant_count,
  SUM(CASE WHEN r.status = 1 THEN 1 ELSE 0 END) AS rented_count,
  SUM(CASE WHEN r.status = 2 THEN 1 ELSE 0 END) AS maintenance_count,
  SUM(CASE WHEN r.status = 3 THEN 1 ELSE 0 END) AS booked_count
FROM apartment a
LEFT JOIN room r ON a.apartment_id = r.apartment_id AND r.deleted_at IS NULL
WHERE a.deleted_at IS NULL
GROUP BY a.apartment_id
ORDER BY a.created_at DESC;

-- 查询楼栋列表(含房间统计)
SELECT
  b.building_id,
  b.name,
  b.code,
  a.name AS apartment_name,
  b.floor_count,
  b.status,
  COUNT(r.room_id) AS room_count
FROM building b
LEFT JOIN apartment a ON b.apartment_id = a.apartment_id AND a.deleted_at IS NULL
LEFT JOIN room r ON b.building_id = r.building_id AND r.deleted_at IS NULL
WHERE b.deleted_at IS NULL
GROUP BY b.building_id
ORDER BY b.created_at DESC;

-- 查询房间列表(关联查询)
SELECT
  r.room_id,
  r.room_number,
  r.floor,
  r.room_type,
  r.area,
  r.rent,
  r.status,
  b.name AS building_name,
  a.name AS apartment_name,
  a.city,
  a.address
FROM room r
LEFT JOIN building b ON r.building_id = b.building_id AND b.deleted_at IS NULL
LEFT JOIN apartment a ON r.apartment_id = a.apartment_id AND a.deleted_at IS NULL
WHERE r.deleted_at IS NULL
ORDER BY r.apartment_id, r.building_id, r.floor, r.room_number;

-- 查询公寓出租率
SELECT
  a.apartment_id,
  a.name AS apartment_name,
  a.city,
  COUNT(r.room_id) AS total_rooms,
  SUM(CASE WHEN r.status = 0 THEN 1 ELSE 0 END) AS vacant_rooms,
  SUM(CASE WHEN r.status = 1 THEN 1 ELSE 0 END) AS rented_rooms,
  ROUND(SUM(CASE WHEN r.status = 1 THEN 1 ELSE 0 END) * 100.0 / COUNT(r.room_id), 2) AS occupancy_rate
FROM apartment a
LEFT JOIN room r ON a.apartment_id = r.apartment_id AND r.deleted_at IS NULL
WHERE a.deleted_at IS NULL
GROUP BY a.apartment_id
ORDER BY occupancy_rate DESC;

-- 根据经纬度计算距离(示例:查找距离指定位置10公里内的公寓)
SELECT
  a.apartment_id,
  a.name,
  a.address,
  a.longitude,
  a.latitude,
  ROUND(
    6371 * ACOS(
      COS(RADIANS(39.915063)) * COS(RADIANS(a.latitude)) *
      COS(RADIANS(a.longitude) - RADIANS(116.447794)) +
      SIN(RADIANS(39.915063)) * SIN(RADIANS(a.latitude))
    ),
    2
  ) AS distance
FROM apartment a
WHERE a.deleted_at IS NULL
  AND a.status = 1
  AND a.longitude IS NOT NULL
  AND a.latitude IS NOT NULL
HAVING distance < 10
ORDER BY distance ASC;

-- ============================================
-- 9. 索引优化建议
-- ============================================

-- 如果需要支持地理位置查询,可以考虑添加空间索引(MySQL 5.7.6+)
-- ALTER TABLE apartment ADD SPATIAL INDEX idx_location (POINT(longitude, latitude));

-- 如果需要按地址模糊查询频繁,可以为address字段添加全文索引
-- ALTER TABLE apartment ADD FULLTEXT INDEX idx_address (address);

-- ============================================
-- 10. 数据库维护
-- ============================================

-- 定期清理软删除数据(示例:删除180天前的软删除记录)
-- DELETE FROM apartment WHERE deleted_at < DATE_SUB(NOW(), INTERVAL 180 DAY);
-- DELETE FROM building WHERE deleted_at < DATE_SUB(NOW(), INTERVAL 180 DAY);
-- DELETE FROM room WHERE deleted_at < DATE_SUB(NOW(), INTERVAL 180 DAY);

-- 定期归档操作日志(示例:归档1年前的日志到历史表)
-- CREATE TABLE operation_log_history LIKE operation_log;
-- INSERT INTO operation_log_history SELECT * FROM operation_log WHERE created_at < DATE_SUB(NOW(), INTERVAL 1 YEAR);
-- DELETE FROM operation_log WHERE created_at < DATE_SUB(NOW(), INTERVAL 1 YEAR);
