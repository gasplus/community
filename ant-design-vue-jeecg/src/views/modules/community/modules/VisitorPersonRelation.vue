<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :footer="null"
    @cancel="handleCancel">
    <!-- 查询开始   -->

    <div class="table-page-search-wrapper">
      <a-card title="基本信息" size="small" style="margin-bottom:10px;" :headStyle="headStyle">
        <div class="base_info">
          <div class="base_info_img">
            <img :src="imgBasePath + selectInfo.zhaoPian" alt="">
          </div>
          <div class="base_info_box">
            <a-row>
              <a-col :span="2">
                <div class="base_info_label">姓名：</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  {{selectInfo.xingMing}}
                </div>
              </a-col>

              <a-col :span="2">
                <div class="base_info_label">联系电话：</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  {{selectInfo.lxdh}}
                </div>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="2">
                <div class="base_info_label">性别：</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  {{selectInfo.xingBie}}
                </div>
              </a-col>
              <a-col :span="2">
                <div class="base_info_label">民族：</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  {{selectInfo.minZu}}
                </div>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="2">
                <div class="base_info_label">身份证号：</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  {{selectInfo.sfzh}}
                </div>
              </a-col>
              <a-col :span="2">
                <div class="base_info_label">人员类型：</div>
              </a-col>
              <a-col :span="6">
                <div class="base_info_content">
                  <a-tag>访客</a-tag>
                </div>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="2">
                <div class="base_info_label">户籍地址：</div>
              </a-col>
              <a-col :span="14">
                <div class="base_info_content">
                  {{selectInfo.hjdz}}
                </div>
              </a-col>
            </a-row>
          </div>
        </div>
      </a-card>
      <a-button @click="startTask" type="primary" style="margin-bottom:10px;" :disabled="count+''!=='0'">
        {{count>0||hasStartTask?'正在检索中...':'检索'}}
      </a-button>
      <a-card title="进出记录" size="small" style="margin-bottom:10px;" :headStyle="headStyle1">
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
                     style="max-width:80px;font-size: 12px;font-style: italic;"/>
              </template>
              <img :src="getImgViewRecord(text)" height="25px" alt="图片不存在"
                   style="max-width:80px;font-size: 12px;font-style: italic;"/>
            </a-popover>
          </template>
        </a-table>
      </a-card>
    </div>
  </a-modal>
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
  import {filterObj} from '@/utils/util';

  import {getAction} from '@/api/manage'
  import {postAction} from "../../../../api/manage";

  export default {
    name: "PersonRelation",
    props: {
      selectInfo: {
        type: Object,
        default() {
          return {}
        }
      }
    },
    components: {},
    data() {
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
        hasStartTask: false,
        columns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 40,
            align: "center",
            customRender: function (t, r, index) {
              return parseInt(index) + 1;
            }
          },
          {
            title: '人员姓名',
            dataIndex: 'personName',
            align: "center"
          },
          {
            title: '图片地址',
            align: "center",
            dataIndex: 'photoUrl',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title: '进出时间',
            dataIndex: 'outInTime',
            align: "center"
          },
          {
            title: '进出地点',
            dataIndex: 'address',
            align: "center"
          },
        ],
        headStyle: {
          backgroundColor: '#1fc8db',
          backgroundImage: 'linear-gradient(141deg,#9fb8ad 0%,#1fc8db 51%,#2cb5e8 75%)',
          color: '#fff',
          opacity: 0.95
        },

        headStyle1: {
          backgroundColor: '#1fc8db',
          backgroundImage: 'linear-gradient(141deg,#9fb8ad 0%,#1fc8db 51%,#2cb5e8 75%)',
          color: '#fff',
          opacity: 0.65
        },
        url: {
          list: "/monitor/monitorPersonRecord/list",
          taskStatus: '/monitor/monitorSearchTask/getCountBySearchRecordId',
          startTask: '/monitor/monitorSearchTask/addSearchTask'
        },
        title: "人员信息",
        tabList: [],
        count: -1,
        btnDisabled: true,
        visible: true
      }
    },
    mounted() {
      this.visible = true
      console.log(this.selectInfo)
      this.loadData(1)
      this.getTaskStatus()
    },
    methods: {
      getTaskStatus() {
        getAction(this.url.taskStatus, {searchRecordId: this.selectInfo.id}).then((res) => {
          if (res.success) {
            this.count = res.result
            this.hasStartTask = false
            if (this.count > 0) {
              setTimeout(() => {
                this.getTaskStatus()
              }, 3000)
            }
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
        })
      },
      startTask() {
        this.hasStartTask = true
        const endTime = new Date().Format('yyyy-MM-dd hh:mm:ss')
        const beginTime = new Date(new Date(endTime).getTime() - 90 * 24 * 60 * 60 * 1000).Format('yyyy-MM-dd hh:mm:ss')
        postAction(this.url.startTask, {
          beginTime: beginTime,
          endTime: endTime,
          searchRecordId: this.selectInfo.id,
          searchUrl: this.selectInfo.zhaoPian
        }).then((res) => {
          if (res.success) {
            this.getTaskStatus()
          }
          if (res.code === 510) {
            this.$message.warning(res.message)
          }
        })
      },
      /* 图片预览 */
      getImgViewRecord(text) {
        if (text && text.indexOf(",") > 0) {
          text = text.substring(0, text.indexOf(","))
        }
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
        console.log(this.selectInfo)
        const params = {
          column: 'outInTime',
          order: 'desc',
          pageNo: this.ipagination.current,
          pageSize: this.ipagination.pageSize,
          personId: this.selectInfo.id
        }
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if (res.code === 510) {
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
  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }

  .anty-img-wrap {
    height: 25px;
    position: relative;
  }

  .anty-img-wrap > img {
    max-height: 100%;
  }

  .base_info {
    position: relative;
  }

  .base_info_img {
    width: 120px;
    height: 160px;
    position: absolute;
    background: rgba(44, 181, 232, 0.25);
    top: 10px;
    left: 10px;
  }

  .base_info_box {
    position: relative;
    margin-left: 150px;
    padding: 20px 0;
  }

  .base_info_label {
    line-height: 36px;
    color: #666;
    font-weight: bold;
    font-size: 14px;
  }

  .base_info_content {
    line-height: 36px;
    color: #333;
    font-size: 14px;
  }

  .relation_list {
    position: relative;
    margin-bottom: 10px;
    width: 370px;
  }

  .relation_item {
    width: 360px;
    height: 140px;
    border-radius: 5px;
    border: 1px solid #ccc;
    box-shadow: 0 0 10px #ccc;
    margin-bottom: 10px;
    position: relative;
    box-sizing: border-box;
    padding: 10px 0;
  }

  .relation_item_img {
    position: absolute;
    left: 10px;
    top: 9px;
    border-radius: 2px;
    width: 90px;
    height: 120px;
    background: rgba(44, 181, 232, 0.25);
  }

  .base_info_img img, .relation_item_img img {
    position: absolute;
    display: block;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
  }

  .relation_item_info {
    margin-left: 110px;
    position: relative;
    min-height: 16px;
    padding: 2px 0;
  }

  .relation_item_info_l {
    position: absolute;
    line-height: 16px;
    height: 16px;
    top: 4px;
    left: 0;
    color: #666;
  }

  .relation_item_info_r {
    position: relative;
    line-height: 16px;
    padding: 2px 2px 2px 70px;
  }

  .relation_item:hover, .relation_item.active {
    border: 1px solid rgba(44, 181, 232, 0.75);
    box-shadow: 0 0 10px rgba(44, 181, 232, 0.75);
    cursor: pointer;
  }
</style>