<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="监控对象">
              <a-input placeholder="请输入监控对象" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="监控类型">
              <j-dict-select-tag placeholder="请选择监控类型" v-model="queryParam.alarmType" dictCode="monitor_alarm_type"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="报警规则">
                <j-dict-select-tag placeholder="请选择报警规则" v-model="queryParam.alarmRuleType"
                                   dictCode="alarm__rule_type"/>
              </a-form-item>
            </a-col>
          </template>
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
        <span slot="monitor" slot-scope="text, record">
          <a @click="showMonitorMessage(record)">查看记录</a>
        </span>
        <span slot="action" slot-scope="text, record">

          <a @click="handleEdit(record)" v-if="record.status === 'N'">编辑</a>
          <a-divider type="vertical" v-if="record.status === 'N'"/>
          <a @click="handleUse(record)" :aria-disabled="true" v-if="record.status === 'N'">生效</a>
          <a-divider type="vertical" v-if="record.status === 'N'"/>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)" v-if="record.status === 'N'">
            <a>删除</a>
          </a-popconfirm>
          <a @click="handleUnUse(record)" :aria-disabled="true" v-if="record.status === 'Y'">取消生效</a>
        </span>

      </a-table>
    </div>

    <monitorAlarmConfig-modal ref="modalForm" @ok="modalFormOk"></monitorAlarmConfig-modal>
    <a-modal
      title="告警记录"
      :width="1000"
      :visible="monitorMessageVisible"
      :footer="null"
      @cancel="closeMonitorMessage"
      cancelText="关闭">
      <MonitorMessageListModal :alarm-id="alarmId" v-if="monitorMessageVisible"></MonitorMessageListModal>
    </a-modal>
  </a-card>
</template>

<script>
  import {filterObj} from '@/utils/util';
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import MonitorAlarmConfigModal from './modules/MonitorAlarmConfigModal'
  import MonitorMessageListModal from './modules/MonitorMessageListModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import {postAction} from "../../../api/manage";

  export default {
    name: "MonitorAlarmConfigList",
    mixins: [JeecgListMixin],
    components: {
      MonitorAlarmConfigModal,
      MonitorMessageListModal
    },
    data() {
      return {
        monitorMessageVisible: false,
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
            title: '监控对象',
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
            title: '告警记录',
            dataIndex: 'monitor',
            align: "center",
            scopedSlots: {customRender: 'monitor'}
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
        alarmId: ''
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      closeMonitorMessage() {
        this.monitorMessageVisible = false
      },
      showMonitorMessage(record) {
        this.alarmId = record.id
        this.monitorMessageVisible = true
      },
      getQueryParams() {
        //获取查询条件
        let sqp = {}
        if (this.superQueryParams) {
          sqp['superQueryParams'] = encodeURI(this.superQueryParams)
        }
        var param = Object.assign(sqp, this.queryParam, this.isorter, this.filters);
        if (param.title != null) {
          param.title = "*" + param.title + "*"
        }

        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      handleUnUse(record) {
        postAction(this.url.unUse, {id: record.id}).then(res => {
          if (res.success) {
            this.$message.success('取消生效成功')
            this.loadData()
          } else {
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