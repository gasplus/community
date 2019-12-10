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
          <j-dict-select-tag type="list" v-decorator="['xingBie']" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
        </a-form-item>
        <a-form-item label="出生日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择出生日期" v-decorator="[ 'csrq', validatorRules.csrq]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="公民身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sfzh', validatorRules.sfzh]" placeholder="请输入公民身份证号"></a-input>
        </a-form-item>
        <a-form-item label="所属派出所" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'pcs', validatorRules.pcs]" placeholder="请输入所属派出所"></a-input>
        </a-form-item>
        <a-form-item label="所属居委会" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'jwh', validatorRules.jwh]" placeholder="请输入所属居委会"></a-input>
        </a-form-item>
        <a-form-item label="户籍地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'hjdz', validatorRules.hjdz]" placeholder="请输入户籍地址"></a-input>
        </a-form-item>
        <a-form-item label="户关系" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'guanXi', validatorRules.guanXi]" placeholder="请输入户关系"></a-input>
        </a-form-item>
        <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'lxdh', validatorRules.lxdh]" placeholder="请输入联系电话"></a-input>
        </a-form-item>
        <a-form-item label="小区id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'xiaoQuId', validatorRules.xiaoQuId]" placeholder="请输入小区id"></a-input>
        </a-form-item>
        <a-form-item label="楼栋号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'louDongHao', validatorRules.louDongHao]" placeholder="请输入楼栋号"></a-input>
        </a-form-item>
        <a-form-item label="民族" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['hu_mz']" :trigger-change="true" dictCode="" placeholder="请选择民族"/>
        </a-form-item>
        <a-form-item label="街路巷" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'jieLuXiang', validatorRules.jieLuXiang]" placeholder="请输入街路巷"></a-input>
        </a-form-item>
        <a-form-item label="所属分局" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'fenJu', validatorRules.fenJu]" placeholder="请输入所属分局"></a-input>
        </a-form-item>
        <a-form-item label="单元号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'danYuanHao', validatorRules.danYuanHao]" placeholder="请输入单元号"></a-input>
        </a-form-item>
        <a-form-item label="房间号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'fangJianHao', validatorRules.fangJianHao]" placeholder="请输入房间号"></a-input>
        </a-form-item>
        <a-form-item label="照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['zhaoPian']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="户号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'huHao', validatorRules.huHao]" placeholder="请输入户号"></a-input>
        </a-form-item>
        <a-form-item label="人员类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['type']" :trigger-change="true" dictCode="hu_person_type" placeholder="请选择人员类型"/>
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
    name: "MonitorPersonModal",
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
        xingMing:{rules: [{ required: true, message: '请输入姓名!' }]},
        xingBie:{},
        csrq:{},
        sfzh:{rules: [{ required: true, message: '请输入公民身份证号!' }]},
        pcs:{},
        jwh:{},
        hjdz:{},
        guanXi:{},
        lxdh:{},
        xiaoQuId:{},
        louDongHao:{},
        minZu:{},
        jieLuXiang:{},
        fenJu:{},
        danYuanHao:{},
        fangJianHao:{},
        zhaoPian:{},
        huHao:{},
        type:{},
        },
        url: {
          add: "/monitor/monitorPerson/add",
          edit: "/monitor/monitorPerson/edit",
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
          this.form.setFieldsValue(pick(this.model,'xingMing','xingBie','csrq','sfzh','pcs','jwh','hjdz','guanXi','lxdh','xiaoQuId','louDongHao','minZu','jieLuXiang','fenJu','danYuanHao','fangJianHao','zhaoPian','huHao','type'))
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
        this.form.setFieldsValue(pick(row,'xingMing','xingBie','csrq','sfzh','pcs','jwh','hjdz','guanXi','lxdh','xiaoQuId','louDongHao','minZu','jieLuXiang','fenJu','danYuanHao','fangJianHao','zhaoPian','huHao','type'))
      },

      
    }
  }
</script>