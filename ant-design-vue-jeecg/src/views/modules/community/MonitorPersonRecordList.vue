<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="人员姓名">
              <a-input placeholder="请输入人员姓名" v-model="queryParam.personName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="身份证">
              <a-input placeholder="请输入身份证" v-model="queryParam.personIdCard"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="8">
            <a-form-item label="进出时间">
              <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择开始时间"
                      class="query-group-cust" v-model="queryParam.outInTime_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" placeholder="请选择结束时间"
                      class="query-group-cust" v-model="queryParam.outInTime_end"></j-date>
            </a-form-item>
          </a-col>
          <a-col :md="18" :sm="18">
            <a-form-item label="设备选择">
              <a-select
                mode="multiple"
                placeholder="请选择设备"
                v-model="selectedDevices"
                @deselect="removeSelected"
                @dropdownVisibleChange="showDeviceSelect"
              >
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="6">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

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


      </a-table>
    </div>
    <SelectDeviceListModal ref="DeviceListModal" @choseDeviceList="choseDeviceList"></SelectDeviceListModal>
    <monitorPersonRecord-modal ref="modalForm" @ok="modalFormOk"></monitorPersonRecord-modal>
  </a-card>
</template>

<script>

  import {filterObj} from '@/utils/util';
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import MonitorPersonRecordModal from './modules/MonitorPersonRecordModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import SelectDeviceListModal from "./modules/SelectDeviceListModal";
  import JDate from '@/components/jeecg/JDate.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "MonitorPersonRecordList",
    mixins:[JeecgListMixin],
    components: {
      JDictSelectTag,
      JDate,
      SelectDeviceListModal,
      MonitorPersonRecordModal
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
          }
        ],
        /* 排序参数 */
        isorter:{
          column: 'outInTime',
          order: 'desc',
        },
        url: {
          list: "/monitor/monitorPersonRecord/list",
          delete: "/monitor/monitorPersonRecord/delete",
          deleteBatch: "/monitor/monitorPersonRecord/deleteBatch",
          exportXlsUrl: "/monitor/monitorPersonRecord/exportXls",
          importExcelUrl: "monitor/monitorPersonRecord/importExcel",
        },
        deviceIds: [],
        selectedDevices:[],
        dictOptions:{
          personType:[],
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
        if (param.personName != null) {
          param.personName = "*" + param.personName + "*"
        }
        if (param.personIdCard != null) {
          param.personIdCard = "*" + param.personIdCard + "*"
        }
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      showDeviceSelect() {
        this.$refs.DeviceListModal.add(this.selectedDevices, this.deviceIds);
      },
      removeSelected(value) {
        let deleteInd = -1
        const deviceIds = this.deviceIds.split(',')
        this.selectedDevices.forEach((item, index) => {
          if (item === value) {
            deleteInd = index
          }
        })
        if(deleteInd!==-1){
          this.selectedDevices.splice(deleteInd,1)
          deviceIds.splice(deleteInd,1)
          this.deviceIds = deviceIds.join(',')
        }
      },
      choseDeviceList(deviceList) {
        console.log(deviceList)
        this.selectedDevices = [];
        this.deviceIds = '';
        for(let i=0;i<deviceList.length;i++){
          this.selectedDevices.push(deviceList[i].address);
        }
        this.deviceIds += deviceList.map(item => item.deviceId).join(",")
        this.queryParam.deviceId = this.deviceIds
      },
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