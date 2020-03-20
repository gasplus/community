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

        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'xingMing', validatorRules.xingMing]" placeholder="请输入姓名"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['xingBie']" :trigger-change="true" dictCode="sex"
                             placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="公民身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sfzh', validatorRules.sfzh]" placeholder="请输入公民身份证号"></a-input>
        </a-form-item>

        <a-form-item label="户籍地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'hjdz', validatorRules.hjdz]" placeholder="请输入户籍地址"></a-input>
        </a-form-item>
        <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'lxdh', validatorRules.lxdh]" placeholder="请输入联系电话"></a-input>
        </a-form-item>

        <a-form-item label="民族" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['minZu']" :trigger-change="true" dictCode="hu_mz" placeholder="请选择民族"/>
        </a-form-item>
        <a-form-item label="照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <img :src="model.zhaoPian" alt="" style="width:200px;">
        </a-form-item>

<!--        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
<!--          <a-textarea v-decorator="['memo']" rows="4" placeholder="请输入备注"/>-->
<!--        </a-form-item>-->

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
  import JCategorySelect from '@/components/jeecg/JCategorySelect'

  export default {
    name: "MonitorRegisterPersonModal",
    components: {
      JDate,
      JUpload,
      JDictSelectTag,
      JCategorySelect
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"访客登记",
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
        xingMing:{rules: [{ required: true, message: '请输入姓名!' }]},
        xingBie:{},
        csrq:{},
        sfzh:{rules: [{ required: true, message: '请输入公民身份证号!' }]},
        pcs:{},
        jwh:{},
        hjdz:{},
        guanXi:{},
        lxdh:{},
        tenantId:{},
        louDongHao:{},
        minZu:{},
        jieLuXiang:{},
        fenJu:{},
        danYuanHao:{},
        fangJianHao:{},
        zhaoPian:{},
        huHao:{},
        type:{},
        memo:{},
        xxdz:{},
        typeText:{},
        },
        url: {
          add: "/monitor/monitorPerson/addStranger",
          edit: "",
        }

      }
    },
    created () {
    },
    methods: {
      add (record) {
        this.edit(record||{});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'xingMing','xingBie','sfzh','hjdz','lxdh','minZu','zhaoPian'))
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
        this.form.setFieldsValue(pick(row,'xingMing','xingBie','sfzh','hjdz','lxdh','minZu','zhaoPian'))
      },
      handleCategoryChange(value,backObj){
        this.form.setFieldsValue(backObj)
      }


    }
  }
</script>