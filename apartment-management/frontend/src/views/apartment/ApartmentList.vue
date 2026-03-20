<template>
  <div class="apartment-list">
    <!-- 搜索区域 -->
    <el-card style="margin-bottom: 20px;">
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="公寓名称">
          <el-input v-model="searchForm.name" placeholder="请输入公寓名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="searchForm.city" placeholder="请输入城市" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="状态" clearable>
            <el-option label="启用" :value="1"></el-option>
            <el-option label="停用" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作按钮 -->
    <el-card>
      <div style="margin-bottom: 20px;">
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增公寓</el-button>
      </div>

      <!-- 数据表格 -->
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        v-loading="loading"
        element-loading-text="加载中...">
        
        <el-table-column prop="apartmentId" label="ID" width="80" align="center"></el-table-column>
        
        <el-table-column prop="name" label="公寓名称" width="150"></el-table-column>
        
        <el-table-column prop="code" label="公寓编码" width="120"></el-table-column>
        
        <el-table-column prop="address" label="地址" min-width="200" show-overflow-tooltip></el-table-column>
        
        <el-table-column prop="roomCount" label="房间总数" width="100" align="center">
          <template slot-scope="scope">
            {{ scope.row.roomCount || 0 }}
          </template>
        </el-table-column>
        
        <el-table-column label="房间状态" width="200">
          <template slot-scope="scope">
            <div v-if="scope.row.roomCount > 0" style="font-size: 12px; color: #606266;">
              空置{{ scope.row.vacantCount || 0 }} / 
              已租{{ scope.row.rentedCount || 0 }} / 
              维修{{ scope.row.maintenanceCount || 0 }} / 
              预订{{ scope.row.bookedCount || 0 }}
            </div>
            <span v-else style="color: #909399; font-size: 12px;">暂无房间</span>
          </template>
        </el-table-column>
        
        <el-table-column prop="phone" label="联系电话" width="130"></el-table-column>
        
        <el-table-column label="状态" width="80" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'" size="small">
              {{ scope.row.status === 1 ? '启用' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="createdAt" label="创建时间" width="160" align="center">
          <template slot-scope="scope">
            {{ formatDate(scope.row.createdAt) }}
          </template>
        </el-table-column>
        
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button 
              type="text" 
              :icon="scope.row.status === 1 ? 'el-icon-video-pause' : 'el-icon-video-play'"
              @click="handleToggleStatus(scope.row)">
              {{ scope.row.status === 1 ? '停用' : '启用' }}
            </el-button>
            <el-button type="text" icon="el-icon-delete" style="color: #F56C6C;" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        style="margin-top: 20px; text-align: right;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.currentPage"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pagination.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total">
      </el-pagination>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog 
      :title="dialogTitle" 
      :visible.sync="dialogVisible" 
      width="800px"
      :close-on-click-modal="false">
      
      <el-form :model="formData" :rules="rules" ref="formRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公寓名称" prop="name">
              <el-input v-model="formData.name" placeholder="请输入公寓名称" maxlength="100"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公寓编码" prop="code">
              <el-input 
                v-model="formData.code" 
                placeholder="请输入公寓编码" 
                maxlength="50"
                :readonly="isEdit"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所在城市" prop="city">
              <el-input v-model="formData.city" placeholder="请输入所在城市" maxlength="20"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="formData.phone" placeholder="请输入联系电话"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="详细地址" prop="address">
          <el-input v-model="formData.address" placeholder="请输入详细地址" maxlength="500"></el-input>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="经度">
              <el-input-number v-model="formData.longitude" :precision="6" :step="0.000001" style="width: 100%;"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="纬度">
              <el-input-number v-model="formData.latitude" :precision="6" :step="0.000001" style="width: 100%;"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="负责人姓名">
              <el-input v-model="formData.managerName" placeholder="请输入负责人姓名" maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人电话">
              <el-input v-model="formData.managerPhone" placeholder="请输入负责人电话"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="formData.status" placeholder="请选择状态" style="width: 100%;">
                <el-option label="启用" :value="1"></el-option>
                <el-option label="停用" :value="0"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注">
              <el-input v-model="formData.remark" placeholder="请输入备注" maxlength="500"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getApartmentList, addApartment, updateApartment, deleteApartment, updateApartmentStatus } from '@/api/apartment'

export default {
  name: 'ApartmentList',
  data() {
    return {
      // 搜索表单
      searchForm: {
        name: '',
        city: '',
        status: null
      },
      // 表格数据
      tableData: [],
      loading: false,
      // 分页
      pagination: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      // 对话框
      dialogVisible: false,
      dialogTitle: '新增公寓',
      isEdit: false,
      // 表单数据
      formData: {
        apartmentId: null,
        name: '',
        code: '',
        city: '',
        address: '',
        longitude: null,
        latitude: null,
        phone: '',
        managerName: '',
        managerPhone: '',
        status: 1,
        remark: ''
      },
      // 表单验证规则
      rules: {
        name: [
          { required: true, message: '请输入公寓名称', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入公寓编码', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        city: [
          { required: true, message: '请输入所在城市', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入详细地址', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    // 加载数据
    async loadData() {
      this.loading = true
      try {
        const params = {
          pageNum: this.pagination.currentPage,
          pageSize: this.pagination.pageSize,
          ...this.searchForm
        }
        const res = await getApartmentList(params)
        if (res.code === 200) {
          this.tableData = res.data.records
          this.pagination.total = res.data.total
        } else {
          this.$message.error(res.message)
        }
      } catch (error) {
        this.$message.error('加载数据失败')
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    // 搜索
    handleSearch() {
      this.pagination.currentPage = 1
      this.loadData()
    },

    // 重置
    handleReset() {
      this.searchForm = {
        name: '',
        city: '',
        status: null
      }
      this.pagination.currentPage = 1
      this.loadData()
    },

    // 新增
    handleAdd() {
      this.dialogTitle = '新增公寓'
      this.isEdit = false
      this.resetForm()
      this.dialogVisible = true
    },

    // 编辑
    handleEdit(row) {
      this.dialogTitle = '编辑公寓'
      this.isEdit = true
      this.formData = { ...row }
      this.dialogVisible = true
    },

    // 删除
    handleDelete(row) {
      this.$confirm('确定要删除该公寓吗？此操作不可恢复！', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const res = await deleteApartment(row.apartmentId)
          if (res.code === 200) {
            this.$message.success('删除成功')
            this.loadData()
          } else {
            this.$message.error(res.message)
          }
        } catch (error) {
          this.$message.error('删除失败')
          console.error(error)
        }
      }).catch(() => {})
    },

    // 切换状态
    async handleToggleStatus(row) {
      const newStatus = row.status === 1 ? 0 : 1
      const action = newStatus === 1 ? '启用' : '停用'
      
      try {
        const res = await updateApartmentStatus(row.apartmentId, newStatus)
        if (res.code === 200) {
          this.$message.success(`${action}成功`)
          this.loadData()
        } else {
          this.$message.error(res.message)
        }
      } catch (error) {
        this.$message.error(`${action}失败`)
        console.error(error)
      }
    },

    // 提交表单
    handleSubmit() {
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          try {
            let res
            if (this.isEdit) {
              res = await updateApartment(this.formData)
            } else {
              res = await addApartment(this.formData)
            }
            
            if (res.code === 200) {
              this.$message.success(this.isEdit ? '编辑成功' : '新增成功')
              this.dialogVisible = false
              this.loadData()
            } else {
              this.$message.error(res.message)
            }
          } catch (error) {
            this.$message.error(this.isEdit ? '编辑失败' : '新增失败')
            console.error(error)
          }
        }
      })
    },

    // 重置表单
    resetForm() {
      this.formData = {
        apartmentId: null,
        name: '',
        code: '',
        city: '',
        address: '',
        longitude: null,
        latitude: null,
        phone: '',
        managerName: '',
        managerPhone: '',
        status: 1,
        remark: ''
      }
      if (this.$refs.formRef) {
        this.$refs.formRef.clearValidate()
      }
    },

    // 分页大小改变
    handleSizeChange(val) {
      this.pagination.pageSize = val
      this.loadData()
    },

    // 当前页改变
    handleCurrentChange(val) {
      this.pagination.currentPage = val
      this.loadData()
    },

    // 格式化日期
    formatDate(date) {
      if (!date) return '-'
      return new Date(date).toLocaleString('zh-CN')
    }
  }
}
</script>

<style scoped>
.apartment-list {
  padding: 20px;
}

.search-form {
  display: flex;
  flex-wrap: wrap;
}

.el-form-item {
  margin-bottom: 0;
}
</style>
