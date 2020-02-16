<template>
  <a-modal
    :title="type==='10'?title:'选择车辆'"
    :width="1200"
    :visible="visible"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :span="6" v-if="type==='10'">
            <a-form-item label="姓名">
              <a-input placeholder="请输入账号查询" v-model="queryParam.xingMing"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6" v-if="type==='10'">
            <a-form-item label="联系电话">
              <a-input placeholder="请输入账号查询" v-model="queryParam.lxdh"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" v-if="type==='20'">
            <a-form-item label="车牌号">
              <a-input placeholder="请输入车牌号" v-model="queryParam.carNumber"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" v-if="type==='20'">
            <a-form-item label="车辆类型">
              <j-dict-select-tag placeholder="请选择车辆类型" v-model="queryParam.carType" dictCode="carType"/>
            </a-form-item>
          </a-col>

          <a-col :span="6" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchByquery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
 <!--    update-begin author:kangxiaolin   date:20190921   for:系统发送通知 用户多选失败 #513  -->
    <a-table
      ref="table"
      rowKey="id"
      :columns="type==='10'?columns:columnsCar"
      :dataSource="dataSource"
      :pagination="ipagination"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,onSelect:onSelect}"
      @change="handleTableChange"
    >
<!--     update-end   author:kangxiaolin  date:20190921     for:系统发送通知 用户多选失败 #513 -->
    </a-table>
  </a-modal>
</template>

<script>
  import { filterObj } from '@/utils/util';
  import { getAction } from '@/api/manage';
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: "SelectUserListModal",
    props: {
      type: {
        type: String,
        default() {
          return '10'
        }
      }
    },
    components: {
      JDictSelectTag
    },
    data () {
      return {
        title:"选择用户",
        queryParam: {},
        columns: [{
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
            title:'姓名',
            align:"center",
            dataIndex: 'xingMing'
          },
          {
            title:'公民身份证号',
            align:"center",
            dataIndex: 'sfzh'
          },
          {
            title:'户籍地址',
            align:"center",
            dataIndex: 'hjdz'
          },
          {
            title:'户关系',
            align:"center",
            dataIndex: 'guanXi'
          },
          {
            title:'联系电话',
            align:"center",
            dataIndex: 'lxdh'
          },{
            title:'民族',
            align:"center",
            dataIndex: 'minZu',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['minZu'], text+"")
              }
            }
          }
        ],
        columnsCar: [{
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
            dataIndex: 'carNumber'
          },
          {
            title: '姓名',
            align: "center",
            dataIndex: 'personName'
          },
          {
            title: '身份证',
            align: "center",
            dataIndex: 'personCardId'
          },
          {
            title: '布控类型',
            align: "center",
            dataIndex: 'alarmType',
            customRender: (text) => {
              if (!text) {
                return ''
              } else {
                return filterMultiDictText(this.dictOptions['alarmType'], text + "")
              }
            }
          }
        ],
        dataSource:[],
        url:{
          userList: '/monitor/monitorPerson/list',
          carList: '/monitor/monitorCar/list'
        },
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '20'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        isorter:{
          column: 'createTime',
          order: 'desc',
        },
        nameKey: {
          10: 'xingMing',
          20: 'carNumber'
        },
        selectedRowKeys: [],
        selectionRows: [],
        visible:false,
        toggleSearchStatus:false,
        dictOptions:{
          minZu:[],
          type:[],
          carType: [],
          alarmType: [],
        },
      }
    },
    created() {
      this.initDictConfig()
    },
    methods: {
      add (selectUser,userIds) {
        this.visible = true;
        this.edit(selectUser,userIds);
        this.loadData();
      },
      edit(selectUser,userIds){
        if(!userIds){
          this.selectedRowKeys = []
        }else{
          if((typeof userIds=='object')&&userIds.constructor==Array){
            this.selectedRowKeys = userIds
          }else{
            this.selectedRowKeys = userIds.split(',');
          }
        }
        if(!selectUser){
          this.selectionRows=[]
        }else{
          var that=this;
          that.selectionRows=[];
          selectUser.forEach(function(record,index){
            const o = {id: that.selectedRowKeys[index]}
            const key = this.nameKey[this.type]
            o[key] = record
            that.selectionRows.push(o)
          })
          // this.selectionRows = selectUser;
        }
      },
      loadData (arg){
        if(arg===1){
          this.ipagination.current = 1;
        }
        let params = this.getQueryParams();//查询条件
        if(this.type === '10'){
          getAction(this.url.userList,params).then((res)=>{
            if(res.success){
              this.dataSource = res.result.records;
              this.ipagination.total = res.result.total;
            }
          })
        } else if(this.type === '20') {
          getAction(this.url.carList,params).then((res)=>{
            if(res.success){
              this.dataSource = res.result.records;
              this.ipagination.total = res.result.total;
            }
          })
        }

      },
      getQueryParams(){
        let param = Object.assign({}, this.queryParam,this.isorter);
        param.field = this.getQueryField();
        //--update-begin----author:scott---date:20190818------for:新建公告时指定特定用户翻页错误SelectUserListModal #379----
        // param.current = this.ipagination.current;
        // param.pageSize = this.ipagination.pageSize;
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        //--update-end----author:scott---date:20190818------for:新建公告时指定特定用户翻页错误SelectUserListModal #379---
        return filterObj(param);
      },
      getQueryField(){
        let str = "id,";
        for(let a = 0;a<this.columns.length;a++){
          str+=","+this.columns[a].dataIndex;
        }
        return str;
      },
      //--update-begin----author:kangxiaolin---date:20190921------for:系统发送通知 用户多选失败 #513----
      onSelectChange (selectedRowKeys) {
        /*
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = []
        this.dataSource.forEach(item => {
          if(selectedRowKeys.indexOf(item.id)>=0){
            this.selectionRows.push(Object.assign({},item))
          }
        })
         */
      },
      onSelect(record, selected){
        if(selected == true ){
          this.selectionRows.push(record);
          this.selectedRowKeys = [record.id];
        }else {
          this.selectedRowKeys = []
          this.selectionRows.forEach(function(item,index,arr){
            if(item.id == record.id) {
              arr.splice(index, 1);
            }
          })
        }
        //--update-end----author:kangxiaolin---date:20190921------for:系统发送通知 用户多选失败 #513----
      },

      searchReset(){
        let that = this;
        Object.keys(that.queryParam).forEach(function(key){
          that.queryParam[key] = '';
        });
        that.loadData(1);
      },
      handleTableChange(pagination, filters, sorter){
        //TODO 筛选
        if (Object.keys(sorter).length>0){
          this.isorter.column = sorter.field;
          this.isorter.order = "ascend"==sorter.order?"asc":"desc"
        }
        this.ipagination = pagination;
        this.loadData();
      },
      handleCancel () {
        this.selectionRows = [];
        this.selectedRowKeys = [];
        this.visible = false;
      },
      handleOk () {
        this.$emit("choseUserList",this.selectionRows);
        this.handleCancel();
      },
      searchByquery(){
        this.loadData(1);
      },
      handleToggleSearch(){
        this.toggleSearchStatus = !this.toggleSearchStatus;
      },
      initDictConfig(){
        initDictOptions('').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'minZu', res.result)
          }
        })
        initDictOptions('hu_person_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'type', res.result)
          }
        })
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
  .ant-card-body .table-operator{
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td{
    padding-top:15px;
    padding-bottom:15px;
  }
  .anty-row-operator button{margin: 0 5px}
  .ant-btn-danger{background-color: #ffffff}

  .ant-modal-cust-warp{height: 100%}
  .ant-modal-cust-warp .ant-modal-body{height:calc(100% - 110px) !important;overflow-y: auto}
  .ant-modal-cust-warp .ant-modal-content{height:90% !important;overflow-y: hidden}

  .anty-img-wrap{height:25px;position: relative;}
  .anty-img-wrap > img{max-height:100%;}
</style>