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
            <a-form-item label="车牌号">
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
            <a-form-item label="车辆类型">
              <a-input placeholder="请输入车辆类型" v-model="queryParam.carType2"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <a-form-item label="车辆颜色">
              <a-input placeholder="请输入车辆颜色" v-model="queryParam.carColor"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="8" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="showSearchQuery" icon="search" style="margin-left:10px;">二次研判</a-button>
              <a-button type="primary" @click="downloadTable" icon="cloud-download" style="margin-left:10px;">下载</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- table区域-begin -->
    <div>
      <a-tabs type="card">
        <a-tab-pane key="1">
          <span slot="tab">
            <a-icon type="table" style="font-size:20px;" />
            表格
          </span>

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
            <template slot="imgSlot" slot-scope="text,record">
              <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
              <viewer>
                <img
                  :bigImg="getPanelImg(record)"
                  :src="getImgViewRecord(text,record)" height="25px" alt=""
                  style="max-width:80px;font-size: 12px;font-style: italic;"/>
              </viewer>
<!--              <a-popover v-else placement="topLeft" arrowPointAtCenter>-->
<!--                <template slot="content">-->
<!--                  <img-->
<!--                    @click="showPanelImg(record)"-->
<!--                    :src="getImgViewRecord(text)" alt="图片不存在"-->
<!--                    style="max-width:500px;font-size: 12px;font-style: italic;"/>-->
<!--                </template>-->
<!--                <img :src="getImgViewRecord(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>-->
<!--              </a-popover>-->
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
          <a @click="showCarRelation(record.carNumber,record.carId,record.carType2,record.carColor,record.photoUrl)">
            {{record.carNumber}}
          </a>
        </span>
            <span slot="showRemarks" slot-scope="text,record">
          <a @click="showRemarksDialog(record)">
            查看备注
          </a>
        </span>

          </a-table>
        </a-tab-pane>
        <a-tab-pane key="2">
          <span slot="tab">
            <a-icon type="appstore" style="font-size:20px;" />
            列表
          </span>
          <a-list :grid="{ gutter: 16, xs: 1, sm: 2, md: 2, lg: 3, xl: 3, xxl: 3 }"
                  size="small"
                  @change="handleListChange"
                  :data-source="dataSource">
            <a-list-item slot="renderItem" slot-scope="item, index">
              <a-card :title="''">
                <div class="person_card">
                  <div class="person_card_photo">
                    <!--                    <img :src="getPanoramaImgViewRecord(item.imgSlot,item)"-->
                    <!--                         @click="showPanelImg(item)"-->
                    <!--                         alt="图片不存在"-->
                    <!--                         style="max-width:500px;font-size: 12px;font-style: italic;"/>-->

                    <viewer>
                      <img
                        :bigImg="getPanelImg(item)"
                        :src="getImgViewRecord(text,item)"
                        alt=""
                        style="max-width:500px;font-size: 12px;font-style: italic;"/>
                    </viewer>

                  </div>
                  <div class="person_card_info">
                    <div class="person_card_info_row">
                      <a-row>
                        <a-col span="12">
                          <span style="font-size:24px;line-height: 40px;">
                             <a @click="showCarRelation(item.carNumber,item.carId,item.carType2,item.carColor,item.photoUrl)">
                              {{item.carNumber}}
                            </a>
                          </span>
                        </a-col>
                        <a-col span="12" style="text-align: right;padding-right:10px;">
                          <span style="font-size:18px;line-height: 40px;">
                            <a v-if="item.personId!=='anonymous'" @click="showPersonRelation(item.personId)">
                              {{item.personName}}
                            </a>
                            <a-button size="small" v-if="item.personId==='anonymous'" type="primary"
                                      @click="showRegisterPerson(item)">登记</a-button>
                          </span>
                        </a-col>
                      </a-row>
                    </div>
                    <div class="person_card_info_row">
                      进出时间：{{item.outInTime}}
                    </div>
                    <div class="person_card_info_row">
                      进出地址：{{item.address}}
                      <a v-if="item.deviceId"
                         @click="showDeviceVideo(item.deviceId)"
                         style="cursor:pointer;margin:0 5px;">
                        <a-icon type="video-camera"/>
                      </a>
                    </div>
                    <div class="person_card_info_row">
                      <a @click="showRemarksDialog(item)">
                        查看备注
                      </a>
                    </div>
                  </div>
                </div>
              </a-card>
            </a-list-item>
          </a-list>
          <div style="text-align: right;">
            <a-pagination v-model="ipagination.current"
                          v-bind:pageSize="ipagination.pageSize"
                          v-bind:showTotal="ipagination.showTotal"
                          v-bind:total="ipagination.total"
                          v-bind:pageSizeOptions="ipagination.pageSizeOptions"
                          v-bind:showQuickJumper="ipagination.showQuickJumper"
                          v-bind:showSizeChanger="ipagination.showSizeChanger"
                          @change="handleListChange"
                          @showSizeChange="handleListChange"
                          size="small"
            ></a-pagination>
          </div>

        </a-tab-pane>
      </a-tabs>
    </div>

    <deviceDetail ref="deviceDetail" :center="true" v-if="deviceDetailShow" @leave="closeDeviceDetail"></deviceDetail>
    <SelectDeviceListModal ref="DeviceListModal" @choseDeviceList="choseDeviceList"></SelectDeviceListModal>
    <SelectDeviceListModal ref="DeviceListModal2" @choseDeviceList="choseDeviceList2"></SelectDeviceListModal>
    <monitorCarRecord-modal ref="modalForm" @ok="modalFormOk"></monitorCarRecord-modal>
    <MonitorRegisterCarModal ref="modalForm1" @ok="modalFormOk"></MonitorRegisterCarModal>
    <PersonRelation v-if="personRelationShow" :selectPersonId="selectPersonId" @close="closePersonRelation"></PersonRelation>
    <CarRelation v-if="carRelationShow" :selectCarId="selectCarId" :selectCarNumber="selectCarNumber"
                 :selectCarColor="selectCarColor" :selectCarType="selectCarType" :selectPhotoUrl="selectPhotoUrl"
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
    <panelImg :center="false" v-if="panelImgShow" @leave="closePanelImg" :title="panelTitle" :imgUrl="imgUrl"></panelImg>
    <a-modal
      title="二次研判"
      :width="1200"
      v-model="secondSearchVisible"
      :footer="null"
    >

      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery2">
          <a-row :gutter="24">
            <a-col :md="timeType2==='3'?24:8" :sm="timeType2==='3'?24:8">
              <a-form-item label="进出时间">
                <a-radio-group v-model="timeType2" buttonStyle="solid">
                  <a-radio-button value="3">自定义</a-radio-button>
                  <a-radio-button value="0">当天</a-radio-button>
                  <a-radio-button value="1">近7天</a-radio-button>
                  <a-radio-button value="2">近30天</a-radio-button>
                </a-radio-group>
                <a-date-picker
                  style="margin-left:20px;"
                  v-if="timeType2==='3'"
                  :disabledDate="disabledStartDate2"
                  :allowClear="false"
                  showTime
                  format="YYYY-MM-DD HH:mm:ss"
                  v-model="startValue2"
                  placeholder="请选择开始时间"
                  @openChange="handleStartOpenChange2"
                />
                <span class="query-group-split-cust" v-if="timeType2==='3'"></span>
                <a-date-picker
                  v-if="timeType2==='3'"
                  :allowClear="false"
                  :disabledDate="disabledEndDate2"
                  showTime
                  format="YYYY-MM-DD HH:mm:ss"
                  placeholder="请选择结束时间"
                  v-model="endValue2"
                  :open="endOpen2"
                  @openChange="handleEndOpenChange2"
                />
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="车牌号">
                <a-input placeholder="请输入车牌号" v-model="queryParam2.carNumber"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="18" :sm="24">
              <a-form-item label="设备选择">
                <a-select
                  mode="multiple"
                  placeholder="请选择设备"
                  v-model="selectedDevices2"
                  @deselect="removeSelected2"
                  @dropdownVisibleChange="showDeviceSelect2"
                >
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="车辆类型">
                <a-input placeholder="请输入车辆类型" v-model="queryParam2.carType2"></a-input>
              </a-form-item>
            </a-col>

            <a-col :md="6" :sm="8">
              <a-form-item label="车辆颜色">
                <a-input placeholder="请输入车辆颜色" v-model="queryParam2.carColor"></a-input>
              </a-form-item>
            </a-col>

            <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery2" icon="search">查询</a-button>
            </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
    </a-modal>
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
  import deviceDetail from '@/components/big/deviceDetail'
  import MonitorCarRecordModal from './modules/MonitorCarRecordModal'
  import MonitorRegisterCarModal from './modules/MonitorRegisterCarModal'
  import MonitorRecordRemarkListModal from './modules/MonitorRecordRemarkListModal'
  import PersonRelation from './modules/PersonRelation'
  import CarRelation from './modules/CarRelation'
  import SelectDeviceListModal from "./modules/SelectDeviceListModal";
  import JDate from '@/components/jeecg/JDate.vue'
  import {getAction,downFile} from "../../../api/manage";


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
      panelImg,
      CarRelation
    },
    data () {
      return {
        secondSearchVisible: false,
        queryParam2: {
          carNumber: '',
          deviceId:'',
          carType2: '',
          carColor: '',

        },
        timeType2: '',
        startValue2: null,
        endValue2: null,
        deviceIds2: '',
        selectedDevices2: [],
        endOpen2: false,

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
          imgDownload: '/monitor/monitorCarRecord/imgDownLoad'
        },
        imgUrl: '',
        panelTitle: '',
        panelImgShow: false,
        dictOptions: {},
        deviceIds: [],
        selectedDevices: [],
        selectPersonId: '',
        personRelationShow: false,
        selectCarId: '',
        selectCarType: '',
        selectCarColor: '',
        selectCarNumber: '',
        selectPhotoUrl: '',
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
      timeType2(value) {
        if(value === '0'){
          const endDate = new Date()
          this.queryParam2.outInTime_begin = endDate.Format('yyyy-MM-dd 00:00:00')
          this.queryParam2.outInTime_end = endDate.Format('yyyy-MM-dd hh:mm:ss')
        } else if(value === '1'){
          const endDate = new Date()
          const beginDate = new Date(endDate.getTime()-7*24*60*60*1000)
          this.queryParam2.outInTime_begin = beginDate.Format('yyyy-MM-dd 00:00:00')
          this.queryParam2.outInTime_end = endDate.Format('yyyy-MM-dd hh:mm:ss')
        } else if(value === '2'){
          const endDate = new Date()
          const beginDate = new Date(endDate.getTime()-30*24*60*60*1000)
          this.queryParam2.outInTime_begin = beginDate.Format('yyyy-MM-dd 00:00:00')
          this.queryParam2.outInTime_end = endDate.Format('yyyy-MM-dd hh:mm:ss')
        } else if(value === '3'){
          this.queryParam2.outInTime_begin = ''
          this.queryParam2.outInTime_end = ''
        }
      },
      startValue2(val) {
        console.log('startValue', val._d.Format('yyyy-MM-dd hh:mm:ss'));
        this.queryParam2.outInTime_begin = val._d.Format('yyyy-MM-dd hh:mm:ss')
      },
      endValue2(val) {
        console.log('endValue', val._d.Format('yyyy-MM-dd hh:mm:ss'));
        this.queryParam2.outInTime_end = val._d.Format('yyyy-MM-dd hh:mm:ss')

      },
    },
    created() {
      Viewer.setDefaults({url: this.showImgBig})
      this.timeType = '3'
    },
    methods: {
      downloadTable() {
        var params1 = this.getQueryParams();//查询条件
        var params2 = this.getQueryParams2();//查询条件
        const params = params1
        for(let key in params2){
          if(params2[key]){
            params['_2_'+key] = params2[key]
          }
        }
        const fileName = '图片导出'
        downFile(this.url.imgDownload, params).then((data) => {
          if (!data) {
            this.$message.warning("文件下载失败")
            return
          }
          if (typeof window.navigator.msSaveBlob !== 'undefined') {
            window.navigator.msSaveBlob(new Blob([data]), fileName+'.xls')
          }else{
            let url = window.URL.createObjectURL(new Blob([data]))
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', fileName+'.zip')
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link); //下载完成移除元素
            window.URL.revokeObjectURL(url); //释放掉blob对象
          }
        })
      },
      showSearchQuery() {
        this.timeType2 = ''
        this.timeType2 = '3'

        this.queryParam2.carNumber = ''
        this.queryParam2.deviceId = ''
        this.queryParam2.carType2 = ''
        this.queryParam2.carColor = ''
        this.selectedDevices2 = []
        this.deviceIds2 = []
        this.startValue2 = null
        this.endValue2 = null
        this.secondSearchVisible = true
      },
      loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params1 = this.getQueryParams();//查询条件
        var params2 = this.getQueryParams2();//查询条件
        const params = params1
        for(let key in params2){
          if(params2[key]){
            params['_2_'+key] = params2[key]
          }
        }
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(res.message)
          }
          this.loading = false;
        })
      },
      searchQuery2() {
        this.loadData(1)
        this.secondSearchVisible = false
      },
      searchQuery() {
        for(let key in this.queryParam2){
          this.queryParam2[key] = ''
        }
        this.loadData(1)
      },
      disabledStartDate2(startValue) {
        const endValue = this.endValue2;
        if (!startValue || !endValue) {
          return false;
        }
        return startValue.valueOf() > endValue.valueOf();
      },
      disabledEndDate2(endValue) {
        const startValue = this.startValue2;
        if (!endValue || !startValue) {
          return false;
        }
        return startValue.valueOf() >= endValue.valueOf();
      },
      handleStartOpenChange2(open) {
        if (!open) {
          this.endOpen2 = true;
        }
      },
      handleEndOpenChange2(open) {
        this.endOpen2 = open;
      },
      getQueryParams2() {
        var param = Object.assign({}, this.queryParam2);
        if (param.carNumber) {
          param.carNumber = "*" + param.carNumber + "*"
        }
        if (param.carType2) {
          param.carType2 = "*" + param.carType2 + "*"
        }
        if (param.carColor) {
          param.carColor = "*" + param.carColor + "*"
        }
        return param;
      },
      showDeviceSelect2() {
        this.$refs.DeviceListModal2.add(this.selectedDevices2, this.deviceIds2);
      },
      removeSelected2(value) {
        let deleteInd = -1
        const deviceIds = this.deviceIds2.split(',')
        this.selectedDevices2.forEach((item, index) => {
          if (item === value) {
            deleteInd = index
          }
        })
        if(deleteInd!==-1){
          this.selectedDevices2.splice(deleteInd,1)
          deviceIds.splice(deleteInd,1)
          this.deviceIds2 = deviceIds.join(',')
        }
      },
      choseDeviceList2(deviceList) {
        console.log(deviceList)
        this.selectedDevices2 = [];
        this.deviceIds2 = '';
        for(let i=0;i<deviceList.length;i++){
          this.selectedDevices2.push(deviceList[i].address);
        }
        this.deviceIds2 += deviceList.map(item => item.deviceId).join(",")
        this.queryParam2.deviceId = this.deviceIds2
      },
      handleListChange(page,pageSize) {
        console.log("handleListChange")
        this.ipagination.current = page;
        this.ipagination.pageSize = pageSize;
        this.loadData();
      },
      showPanelImg(data) {
        const panelData = data
        this.imgUrl = window._CONFIG['imgDomainRecordURL']+(panelData.panorama || panelData.photoUrl)
        this.panelTitle = panelData.address
        this.panelImgShow = true

        console.log(panelData)
      },
      closePanelImg() {
        this.panelImgShow = false
      },
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
        if (param.carNumber) {
          param.carNumber = "*" + param.carNumber + "*"
        }
        if (param.carColor) {
          param.carColor = "*" + param.carColor + "*"
        }
        if (param.carType2) {
          param.carType2 = "*" + param.carType2 + "*"
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

      getPanelImg(data) {
        const panelData = data
        return window._CONFIG['imgDomainRecordURL'] + (panelData.photoUrl)
      },
      showImgBig(image) {
        return image.getAttribute("bigImg") || image.getAttribute("src")
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
        this.selectPhotoUrl = ''
      },
      showCarRelation(carNumber, carId, carType, carColor, photoUrl) {
        // carId = '1205450244422303747'
        // carNumber = '鲁R737HH'
        this.selectCarId = carId || ''
        this.selectCarNumber = carNumber || ''
        this.selectCarType = carType || ''
        this.selectCarColor = carColor || ''
        this.selectPhotoUrl = photoUrl || ''
        this.carRelationShow = true
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
  .person_card{
    position:relative;
    margin:-24px -32px;
  }
  .person_card_photo{
    position:absolute;
    left:10px;
    top:10px;
    width:200px;
    height:220px;
    line-height: 30px;
    text-align: center;
  }
  .person_card_photo img{
    display: block;
    width:200px;
    height:200px;
    padding:0;
    margin:0;
  }
  .person_card_info{
    margin-left:210px;
    min-height: 220px;
    padding:10px;
  }
  .ant-card-wider-padding .ant-card-body{
    padding:0;
  }
  .person_card_info_row{
    line-height: 20px;
    padding:4px 0px;
  }
</style>