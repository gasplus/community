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

        <a-form-item label="监控标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'title', validatorRules.title]" placeholder="请输入监控标题"></a-input>
        </a-form-item>
        <a-form-item label="关联数据id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dataId', validatorRules.dataId]" placeholder="请输入关联数据id"></a-input>
        </a-form-item>
        <a-form-item label="监控类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'alarmType', validatorRules.alarmType]" placeholder="请输入监控类型"></a-input>
        </a-form-item>
        <a-form-item label="报警规则" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'alarmRuleType', validatorRules.alarmRuleType]" placeholder="请输入报警规则"></a-input>
        </a-form-item>
        <a-form-item label="间隔天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'intervalDays', validatorRules.intervalDays]" placeholder="请输入间隔天数"
                          style="width: 100%"/>
        </a-form-item>
        <a-form-item label="时间区间定义" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'alarmTimeConfig', validatorRules.alarmTimeConfig]" placeholder="请输入时间区间定义"></a-input>
        </a-form-item>
        <a-form-item label="报警模板" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['alarmTemplate']" :trigger-change="true"
                             dictCode="monitor_alarm_template" placeholder="请选择报警模板"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import {httpAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "MonitorAlarmConfigModal",
    components: {
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
          title: {},
          dataId: {rules: [{required: true, message: '请输入关联数据id!'}]},
          alarmType: {rules: [{required: true, message: '请输入监控类型!'}]},
          alarmRuleType: {rules: [{required: true, message: '请输入报警规则!'}]},
          intervalDays: {},
          alarmTimeConfig: {},
          alarmTemplate: {},
        },
        url: {
          add: "/monitor/monitorAlarmConfig/add",
          edit: "/monitor/monitorAlarmConfig/edit",
        }

      }
    },
    created() {
    },
    methods: {
      add() {
        this.edit({});
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'title', 'dataId', 'alarmType', 'alarmRuleType', 'intervalDays', 'alarmTimeConfig', 'alarmTemplate'))
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
        this.form.setFieldsValue(pick(row, 'title', 'dataId', 'alarmType', 'alarmRuleType', 'intervalDays', 'alarmTimeConfig', 'alarmTemplate'))
      },


    }
  }
</script>