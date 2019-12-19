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

        <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['messageType']" :trigger-change="true" dictCode="message_type" placeholder="请选择类型"/>
        </a-form-item>
        <a-form-item label="日志内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'content', validatorRules.content]" placeholder="请输入日志内容"></a-input>
        </a-form-item>
        <a-form-item label="数据json" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dataContent', validatorRules.dataContent]" placeholder="请输入数据json"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['status']" :trigger-change="true" dictCode="message_status" placeholder="请选择状态"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'memo', validatorRules.memo]" placeholder="请输入备注"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "MonitorMessageModal",
    components: { 
      JDictSelectTag,
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
        messageType:{},
        content:{},
        dataContent:{},
        status:{},
        memo:{},
        },
        url: {
          add: "/monitor/monitorMessage/add",
          edit: "/monitor/monitorMessage/edit",
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
          this.form.setFieldsValue(pick(this.model,'createTime','messageType','content','dataContent','status','memo'))
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
        this.form.setFieldsValue(pick(row,'createTime','messageType','content','dataContent','status','memo'))
      },

      
    }
  }
</script>