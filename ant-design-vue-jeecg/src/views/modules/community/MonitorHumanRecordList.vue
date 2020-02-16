<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="12" :sm="16">
            <a-form-item label="进出时间">
              <a-date-picker
                :disabledDate="disabledStartDate"
                :allowClear="false"
                showTime
                format="YYYY-MM-DD HH:mm:ss"
                :defaultValue="moment(queryParam.outInTime_begin, 'YYYY-MM-DD HH:mm:ss')"
                v-model="startValue"
                placeholder="请选择开始时间"
                @openChange="handleStartOpenChange"
              />
              <span class="query-group-split-cust"></span>
              <a-date-picker
                :allowClear="false"
                :disabledDate="disabledEndDate"
                showTime
                format="YYYY-MM-DD HH:mm:ss"
                placeholder="请选择结束时间"
                :defaultValue="moment(queryParam.outInTime_end, 'YYYY-MM-DD HH:mm:ss')"
                v-model="endValue"
                :open="endOpen"
                @openChange="handleEndOpenChange"
              />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="16">
            <a-form-item label="人体识别年龄">
              <a-input placeholder="请输入最小值" class="query-group-cust" v-model="queryParam.bodyInfoAge_begin"></a-input>
              <span class="query-group-split-cust"></span>
              <a-input placeholder="请输入最大值" class="query-group-cust" v-model="queryParam.bodyInfoAge_end"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="24" :sm="24">
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
    <div id="bd">
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
        <span slot="action1" slot-scope="text, record">
          <a-popover title="监控结果" trigger="hover">
            <template slot="content">
              <a-row>
                <a-col span="12" v-if="record.bodyInfo">
                  <a-card hoverable style="width: 240px;margin:0 10px;" title="人体识别结果" size="small">
                    <img
                      alt="example"
                      :src="imgBasePath+JSON.parse(record.bodyInfo).picture"
                      slot="cover"
                    />
                    <a-card-meta>
                      <template slot="description">
                        <a-row>
                          <a-col span="12">年龄</a-col>
                          <a-col span="12">{{JSON.parse(record.bodyInfo).age}}</a-col>
                          <a-col span="12">性别</a-col>
                          <a-col span="12">{{JSON.parse(record.bodyInfo).gender}}</a-col>
                        </a-row>
                      </template>
                    </a-card-meta>
                  </a-card>
                </a-col>
                <a-col span="12" v-if="record.faceInfo">
                  <a-card hoverable style="width: 240px;margin:0 10px;" title="人脸识别结果" size="small">
                    <img
                      alt="example"
                      :src="imgBasePath+JSON.parse(record.faceInfo).picture"
                      slot="cover"
                    />
                    <a-card-meta title="">
                      <template slot="description">
                         <a-row>
                          <a-col span="12">年龄</a-col>
                          <a-col span="12">{{JSON.parse(record.faceInfo).age}}</a-col>
                          <a-col span="12">性别</a-col>
                          <a-col span="12">{{JSON.parse(record.faceInfo).gender}}</a-col>
                        </a-row>
                      </template>
                    </a-card-meta>
                  </a-card>
                </a-col>
              </a-row>

            </template>
            <a-button type="primary">查看</a-button>
          </a-popover>
        </span>
        <span slot="action" slot-scope="text, record">

          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <SelectDeviceListModal ref="DeviceListModal" @choseDeviceList="choseDeviceList"></SelectDeviceListModal>
    <monitorHumanRecord-modal ref="modalForm" @ok="modalFormOk"></monitorHumanRecord-modal>
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
  import moment from 'moment';
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import MonitorHumanRecordModal from './modules/MonitorHumanRecordModal'
  import SelectDeviceListModal from "./modules/SelectDeviceListModal";
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JDate from '@/components/jeecg/JDate.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "MonitorHumanRecordList",
    mixins: [JeecgListMixin],
    components: {
      JDictSelectTag,
      JDate,
      SelectDeviceListModal,
      MonitorHumanRecordModal
    },
    data() {
      return {
        imgBasePath: window._CONFIG['imgDomainRecordURL'],
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
            title:'进出时间',
            align:"center",
            dataIndex: 'outInTime'
          },
          {
            title: '进出地址',
            align: "center",
            dataIndex: 'address'
          },
          {
            title: '识别结果',
            align: "center",
            dataIndex: 'action1',
            scopedSlots: {customRender: 'action1'}
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
        startValue: null,
        endValue: null,
        endOpen: false,
        deviceIds: [],
        selectedDevices:[],
        disableMixinCreated: true
      }
    },
    watch: {
      startValue(val) {
        console.log('startValue', val);
      },
      endValue(val) {
        console.log('endValue', val);
      },
    },
    created() {
      const endDate = new Date()
      const beginDate = new Date(endDate.getTime()-30*24*60*60*1000)
      this.queryParam.outInTime_begin = beginDate.Format('yyyy-MM-dd hh:mm:ss')
      this.queryParam.outInTime_end = endDate.Format('yyyy-MM-dd hh:mm:ss')

      this.loadData();
      //初始化字典配置 在自己页面定义
      this.initDictConfig();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      showDeviceSelect() {
        this.$refs.DeviceListModal.add(this.selectedDevices,this.deviceIds);
      },
      removeSelected(value) {
        let deleteInd = -1
        const deviceIds = this.deviceIds.split(',')
        this.selectedDevices.forEach((item,index) => {
          if(item === value) {
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
<style>
  #bd .ant-card-body{
    padding:10px;
  }
</style>