# 公寓管理系统

## 项目简介

公寓管理系统是一个基于Spring Boot + MyBatis-Plus的公寓管理平台,提供公寓、楼栋、房间的统一管理功能。

## 技术栈

- **后端框架**: Spring Boot 2.7.18
- **持久层框架**: MyBatis-Plus 3.5.3.1
- **数据库**: MySQL 8.0
- **API文档**: Swagger2
- **工具类**: Hutool
- **构建工具**: Maven

## 项目结构

```
apartment-management
├── src/main/java/com/huawei/apartment
│   ├── entity                    # 实体类
│   │   ├── ApartmentEntity.java  # 公寓实体
│   │   ├── BuildingEntity.java   # 楼栋实体
│   │   └── RoomEntity.java       # 房间实体
│   ├── mapper                    # 数据访问层
│   │   ├── ApartmentMapper.java
│   │   ├── BuildingMapper.java
│   │   └── RoomMapper.java
│   ├── service                   # 业务逻辑层
│   │   ├── ApartmentService.java
│   │   └── impl
│   │       └── ApartmentServiceImpl.java
│   ├── controller                # 控制器层
│   │   └── ApartmentController.java
│   ├── dto                       # 数据传输对象
│   │   ├── ApartmentAddDTO.java
│   │   └── ApartmentQueryDTO.java
│   ├── vo                        # 视图对象
│   │   ├── ApartmentListVO.java
│   │   ├── BuildingListVO.java
│   │   └── RoomListVO.java
│   ├── config                    # 配置类
│   │   ├── SwaggerConfig.java
│   │   └── MybatisPlusConfig.java
│   ├── common                    # 公共模块
│   │   ├── enums                 # 枚举类
│   │   ├── exception             # 异常类
│   │   │   ├── BusinessException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── result                # 返回结果
│   │   │   └── Result.java
│   │   └── utils                 # 工具类
│   └── ApartmentManagementApplication.java  # 启动类
├── src/main/resources
│   ├── mapper                    # MyBatis XML文件
│   │   ├── ApartmentMapper.xml
│   │   ├── BuildingMapper.xml
│   │   └── RoomMapper.xml
│   └── application.yml           # 配置文件
└── pom.xml                       # Maven配置文件
```

## 功能模块

### 1. 公寓管理
- 公寓列表查询(支持分页、筛选)
- 公具新增
- 公寓编辑
- 公寓删除(软删除)
- 公寓启用/停用

### 2. 楼栋管理
- 楼栋列表查询
- 楼栋新增
- 楼栋编辑
- 楼栋删除

### 3. 房间管理
- 房间列表查询
- 房间新增
- 房间编辑
- 房间删除
- 房间状态管理

## 数据库设计

### 公寓表(apartment)
| 字段 | 类型 | 说明 |
|------|------|------|
| apartment_id | bigint | 公寓ID,主键 |
| name | varchar(100) | 公寓名称 |
| code | varchar(50) | 公寓编码,唯一 |
| city | varchar(20) | 所在城市 |
| address | varchar(500) | 详细地址 |
| longitude | decimal(10,7) | 经度 |
| latitude | decimal(10,7) | 纬度 |
| phone | varchar(20) | 联系电话 |
| manager_name | varchar(50) | 负责人姓名 |
| manager_phone | varchar(20) | 负责人电话 |
| status | tinyint | 状态: 0=停用 1=启用 |
| remark | text | 备注 |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |
| deleted_at | datetime | 删除时间(软删除) |

### 楼栋表(building)
| 字段 | 类型 | 说明 |
|------|------|------|
| building_id | bigint | 楼栋ID,主键 |
| apartment_id | bigint | 所属公寓ID |
| name | varchar(100) | 楼栋名称 |
| code | varchar(50) | 楼栋编码 |
| floor_count | int | 楼层数 |
| status | tinyint | 状态: 0=停用 1=启用 |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |
| deleted_at | datetime | 删除时间 |

### 房间表(room)
| 字段 | 类型 | 说明 |
|------|------|------|
| room_id | bigint | 房间ID,主键 |
| building_id | bigint | 所属楼栋ID |
| apartment_id | bigint | 所属公寓ID |
| floor | int | 所在楼层 |
| room_number | varchar(20) | 房间号 |
| room_type | varchar(50) | 房间类型 |
| area | decimal(10,2) | 面积(平方米) |
| rent | decimal(10,2) | 月租金(元) |
| status | tinyint | 房间状态: 0=空置 1=已租 2=维修中 3=预订中 |
| created_at | datetime | 创建时间 |
| updated_at | datetime | 更新时间 |
| deleted_at | datetime | 删除时间 |

## API接口

### 公寓管理接口

#### 1. 分页查询公寓列表
- **接口路径**: GET /api/apartment/list
- **请求参数**:
  - name: 公寓名称(可选)
  - city: 城市(可选)
  - status: 状态(可选)
  - pageNum: 当前页码(默认1)
  - pageSize: 每页条数(默认10)
- **响应数据**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [...],
    "total": 100,
    "size": 10,
    "current": 1
  }
}
```

#### 2. 新增公寓
- **接口路径**: POST /api/apartment/add
- **请求参数**:
```json
{
  "name": "阳光公寓",
  "code": "AP001",
  "city": "北京",
  "address": "朝阳区建国路88号",
  "longitude": 116.447794,
  "latitude": 39.915063,
  "phone": "13800138000",
  "managerName": "张三",
  "managerPhone": "13900139000",
  "status": 1,
  "remark": "新开公寓"
}
```

#### 3. 编辑公寓
- **接口路径**: PUT /api/apartment/update
- **请求参数**: 同新增公寓,需包含apartmentId

#### 4. 删除公寓
- **接口路径**: DELETE /api/apartment/delete/{apartmentId}

#### 5. 启用/停用公寓
- **接口路径**: PUT /api/apartment/status
- **请求参数**:
  - apartmentId: 公寓ID
  - status: 状态(0或1)

## 快速开始

### 1. 环境要求
- JDK 1.8+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库初始化
执行项目根目录下的`apartment_management.sql`文件创建数据库和表结构。

### 3. 修改配置
修改`application.yml`中的数据库连接信息:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/apartment_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: root
```

### 4. 启动项目
```bash
# 方式1: 使用Maven命令
mvn spring-boot:run

# 方式2: 打包后运行
mvn clean package
java -jar target/apartment-management.jar
```

### 5. 访问项目
- 项目地址: http://localhost:8080
- Swagger文档: http://localhost:8080/swagger-ui.html

## 开发规范

### 1. 命名规范
- **包名**: 全小写,如`com.huawei.apartment`
- **类名**: 大驼峰,如`ApartmentEntity`
- **方法名**: 小驼峰,如`queryApartmentPage`
- **常量名**: 全大写+下划线,如`MAX_PAGE_SIZE`
- **数据库表名**: 小写+下划线,如`apartment`
- **数据库字段名**: 小写+下划线,如`apartment_id`

### 2. 分层规范
- **Entity层**: 实体类,与数据库表一一对应
- **Mapper层**: 数据访问层,负责数据库操作
- **Service层**: 业务逻辑层,负责业务处理
- **Controller层**: 控制器层,负责接收请求和返回响应
- **DTO层**: 数据传输对象,用于接收前端参数
- **VO层**: 视图对象,用于返回给前端的数据

### 3. 异常处理
- 业务异常使用`BusinessException`
- 全局异常处理器`GlobalExceptionHandler`统一处理异常
- 返回结果统一使用`Result`对象

### 4. 日志规范
- 使用SLF4J+Logback
- 日志级别: ERROR > WARN > INFO > DEBUG > TRACE
- 关键操作必须记录日志

## 注意事项

1. 所有删除操作均为软删除,不会真正删除数据库记录
2. 公寓编码和名称具有唯一性约束
3. 删除公寓前需确保该公寓下无楼栋和房间
4. 经纬度字段可为空,用于地图定位功能

## 版本历史

- **V1.0.0** (2026-03-18)
  - 初始版本
  - 实现公寓、楼栋、房间的基础管理功能
  - 集成Swagger API文档
  - 实现软删除和自动填充功能

## 联系方式

如有问题,请联系开发团队。

---

**华为开发规范**
