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


    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"

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

    <monitorSearchResult-modal ref="modalForm" @ok="modalFormOk"></monitorSearchResult-modal>
  </a-card>
</template>

<script>

  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import MonitorSearchResultModal from './modules/MonitorSearchResultModal'

  export default {
    name: "MonitorSearchResultList",
    mixins: [JeecgListMixin],
    components: {
      MonitorSearchResultModal
    },
    props: {
      searchId: {
        type: String,
        default() {
          return '123'
        }
      }
    },
    data() {
      return {
        description: '查询结果管理页面',
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
            title: '租户id',
            align: "center",
            dataIndex: 'tenantId'
          },
          {
            title: 'data_id',
            align: "center",
            dataIndex: 'dataId'
          },
          {
            title: '数据类型',
            align: "center",
            dataIndex: 'dataType'
          },
          {
            title: '查询id',
            align: "center",
            dataIndex: 'searchId'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'}
          }
        ],
        url: {
          list: "/monitor/monitorSearchResult/list",
          delete: "/monitor/monitorSearchResult/delete",
          deleteBatch: "/monitor/monitorSearchResult/deleteBatch",
          exportXlsUrl: "/monitor/monitorSearchResult/exportXls",
          importExcelUrl: "monitor/monitorSearchResult/importExcel",
        },
        dictOptions: {},
        disableMixinCreated: true
      }
    },
    mounted() {
      console.log(this.searchId)
      if(this.searchId){
        this.queryParam.searchId = this.searchId
        this.loadData();
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig() {
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>