# 快速启动指南

## 一、环境准备

### 1. 必需软件
- ✅ JDK 1.8 或更高版本
- ✅ Maven 3.6+
- ✅ MySQL 8.0+
- ✅ IDEA (推荐) 或 Eclipse

### 2. 检查环境
```bash
# 检查Java版本
java -version
# 应显示: java version "1.8.x"

# 检查Maven版本
mvn -version
# 应显示: Apache Maven 3.6.x

# 检查MySQL
mysql --version
# 应显示: mysql Ver 8.0.x
```

---

## 二、数据库初始化

### 1. 启动MySQL服务
```bash
# Windows
net start mysql

# Linux/Mac
sudo systemctl start mysqld
# 或
sudo service mysql start
```

### 2. 创建数据库
```bash
# 登录MySQL
mysql -u root -p

# 创建数据库
CREATE DATABASE apartment_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# 退出MySQL
exit;
```

### 3. 导入SQL脚本
```bash
# 方式1: 使用MySQL命令
mysql -u root -p apartment_db < apartment_management.sql

# 方式2: 使用MySQL客户端工具
# 打开Navicat/MySQL Workbench,执行apartment_management.sql
```

---

## 三、项目配置

### 1. 修改数据库配置
编辑 `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/apartment_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root          # 修改为你的用户名
    password: root          # 修改为你的密码
```

### 2. 配置Maven镜像(可选,加速下载)
编辑 Maven的 `settings.xml` (通常在 `~/.m2/settings.xml`):
```xml
<mirrors>
    <mirror>
        <id>aliyunmaven</id>
        <mirrorOf>*</mirrorOf>
        <name>阿里云公共仓库</name>
        <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
</mirrors>
```

---

## 四、IDE配置(IDEA)

### 1. 导入项目
- File → Open → 选择 `apartment-management` 目录
- 选择 "Maven项目"
- 等待依赖下载完成

### 2. 安装Lombok插件
- File → Settings → Plugins
- 搜索 "Lombok"
- 点击 Install
- 重启IDEA

### 3. 启用注解处理器
- File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
- 勾选 "Enable annotation processing"
- Apply → OK

### 4. 配置JDK
- File → Project Structure → Project
- Project SDK: 选择 JDK 1.8
- Project language level: 8 - Lambdas, type annotations etc.

---

## 五、启动项目

### 方式1: 使用Maven命令
```bash
# 进入项目目录
cd apartment-management

# 清理并编译
mvn clean compile

# 启动项目
mvn spring-boot:run
```

### 方式2: 使用IDEA
- 打开 `ApartmentManagementApplication.java`
- 右键 → Run 'ApartmentManagementApplication'
- 或点击类旁边的绿色运行按钮

### 方式3: 打包后运行
```bash
# 打包
mvn clean package

# 运行
java -jar target/apartment-management.jar
```

---

## 六、验证启动

### 1. 查看控制台输出
```
========================================
公寓管理系统启动成功!
========================================
```

### 2. 访问Swagger文档
浏览器打开: http://localhost:8080/swagger-ui.html

应该看到API文档页面,包含以下接口:
- 公寓管理
  - GET /api/apartment/list
  - POST /api/apartment/add
  - PUT /api/apartment/update
  - DELETE /api/apartment/delete/{id}
  - PUT /api/apartment/status

### 3. 测试接口
使用Swagger或Postman测试:

**查询公寓列表**:
```
GET http://localhost:8080/api/apartment/list?pageNum=1&pageSize=10
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "apartmentId": 1,
        "name": "阳光公寓",
        "code": "AP001",
        "city": "北京",
        "address": "朝阳区建国路88号",
        "longitude": 116.447794,
        "latitude": 39.915063,
        "phone": "13800138000",
        "status": 1,
        "roomCount": 8,
        "vacantCount": 3,
        "rentedCount": 4,
        "maintenanceCount": 1,
        "bookedCount": 0
      }
    ],
    "total": 5,
    "size": 10,
    "current": 1
  }
}
```

---

## 七、常见问题排查

### 问题1: 依赖下载失败
```bash
# 清理本地仓库缓存
mvn dependency:purge-local-repository

# 强制更新依赖
mvn clean install -U
```

### 问题2: 端口占用
```bash
# Windows查看端口占用
netstat -ano | findstr :8080

# 修改端口(在application.yml中)
server:
  port: 8081
```

### 问题3: 数据库连接失败
```bash
# 检查MySQL是否启动
# Windows
net start mysql

# 检查数据库是否存在
mysql -u root -p -e "SHOW DATABASES LIKE 'apartment_db';"
```

### 问题4: Lombok不生效
- 确认已安装Lombok插件
- 确认已启用注解处理器
- 重启IDEA
- Maven → Reimport

---

## 八、开发建议

### 1. 热部署
项目已集成spring-boot-devtools,修改代码后会自动重启。

### 2. 日志查看
日志文件位置: `logs/apartment-management.log`

### 3. 数据库管理
推荐使用:
- Navicat
- MySQL Workbench
- DBeaver

### 4. API测试
推荐使用:
- Swagger UI (已集成)
- Postman
- Apifox

---

## 九、下一步

### 1. 开发新功能
参考 `ApartmentController` 和 `ApartmentService` 的实现模式。

### 2. 添加新模块
- 创建Entity
- 创建Mapper
- 创建Service
- 创建Controller
- 创建DTO/VO

### 3. 扩展功能
- 添加权限控制
- 添加缓存
- 添加消息队列
- 添加文件上传

---

## 十、项目结构说明

```
apartment-management/
├── src/main/java/com/huawei/apartment/
│   ├── entity/              # 实体类(对应数据库表)
│   ├── mapper/              # 数据访问层(SQL操作)
│   ├── service/             # 业务逻辑层
│   │   └── impl/            # 业务实现
│   ├── controller/          # 控制器层(接口)
│   ├── dto/                 # 数据传输对象(接收参数)
│   ├── vo/                  # 视图对象(返回数据)
│   ├── config/              # 配置类
│   └── common/              # 公共模块
│       ├── exception/       # 异常处理
│       ├── result/          # 返回结果
│       └── utils/           # 工具类
├── src/main/resources/
│   ├── mapper/              # MyBatis XML文件
│   └── application.yml      # 配置文件
└── pom.xml                  # Maven依赖配置
```

---

**祝开发顺利!** 🎉
