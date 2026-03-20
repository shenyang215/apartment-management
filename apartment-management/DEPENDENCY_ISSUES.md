# 依赖问题分析与解决方案

## 已修复的依赖问题

### 1. Guava依赖缺失 ✅ 已修复
**问题描述**:
- 文件: `SwaggerConfig.java`
- 错误: `import com.google.common.base.Predicate;` 找不到类
- 原因: Swagger依赖Guava库,但pom.xml中未显式引入

**解决方案**:
已在pom.xml中添加Guava依赖:
```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>31.1-jre</version>
</dependency>
```

---

## 可能遇到的环境问题

### 1. Maven依赖下载失败

**错误信息**:
```
Could not resolve dependencies for project com.huawei:apartment-management
Failed to read artifact descriptor for xxx
```

**原因**:
- 本地Maven仓库未配置或配置错误
- 网络问题导致无法从中央仓库下载依赖
- 公司内网需要配置代理

**解决方案**:

#### 方案1: 配置Maven镜像(阿里云镜像)
在Maven的`settings.xml`文件中添加:
```xml
<mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>阿里云公共仓库</name>
    <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```

#### 方案2: 配置公司代理
```xml
<proxy>
    <id>optional</id>
    <active>true</active>
    <protocol>http</protocol>
    <username>proxyuser</username>
    <password>proxypass</password>
    <host>proxy.host.net</host>
    <port>80</port>
</proxy>
```

#### 方案3: 清理本地仓库
```bash
# 删除本地仓库中的相关依赖
mvn dependency:purge-local-repository

# 重新下载依赖
mvn clean install -U
```

---

### 2. Lombok插件未安装

**错误信息**:
```
Cannot resolve symbol 'log'
Cannot resolve symbol 'Data'
Cannot resolve symbol 'Slf4j'
```

**原因**:
- IDE未安装Lombok插件
- IDE未启用注解处理器

**解决方案**:

#### IDEA配置:
1. **安装Lombok插件**:
   - File → Settings → Plugins
   - 搜索"Lombok",点击Install
   - 重启IDEA

2. **启用注解处理器**:
   - File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - 勾选"Enable annotation processing"
   - 点击Apply和OK

3. **重新导入Maven项目**:
   - 右键项目 → Maven → Reimport

---

### 3. JDK版本不匹配

**错误信息**:
```
java: 无效的源发行版: 1.8
java: 不支持发行版本5
```

**原因**:
- 本地JDK版本与项目配置不匹配
- 项目配置为JDK 1.8,但本地安装的是其他版本

**解决方案**:

#### 检查本地JDK版本:
```bash
java -version
javac -version
```

#### IDEA配置JDK:
1. File → Project Structure → Project
2. 设置Project SDK为JDK 1.8
3. 设置Project language level为8 - Lambdas, type annotations etc.

#### Maven配置:
在pom.xml中已配置:
```xml
<properties>
    <java.version>1.8</java.version>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
</properties>
```

---

### 4. MySQL驱动版本问题

**错误信息**:
```
Loading class `com.mysql.jdbc.Driver'. This is deprecated.
```

**原因**:
- MySQL 8.0+推荐使用新的驱动类名

**解决方案**:
已在配置文件中使用正确的驱动类:
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
```

---

### 5. 数据库连接失败

**错误信息**:
```
Communications link failure
Access denied for user 'root'@'localhost'
Unknown database 'apartment_db'
```

**原因**:
- MySQL服务未启动
- 数据库用户名或密码错误
- 数据库未创建

**解决方案**:

#### 1. 启动MySQL服务:
```bash
# Windows
net start mysql

# Linux
systemctl start mysqld
```

#### 2. 创建数据库:
```sql
CREATE DATABASE apartment_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

#### 3. 修改配置文件:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/apartment_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 你的密码
```

---

### 6. 端口占用

**错误信息**:
```
Web server failed to start. Port 8080 was already in use.
```

**原因**:
- 8080端口已被其他程序占用

**解决方案**:

#### 方案1: 修改端口
在`application.yml`中修改:
```yaml
server:
  port: 8081
```

#### 方案2: 关闭占用端口的程序
```bash
# Windows查看端口占用
netstat -ano | findstr :8080

# 关闭进程
taskkill /PID 进程ID /F
```

---

## 完整的依赖清单

项目已包含以下所有必要依赖:

### Spring Boot相关:
- ✅ spring-boot-starter-web
- ✅ spring-boot-starter-validation
- ✅ spring-boot-starter-aop
- ✅ spring-boot-starter-test
- ✅ spring-boot-devtools
- ✅ spring-boot-configuration-processor

### 数据库相关:
- ✅ mybatis-plus-boot-starter (3.5.3.1)
- ✅ mysql-connector-java (8.0.33)
- ✅ druid-spring-boot-starter (1.2.16)

### API文档:
- ✅ springfox-swagger2 (2.9.2)
- ✅ springfox-swagger-ui (2.9.2)
- ✅ swagger-models (1.6.2)
- ✅ swagger-annotations (1.6.2)
- ✅ guava (31.1-jre) ← **新增**

### 工具类:
- ✅ hutool-all (5.8.16)
- ✅ lombok (1.18.26)

---

## 验证依赖是否正确

### 方法1: 使用Maven命令
```bash
# 进入项目目录
cd apartment-management

# 查看依赖树
mvn dependency:tree

# 编译项目
mvn clean compile

# 打包项目
mvn clean package
```

### 方法2: 使用IDEA
1. 右键项目 → Maven → Reimport
2. 查看External Libraries是否包含所有依赖
3. 运行`ApartmentManagementApplication.java`

---

## 常见IDE报错及解决

### 1. "Cannot resolve symbol"
**解决**: Maven → Reimport → Invalidate Caches and Restart

### 2. "Package does not exist"
**解决**: 检查pom.xml依赖是否正确,重新导入Maven项目

### 3. "Class not found"
**解决**:
- 检查import语句是否正确
- 检查类是否在正确的包下
- Maven clean install

---

## 总结

### ✅ 已修复:
1. Guava依赖缺失 - 已添加到pom.xml

### ⚠️ 需要本地环境配置:
1. JDK 1.8安装
2. Maven配置(镜像/代理)
3. MySQL数据库安装和配置
4. IDEA Lombok插件安装
5. 数据库创建和初始化

### 📝 建议:
1. 首次运行前执行: `mvn clean install -U`
2. 确保MySQL服务已启动
3. 确保数据库已创建并执行SQL脚本
4. 检查application.yml中的数据库配置

---

如有其他依赖问题,请提供具体错误信息以便进一步分析。
