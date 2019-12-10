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

        <a-form-item label="车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'carNumber', validatorRules.carNumber]" placeholder="请输入车牌号"></a-input>
        </a-form-item>
        <a-form-item label="进入类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'outInType', validatorRules.outInType]" placeholder="请输入进入类型"></a-input>
        </a-form-item>
        <a-form-item label="进出时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择进出时间" v-decorator="[ 'outInTime', validatorRules.outInTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="人员类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'carType', validatorRules.carType]" placeholder="请输入人员类型"></a-input>
        </a-form-item>
        <a-form-item label="图片地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'photoUrl', validatorRules.photoUrl]" placeholder="请输入图片地址"></a-input>
        </a-form-item>
        <a-form-item label="进出地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入进出地址"></a-input>
        </a-form-item>
        <a-form-item label="carId" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'carId', validatorRules.carId]" placeholder="请输入carId"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: "MonitorCarRecordModal",
    components: { 
      JDate,
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
        carNumber:{},
        outInType:{rules: [{ required: true, message: '请输入进入类型!' }]},
        outInTime:{rules: [{ required: true, message: '请输入进出时间!' }]},
        carType:{rules: [{ required: true, message: '请输入人员类型!' }]},
        photoUrl:{},
        address:{},
        carId:{},
        },
        url: {
          add: "/monitor/monitorCarRecord/add",
          edit: "/monitor/monitorCarRecord/edit",
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
          this.form.setFieldsValue(pick(this.model,'carNumber','outInType','outInTime','carType','photoUrl','address','carId'))
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
        this.form.setFieldsValue(pick(row,'carNumber','outInType','outInTime','carType','photoUrl','address','carId'))
      },

      
    }
  }
</script>