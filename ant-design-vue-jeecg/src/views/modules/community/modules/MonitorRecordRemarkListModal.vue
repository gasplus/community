<template>
  <a-modal
    title="备注管理"
    :width="1200"
    :visible="visible"
    :footer="null"
    @cancel="handleCancel"
  >
    <div class="table-operator" style="margin-bottom:10px;">
      <a-button @click="add()" type="primary" icon="plus">新增</a-button>
    </div>
    <!--    update-begin author:kangxiaolin   date:20190921   for:系统发送通知 用户多选失败 #513  -->
    <a-table
      ref="table"
      rowKey="id"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="ipagination"
    >
      <span slot="action" slot-scope="text,record">
        <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
          <a>删除</a>
        </a-popconfirm>
      </span>
      <span slot="remarksType" slot-scope="text">
        {{text?(remarkMap[text]||''):''}}
      </span>
      <!--     update-end   author:kangxiaolin  date:20190921     for:系统发送通知 用户多选失败 #513 -->
    </a-table>
    <MonitorRecordRemarksModal ref="modalForm" @ok="modalFormOk"></MonitorRecordRemarksModal>
  </a-modal>
</template>

<script>
  import { filterObj } from '@/utils/util';
  import { getAction,deleteAction } from '@/api/manage';
  import MonitorRecordRemarksModal from './MonitorRecordRemarksModal'

  export default {
    name: "MonitorRecordRemarkListModal",
    props: {
      recordShow: {
        type: Boolean,
        default() {
          return false
        }
      },
      //监控记录id
      recordId: {
        type: String,
        default() {
          return ''
        }
      },
      //监控dataId
      dataId: {
        type: String,
        default() {
          return ''
        }
      },
      //监控personId
      personId: {
        type: String,
        default() {
          return ''
        }
      },
      //备注类别
      remarksType: {
        type: String,
        default() {
          return ''
        }
      }
    },
    components: {
      MonitorRecordRemarksModal
    },
    data () {
      return {
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
          title:'备注人',
          align:"center",
          dataIndex: 'remarksName'
        },
        {
          title:'备注内容',
          align:"center",
          dataIndex: 'remarksContent'
        },
        {
          title:'备注时间',
          align:"center",
          dataIndex: 'createTime'
        },
          /*
        {
          title:'备注类别',
          align:"center",
          dataIndex: 'remarksType',
          scopedSlots: {customRender: 'remarksType'}
        },*/
        {
          title:'操作',
          align:"center",
          dataIndex: 'action',
          scopedSlots: {customRender: 'action'}
        }],
        dataSource:[],
        url:{
          list: '/monitor/monitorRecordRemarks/list',
          delete: '/monitor/monitorRecordRemarks/delete'
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
        remarkMap: {
          10: '人脸',
          20: '人体',
          30: '车辆'
        },
        visible:false,
        toggleSearchStatus:false,
        dictOptions:{
        },
        editRecord: {},
        editShow: false
      }
    },
    watch: {
      recordShow(v) {
        this.visible = v
        console.log('show-----------',v)
        if(v) {
          this.queryParam.recordId = this.recordId
          this.loadData(1)
        }
      }
    },
    created() {
    },
    methods: {
      handleCancel() {
        this.$emit('handleCancel')
      },
      add () {
        this.$refs.modalForm.add({
          recordId: this.recordId,
          personId: this.personId,
          dataId: this.dataId,
          remarksType: this.remarksType
        });
        this.$refs.modalForm.title = "新增";
        this.$refs.modalForm.disableSubmit = false;
      },
      handleDelete(id) {
        if(!this.url.delete){
          this.$message.error("请设置url.delete属性!")
          return
        }
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData(1);
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      loadData (arg){
        if(arg===1){
          this.ipagination.current = 1;
        }
        let params = this.getQueryParams();//查询条件
        getAction(this.url.list,params).then((res)=>{
          if(res.success){
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
        })
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
      modalFormOk() {
        this.loadData()
      },
      searchByquery(){
        this.loadData(1);
      },
      handleToggleSearch(){
        this.toggleSearchStatus = !this.toggleSearchStatus;
      },
      initDictConfig(){
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