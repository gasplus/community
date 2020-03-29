<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="车牌号">
              <a-input placeholder="请输入车牌号" v-model="queryParam.carNumber"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="姓名">
              <a-input placeholder="请输入姓名" v-model="queryParam.personName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="车辆类型">
              <j-dict-select-tag placeholder="请选择车辆类型" v-model="queryParam.carType" dictCode="carType"/>
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

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('车辆信息')">导出</a-button>
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
        <span slot="show" slot-scope="text,record">
          <a v-if="record.personId&&record.personId!=='anonymous'" @click="showPersonRelation(record.personId)">
            {{record.personName}}
          </a>
          <span v-if="!record.personId||record.personId==='anonymous'">{{record.personName}}</span>
        </span>

        <span slot="show1" slot-scope="text,record">
          <a @click="showCarRelation(record.carNumber,record.id)">
            {{record.carNumber}}
          </a>
        </span>
      </a-table>
    </div>
    <PersonRelation v-if="personRelationShow" :selectPersonId="selectPersonId" @close="closePersonRelation"></PersonRelation>
    <CarRelation v-if="carRelationShow" :selectCarId="selectCarId" :selectCarNumber="selectCarNumber" @close="closeCarRelation"></CarRelation>
    <monitorCar-modal ref="modalForm" @ok="modalFormOk"></monitorCar-modal>
  </a-card>
</template>

<script>
  import {filterObj} from '@/utils/util';
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import MonitorCarModal from './modules/MonitorCarModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import PersonRelation from './modules/PersonRelation'
  import CarRelation from './modules/CarRelation'

  export default {
    name: "MonitorCarList",
    mixins: [JeecgListMixin],
    components: {
      JDictSelectTag,
      MonitorCarModal,
      PersonRelation,
      CarRelation
    },
    data() {
      return {
        description: '车辆信息管理页面',
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
            title: '姓名',
            align: "center",
            dataIndex: 'personName',
            scopedSlots: {customRender: 'show'}
          },
          {
            title: '身份证',
            align: "center",
            dataIndex: 'personCardId'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'}
          }
        ],
        selectPersonId: '',
        personRelationShow: false,
        selectCarId: '',
        selectCarNumber: '',
        carRelationShow: false,
        url: {
          list: "/monitor/monitorCar/list",
          delete: "/monitor/monitorCar/delete",
          deleteBatch: "/monitor/monitorCar/deleteBatch",
          exportXlsUrl: "/monitor/monitorCar/exportXls",
          importExcelUrl: "monitor/monitorCar/importExcel",
        },
        dictOptions: {
          carType: [],
          alarmType: [],
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
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
        this.selectCarNumber = ''
      },
      showCarRelation(carNumber, carId) {
        // carId = '1205450244422303747'
        // carNumber = '鲁R737HH'
        this.selectCarId = carId || ''
        this.selectCarNumber = carNumber || ''
        this.carRelationShow = true
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
        if (param.personName != null) {
          param.personName = "*" + param.personName + "*"
        }

        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      initDictConfig() {
        initDictOptions('carType').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'carType', res.result)
          }
        })
        initDictOptions('isYN').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'alarmType', res.result)
          }
        })
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>