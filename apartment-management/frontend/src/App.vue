<template>
  <div id="app">
    <el-container style="height: 100vh">
      <!-- 顶部导航栏 -->
      <el-header style="background: #2C3E50; color: white; display: flex; align-items: center; justify-content: space-between;">
        <div style="display: flex; align-items: center;">
          <i class="el-icon-s-fold" style="font-size: 20px; cursor: pointer; margin-right: 20px;" @click="toggleSidebar"></i>
          <span style="font-size: 18px; font-weight: bold;">公寓管理系统</span>
          <el-breadcrumb separator="/" style="margin-left: 30px; display: inline-block;">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-for="(item, index) in $route.meta.breadcrumb" :key="index">{{ item }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div style="display: flex; align-items: center;">
          <el-avatar :size="32" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" style="margin-right: 10px;"></el-avatar>
          <span>管理员</span>
          <i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
        </div>
      </el-header>

      <el-container>
        <!-- 左侧菜单栏 -->
        <el-aside :width="sidebarWidth" style="background: #2C3E50;">
          <el-menu
            :default-active="activeMenu"
            :collapse="isCollapse"
            background-color="#2C3E50"
            text-color="#fff"
            active-text-color="#3498DB"
            router>
            
            <el-menu-item index="/">
              <i class="el-icon-s-home"></i>
              <span slot="title">首页</span>
            </el-menu-item>

            <el-submenu index="apartment">
              <template slot="title">
                <i class="el-icon-office-building"></i>
                <span>公寓管理</span>
              </template>
              <el-menu-item index="/apartment/list">公寓列表</el-menu-item>
              <el-menu-item index="/apartment/building">楼栋管理</el-menu-item>
              <el-menu-item index="/apartment/room">房间管理</el-menu-item>
            </el-submenu>

            <el-submenu index="tenant">
              <template slot="title">
                <i class="el-icon-user"></i>
                <span>租户管理</span>
              </template>
              <el-menu-item index="/tenant/list">租户列表</el-menu-item>
            </el-submenu>

            <el-submenu index="order">
              <template slot="title">
                <i class="el-icon-document"></i>
                <span>订单管理</span>
              </template>
              <el-menu-item index="/order/list">订单列表</el-menu-item>
            </el-submenu>

            <el-menu-item index="/bill">
              <i class="el-icon-money"></i>
              <span slot="title">账单列表</span>
            </el-menu-item>

            <el-submenu index="workorder">
              <template slot="title">
                <i class="el-icon-setting"></i>
                <span>工单管理</span>
              </template>
              <el-menu-item index="/workorder/list">工单列表</el-menu-item>
            </el-submenu>

            <el-menu-item index="/overview">
              <i class="el-icon-data-analysis"></i>
              <span slot="title">运营概览</span>
            </el-menu-item>

            <el-submenu index="system">
              <template slot="title">
                <i class="el-icon-setting"></i>
                <span>系统管理</span>
              </template>
              <el-menu-item index="/system/user">用户管理</el-menu-item>
              <el-menu-item index="/system/role">角色管理</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>

        <!-- 主内容区 -->
        <el-main style="background: #F5F5F5;">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      isCollapse: false
    }
  },
  computed: {
    activeMenu() {
      return this.$route.path
    },
    sidebarWidth() {
      return this.isCollapse ? '64px' : '200px'
    }
  },
  methods: {
    toggleSidebar() {
      this.isCollapse = !this.isCollapse
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', Arial, sans-serif;
}

.el-menu {
  border-right: none;
}

.el-breadcrumb__inner a, .el-breadcrumb__inner.is-link {
  color: #95A5A6;
}

.el-breadcrumb__separator {
  color: #95A5A6;
}
</style>
