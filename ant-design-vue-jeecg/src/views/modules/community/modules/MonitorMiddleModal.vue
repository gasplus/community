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

        <a-form-item label="小区id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'xiaoQuId', validatorRules.xiaoQuId]" placeholder="请输入小区id"></a-input>
        </a-form-item>
        <a-form-item label="表名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'tableName', validatorRules.tableName]" placeholder="请输入表名"></a-input>
        </a-form-item>
        <a-form-item label="主键值" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'pkValue', validatorRules.pkValue]" placeholder="请输入主键值"></a-input>
        </a-form-item>
        <a-form-item label="动作类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'actionType', validatorRules.actionType]" placeholder="请输入动作类型" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="同步状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'syncType', validatorRules.syncType]" placeholder="请输入同步状态" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "MonitorMiddleModal",
    components: { 
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        xiaoQuId:{rules: [{ required: true, message: '请输入小区id!' }]},
        tableName:{rules: [{ required: true, message: '请输入表名!' }]},
        pkValue:{rules: [{ required: true, message: '请输入主键值!' }]},
        actionType:{},
        syncType:{},
        },
        url: {
          add: "/monitor/monitorMiddle/add",
          edit: "/monitor/monitorMiddle/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'xiaoQuId','tableName','pkValue','actionType','syncType'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'xiaoQuId','tableName','pkValue','actionType','syncType'))
      },

      
    }
  }
</script>