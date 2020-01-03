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
          <a-input v-decorator="[ 'outInType', validatorRules.outInType]" placeholder="请输入进入类型"></a-input>
        </a-form-item>
        <a-form-item label="进出时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择进出时间" v-decorator="[ 'outInTime', validatorRules.outInTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="人员类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'personType', validatorRules.personType]" placeholder="请输入人员类型"></a-input>
        </a-form-item>
        <a-form-item label="图片地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'photoUrl', validatorRules.photoUrl]" placeholder="请输入图片地址"></a-input>
        </a-form-item>
        <a-form-item label="进出地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入进出地址"></a-input>
        </a-form-item>
        <a-form-item label="站点id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'stationId', validatorRules.stationId]" placeholder="请输入站点id"></a-input>
        </a-form-item>
        <a-form-item label="通道id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'channelId', validatorRules.channelId]" placeholder="请输入通道id"></a-input>
        </a-form-item>
        <a-form-item label="设备id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'deviceId', validatorRules.deviceId]" placeholder="请输入设备id"></a-input>
        </a-form-item>
        <a-form-item label="设备ip" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'deviceIp', validatorRules.deviceIp]" placeholder="请输入设备ip"></a-input>
        </a-form-item>
        <a-form-item label="人员id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'personId', validatorRules.personId]" placeholder="请输入人员id"></a-input>
        </a-form-item>
        <a-form-item label="租户id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'tenantId', validatorRules.tenantId]" placeholder="请输入租户id"></a-input>
        </a-form-item>
        <a-form-item label="人体识别结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['bodyInfo']" rows="4" placeholder="请输入人体识别结果"/>
        </a-form-item>
        <a-form-item label="人脸识别结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['faceInfo']" rows="4" placeholder="请输入人脸识别结果"/>
        </a-form-item>
        <a-form-item label="单元号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'danYuanHao', validatorRules.danYuanHao]" placeholder="请输入单元号"></a-input>
        </a-form-item>
        <a-form-item label="房间号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'fangJianHao', validatorRules.fangJianHao]" placeholder="请输入房间号"></a-input>
        </a-form-item>
        <a-form-item label="户籍地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'hjdz', validatorRules.hjdz]" placeholder="请输入户籍地址"></a-input>
        </a-form-item>
        <a-form-item label="楼栋号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'louDongHao', validatorRules.louDongHao]" placeholder="请输入楼栋号"></a-input>
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
    name: "MonitorHumanRecordModal",
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
        personName:{},
        personIdCard:{},
        outInType:{rules: [{ required: true, message: '请输入进入类型!' }]},
        outInTime:{rules: [{ required: true, message: '请输入进出时间!' }]},
        personType:{rules: [{ required: true, message: '请输入人员类型!' }]},
        photoUrl:{},
        address:{},
        stationId:{},
        channelId:{},
        deviceId:{},
        deviceIp:{},
        personId:{rules: [{ required: true, message: '请输入人员id!' }]},
        tenantId:{},
        bodyInfo:{},
        faceInfo:{},
        danYuanHao:{},
        fangJianHao:{},
        hjdz:{},
        louDongHao:{},
        },
        url: {
          add: "/monitor/monitorHumanRecord/add",
          edit: "/monitor/monitorHumanRecord/edit",
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
          this.form.setFieldsValue(pick(this.model,'personName','personIdCard','outInType','outInTime','personType','photoUrl','address','stationId','channelId','deviceId','deviceIp','personId','tenantId','bodyInfo','faceInfo','danYuanHao','fangJianHao','hjdz','louDongHao'))
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
        this.form.setFieldsValue(pick(row,'personName','personIdCard','outInType','outInTime','personType','photoUrl','address','stationId','channelId','deviceId','deviceIp','personId','tenantId','bodyInfo','faceInfo','danYuanHao','fangJianHao','hjdz','louDongHao'))
      },

      
    }
  }
</script>