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
          <img v-else :src="getImgViewRecord(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
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
        <span slot="show" slot-scope="text,record">
          <a-popover title="监控结果" trigger="hover" v-if="record.personId!=='anonymous'">
            <template slot="content">
              <a-row>
                <a-col span="24">
                  <a-card hoverable style="width: 540px;margin:0 10px;" title="" size="small">
                    <img
                      alt="example"
                      :src="imgBasePath+record.photoUrl"
                      slot="cover"
                    />
                    <a-card-meta>
                      <template slot="description">
                        <a-row>
                          <a-col span="6">姓名</a-col>
                          <a-col span="18">{{record.personName}}</a-col>
                        </a-row>
                        <a-row>
                          <a-col span="6">身份证号</a-col>
                          <a-col span="18">{{record.personIdCard}}</a-col>
                        </a-row>
                        <a-row>
                          <a-col span="6">户籍地址</a-col>
                          <a-col span="18">{{record.hjdz}}</a-col>
                        </a-row>
                        <a-row>
                          <a-col span="6">出入时间</a-col>
                          <a-col span="18">{{record.outInTime}}</a-col>
                        </a-row>
                      </template>
                    </a-card-meta>
                  </a-card>
                </a-col>
              </a-row>
            </template>
            <a>{{record.personName}}</a>
          </a-popover>
          <span v-if="record.personId==='anonymous'">{{record.personName}}</span>
        </span>

        <span slot="device" slot-scope="text, record">
          <a-icon v-if="record.deviceId" type="video-camera" style="cursor:pointer" @click="showDeviceVideo(record.deviceId)" />
        </span>
      </a-table>
    </div>
    <deviceDetail ref="deviceDetail" :center="true" v-if="deviceDetailShow" @leave="closeDeviceDetail"></deviceDetail>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {deviceMixin} from '@/mixins/deviceMixin'
  import deviceDetail from '@/components/big/deviceDetail'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "SelectMonitorPersonRecordList",
    mixins:[JeecgListMixin,deviceMixin],
    props: {
      selectId:{
        type: String,
        default() {
          return ''
        }
      }
    },
    components: {
      deviceDetail
    },
    data () {
      return {
        imgBasePath: window._CONFIG['imgDomainRecordURL'],
        description: '人员监控信息管理页面',
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
            dataIndex: 'show',
            align: "center",
            scopedSlots: {customRender: 'show'}
          },
          {
            title:'进出时间',
            align:"center",
            dataIndex: 'outInTime'
          },
          {
            title:'人员类型',
            align:"center",
            dataIndex: 'personType',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['personType'], text+"")
              }
            }
          },
          {
            title:'图片地址',
            align:"center",
            dataIndex: 'photoUrl',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'进出地址',
            align:"center",
            dataIndex: 'address'
          },
          {
            title: '查看监控',
            align: "center",
            dataIndex: 'deviceId',
            scopedSlots: {customRender: 'device'}
          }
        ],
        /* 排序参数 */
        isorter:{
          column: 'outInTime',
          order: 'desc',
        },
        url: {
          list: "/monitor/monitorPersonRecord/list"
        },
        dictOptions:{
          personType:[],
        },
        disableMixinCreated: true
      }
    },
    created() {
      if(this.selectId){
        this.queryParam.id = this.selectId
        this.loadData();
      }

      //初始化字典配置 在自己页面定义
      this.initDictConfig();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        initDictOptions('person_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'personType', res.result)
          }
        })
      },
      /* 图片预览 */
      getImgViewRecord(text){
        if(text && text.indexOf(",")>0){
          text = text.substring(0,text.indexOf(","))
        }
        return window._CONFIG['imgDomainRecordURL']+text
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>