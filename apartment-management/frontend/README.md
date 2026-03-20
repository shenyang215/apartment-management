# 公寓管理系统前端开发文档

## 一、技术栈

本项目使用华为内部常用的前端技术栈：

- **Vue 2.6.14** - 渐进式JavaScript框架
- **Element UI 2.15.13** - 饿了么UI组件库
- **Vue Router 3.5.1** - Vue.js官方路由管理器
- **Vuex 3.6.2** - Vue.js状态管理模式
- **Axios 0.27.2** - HTTP请求库

## 二、项目结构

```
frontend/
├── public/                 # 静态资源
├── src/
│   ├── api/               # API接口
│   │   └── apartment.js   # 公寓相关API
│   ├── assets/            # 资源文件
│   ├── components/        # 公共组件
│   ├── router/            # 路由配置
│   │   └── index.js
│   ├── store/             # Vuex状态管理
│   │   └── index.js
│   ├── utils/             # 工具函数
│   │   └── request.js     # axios封装
│   ├── views/             # 页面组件
│   │   └── apartment/
│   │       ├── ApartmentList.vue  # 公寓列表页
│   │       ├── BuildingList.vue   # 楼栋管理页
│   │       └── RoomList.vue       # 房间管理页
│   ├── App.vue            # 根组件
│   └── main.js            # 入口文件
├── package.json           # 项目配置
└── vue.config.js          # Vue CLI配置
```

## 三、功能特性

### 3.1 已实现功能

✅ **主布局**
- 顶部导航栏（系统名称、面包屑、用户信息）
- 左侧菜单栏（可折叠、多级菜单）
- 主内容区域

✅ **公寓管理**
- 公寓列表展示（分页）
- 搜索过滤（名称、城市、状态）
- 新增公寓
- 编辑公寓
- 删除公寓（带确认）
- 启用/停用状态切换

✅ **UI设计**
- 完全按照设计图实现
- 配色方案：深蓝灰(#2C3E50) + 蓝色(#3498DB)
- 响应式布局
- Element UI组件库

## 四、安装和运行

### 4.1 安装依赖

```bash
cd frontend
npm install
```

### 4.2 开发模式运行

```bash
npm run dev
```

访问：http://localhost:8080

### 4.3 生产环境构建

```bash
npm run build
```

## 五、API接口说明

### 5.1 基础配置

API基础路径：`http://localhost:8080/api`

### 5.2 公寓管理接口

| 接口名称 | 方法 | 路径 | 说明 |
|---------|------|------|------|
| 获取公寓列表 | GET | /apartment/list | 支持分页和搜索 |
| 新增公寓 | POST | /apartment/add | 提交公寓信息 |
| 编辑公寓 | PUT | /apartment/update | 更新公寓信息 |
| 删除公寓 | DELETE | /apartment/delete/{id} | 删除指定公寓 |
| 更新状态 | PUT | /apartment/status | 启用/停用公寓 |

### 5.3 请求示例

```javascript
// 获取公寓列表
getApartmentList({
  pageNum: 1,
  pageSize: 10,
  name: '测试',
  city: '北京',
  status: 1
})

// 新增公寓
addApartment({
  name: '测试公寓',
  code: 'TEST001',
  city: '北京',
  address: '北京市海淀区',
  phone: '13800138000',
  status: 1
})
```

## 六、页面说明

### 6.1 公寓列表页 (ApartmentList.vue)

**搜索区域**
- 公寓名称：文本输入
- 城市：文本输入
- 状态：下拉选择（启用/停用）
- 操作按钮：搜索、重置

**数据表格**
- ID：公寓唯一标识
- 公寓名称：显示名称
- 公寓编码：内部编码
- 地址：详细地址
- 房间总数：统计数量
- 房间状态：空置/已租/维修/预订
- 联系电话：联系方式
- 状态：启用/停用标签
- 创建时间：格式化显示
- 操作：编辑、启用/停用、删除

**分页**
- 总数显示
- 每页条数选择
- 页码跳转

**新增/编辑对话框**
- 公寓名称（必填）
- 公寓编码（必填，编辑时只读）
- 所在城市（必填）
- 联系电话（必填）
- 详细地址（必填）
- 经度/纬度（可选）
- 负责人信息（可选）
- 状态（必填）
- 备注（可选）

## 七、组件使用

### 7.1 Element UI组件

本项目使用了以下Element UI组件：

- `el-container` - 布局容器
- `el-header` - 顶部栏
- `el-aside` - 侧边栏
- `el-main` - 主内容
- `el-menu` - 导航菜单
- `el-breadcrumb` - 面包屑
- `el-avatar` - 头像
- `el-card` - 卡片
- `el-form` - 表单
- `el-table` - 表格
- `el-pagination` - 分页
- `el-dialog` - 对话框
- `el-button` - 按钮
- `el-input` - 输入框
- `el-select` - 选择器
- `el-tag` - 标签
- `el-message` - 消息提示

### 7.2 自定义组件

可根据需要创建以下公共组件：

- `SearchForm` - 搜索表单组件
- `DataTable` - 数据表格组件
- `Pagination` - 分页组件
- `StatusTag` - 状态标签组件

## 八、状态管理

### 8.1 Vuex Store

```javascript
// store/index.js
state: {
  userInfo: null  // 用户信息
}

mutations: {
  SET_USER_INFO  // 设置用户信息
}

actions: {
  setUserInfo  // 异步设置用户信息
}
```

### 8.2 使用示例

```javascript
// 获取用户信息
this.$store.state.userInfo

// 设置用户信息
this.$store.dispatch('setUserInfo', userInfo)
```

## 九、路由配置

### 9.1 路由列表

| 路径 | 名称 | 组件 | 面包屑 |
|------|------|------|--------|
| / | - | 重定向到公寓列表 | - |
| /apartment/list | ApartmentList | 公寓列表页 | 公寓管理/公寓列表 |
| /apartment/building | BuildingList | 楼栋管理页 | 公寓管理/楼栋管理 |
| /apartment/room | RoomList | 房间管理页 | 公寓管理/房间管理 |

### 9.2 路由守卫

可在`router/index.js`中添加路由守卫：

```javascript
router.beforeEach((to, from, next) => {
  // 权限验证
  next()
})
```

## 十、样式规范

### 10.1 配色方案

- **主色**：#3498DB（蓝色）
- **辅色**：#2C3E50（深蓝灰）
- **背景色**：#F5F5F5（浅灰）
- **成功色**：#67C23A
- **警告色**：#E6A23C
- **危险色**：#F56C6C
- **信息色**：#909399

### 10.2 字体规范

- **主字体**：14px
- **小字体**：12px
- **大字体**：16px
- **标题字体**：18px

### 10.3 间距规范

- **小间距**：8px
- **中间距**：16px
- **大间距**：24px

## 十一、开发建议

### 11.1 代码规范

- 使用ESLint进行代码检查
- 组件命名使用PascalCase
- 方法命名使用camelCase
- 常量使用UPPER_CASE

### 11.2 性能优化

- 使用路由懒加载
- 图片使用懒加载
- 合理使用keep-alive
- 避免不必要的重新渲染

### 11.3 安全建议

- API请求添加token验证
- 敏感数据加密传输
- XSS攻击防护
- CSRF防护

## 十二、扩展功能

### 12.1 待开发功能

- [ ] 楼栋管理
- [ ] 房间管理
- [ ] 租户管理
- [ ] 订单管理
- [ ] 账单管理
- [ ] 工单管理
- [ ] 运营概览
- [ ] 系统管理

### 12.2 功能增强

- [ ] 数据导出
- [ ] 批量操作
- [ ] 高级搜索
- [ ] 数据统计图表
- [ ] 地图集成
- [ ] 消息通知
- [ ] 权限管理

## 十三、常见问题

### Q1: 如何修改API地址？

修改`src/utils/request.js`中的`baseURL`

### Q2: 如何添加新页面？

1. 在`src/views`下创建组件
2. 在`src/router/index.js`添加路由
3. 在左侧菜单添加菜单项

### Q3: 如何添加新API？

在`src/api`下创建或修改API文件

### Q4: 如何修改主题色？

修改Element UI主题或自定义CSS变量

## 十四、部署说明

### 14.1 构建生产版本

```bash
npm run build
```

### 14.2 部署到服务器

将`dist`目录部署到Web服务器（Nginx、Apache等）

### 14.3 Nginx配置示例

```nginx
server {
  listen 80;
  server_name your-domain.com;
  
  location / {
    root /path/to/dist;
    index index.html;
    try_files $uri $uri/ /index.html;
  }
  
  location /api {
    proxy_pass http://localhost:8080;
  }
}
```

---

**前端开发文档完成！**
