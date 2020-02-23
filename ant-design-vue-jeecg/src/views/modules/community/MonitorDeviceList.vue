<template>
  <a-card :bordered="false">
    <!-- 查询开始   -->

    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :span="6">
            <a-form-item label="设备序号">
              <a-input placeholder="请输入设备序号查询" v-model="queryParam.deviceId"></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="6">
            <a-form-item label="地址">
              <a-input placeholder="请输入地址查询" v-model="queryParam.address"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="设备地址">
              <a-input placeholder="请输入设备地址查询" v-model="queryParam.deviceIp"></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="6">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询结束   -->
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :expandedRowKeys="expandedRowKeys"
        @change="handleTableChange"
        @expand="handleExpand"
        v-bind="tableProps">

        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
        <span slot="device" slot-scope="text, record">
          <a-icon v-if="record.deviceId" type="video-camera" style="cursor:pointer" @click="showDeviceVideo(record.deviceId)" />
        </span>
      </a-table>
    </div>
    <deviceDetail ref="deviceDetail" :center="true" v-if="deviceDetailShow" @leave="closeDeviceDetail"></deviceDetail>
    <monitorDevice-modal ref="modalForm" @ok="modalFormOk"></monitorDevice-modal>
  </a-card>
</template>

<script>

  import { getAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {deviceMixin} from '@/mixins/deviceMixin'
  import deviceDetail from '@/components/big/deviceDetail'
  import MonitorDeviceModal from './modules/MonitorDeviceModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "MonitorDeviceList",
    mixins:[JeecgListMixin,deviceMixin],
    components: {
      MonitorDeviceModal,
      deviceDetail
    },
    data () {
      return {
        description: '监控设备管理管理页面',
        // 表头
        columns: [
          {
            title:'名称',
            align:"left",
            dataIndex: 'name'
          },
          {
            title: '设备序号',
            align: "left",
            dataIndex: 'deviceId'
          },

          {
            title:'地址',
            align:"left",
            dataIndex: 'address'
          },
          {
            title: '状态',
            align: "left",
            dataIndex: 'status',
            customRender: (text) => {
              if (!text) {
                return ''
              } else {
                return filterMultiDictText(this.dictOptions['status'], text + "")
              }
            }
          },
          {
            title: '设备地址',
            align: "left",
            dataIndex: 'deviceIp'
          },


          {
            title: '播放地址',
            align: "left",
            dataIndex: 'liveUrl'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          },
          {
            title: '查看监控',
            align: "center",
            dataIndex: 'deviceId1',
            scopedSlots: {customRender: 'device'}
          }
        ],
        url: {
          list: "/monitor/monitorDevice/rootList",
          childList: "/monitor/monitorDevice/childList",
          delete: "/monitor/monitorDevice/delete",
          deleteBatch: "/monitor/monitorDevice/deleteBatch",
          exportXlsUrl: "/monitor/monitorDevice/exportXls",
          importExcelUrl: "monitor/monitorDevice/importExcel",
        },
        expandedRowKeys: [],
        hasChildrenField: "hasChild",
        pidField: "pid",
        dictOptions: {
          status: [],
        }
      }
    },
    computed: {
      importExcelUrl(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
      tableProps() {
        let _this = this
        return {
          // 列表项是否可选择
          rowSelection: {
            selectedRowKeys: _this.selectedRowKeys,
            onChange: (selectedRowKeys) => _this.selectedRowKeys = selectedRowKeys
          }
        }
      }
    },
    methods: {
      loadData(arg){
        if(arg==1){
          this.ipagination.current=1
        }
        this.loading = true
        this.expandedRowKeys = []
        let params = this.getQueryParams()
        return new Promise((resolve) => {
          getAction(this.url.list,params).then(res=>{
            if(res.success){
              let result = res.result
              if(Number(result.total)>0){
                this.ipagination.total = Number(result.total)
                this.dataSource = this.getDataByResult(res.result.records)
                resolve()
              }else{
                this.ipagination.total=0
                this.dataSource=[]
              }
            }else{
              this.$message.warning(res.message)
            }
            this.loading = false
          })
        })
      },
      getDataByResult(result){
        return result.map(item=>{
          //判断是否标记了带有子节点
          if(item[this.hasChildrenField]=='1'){
            let loadChild = { id: item.id+'_loadChild', name: 'loading...', isLoading: true }
            item.children = [loadChild]
          }
          return item
        })
      },
      handleExpand(expanded, record){
        // 判断是否是展开状态
        if (expanded) {
          this.expandedRowKeys.push(record.id)
          if (record.children.length>0 && record.children[0].isLoading === true) {
            let params = this.getQueryParams();//查询条件
            params[this.pidField] = record.id
            getAction(this.url.childList,params).then((res)=>{
              if(res.success){
                if(res.result && res.result.length>0){
                  record.children = this.getDataByResult(res.result)
                  this.dataSource = [...this.dataSource]
                }else{
                  record.children=''
                  record.hasChildrenField='0'
                }
              }else{
                this.$message.warning(res.message)
              }
            })
          }
        }else{
          let keyIndex = this.expandedRowKeys.indexOf(record.id)
          if(keyIndex>=0){
            this.expandedRowKeys.splice(keyIndex, 1);
          }
        }
      },
      initDictConfig() {
        initDictOptions('device_status').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'status', res.result)
          }
        })
      },
      modalFormOk(formData,arr){
        if(!formData.id){
          this.addOk(formData,arr)
        }else{
          if(!arr){
            this.loadData()
          }else{
            this.editOk(formData,this.dataSource)
            this.dataSource=[...this.dataSource]
          }
        }
      },
      editOk(formData,arr){
        if(arr && arr.length>0){
          for(let i=0;i<arr.length;i++){
            if(arr[i].id==formData.id){
              arr[i]=formData
              break
            }else{
              this.editOk(formData,arr[i].children)
            }
          }
        }
      },
      async addOk(formData,arr){
        if(!formData[this.pidField]){
          this.loadData()
        }else{
          this.expandedRowKeys=[]
          for(let i of arr){
            await this.expandTreeNode(i)
          }
        }
      },
      expandTreeNode(nodeId){
        return new Promise((resolve,reject)=>{
          this.getFormDataById(nodeId,this.dataSource)
          let row = this.parentFormData
          this.expandedRowKeys.push(nodeId)
          let params = this.getQueryParams();//查询条件
          params[this.pidField] = nodeId
          getAction(this.url.childList,params).then((res)=>{
            if(res.success){
              if(res.result && res.result.length>0){
                row.children = this.getDataByResult(res.result)
                this.dataSource = [...this.dataSource]
                resolve()
              }else{
                reject()
              }
            }else{
              reject()
            }
          })
        })
      },
      getFormDataById(id,arr){
        if(arr && arr.length>0){
          for(let i=0;i<arr.length;i++){
            if(arr[i].id==id){
              this.parentFormData = arr[i]
            }else{
              this.getFormDataById(id,arr[i].children)
            }
          }
        }
      },


    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>