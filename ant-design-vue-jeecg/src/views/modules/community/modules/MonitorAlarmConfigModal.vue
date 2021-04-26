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

        <!--        <a-form-item label="监控标题" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--          <a-input v-decorator="[ 'title', validatorRules.title]" placeholder="请输入监控标题"></a-input>-->
        <!--        </a-form-item>-->
        <a-form-item label="监控类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['alarmType', validatorRules.alarmType]" :trigger-change="true"
                             @change="handleChangeAlarmType"
                             dictCode="monitor_alarm_type" placeholder="请选择监控类型"/>
        </a-form-item>
        <a-form-item v-if="selectAlarmType==='10'" :label="(selectAlarmType==='10'?'人员':'车辆')+'选择'" :labelCol="labelCol"
                     :wrapperCol="wrapperCol">
          <a-select

            :placeholder="'请选择'+(selectAlarmType+''==='10'?'人员':'车辆')"
            v-model="selectedUser"
            @deselect="removeSelected"
            @dropdownVisibleChange="showUserSelect"
          >
          </a-select>
        </a-form-item>
        <a-form-item v-if="selectAlarmType === '20'" label="车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'dataId', validatorRules.dataId]" placeholder="请输入车牌号"></a-input>
        </a-form-item>
        <a-form-item label="报警规则" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['alarmRuleType', validatorRules.alarmRuleType]" :trigger-change="true"
                             @change="handleChangeAlarmRuleType"
                             dictCode="alarm__rule_type" placeholder="请选择报警规则"/>
        </a-form-item>

        <a-form-item v-if="selectAlarmRuleType==='10'" label="间隔天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'intervalDays', validatorRules.intervalDays]" placeholder="请输入间隔天数"></a-input>
        </a-form-item>
        <!--
        <a-form-item label="时间区间定义" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'alarmTimeConfig', validatorRules.alarmTimeConfig]" placeholder="请输入时间区间定义"></a-input>
        </a-form-item>

        <a-form-item label="是否有效" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['status']" :trigger-change="true" dictCode="isYN"
                             placeholder="请选择是否有效"/>
        </a-form-item>
        -->
      </a-form>
    </a-spin>
    <SelectUCListModal ref="UserListModal" v-if="selectAlarmType" :type="selectAlarmType" @choseUserList="choseUserList"></SelectUCListModal>
  </a-modal>
</template>

<script>

  import {httpAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import SelectUCListModal from './SelectUCListModal'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import {getAction} from "../../../../api/manage";

  export default {
    name: "MonitorAlarmConfigModal",
    components: {
      SelectUCListModal,
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
          // title: {rules: [{required: true, message: '请输入监控标题!'}]},
          alarmType: {rules: [{required: true, message: '请输入监控类型!'}]},
          alarmRuleType: {
            rules: [
              {required: true, message: '请输入报警规则!'}
              , {validator: this.validateType}
            ]
          },
          dataId: {rules: [{required: true, message: '请输入车牌号!'}]},
          intervalDays: {rules: [{required: true, message: '请输入间隔天数!'}]},
          alarmTimeConfig: {},
          status: {},
        },
        url: {
          getUserById: '/monitor/monitorPerson/queryById',
          getCarById: '/monitor/monitorCar/queryById',
          add: "/monitor/monitorAlarmConfig/add",
          edit: "/monitor/monitorAlarmConfig/edit",
        },
        userIds: [],
        userNames: [],
        userIdCards: [],
        userTypes: [],
        selectedUser: [],
        selectAlarmType: '',
        selectAlarmRuleType: '',
        nameKey: {
          10: 'xingMing',
          20: 'dataId'
        }
      }
    },
    created() {
    },
    methods: {
      handleChangeAlarmRuleType(v) {
        this.selectAlarmRuleType = v
      },
      handleChangeAlarmType(v) {
        this.userIds = ''
        this.selectedUser = []
        this.selectAlarmType = v
      },
      validateType(rule, value, callback) {
        if (this.selectAlarmType === '10' && value === '30') {
          callback('人员不可选择模糊匹配')
        } else {
          callback()
        }

      },
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
      },
      choseUserList(userList) {
        this.selectedUser = [];
        this.userIds = '';
        for (let i = 0; i < userList.length; i++) {
          this.selectedUser.push(userList[i][this.nameKey[this.selectAlarmType]]);
        }
        this.userIds += userList.map(item => item.id).join(",")
        this.userNames += userList.map(item => item.xingMing).join(",")
        this.userIdCards += userList.map(item => item.sfzh).join(",")
        this.userTypes += userList.map(item => item.type).join(",")
        this.model.dataId = this.userIds
        this.model.title = this.userNames
        this.model.prop1 = this.userIdCards
        this.model.prop2 = this.userTypes
      },
      queryUserById(id) {
        let url = ''
        if(this.selectAlarmType === '10'){
          url = this.url.getUserById
        }else if(this.selectAlarmType === '20'){
          //url = this.url.getCarById
          this.model.dataId = id
          console.log(id)
          this.form.setFieldsValue(pick(this.model, 'dataId'))
          return
        }
        getAction(url,{id}).then(res => {
          if(res.success){
            this.selectedUser = [res.result[this.nameKey[this.selectAlarmType]]]
          }else{
            this.selectedUser = []
          }
        })
      },
      add() {
        this.edit({
          status: 'N'
        });
      },
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.userIds = record.dataId
        this.selectAlarmType = record.alarmType
        this.selectAlarmRuleType = record.alarmRuleType
        this.queryUserById(record.dataId)

        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'title', 'alarmType', 'alarmRuleType', 'alarmTimeConfig', 'status','intervalDays'))
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
        this.form.setFieldsValue(pick(row, 'title', 'alarmType', 'alarmRuleType', 'alarmTimeConfig', 'status','intervalDays'))
      },


    }
  }
</script>