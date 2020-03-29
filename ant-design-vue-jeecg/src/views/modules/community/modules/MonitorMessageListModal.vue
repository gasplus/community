<template>
  <a-card :bordered="false">
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
        <span slot="monitor_address" slot-scope="text,record">
          <span v-if="record.dataContent&&record.dataContent.indexOf('{')>=0">
            {{JSON.parse(record.dataContent).address}}
          </span>
        </span>
        <span slot="monitor_time" slot-scope="text,record">
          <span v-if="record.dataContent&&record.dataContent.indexOf('{')>=0">
            {{getDateStr(JSON.parse(record.dataContent).outInTime)}}
          </span>
        </span>
        <div slot="monitor_img" slot-scope="text,record">
          <div v-if="record.dataContent&&record.dataContent.indexOf('{')>=0">
            <span v-if="!JSON.parse(record.dataContent).photoUrl"
                  style="font-size: 12px;font-style: italic;">无此图片</span>
            <img v-else :src="imgBasePath+JSON.parse(record.dataContent).photoUrl" height="25px" alt="图片不存在"
                 style="max-width:80px;font-size: 12px;font-style: italic;"/>
          </div>
        </div>

      </a-table>
    </div>

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
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JDate from '@/components/jeecg/JDate.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "MonitorMessageList",
    mixins: [JeecgListMixin],
    components: {
      JDictSelectTag,
      JDate
    },
    props: {
      alarmId: {
        type: String,
        default() {
          return ''
        }
      }
    },
    data() {
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
              return parseInt(index) + 1;
            }
          },
          /*{
            title:'类型',
            align:"center",
            dataIndex: 'messageType',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['messageType'], text+"")
              }
            }
          },*/
          {
            title: '告警内容',
            align: "center",
            dataIndex: 'content'
          },
          {
            title: '进出时间',
            align: 'center',
            width: '100px',
            dataIndex: 'monitor_time',
            scopedSlots: {customRender: 'monitor_time'}
          },
          {
            title: '监控照片',
            align: 'center',
            dataIndex: 'monitor_img',
            scopedSlots: {customRender: 'monitor_img'}
          },
          {
            title: '进出地点',
            align: 'center',
            width: '200px',
            dataIndex: 'monitor_address',
            scopedSlots: {customRender: 'monitor_address'}
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
        dictOptions: {
          messageType: [],
          status: [],
        },
        disableMixinCreated: true
      }
    },
    created() {
      this.queryParam.alarmConfigId = this.alarmId
      this.loadData();
      //初始化字典配置 在自己页面定义
      this.initDictConfig();
    },
    computed: {
      importExcelUrl: function () {
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