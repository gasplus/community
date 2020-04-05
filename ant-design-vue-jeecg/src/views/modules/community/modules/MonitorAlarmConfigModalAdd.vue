<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <!--        <a-form-item label="监控标题" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="[ 'title', validatorRules.title]" placeholder="请输入监控标题"></a-input>-->
        <!--        </a-form-item>-->
        <a-form-item label="监控类型" v-if="selectAlarmType==='10'"  :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-tag>人员</a-tag>
        </a-form-item>
        <a-form-item label="监控类型" v-if="selectAlarmType==='20'"  :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-tag>车辆</a-tag>
        </a-form-item>
        <a-form-item v-if="selectAlarmType==='10'" :label="'监控'+(selectAlarmType==='10'?'人员':'车辆')" :labelCol="labelCol"
                     :wrapperCol="wrapperCol">
          <a-tag color="blue">{{selectObj.xingMing}}</a-tag>
        </a-form-item>
        <a-form-item v-if="selectAlarmType === '20'" label="车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-tag color="blue">{{selectObj.carNumber}}</a-tag>
        </a-form-item>
        <a-form-item label="报警规则" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['alarmRuleType', validatorRules.alarmRuleType]" :trigger-change="true"
                             @change="handleChangeAlarmRuleType"
                             dictCode="alarm__rule_type" placeholder="请选择报警规则"/>
        </a-form-item>

        <a-form-item v-if="selectAlarmRuleType==='10'" label="间隔天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'intervalDays', validatorRules.intervalDays]" placeholder="请输入间隔天数"></a-input>
        </a-form-item>
        <!--
        <a-form-item label="时间区间定义" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'alarmTimeConfig', validatorRules.alarmTimeConfig]" placeholder="请输入时间区间定义"></a-input>
        </a-form-item>

        <a-form-item label="是否有效" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['status']" :trigger-change="true" dictCode="isYN"
                             placeholder="请选择是否有效"/>
        </a-form-item>
        -->
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import {httpAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import SelectUCListModal from './SelectUCListModal'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import {getAction} from "../../../../api/manage";

  export default {
    name: "MonitorAlarmConfigModal",
    components: {
      SelectUCListModal,
      JDictSelectTag,
    },
    data() {
      return {
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

        confirmLoading: false,
        validatorRules: {
          // title: {rules: [{required: true, message: '请输入监控标题!'}]},
          alarmType: {rules: [{required: true, message: '请输入监控类型!'}]},
          alarmRuleType: {rules: [{required: true, message: '请输入报警规则!'}]},
          dataId: {rules: [{required: true, message: '请输入车牌号!'}]},
          intervalDays: {rules: [{required: true, message: '请输入间隔天数!'}]},
          alarmTimeConfig: {},
          status: {},
        },
        url: {
          getUserById: '/monitor/monitorPerson/queryById',
          getCarById: '/monitor/monitorCar/queryById',
          add: "/monitor/monitorAlarmConfig/add",
          edit: "/monitor/monitorAlarmConfig/edit",
        },
        userIds: [],
        userNames: [],
        userIdCards: [],
        userTypes: [],
        selectedUser: [],
        selectAlarmType: '',
        selectObj: {},
        selectAlarmRuleType: '',
        nameKey: {
          10: 'xingMing',
          20: 'dataId'
        }
      }
    },
    created() {
    },
    methods: {
      handleChangeAlarmRuleType(v){
        this.selectAlarmRuleType = v
      },
      add(type,obj) {
        this.selectAlarmType = type
        this.selectAlarmRuleType = ''
        this.selectObj = obj
        const o = {
          status: 'N'
        }
        if(type==='10'){
          o.dataId = obj.id
          o.title = obj.xingMing
          o.prop1 = obj.sfzh
          o.prop2 = obj.type
        } else if(type === '20'){
          o.dataId = obj.carNumber
          o.title = obj.carNumber
        }
        this.edit(o);
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'title', 'alarmType', 'alarmRuleType', 'alarmTimeConfig', 'status','intervalDays'))
        })
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
            const httpurl = this.url.add;
            const method = 'post';
            let formData = Object.assign(this.model, values);
            formData.alarmType = this.selectAlarmType
            console.log("表单提交数据", formData)
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }

        })
      },
      handleCancel() {
        this.close()
      },
      popupCallback(row) {
        this.form.setFieldsValue(pick(row, 'title', 'alarmType', 'alarmRuleType', 'alarmTimeConfig', 'status','intervalDays'))
      },


    }
  }
</script>