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
        <a-form-item label="车辆类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['carType']" :trigger-change="true" dictCode="carType"
                             placeholder="请选择车辆类型"/>
        </a-form-item>
        <a-form-item label="图片地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['photoUrl']" :trigger-change="true"></j-upload>
        </a-form-item>

        <a-form-item label="人员类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group v-model="personType" buttonStyle="solid">
            <a-radio-button value="0">未登记</a-radio-button>
            <a-radio-button value="1">已登记</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item v-if="personType==='1'" label="人员选择" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            mode="multiple"
            placeholder="请选择人员"
            v-model="selectedUser"
            @deselect="removeSelected"
            @dropdownVisibleChange="showUserSelect"
          >
          </a-select>
        </a-form-item>
        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'personName', validatorRules.personName]" placeholder="请输入姓名"
                   :disabled="personType==='1'"></a-input>
        </a-form-item>
        <a-form-item label="身份证" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'personCardId', validatorRules.personCardId]" placeholder="请输入身份证"
                   :disabled="personType==='1'"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
    <SelectUCListModal ref="UserListModal" v-if="personType==='1'" :type="'10'"
                       @choseUserList="choseUserList"></SelectUCListModal>
  </a-modal>
</template>

<script>

  import {httpAction} from '@/api/manage'
  import SelectUCListModal from './SelectUCListModal'
  import pick from 'lodash.pick'
  import JUpload from '@/components/jeecg/JUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import {getAction} from "../../../../api/manage";

  export default {
    name: "MonitorCarModal",
    components: {
      JUpload,
      JDictSelectTag,
      SelectUCListModal
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
        validatorRules: {
          carNumber: {rules: [{required: true, message: '请输入车牌号!'}]},
          carType: {rules: [{required: true, message: '请输入车辆类型!'}]},
          photoUrl: {},
          personName: {rules: [{required: true, message: '请输入姓名!'}]},
          personCardId: {rules: [{required: true, message: '请输入身份证!'}]},
        },
        personType: '0',
        userIds: [],
        selectedUser: [],
        url: {
          getUserById: '/monitor/monitorPerson/queryById',
          add: "/monitor/monitorCar/add",
          edit: "/monitor/monitorCar/edit"
        }

      }
    },
    watch: {
      personType(v) {
        this.userIds = ''
        this.selectedUser = []
        this.model.personName = ''
        this.model.personCardId = ''
        this.model.personId = ''
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'carNumber', 'carType', 'photoUrl', 'personName', 'personCardId', 'personId'))
        })
      }
    },
    created() {
    },
    methods: {
      showUserSelect() {
        this.$refs.UserListModal.add(this.selectedUser, this.userIds);
      },
      removeSelected(value) {
        let deleteInd = -1
        const userIds = this.userIds.split(',')
        this.selectedUser.forEach((item, index) => {
          if (item === value) {
            deleteInd = index
          }
        })
        if (deleteInd !== -1) {
          this.selectedUser.splice(deleteInd, 1)
          userIds.splice(deleteInd, 1)
          this.userIds = userIds.join(',')
        }
        this.model.personCardId = ''
        this.model.personName = ''
        this.model.personId = ''
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'carNumber', 'carType', 'photoUrl', 'personName', 'personCardId', 'personId'))
        })
      },
      choseUserList(userList) {
        this.selectedUser = [];
        this.userIds = '';
        for (let i = 0; i < userList.length; i++) {
          this.selectedUser.push(userList[i].xingMing);
        }
        if (userList.length > 0) {
          this.model.personName = userList[0].xingMing
          this.model.personCardId = userList[0].sfzh
        }
        this.model.personId = this.userIds
        this.userIds += userList.map(item => item.id).join(",")

        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'carNumber', 'carType', 'photoUrl', 'personName', 'personCardId', 'personId'))
        })
      },
      queryUserById(id) {
        const url = this.url.getUserById
        getAction(url, {id}).then(res => {
          if (res.success) {
            this.selectedUser = [res.result.xingMing]
          } else {
            this.selectedUser = []
          }
        })
      },
      add() {
        this.edit({});
      },
      edit(record) {
        console.log(record, 'edit')
        this.form.resetFields();
        this.model = Object.assign({}, record);
        if (record.personId && record.personId !== 'anonymous') {
          this.userIds = record.personId
          this.personType = '1'
          this.queryUserById(record.personId)
        }
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'carNumber', 'carType', 'photoUrl', 'personName', 'personCardId', 'personId'))
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
            if (!this.model.id) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            if (this.userIds) {
              formData.personId = this.userIds
            }
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
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row, 'carNumber', 'carType', 'photoUrl', 'personName', 'personCardId', 'personId'))
      },


    }
  }
</script>