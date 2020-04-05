<template>
  <a-modal
    title="发起以图搜人"
    :width="1000"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :okButtonProps="okSetting"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <div class="result_list" v-if="okSetting.props.disabled">
      <div>
        <span v-if="searchStatus!=='30'" style="padding-right:20px;">搜索已耗时：<span
          style="color:#1890ff;">{{getTimeStr()}}</span></span>
        <span style="padding-right:20px;">检索结果数量：<span style="color:#1890ff;">{{getResultCount()}}</span></span>
        <span v-if="searchStatus">
          搜索状态：<span :style="'color:#'+(searchStatus==='30'?'8ada54':'1890ff')+';'">{{statusMap[searchStatus]}}
          <span v-if="time>0">.</span>
          <span v-if="time%5===1">.</span>
          <span v-if="time%5===2">..</span>
          <span v-if="time%5===3">...</span>
          <span v-if="time%5===4">....</span>
        </span></span>
      </div>
      <div style="padding:10px 0">
        <a-button type="primary" style="margin-right:10px;" @click="refreshResult">刷新</a-button>
        <a-button type="primary" @click="showTaskRecord">查看任务</a-button>
      </div>
      <MonitorSearchResultList ref="resultList" :searchId="selectRecord.id"></MonitorSearchResultList>
    </div>
    <a-spin :spinning="confirmLoading" v-if="!okSetting.props.disabled">
      <a-form :form="form">

        <!--
                <a-form-item label="搜索标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'searchTitle', validatorRules.searchTitle]" placeholder="请输入搜索标题"></a-input>
                </a-form-item>-->
        <a-form-item label="搜索图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <img :src="getImgViewRecord(personRecord.photoUrl)" style="width:100px;" alt="">
        </a-form-item>
        <a-form-item label="进出时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group v-model="timeType" buttonStyle="solid">
            <a-radio-button value="0">当天</a-radio-button>
            <a-radio-button value="1">近7天</a-radio-button>
            <a-radio-button value="2">近30天</a-radio-button>
            <a-radio-button value="3">自定义</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item v-if="timeType==='3'" label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开始时间" v-decorator="[ 'beginTime', validatorRules.beginTime]" :trigger-change="true"
                  :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item v-if="timeType==='3'" label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束时间" v-decorator="[ 'endTime', validatorRules.endTime]" :trigger-change="true"
                  :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import {httpAction, getAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JUpload from '@/components/jeecg/JUpload'
  import MonitorSearchResultList from '../MonitorSearchResultList'

  export default {
    name: "MonitorSearchTaskModal",
    components: {
      JDate,
      JUpload,
      MonitorSearchResultList
    },
    props: {
      searchType: {
        type: String,
        default() {
          return ''
        }
      },
      personRecord: {
        type: Object,
        default() {
          return {
            personId: '',
            photoUrl: ''
          }
        }
      },
      personId: {
        type: String,
        default() {
          return ''
        }
      },
      showFlag: {
        type: Boolean,
        default() {
          return false
        }
      },
      searchType: ''
    },
    watch: {
      timeType(value) {
        if (value === '0') {
          const endDate = new Date()
          this.model.beginTime = endDate.Format('yyyy-MM-dd 00:00:00')
          this.model.endTime = endDate.Format('yyyy-MM-dd hh:mm:ss')
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model, 'beginTime', 'endTime'))
          })
        } else if (value === '1') {
          const endDate = new Date()
          const beginDate = new Date(endDate.getTime() - 7 * 24 * 60 * 60 * 1000)
          this.model.beginTime = beginDate.Format('yyyy-MM-dd hh:mm:ss')
          this.model.endTime = endDate.Format('yyyy-MM-dd hh:mm:ss')
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model, 'beginTime', 'endTime'))
          })
        } else if (value === '2') {
          const endDate = new Date()
          const beginDate = new Date(endDate.getTime() - 30 * 24 * 60 * 60 * 1000)
          this.model.beginTime = beginDate.Format('yyyy-MM-dd hh:mm:ss')
          this.model.endTime = endDate.Format('yyyy-MM-dd hh:mm:ss')
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model, 'beginTime', 'endTime'))
          })
        } else if (value === '3') {
          this.model.beginTime = ''
          this.model.endTime = ''
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model, 'beginTime', 'endTime'))
          })
        }
      },
      showFlag(v) {
        if (v) {
          this.timeType = ''
          this.form.resetFields();
          this.model = {};
          this.initData()
          this.visible = true;
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model, 'searchTitle', 'searchUrl', 'beginTime', 'endTime'))
          })
        }
      }
    },
    created() {
      console.log(this.showFlag)
      console.log(this.personRecord)

    },
    data() {
      return {
        statusMap: {
          10: '搜索中',
          20: '搜索中',
          30: '已完成'
        },
        okSetting: {
          props: {
            disabled: false
          }
        },
        timeType: '',
        form: this.$form.createForm(this),
        title: "操作",
        width: 800,
        visible: false,
        model: {},
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
        time: 0,
        timeInterval: undefined,
        confirmLoading: false,
        validatorRules: {
          searchTitle: {rules: [{required: true, message: '请输入搜索标题!'}]},
          searchUrl: {rules: [{required: true, message: '请输入搜索图片!'}]},
          beginTime: {rules: [{required: true, message: '请输入开始时间!'}]},
          endTime: {rules: [{required: true, message: '请输入结束时间!'}]},
        },
        searchStatus: '',
        url: {
          getStatus: "/monitor/monitorSearchTask/queryById",
          add: "/monitor/monitorSearchTask/add",
          edit: "/monitor/monitorSearchTask/edit",
        }

      }
    },
    mounted() {
    },
    methods: {
      initData() {
        this.timeType = '0'
        const endDate = new Date()
        this.model.beginTime = endDate.Format('yyyy-MM-dd 00:00:00')
        this.model.endTime = endDate.Format('yyyy-MM-dd hh:mm:ss')
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'beginTime', 'endTime'))
        })
        this.time = 0
        this.searchStatus = ''
        this.okSetting.props.disabled = false
        if (this.timeInterval) {
          clearInterval(this.timeInterval)
        }
      },
      getResultCount() {
        if (this.$refs.resultList) {
          return this.$refs.resultList.ipagination.total
        }
        return 0
      },
      getImgViewRecord(text) {
        // if (text && text.indexOf(",") > 0) {
        //   text = text.substring(0, text.indexOf(","))
        // }
        return window._CONFIG['imgDomainRecordURL'] + text
      },
      showTaskRecord() {
        this.$router.push({
          path: '/modules/community/MonitorSearchTaskList'
        })
      },
      refreshResult() {
        this.$nextTick(() => {
          this.$refs.resultList.loadData(1)
        })
      },
      getResultStatus() {
        getAction(this.url.getStatus, {id: this.selectRecord.id}).then(res => {
          console.log(res)
          if (res.success) {
            this.searchStatus = res.result.searchStatus + ''
            if (this.searchStatus === '30') {
              if (this.timeInterval) {
                clearInterval(this.timeInterval)
                this.time = 0
              }
            }
          }
        })
      },
      searchResultList() {
        this.$nextTick(() => {
          this.getResultStatus()
          this.$refs.resultList.loadData()
        })
      },
      showTimeCount() {
        this.time = 1
        if (this.timeInterval) {
          clearInterval(this.timeInterval)
        }
        this.searchStatus = '10'
        this.timeInterval = setInterval(() => {
          this.time++
          if (this.time % 5 === 0) {
            this.searchResultList()
          }
        }, 1000)
      },
      add() {

        this.edit({});
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'searchTitle', 'searchUrl', 'beginTime', 'endTime'))
        })
      },
      getTimeStr() {
        if (this.time / (3600) >= 1) {
          return Math.floor(this.time / 3600) + '小时' + Math.floor(this.time % 3600 / 60) + '分钟' + this.time % 60 + '秒'
        } else if (this.time / (60) >= 1) {
          return Math.floor(this.time % 3600 / 60) + '分钟' + this.time % 60 + '秒'
        } else if (this.time > 0) {
          return this.time % 60 + '秒'
        }
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if (!this.model.id) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            if (this.searchType) {
              formData.searchType = this.searchType
            }
            console.log("表单提交数据", formData)
            formData.personId = this.personRecord.personId
            formData.searchUrl = this.getImgViewRecord(this.personRecord.photoUrl)
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success) {
                that.$message.success('添加成功');
                that.selectRecord = res.result
                that.okSetting.props.disabled = true
                that.showTimeCount();
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              //that.close();
              this.$emit('handleCancel')
            })
          }

        })
      },
      handleCancel() {
        this.initData()
        this.close()
        this.$emit('handleCancel')
      },
      popupCallback(row) {
        this.form.setFieldsValue(pick(row, 'searchTitle', 'searchUrl', 'beginTime', 'endTime'))
      },


    }
  }
</script>
<style>
  .result_list .ant-card-body {
    padding: 0;
  }
</style>