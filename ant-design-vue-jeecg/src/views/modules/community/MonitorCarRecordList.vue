<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="timeType==='3'?24:8" :sm="timeType==='3'?24:8">
            <a-form-item label="进出时间">
              <a-radio-group v-model="timeType" buttonStyle="solid">
                <a-radio-button value="3">自定义</a-radio-button>
                <a-radio-button value="0">当天</a-radio-button>
                <a-radio-button value="1">近7天</a-radio-button>
                <a-radio-button value="2">近30天</a-radio-button>
              </a-radio-group>
              <a-date-picker
                style="margin-left:20px;"
                v-if="timeType==='3'"
                :disabledDate="disabledStartDate"
                :allowClear="false"
                showTime
                format="YYYY-MM-DD HH:mm:ss"
                v-model="startValue"
                placeholder="请选择开始时间"
                @openChange="handleStartOpenChange"
              />
              <span class="query-group-split-cust" v-if="timeType==='3'"></span>
              <a-date-picker
                v-if="timeType==='3'"
                :allowClear="false"
                :disabledDate="disabledEndDate"
                showTime
                format="YYYY-MM-DD HH:mm:ss"
                placeholder="请选择结束时间"
                v-model="endValue"
                :open="endOpen"
                @openChange="handleEndOpenChange"
              />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="汽车牌号">
              <a-input placeholder="请输入车牌号" v-model="queryParam.carNumber"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="18" :sm="24">
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
          <a-col :md="6" :sm="8">
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
        <span slot="device" slot-scope="text, record">
          <a-icon v-if="record.deviceId" type="video-camera" style="cursor:pointer" @click="showDeviceVideo(record.deviceId)" />
        </span>
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <a-popover v-else placement="topLeft" arrowPointAtCenter>
            <template slot="content">
              <img :src="getImgViewRecord(text)" alt="图片不存在"
                   style="max-width:500px;font-size: 12px;font-style: italic;"/>
            </template>
            <img :src="getImgViewRecord(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
          </a-popover>
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
          <a v-if="record.personId&&record.personId!=='anonymous'" @click="showPersonRelation(record.personId)">
            {{record.personName}}
          </a>

          <span v-if="record.carId&&record.carId!=='anonymous'&&record.personId==='anonymous'">
            {{record.personName}}
          </span>
          <a-button v-if="record.carId&&record.carId==='anonymous'" type="primary" size="small"
                    @click="showCarRegister(record)">登记</a-button>
        </span>

        <span slot="show1" slot-scope="text,record">
          <a @click="showCarRelation(record.carNumber,record.carId,record.carType2,record.carColor)">
            {{record.carNumber}}
          </a>
        </span>
        <span slot="showRemarks" slot-scope="text,record">
          <a @click="showRemarksDialog(record)">
            查看备注
          </a>
        </span>

      </a-table>
    </div>

    <deviceDetail ref="deviceDetail" :center="true" v-if="deviceDetailShow" @leave="closeDeviceDetail"></deviceDetail>
    <SelectDeviceListModal ref="DeviceListModal" @choseDeviceList="choseDeviceList"></SelectDeviceListModal>
    <monitorCarRecord-modal ref="modalForm" @ok="modalFormOk"></monitorCarRecord-modal>
    <MonitorRegisterCarModal ref="modalForm1" @ok="modalFormOk"></MonitorRegisterCarModal>
    <PersonRelation v-if="personRelationShow" :selectPersonId="selectPersonId" @close="closePersonRelation"></PersonRelation>
    <CarRelation v-if="carRelationShow" :selectCarId="selectCarId" :selectCarNumber="selectCarNumber"
                 :selectCarColor="selectCarColor" :selectCarType="selectCarType"
                 @close="closeCarRelation"></CarRelation>
    <MonitorRecordRemarkListModal
      v-if="selectRecord"
      :recordId="selectRecord.id"
      :dataId="selectRecord.carId"
      :personId="selectRecord.personId"
      remarks-type="30"
      :recordShow="remarksShow"
      @handleCancel="closeRemarks"
    ></MonitorRecordRemarkListModal>
  </a-card>
</template>

<script>
  import moment from "moment";

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
  import {deviceMixin} from '@/mixins/deviceMixin'
  import deviceDetail from '@/components/big/deviceDetail'
  import MonitorCarRecordModal from './modules/MonitorCarRecordModal'
  import MonitorRegisterCarModal from './modules/MonitorRegisterCarModal'
  import MonitorRecordRemarkListModal from './modules/MonitorRecordRemarkListModal'
  import PersonRelation from './modules/PersonRelation'
  import CarRelation from './modules/CarRelation'
  import SelectDeviceListModal from "./modules/SelectDeviceListModal";
  import JDate from '@/components/jeecg/JDate.vue'

  export default {
    name: "MonitorCarRecordList",
    mixins:[JeecgListMixin,deviceMixin],
    components: {
      JDate,
      SelectDeviceListModal,
      MonitorCarRecordModal,
      MonitorRecordRemarkListModal,
      deviceDetail,
      PersonRelation,
      MonitorRegisterCarModal,
      CarRelation
    },
    data () {
      return {
        timeType: '',
        startValue: null,
        endValue: null,
        endOpen: false,
        description: '车辆监控记录管理页面',
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
            title:'车牌号',
            align:"center",
            dataIndex: 'carNumber',
            scopedSlots: {customRender: 'show1'}
          },
          {
            title:'车主',
            align:"center",
            dataIndex: 'personName',
            scopedSlots: {customRender: 'show'}
          },
          {
            title:'进出时间',
            align:"center",
            dataIndex: 'outInTime'
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
            title: '备注信息',
            align: "center",
            dataIndex: 'remark',
            scopedSlots: {customRender: 'showRemarks'}
          },
          {
            title: '查看监控',
            align: "center",
            dataIndex: 'deviceId',
            scopedSlots: {customRender: 'device'}
          }
        ],
        url: {
          list: "/monitor/monitorCarRecord/list",
          delete: "/monitor/monitorCarRecord/delete",
          deleteBatch: "/monitor/monitorCarRecord/deleteBatch",
          exportXlsUrl: "/monitor/monitorCarRecord/exportXls",
          importExcelUrl: "monitor/monitorCarRecord/importExcel",
        },
        dictOptions: {},
        deviceIds: [],
        selectedDevices: [],
        selectPersonId: '',
        personRelationShow: false,
        selectCarId: '',
        selectCarType: '',
        selectCarColor: '',
        selectCarNumber: '',
        carRelationShow: false,
        selectRecord: undefined,
        remarksShow: false
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    watch: {
      timeType(value) {
        if(value === '0'){
          const endDate = new Date()
          this.queryParam.outInTime_begin = endDate.Format('yyyy-MM-dd 00:00:00')
          this.queryParam.outInTime_end = endDate.Format('yyyy-MM-dd hh:mm:ss')
        } else if(value === '1'){
          const endDate = new Date()
          const beginDate = new Date(endDate.getTime()-7*24*60*60*1000)
          this.queryParam.outInTime_begin = beginDate.Format('yyyy-MM-dd 00:00:00')
          this.queryParam.outInTime_end = endDate.Format('yyyy-MM-dd hh:mm:ss')
        } else if(value === '2'){
          const endDate = new Date()
          const beginDate = new Date(endDate.getTime()-30*24*60*60*1000)
          this.queryParam.outInTime_begin = beginDate.Format('yyyy-MM-dd 00:00:00')
          this.queryParam.outInTime_end = endDate.Format('yyyy-MM-dd hh:mm:ss')
        } else if(value === '3'){
          this.queryParam.outInTime_begin = ''
          this.queryParam.outInTime_end = ''
        }
      },
      startValue(val) {
        console.log('startValue', val._d.Format('yyyy-MM-dd hh:mm:ss'));
        this.queryParam.outInTime_begin = val._d.Format('yyyy-MM-dd hh:mm:ss')
      },
      endValue(val) {
        console.log('endValue', val._d.Format('yyyy-MM-dd hh:mm:ss'));
        this.queryParam.outInTime_end = val._d.Format('yyyy-MM-dd hh:mm:ss')

      },
    },
    created() {
      this.timeType = '3'
    },
    methods: {
      showCarRegister(record) {
        console.log(record)
        this.$refs.modalForm1.add({
          carNumber: record.carNumber,
          carType: '200',
          recordId: record.id
        });
        this.$refs.modalForm1.title = "登记车辆";
        this.$refs.modalForm1.disableSubmit = false;
      },
      moment,
      disabledStartDate(startValue) {
        const endValue = this.endValue;
        if (!startValue || !endValue) {
          return false;
        }
        return startValue.valueOf() > endValue.valueOf();
      },
      disabledEndDate(endValue) {
        const startValue = this.startValue;
        if (!endValue || !startValue) {
          return false;
        }
        return startValue.valueOf() >= endValue.valueOf();
      },
      handleStartOpenChange(open) {
        if (!open) {
          this.endOpen = true;
        }
      },
      handleEndOpenChange(open) {
        this.endOpen = open;
      },
      closeRemarks() {
        this.selectRecord = undefined
        this.remarksShow = false
      },
      showRemarksDialog(record) {
        this.selectRecord = record
        this.$nextTick(() => {
          this.remarksShow = true
        })
      },
      getQueryParams() {
        //获取查询条件
        let sqp = {}
        if (this.superQueryParams) {
          sqp['superQueryParams'] = encodeURI(this.superQueryParams)
        }
        var param = Object.assign(sqp, this.queryParam, this.isorter, this.filters);
        if (param.carNumber != null) {
          param.carNumber = "*" + param.carNumber + "*"
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
      /* 图片预览 */
      getImgViewRecord(text) {
        // if(text && text.indexOf(",")>0){
        //   text = text.substring(0,text.indexOf(","))
        // }
        return window._CONFIG['imgDomainRecordURL'] + text
      },
      initDictConfig(){
      },
      closePersonRelation() {
        this.personRelationShow = false
        this.selectPersonId = ''
      },
      showPersonRelation(recordId) {
        // recordId = '1213423090747346946'
        this.selectPersonId = recordId
        this.personRelationShow = true
      },
      closeCarRelation() {
        this.carRelationShow = false
        this.selectCarId = ''
        this.selectCarType = ''
        this.selectCarColor = ''
        this.selectCarNumber = ''
      },
      showCarRelation(carNumber, carId, carType, carColor) {
        // carId = '1205450244422303747'
        // carNumber = '鲁R737HH'
        this.selectCarId = carId || ''
        this.selectCarNumber = carNumber || ''
        this.selectCarType = carType || ''
        this.selectCarColor = carColor || ''
        this.carRelationShow = true
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>