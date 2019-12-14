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

        <a-form-item label="人员姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'personName', validatorRules.personName]" placeholder="请输入人员姓名"></a-input>
        </a-form-item>
        <a-form-item label="身份证" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'personIdCard', validatorRules.personIdCard]" placeholder="请输入身份证"></a-input>
        </a-form-item>
        <a-form-item label="进入类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['outInType']" :trigger-change="true" dictCode="out_in_type" placeholder="请选择进入类型"/>
        </a-form-item>
        <a-form-item label="进出时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择进出时间" v-decorator="[ 'outInTime', validatorRules.outInTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="人员类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['personType']" :trigger-change="true" dictCode="person_type" placeholder="请选择人员类型"/>
        </a-form-item>
        <a-form-item label="图片地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['photoUrl']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="进出地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入进出地址"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "MonitorPersonRecordModal",
    components: { 
      JDate,
      JUpload,
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
        personName:{},
        personIdCard:{},
        outInType:{rules: [{ required: true, message: '请输入进入类型!' }]},
        outInTime:{rules: [{ required: true, message: '请输入进出时间!' }]},
        personType:{rules: [{ required: true, message: '请输入人员类型!' }]},
        photoUrl:{},
        address:{},
        },
        url: {
          add: "/monitor/monitorPersonRecord/add",
          edit: "/monitor/monitorPersonRecord/edit",
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
          this.form.setFieldsValue(pick(this.model,'personName','personIdCard','outInType','outInTime','personType','photoUrl','address'))
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
        this.form.setFieldsValue(pick(row,'personName','personIdCard','outInType','outInTime','personType','photoUrl','address'))
      },

      
    }
  }
</script>