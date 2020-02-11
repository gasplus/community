<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="12" :sm="16">
            <a-form-item label="进出时间">
              <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始时间" class="query-group-cust"
                      v-model="queryParam.outInTime_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间" class="query-group-cust"
                      v-model="queryParam.outInTime_end"></j-date>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="16">
            <a-form-item label="人体识别年龄">
              <a-input placeholder="请输入最小值" class="query-group-cust" v-model="queryParam.bodyInfoAge_begin"></a-input>
              <span class="query-group-split-cust"></span>
              <a-input placeholder="请输入最大值" class="query-group-cust" v-model="queryParam.bodyInfoAge_end"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="人体识别性别">
                <j-dict-select-tag placeholder="请选择人体识别性别" v-model="queryParam.bodyInfoGender"
                                   dictCode="monitor_gender"/>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="16">
              <a-form-item label="人脸识别年龄">
                <a-input placeholder="请输入最小值" class="query-group-cust" v-model="queryParam.faceInfoAge_begin"></a-input>
                <span class="query-group-split-cust"></span>
                <a-input placeholder="请输入最大值" class="query-group-cust" v-model="queryParam.faceInfoAge_end"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="人脸识别性别">
                <j-dict-select-tag placeholder="请选择人脸识别性别" v-model="queryParam.faceInfoGender"
                                   dictCode="monitor_gender"/>
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

    <!-- 操作按钮区域
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('人体检测结果')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>
-->
    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
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

      </a-table>
    </div>

    <monitorHumanRecord-modal ref="modalForm" @ok="modalFormOk"></monitorHumanRecord-modal>
  </a-card>
</template>

<script>

  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import MonitorHumanRecordModal from './modules/MonitorHumanRecordModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JDate from '@/components/jeecg/JDate.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "MonitorHumanRecordList",
    mixins: [JeecgListMixin],
    components: {
      JDictSelectTag,
      JDate,
      MonitorHumanRecordModal
    },
    data() {
      return {
        description: '人体检测结果管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'人员姓名',
            align:"center",
            dataIndex: 'personName'
          },
          {
            title:'身份证',
            align:"center",
            dataIndex: 'personIdCard'
          },
          {
            title:'进出时间',
            align:"center",
            dataIndex: 'outInTime'
          },
          {
            title: '图片地址',
            align: "center",
            dataIndex: 'photoUrl',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title: '进出地址',
            align: "center",
            dataIndex: 'address'
          },
          {
            title: '人体识别年龄',
            align: "center",
            dataIndex: 'bodyInfoAge'
          },
          {
            title: '人体识别性别',
            align: "center",
            dataIndex: 'bodyInfoGender',
            customRender: (text) => {
              if (!text) {
                return ''
              } else {
                return filterMultiDictText(this.dictOptions['bodyInfoGender'], text + "")
              }
            }
          },
          {
            title: '人体识别头发',
            align: "center",
            dataIndex: 'bodyInfoHair',
            customRender: (text) => {
              if (!text) {
                return ''
              } else {
                return filterMultiDictText(this.dictOptions['bodyInfoHair'], text + "")
              }
            }
          },
          {
            title: '人脸识别年龄',
            align: "center",
            dataIndex: 'faceInfoAge'
          },
          {
            title: '人脸识别性别',
            align: "center",
            dataIndex: 'faceInfoGender',
            customRender: (text) => {
              if (!text) {
                return ''
              } else {
                return filterMultiDictText(this.dictOptions['faceInfoGender'], text + "")
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

        /* 排序参数 */
        isorter:{
          column: 'outInTime',
          order: 'desc',
        },
        url: {
          list: "/monitor/monitorHumanRecord/list",
          delete: "/monitor/monitorHumanRecord/delete",
          deleteBatch: "/monitor/monitorHumanRecord/deleteBatch",
          exportXlsUrl: "/monitor/monitorHumanRecord/exportXls",
          importExcelUrl: "monitor/monitorHumanRecord/importExcel",
        },
        dictOptions: {
          bodyInfoGender: [],
          bodyInfoHair: [],
          faceInfoGender: [],
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig() {
        initDictOptions('monitor_gender').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'bodyInfoGender', res.result)
          }
        })
        initDictOptions('monitor_hair').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'bodyInfoHair', res.result)
          }
        })
        initDictOptions('monitor_gender').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'faceInfoGender', res.result)
          }
        })
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>