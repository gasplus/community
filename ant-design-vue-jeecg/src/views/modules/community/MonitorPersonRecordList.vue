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
            <a-form-item label="人员姓名">
              <a-input placeholder="请输入人员姓名" v-model="queryParam.personName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="身份证">
              <a-input placeholder="请输入身份证" v-model="queryParam.personIdCard"></a-input>
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
        <span slot="showRemarks" slot-scope="text,record">
          <a @click="showRemarksDialog(record)">
            查看备注
          </a>
        </span>
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text,record">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <viewer>
            <img
              :bigImg="getPanelImg(record)"
              :src="getImgViewRecord(text,record)" height="25px" alt=""
              style="max-width:80px;font-size: 12px;font-style: italic;"/>
          </viewer>
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
        <span slot="device" slot-scope="text, record">
          <a-icon v-if="record.deviceId" type="video-camera" style="cursor:pointer"
                  @click="showDeviceVideo(record.deviceId)"/>
        </span>
        <span slot="show" slot-scope="text,record">
          <a v-if="record.personId!=='anonymous'" @click="showPersonRelation(record.personId)">
            {{record.personName}}
          </a>
          <a-button size="small" v-if="record.personId==='anonymous'" type="primary"
                    @click="showRegisterPerson(record)">登记</a-button>
        </span>
        <span slot="taskAdd" slot-scope="text,record">
          <a-button size="small" type="primary" @click="showTaskAdd(record)">搜索</a-button>
        </span>


      </a-table>
    </div>
    <deviceDetail ref="deviceDetail" :center="true" v-if="deviceDetailShow" @leave="closeDeviceDetail"></deviceDetail>
    <SelectDeviceListModal ref="DeviceListModal" @choseDeviceList="choseDeviceList"></SelectDeviceListModal>
    <monitorPersonRecord-modal ref="modalForm" @ok="modalFormOk"></monitorPersonRecord-modal>
    <MonitorRegisterPersonModal ref="modalRegisterForm" :recordIdDJ="recordIdDJ"
                                @ok="modalFormOk"></MonitorRegisterPersonModal>
    <PersonRelation v-if="personRelationShow" :selectPersonId="selectPersonId"
                    @close="closePersonRelation"></PersonRelation>
    <VisitorPersonRelation v-if="personRelationShowTwo" :selectInfo="selectPerson"
                           @close="closePersonRelationTwo"></VisitorPersonRelation>
    <MonitorRecordRemarkListModal
      v-if="selectRecord"
      :recordId="selectRecord.id"
      :dataId="selectRecord.personId"
      :personId="selectRecord.personId"

      remarks-type="10"
      :recordShow="remarksShow"
      @handleCancel="closeRemarks"
    ></MonitorRecordRemarkListModal>
    <MonitorSearchTaskModalAdd
      :showFlag="taskShow"
      :person-record="selectRecord"
      :searchType="10"
      ref="taskAdd"
      @handleCancel="closeTaskAdd"
    ></MonitorSearchTaskModalAdd>
    <panelImg :center="false" v-if="panelImgShow" @leave="closePanelImg" :title="panelTitle" :imgUrl="imgUrl"></panelImg>
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
  import Viewer from 'viewerjs'
  import {filterObj} from '@/utils/util';
  import panelImg from '@/components/big/panelImg'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {deviceMixin} from '@/mixins/deviceMixin'
  import MonitorPersonRecordModal from './modules/MonitorPersonRecordModal'
  import MonitorRegisterPersonModal from './modules/MonitorRegisterPersonModal'
  import MonitorSearchTaskModalAdd from './modules/MonitorSearchTaskModalAdd'
  import PersonRelation from './modules/PersonRelation'
  import deviceDetail from '@/components/big/deviceDetail'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import SelectDeviceListModal from "./modules/SelectDeviceListModal";
  import JDate from '@/components/jeecg/JDate.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import MonitorRecordRemarkListModal from "./modules/MonitorRecordRemarkListModal";
  import VisitorPersonRelation from './modules/VisitorPersonRelation'
  import {getAction} from "../../../api/manage";

  export default {
    name: "MonitorPersonRecordList",
    mixins: [JeecgListMixin, deviceMixin],
    components: {
      JDictSelectTag,
      JDate,
      panelImg,
      SelectDeviceListModal,
      MonitorRecordRemarkListModal,
      MonitorSearchTaskModalAdd,
      MonitorPersonRecordModal,
      deviceDetail,
      PersonRelation,
      MonitorRegisterPersonModal,
      VisitorPersonRelation
    },
    data () {
      return {
        imgUrl: '',
        panelTitle: '',
        panelImgShow: false,
        selectPerson: {},
        personRelationShowTwo: false,
        recordIdDJ: '',
        taskShow: false,
        timeType: '',
        startValue: null,
        endValue: null,
        endOpen: false,
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
              return parseInt(index) + 1;
            }
          },
          {
            title: '人员姓名',
            dataIndex: 'show',
            align: "center",
            scopedSlots: {customRender: 'show'}
          },
          {
            title: '以图搜人',
            dataIndex: 'task',
            align: "center",
            scopedSlots: {customRender: 'taskAdd'}
          },
          {
            title: '进出时间',
            align: "center",
            dataIndex: 'outInTime'
          },
          /*
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
          },*/
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
          queryById: '/monitor/monitorPerson/queryById'
        },
        deviceIds: [],
        selectedDevices: [],
        dictOptions: {
          personType: [],
        },
        selectPersonId: '',
        personRelationShow: false,
        selectRecord: undefined,
        remarksShow: false,
        disableMixinCreated: true
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
      Viewer.setDefaults({url: this.showImgBig})
      if (sessionStorage.getItem('recordId')) {
        this.queryParam.id = sessionStorage.getItem('recordId')
        sessionStorage.removeItem('recordId')
      }
      this.loadData();
      //初始化字典配置 在自己页面定义
      this.initDictConfig();
      this.timeType = '3'
    },
    methods: {
      getPanelImg(data) {
        const panelData = data
        return window._CONFIG['imgDomainRecordURL'] + (panelData.panorama || panelData.photoUrl)
      },
      showImgBig(image) {
        return image.getAttribute("bigImg") || image.getAttribute("src")
      },
      showPanelImg(data) {
        const panelData = data
        this.imgUrl = window._CONFIG['imgDomainRecordURL'] + (panelData.panorama || panelData.photoUrl)
        this.panelTitle = panelData.address
        this.panelImgShow = true
        console.log(panelData)
      },
      closePanelImg() {
        this.panelImgShow = false
      },
      closePersonRelation() {
        this.personRelationShow = false
        this.selectPersonId = ''
      },
      showPersonRelation(recordId) {
        this.loading = true;
        this.getPrersonData(recordId)
      },
      getPrersonData(data) {
        getAction(this.url.queryById, {id: data}).then(res => {
          if (res.success) {
            if (res.result.type == 'A01A05') {
              this.selectPerson = res.result
              this.personRelationShowTwo = true
            } else {
              this.selectPersonId = data
              this.personRelationShow = true
            }

          }
          this.loading = false;
        })
      },
      closePersonRelationTwo() {
        this.personRelationShowTwo = false
        this.selectPerson = ''
      },
      closeTaskAdd() {
        this.selectRecord = undefined
        this.taskShow = false
      },
      showTaskAdd(record) {
        this.selectRecord = record
        this.taskShow = true
        this.$refs.taskAdd.initData()
      },
      showRegisterPerson(record) {
        this.recordIdDJ = record.id
        let zhaoPian = this.getImgViewRecord(record.photoUrl)
        //   zhaoPian = 'http://www.people.com.cn/mediafile/pic/20140624/5/15681153115536736913.jpg'
        console.log(zhaoPian)
        this.$refs.modalRegisterForm.add({
          zhaoPian,
          recordId: record.id
        });
        this.$refs.modalRegisterForm.title = "访客登记";
        this.$refs.modalRegisterForm.disableSubmit = false;
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
      getImgViewRecord(text) {
        // if(text && text.indexOf(",")>0){
        //   text = text.substring(0,text.indexOf(","))
        // }
        return window._CONFIG['imgDomainRecordURL'] + text
      },
      /*大 图片预览 */
      getPanoramaImgViewRecord(text, record) {
        let panoramaImg = record.panorama
        if (panoramaImg) {
          panoramaImg = panoramaImg
        } else {
          panoramaImg = text
        }
        return window._CONFIG['imgDomainRecordURL'] + panoramaImg
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>