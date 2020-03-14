<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="搜索标题">
              <a-input placeholder="请输入搜索标题" v-model="queryParam.searchTitle"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('搜索任务')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
                @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"

        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在"
               style="max-width:80px;font-size: 12px;font-style: italic;"/>
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

          <a-divider type="vertical"/>

          <!--<a @click="handleView(record)">查看监控</a>-->
          <a @click="handleResult(record)">查看结果</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <a-modal
      v-if="selectRecord"
      title="监控记录"
      :width="1200"
      v-model="jkVisible"
      :footer="null">
      <SelectMonitorPersonRecordList v-if="jkVisible" :selectId="selectRecord.searchContent"></SelectMonitorPersonRecordList>
    </a-modal>
    <monitorSearchTask-modal ref="modalForm" @ok="modalFormOk"></monitorSearchTask-modal>
  </a-card>
</template>

<script>

  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import SelectMonitorPersonRecordList from './modules/SelectMonitorPersonRecordList'
  import MonitorSearchTaskModal from './modules/MonitorSearchTaskModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "MonitorSearchTaskList",
    mixins: [JeecgListMixin],
    components: {
      SelectMonitorPersonRecordList,
      MonitorSearchTaskModal
    },
    data() {
      return {
        selectRecord: undefined,
        description: '搜索任务管理页面',
        jkVisible:false,
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: "center",
            customRender: function (t, r, index) {
              return parseInt(index) + 1;
            }
          },
          {
            title: '搜索标题',
            align: "center",
            dataIndex: 'searchTitle'
          },
          {
            title: '搜索图片',
            align: "center",
            dataIndex: 'searchUrl',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title: '开始时间',
            align: "center",
            dataIndex: 'beginTime'
          },
          {
            title: '结束时间',
            align: "center",
            dataIndex: 'endTime'
          },
          {
            title: '状态',
            align: "center",
            dataIndex: 'searchStatus',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['monitor_search_status'], text+"")
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'}
          }
        ],
        url: {
          list: "/monitor/monitorSearchTask/list",
          delete: "/monitor/monitorSearchTask/delete",
          deleteBatch: "/monitor/monitorSearchTask/deleteBatch",
          exportXlsUrl: "/monitor/monitorSearchTask/exportXls",
          importExcelUrl: "monitor/monitorSearchTask/importExcel",
        },
        dictOptions: {
          monitor_search_status: []
        },
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      handleView(record) {
        this.selectRecord = record
        this.jkVisible = true
      },
      handleResult(record) {

        //MonitorSearchResultList
      },
      initDictConfig() {

        initDictOptions('monitor_search_status').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'monitor_search_status', res.result)
          }
        })
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>