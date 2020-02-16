<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('警告配置')">导出</a-button>
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
          <a @click="handleEdit(record)" v-if="record.status === 'N'">编辑</a>
          <a-divider type="vertical" v-if="record.status === 'N'"/>
          <a @click="handleUse(record)" :aria-disabled="true" v-if="record.status === 'N'">生效</a>
          <a-divider type="vertical" v-if="record.status === 'N'"/>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)"  v-if="record.status === 'N'">
            <a>删除</a>
          </a-popconfirm>

          <a @click="handleUnUse(record)" :aria-disabled="true" v-if="record.status === 'Y'">取消生效</a>
        </span>

      </a-table>
    </div>
    <monitorAlarmConfig-modal ref="modalForm" @ok="modalFormOk"></monitorAlarmConfig-modal>
  </a-card>
</template>

<script>

  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import MonitorAlarmConfigModal from './modules/MonitorAlarmConfigModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import {postAction} from "../../../api/manage";

  export default {
    name: "MonitorAlarmConfigList",
    mixins: [JeecgListMixin],
    components: {
      MonitorAlarmConfigModal
    },
    data() {
      return {
        description: '警告配置管理页面',
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
            title: '监控标题',
            align: "center",
            dataIndex: 'title'
          },
          {
            title: '监控类型',
            align: "center",
            dataIndex: 'alarmType',
            customRender: (text) => {
              if (!text) {
                return ''
              } else {
                return filterMultiDictText(this.dictOptions['alarmType'], text + "")
              }
            }
          },
          {
            title: '报警规则',
            align: "center",
            dataIndex: 'alarmRuleType',
            customRender: (text) => {
              if (!text) {
                return ''
              } else {
                return filterMultiDictText(this.dictOptions['alarmRuleType'], text + "")
              }
            }
          },
          {
            title: '是否有效',
            align: "center",
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
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'}
          }
        ],
        url: {
          unUse: "/monitor/monitorAlarmConfig/invalid",
          use: "/monitor/monitorAlarmConfig/effective",
          list: "/monitor/monitorAlarmConfig/list",
          delete: "/monitor/monitorAlarmConfig/delete",
          deleteBatch: "/monitor/monitorAlarmConfig/deleteBatch",
          exportXlsUrl: "/monitor/monitorAlarmConfig/exportXls",
          importExcelUrl: "monitor/monitorAlarmConfig/importExcel",
        },
        dictOptions: {
          alarmType: [],
          alarmRuleType: [],
          status: [],
        },
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      handleUnUse(record){
        postAction(this.url.unUse,{id:record.id}).then(res => {
          if(res.success){
            this.$message.success('取消生效成功')
            this.loadData()
          }else{
            this.$message.error(res.message)
          }
        })
      },
      handleUse(record) {
        postAction(this.url.use,{id:record.id}).then(res => {
          if(res.success){
            this.$message.success('配置生效成功')
            this.loadData()
          }else{
            this.$message.error(res.message)
          }
        })
      },
      initDictConfig() {
        initDictOptions('monitor_alarm_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'alarmType', res.result)
          }
        })
        initDictOptions('alarm__rule_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'alarmRuleType', res.result)
          }
        })
        initDictOptions('isYN').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'status', res.result)
          }
        })
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>