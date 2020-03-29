<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="visible"
    :footer="null"
    @cancel="handleCancel">
    <!-- 查询开始   -->

    <div class="table-page-search-wrapper">
      <a-card title="车辆信息" size="small" style="margin-bottom:10px;" :headStyle="headStyle">
        <div class="base_info">
          <div class="base_info_img">
            <img :src="imgBasePath + selectInfo.photoUrl" alt="">
            <div class="base_info_img_no" v-if="!selectInfo.photoUrl">
              暂无
            </div>
          </div>

          <div class="base_info_box">
            <a-row>
              <a-col :span="3">
                <div class="base_info_label">车牌号：</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  {{selectCarNumber}}
                </div>
              </a-col>
              <a-col :span="3">
                <div class="base_info_label">车辆类型</div>
              </a-col>
              <a-col :span="6">
                {{selectCarType}}
              </a-col>
            </a-row>
            <a-row v-if="selectCarId!=='anonymous'&&selectInfo.personName">
              <a-col :span="3">
                <div class="base_info_label">车主：</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  {{selectInfo.personName}}
                </div>
              </a-col>

              <a-col :span="3">
                <div class="base_info_label">车辆颜色</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  {{selectCarColor}}
                </div>
              </a-col>
            </a-row>
            <a-row v-if="selectCarId!=='anonymous'&&selectInfo.personCardId">
              <a-col :span="3">
                <div class="base_info_label">身份证号：</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  {{selectInfo.personCardId}}
                </div>
              </a-col>

              <a-col :span="3">
                <div class="base_info_label"></div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                </div>
              </a-col>
            </a-row>
          </div>
        </div>
      </a-card>
      <a-card title="进出记录" size="small" style="margin-bottom:10px;" :headStyle="headStyle"
              v-if="selectCarNumber!=='无法识别'">
        <a-row style="line-height:30px;padding-bottom:10px;">
          <a-col :span="8">
            总天数：{{tjData.totalDays||0}}天
          </a-col>
          <a-col :span="8">
            总次数：{{tjData.totalCount||0}}次
          </a-col>
          <a-col :span="8">
            出现频率：{{tjData.rate||0}}次/天
          </a-col>
        </a-row>
        <a-table
          ref="table"
          size="small"
          bordered
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          @change="handleTableChange">
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
        </a-table>
      </a-card>
    </div>
  </a-modal>
</template>

<script>
  import { filterObj } from '@/utils/util';

  import { getAction } from '@/api/manage'

  export default {
    name: "PersonRelation",
    props: {
      selectCarType: {
        type: String,
        default() {
          return ''
        }
      },
      selectCarColor: {
        type: String,
        default() {
          return ''
        }
      },
      selectCarId: {
        type: String,
        default() {
          return ''
        }
      },
      selectCarNumber: {
        type: String,
        default() {
          return ''
        }
      },
      selectCarStatus: ''
    },
    components: {
    },
    data () {
      return {
        imgBasePath: window._CONFIG['domianURL'] + '/',
        dataSource: [],
        loading: false,
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
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
            dataIndex: 'carNumber'
          },
          {
            title:'车主',
            align:"center",
            dataIndex: 'personName'
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
        ],
        headStyle: {
          backgroundColor: '#1fc8db',
          backgroundImage: 'linear-gradient(141deg,#9fb8ad 0%,#1fc8db 51%,#2cb5e8 75%)',
          color:'#fff',
          opacity: 0.95
        },

        headStyle1: {
          backgroundColor: '#1fc8db',
          backgroundImage: 'linear-gradient(141deg,#9fb8ad 0%,#1fc8db 51%,#2cb5e8 75%)',
          color:'#fff',
          opacity: 0.65
        },
        url: {
          tongji: 'monitor/monitorCarRecord/getCarStat',
          getCarById: 'monitor/monitorCar/queryById',
          queryByCarNumber: 'monitor/monitorCar/queryByCarNumber',
          list: "/monitor/monitorCarRecord/list",
        },
        tjData: {},
        tabId: '',
        title:"车辆信息",
        selectInfo: {},
        visible:true
      }
    },
    created() {
      this.visible = true
      this.loadData(1)
      this.getCarData()
      this.tongji()
    },
    methods: {
      tongji() {
        getAction(this.url.tongji, {
          carNumber: this.selectCarNumber,
          xiaoQuId: 1
        }).then(res => {
          if(res.success){
            console.log(res)
            this.tjData = res.result
          }
        })
      },
      getCarData() {
        if (this.selectCarStatus == 'status') {
          getAction(this.url.queryByCarNumber, {
            carNumber: this.selectCarId
          }).then(res => {
            if (res.success) {
              console.log(res)
              this.selectInfo = res.result
            }
          })
        } else {
          getAction(this.url.getCarById, {
            id: this.selectCarId
          }).then(res => {
            if (res.success) {
              console.log(res)
              this.selectInfo = res.result
            }
          })
        }

      },
      /* 图片预览 */
      getImgViewRecord(text) {
        // if(text && text.indexOf(",")>0){
        //   text = text.substring(0,text.indexOf(","))
        // }
        return window._CONFIG['imgDomainRecordURL'] + text
      },
      handleTableChange(pagination, filters, sorter) {
        this.ipagination = pagination;
        this.loadData();
      },
      loadData(arg) {
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        const params = {
          column: 'outInTime',
          order: 'desc',
          pageNo: this.ipagination.current,
          pageSize: this.ipagination.pageSize,
          carNumber: this.selectCarNumber
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
      handleCancel() {
        this.$emit('close')
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
  .base_info{
    position:relative;
  }
  .base_info_img{
    width:120px;
    height:160px;
    position:absolute;
    background: rgba(44, 181, 232,0.25);
    top:10px;
    left:10px;
  }

  .base_info_img_no{
    text-align: center;
    line-height: 160px;
  }
  .base_info_box{
    position:relative;
    margin-left:150px;
    padding: 20px 0;
    min-height: 180px;
  }
  .base_info_label{
    line-height: 36px;
    color:#666;
    font-weight: bold;
    font-size:14px;
  }
  .base_info_content{
    line-height: 36px;
    color:#333;
    font-size:14px;
  }
  .relation_list{
    position:relative;
    margin-bottom:10px;
    width:370px;
  }
  .relation_item{
    width:360px;
    height:140px;
    border-radius: 5px;
    border:1px solid #ccc;
    box-shadow: 0 0 10px #ccc;
    margin-bottom: 10px;
    position:relative;
    box-sizing: border-box;
    padding:10px 0;
  }
  .relation_item_img{
    position:absolute;
    left:10px;
    top:9px;
    border-radius: 2px;
    width:90px;
    height:120px;
    background: rgba(44, 181, 232,0.25);
  }
  .base_info_img img,.relation_item_img img{
    position:absolute;
    display: block;
    left:0;
    top:0;
    width: 100%;
    height:100%;
  }
  .relation_item_info{
    margin-left:110px;
    position:relative;
    min-height: 16px;
    padding:2px 0;
  }
  .relation_item_info_l{
    position:absolute;
    line-height: 16px;
    height:16px;
    top:4px;
    left:0;
    color:#666;
  }
  .relation_item_info_r{
    position:relative;
    line-height: 16px;
    padding: 2px 2px 2px 70px;
  }
  .relation_item:hover,.relation_item.active{
    border:1px solid  rgba(44, 181, 232,0.75);
    box-shadow: 0 0 10px rgba(44, 181, 232,0.75);
    cursor: pointer;
  }
</style>