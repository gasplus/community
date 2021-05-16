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

        <a-form-item label="图片分组"
                     :labelCol="labelCol"
                     :wrapperCol="wrapperCol">
          <a-select
            v-decorator="[ 'group_id_list', validatorRules.group_id_list]"
            mode="multiple"
            style="width: 100%"
            placeholder="请选择图片分组"
            :maxTagCount="10"
            @change="changeData"
            option-label-prop="label"
          >
            <a-select-option
              :key="ind"
              :disabled="getDisabledStatus(item)"
              v-for="(item,ind) in baiduGroupList"
              :value="item.value"
              :label="item.label">
               {{item.label}}
            </a-select-option>
          </a-select>
<!--          <a-input v-decorator="[ 'searchTitle', validatorRules.searchTitle]" placeholder="请输入搜索标题"></a-input>-->
        </a-form-item>
        <a-form-item label="搜索图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <JUploadImageBase64
            v-decorator="[ 'image', validatorRules.image]"
            :trigger-change="true"></JUploadImageBase64>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import {httpAction,getAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JUploadImageBase64 from '@/components/jeecg/JUploadImageBase64'

  export default {
    name: "MonitorSearchTaskModal",
    components: {
      JDate,
      JUploadImageBase64,
    },
    data() {
      return {
        selectedGroups: [],
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
          group_id_list: {rules: [{required: true, message: '请选择百度分组!'}]},
          image: {rules: [{required: true, message: '请选择搜索图片!'}]},
        },
        url: {
          add: "/monitor/monitorSearchTask/add",
          edit: "/monitor/monitorSearchTask/edit",
          addBaiduSearchTask:"/monitor/monitorSearchTask/addBaiduSearchTask",
          baiduGroup: "/monitor/monitorSearchTask/getGroupList"
        },
        baiduGroupList: [],
        selectGroupList: [],
        limitGroupCount: 10
      }
    },
    created() {
      this.getBaiduGroup()
    },
    methods: {
      getDisabledStatus(item) {
        const result = this.selectGroupList.length >= this.limitGroupCount && this.selectGroupList.indexOf(item.value)===-1
        return result
      },
      changeData(value) {
        this.selectGroupList.splice(0,this.selectGroupList.length,...value)
      },
      getBaiduGroup() {
        getAction(this.url.baiduGroup, {}).then((res) => {
          if (res.success) {
            const result = res.result
            const statusList = result.group_option_list
            const groupList = result.group_id_list
            const resultList = []
            groupList.forEach((item,ind)=>{
              if(statusList[ind] && statusList[ind] === 'LIVE'){
                resultList.push({
                  value: item,
                  label: item,
                  disabled: true
                })
              }
            })
            this.baiduGroupList = resultList
          } else {
            this.$message.warning(res.message);
          }
        })
      },
      add() {
        this.edit({});
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'group_id_list', 'image'))
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
            let httpurl = this.url.addBaiduSearchTask;
            let method = 'post';
            let formData = Object.assign(this.model, values);
            const params = {
              group_id_list: formData.group_id_list.join(','),
              image: formData.image
            }
            httpAction(httpurl, params, method).then((res) => {
              if (res.success) {
                that.$message.success('添加成功');
                that.$emit('ok',{id: res.message});
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
        this.form.setFieldsValue(pick(row, 'group_id_list', 'image'))
      },


    }
  }
</script>