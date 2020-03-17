<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="12" :sm="16">
            <a-form-item label="创建日期">
              <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始时间" class="query-group-cust"
                      v-model="queryParam.createTime_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间" class="query-group-cust"
                      v-model="queryParam.createTime_end"></j-date>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <a-form-item label="日志内容">

              <a-input placeholder="请输入日志内容" v-model="queryParam.content"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <a-form-item label="类型">
              <j-dict-select-tag placeholder="请选择类型" v-model="queryParam.messageType" dictCode="message_type"/>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <a-form-item label="状态">
              <j-dict-select-tag placeholder="请选择状态" v-model="queryParam.status" dictCode="message_status"/>
              </a-form-item>
            </a-col>

          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>

            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator" v-if="selectedRowKeys.length > 0">
      <a-dropdown>
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>批量删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
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
        <span slot="monitor" slot-scope="text,record">
          <a-popover placement="top" trigger="hover" v-if="record.dataContent&&record.dataContent.indexOf('{')>=0">
            <template slot="content">
              <a-row style="min-width:260px;">
                <a-col span="24">
                  <div class="monitor_img">
                    <img
                      alt="example"
                      :src="imgBasePath+JSON.parse(record.dataContent).photoUrl"
                      slot="cover"
                    />
                  </div>
                </a-col>
              </a-row>
              <a-row>
                <a-col span="6">进出地点</a-col>
                <a-col span="18">{{JSON.parse(record.dataContent).address}}</a-col>
              </a-row>
              <a-row>
                <a-col span="6">进出时间</a-col>
                <a-col span="18">{{getDateStr(JSON.parse(record.dataContent).outInTime)}}</a-col>
              </a-row>
            </template>
            <a>
              查看
            </a>
          </a-popover>
        </span>

        <span slot="action" slot-scope="text, record">

          <a @click="handleRead(record)" v-if="record.status === '0'">处理</a>
          <a-divider type="vertical" v-if="record.status === '0'"/>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>

    <monitorMessage-modal ref="modalForm" @ok="modalFormOk"></monitorMessage-modal>
  </a-card>
</template>

<script>
  Date.prototype.Format = function (fmt) { //author: meizz
    const o = {
      "M+": this.getMonth() + 1, //月份
      "d+": this.getDate(), //日
      "h+": this.getHours(), //小时
      "m+": this.getMinutes(), //分
      "s+": this.getSeconds(), //秒
      "q+": Math.floor((this.getMonth() + 3) / 3), //季度
      "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
      if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
  }
  import {filterObj} from '@/utils/util';
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {putAction} from "@/api/manage";
  import MonitorMessageModal from './modules/MonitorMessageModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JDate from '@/components/jeecg/JDate.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "MonitorMessageList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      JDate,
      MonitorMessageModal
    },
    data () {
      return {
        imgBasePath: window._CONFIG['imgDomainRecordURL'] + '/',
        description: '监控信息管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: "center",
            customRender: function (t, r, index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'创建日期',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title:'类型',
            align:"center",
            dataIndex: 'messageType',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['messageType'], text + "")
              }
            }
          },
          {
            title: '日志内容',
            align: "center",
            dataIndex: 'content'
          },
          {
            title: '监控信息',
            align: 'center',
            dataIndex: 'monitor',
            scopedSlots: {customRender: 'monitor'}
          },
          {
            title: '状态',
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
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          readMessage: '/monitor/monitorMessage/read',
          list: "/monitor/monitorMessage/list",
          delete: "/monitor/monitorMessage/delete",
          deleteBatch: "/monitor/monitorMessage/deleteBatch",
          exportXlsUrl: "/monitor/monitorMessage/exportXls",
          importExcelUrl: "monitor/monitorMessage/importExcel",
        },
        dictOptions:{
         messageType:[],
         status:[],
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      getQueryParams() {
        //获取查询条件
        let sqp = {}
        if (this.superQueryParams) {
          sqp['superQueryParams'] = encodeURI(this.superQueryParams)
        }
        var param = Object.assign(sqp, this.queryParam, this.isorter, this.filters);
        if (param.content != null) {
          param.content = "*" + param.content + "*"
        }

        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      handleRead(record) {
        putAction(this.url.readMessage, {id: record.id, status: 1}).then(res => {
          this.loadData()
        })
      },
      getDateStr(time) {
        if (time) {
          return new Date(time).Format("yyyy-MM-dd hh:mm:ss")
        }
        return ''
      },
      initDictConfig() {
        initDictOptions('message_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'messageType', res.result)
          }
        })
        initDictOptions('message_status').then((res) => {
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
<style>
  .monitor_img {
    width: 100px;
    margin: 0 auto;
  }

  .monitor_img img {
    display: block;
    padding: 0;
    margin: 0;
    width: 100%;
  }
</style>