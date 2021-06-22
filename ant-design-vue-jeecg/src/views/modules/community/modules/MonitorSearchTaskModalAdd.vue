<template>
  <a-modal
    title="发起以图搜人"
    :width="1000"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :okButtonProps="okSetting"
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

        <!--
                <a-form-item label="搜索标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <a-input v-decorator="[ 'searchTitle', validatorRules.searchTitle]" placeholder="请输入搜索标题"></a-input>
                </a-form-item>-->
        <a-form-item label="搜索图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
<!--          <JUploadImageBase64-->
<!--            v-decorator="[ 'image', validatorRules.image]"-->
<!--            :trigger-change="true"></JUploadImageBase64>-->
          <img :src="base64Img" v-if="base64Img" style="width:100px;" alt="">
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import {httpAction, getAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JUpload from '@/components/jeecg/JUpload'
  import MonitorSearchResultList from '../MonitorSearchResultList'
  import JUploadImageBase64 from '@/components/jeecg/JUploadImageBase64'

  export default {
    name: "MonitorSearchTaskModal",
    components: {
      JDate,
      JUpload,
      MonitorSearchResultList,
      JUploadImageBase64,
    },
    props: {
      searchType: {
        type: String,
        default() {
          return ''
        }
      },
      personRecord: {
        type: Object,
        default() {
          return {
            personId: '',
            photoUrl: ''
          }
        }
      },
      personId: {
        type: String,
        default() {
          return ''
        }
      },
      showFlag: {
        type: Boolean,
        default() {
          return false
        }
      },
      searchType: ''
    },
    watch: {
      showFlag(v) {
        if (v) {
          this.timeType = ''
          this.form.resetFields();
          this.model = {};
          this.initData()
          this.visible = true;
        }
      }
    },
    created() {
      console.log(this.showFlag)
      console.log(this.personRecord)

    },
    data() {
      return {
        statusMap: {
          10: '搜索中',
          20: '搜索中',
          30: '已完成'
        },
        okSetting: {
          props: {
            disabled: false
          }
        },
        timeType: '',
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
        time: 0,
        timeInterval: undefined,
        confirmLoading: false,
        validatorRules: {
          group_id_list: {rules: [{required: true, message: '请选择百度分组!'}]},
          image: {rules: [{required: true, message: '请选择搜索图片!'}]},
        },
        searchStatus: '',
        url: {
          getStatus: "/monitor/monitorSearchTask/queryById",
          add: "/monitor/monitorSearchTask/add",
          edit: "/monitor/monitorSearchTask/edit",
          addBaiduSearchTask:"/monitor/monitorSearchTask/addBaiduSearchTask",
          baiduGroup: "/monitor/monitorSearchTask/getGroupList"
        },
        baiduGroupList: [],
        selectGroupList: [],
        limitGroupCount: 10,
        base64Img: ''
      }
    },
    mounted() {

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
      // 将图片转换为Base64
      imageUrlToBase64(img) {
        return  new Promise((resolve, reject) => {
          // 一定要设置为let，不然图片不显示
          let image = new Image();
          // 解决跨域问题
          image.setAttribute('crossOrigin', 'anonymous');
          let imageUrl = img;
          image.src = imageUrl;
          // image.onload为异步加载
          image.onload = () => {
            const resultUrl = this.getImage(image);
            resolve(resultUrl)
          };
        })
      },
      getImage(image) {
        let canvas = document.createElement('canvas');
        canvas.width = image.width;
        canvas.height = image.height;
        let context = canvas.getContext('2d');
        context.drawImage(image, 0, 0, image.width, image.height);
        let quality = 1;
        // 这里的dataurl就是base64类型
        let dataURL = canvas.toDataURL('image/png', quality);
        return dataURL
      },
      initData() {
        if(this.personRecord.photoUrl) {
          this.imageUrlToBase64(this.personRecord.photoUrl).then(res => {
            this.base64Img = res
          })
        }
        // this.imageUrlToBase64('/manager.png').then(res => {
        //   this.model.image = res
        //   console.log(res)
        //   this.base64Img = res
        // })
      },
      getResultCount() {
        if (this.$refs.resultList) {
          return this.$refs.resultList.ipagination.total
        }
        return 0
      },
      getImgViewRecord(text) {
        // if (text && text.indexOf(",") > 0) {
        //   text = text.substring(0, text.indexOf(","))
        // }
        return window._CONFIG['imgDomainRecordURL'] + text
      },
      close() {
        this.base64Img = ''
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        if(!this.base64Img){
          that.$message.warning('当前记录没有监控照片，无法发起搜索');
          return
        }

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
              image: this.base64Img
            }
            console.log("表单提交数据", params)
            httpAction(httpurl, params, method).then((res) => {
              if (res.success) {
                that.$message.success('添加成功');
                that.$emit('ok');
                this.base64Img = ''
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              //that.close();
              this.$emit('handleCancel')
            })
          }

        })
      },
      handleCancel() {
        this.initData()
        this.close()
        this.$emit('handleCancel')
      },


    }
  }
</script>
<style>
  .result_list .ant-card-body {
    padding: 0;
  }
</style>