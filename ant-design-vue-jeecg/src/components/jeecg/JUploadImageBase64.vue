<template>
  <div>
    <a-upload
      name="file"
      :multiple="false"
      :fileList="fileList"
      list-type="picture"
      :showUploadList="false"
      :beforeUpload="beforeUpload"
      @change="handleChange"
      :disabled="disabled">
      <div>
        <a-button>
          <a-icon type="upload" />{{ text }}
        </a-button>
      </div>
    </a-upload>
    <a-card hoverable style="width: 240px"
            :key="ind"
            v-for="(item,ind) in imgList">
        <img
          slot="cover"
          alt="example"
          :src="item"
        />
    </a-card>
  </div>
</template>

<script>

  function getBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }
  import Vue from 'vue'
  import Viewer from 'viewerjs'
  import { ACCESS_TOKEN } from "@/store/mutation-types"

  const FILE_TYPE_ALL = "all"
  const FILE_TYPE_IMG = "image"
  const FILE_TYPE_TXT = "file"
  const uidGenerator=()=>{
    return '-'+parseInt(Math.random()*10000+1,10);
  }
  const getFileName=(path)=>{
    if(path.lastIndexOf("\\")>=0){
      let reg=new RegExp("\\\\","g");
      path = path.replace(reg,"/");
    }
    return path.substring(path.lastIndexOf("/")+1);
  }
  export default {
    name: 'JUpload',
    data(){
      return {
        previewVisible: false,
        previewImage: '',
        uploadAction:window._CONFIG['domianURL']+"/sys/common/upload",
        urlDownload:window._CONFIG['domianURL'] + "/sys/common/download/",
        headers:{},
        imgList: [],
        fileList: []
      }
    },
    props:{
      multiple:{
        type:Boolean,
        required:false,
        default:true
      },
      text:{
        type:String,
        required:false,
        default:"点击上传"
      },
      fileType:{
        type:String,
        required:false,
        default:FILE_TYPE_IMG
      },
      /*这个属性用于控制文件上传的业务路径*/
      bizPath:{
        type:String,
        required:false,
        default:"temp"
      },
      value:{
        type:String,
        required:false
      },
      // update-begin- --- author:wangshuai ------ date:20190929 ---- for:Jupload组件增加是否能够点击
      disabled:{
        type:Boolean,
        required:false,
        default: false
      },
      // update-end- --- author:wangshuai ------ date:20190929 ---- for:Jupload组件增加是否能够点击
      //此属性被废弃了
      triggerChange:{
        type: Boolean,
        required: false,
        default: false
      },
    },
    watch:{
      value(val){
        console.log(val)
        this.initFileList(val)
      }
    },
    created(){
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}
    },

    methods:{
      handleCancel() {
        this.previewVisible = false;
      },
      async handlePreview(file) {
        if (!file.url && !file.preview) {
          file.preview = await getBase64(file.originFileObj);
        }
        this.previewImage = file.url || file.preview;
        this.previewVisible = true;
      },
      initFileList(paths){
        console.log(paths)
        if(!paths){
          this.imgList = []
          this.fileList = []
        }
        // console.log('initFileList')
        // if(!paths || paths.length==0){
        //   //return [];
        //   // update-begin- --- author:os_chengtgen ------ date:20190729 ---- for:issues:326,Jupload组件初始化bug
        //   this.fileList = [];
        //   return;
        //   // update-end- --- author:os_chengtgen ------ date:20190729 ---- for:issues:326,Jupload组件初始化bug
        // }
        // let fileList = [];
        // let arr = paths.split(",")
        // for(var a=0;a<arr.length;a++){
        //   fileList.push({
        //     uid:uidGenerator(),
        //     name:getFileName(arr[a]),
        //     status: 'done',
        //     url: this.urlDownload+arr[a],
        //     response:{
        //       status:"history",
        //       message:arr[a]
        //     }
        //   })
        // }
        // this.fileList = fileList
      },
      async handlePreview(file) {
        if (!file.url && !file.preview) {
          file.preview = await getBase64(file.originFileObj);
        }
        this.previewImage = file.url || file.preview;
        this.previewVisible = true;
      },
      async handlePathChange(){
        if(this.fileList.length === 0){
          this.imgList = []
          this.$emit('change', '');
        } else {
          const file = this.fileList[0]
          const url = await getBase64(file.originFileObj);
          this.imgList = [url]
          console.log(url)
          this.$emit('change', url);
        }

      },
      beforeUpload(file){
        var fileType = file.type;
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 扩展功能验证文件大小
        this.$forceUpdate()
        return false
      },
      handleChange(info) {
        console.log("--文件列表改变--",info)
        let fileList = info.fileList
        // if(info.file.status==='done'){
        //   if(info.file.response.success){
        //     fileList = fileList.map((file) => {
        //       if (file.response) {
        //         file.url = this.urlDownload+file.response.message;
        //       }
        //       return file;
        //     });
        //   }
        //   this.$message.success(`${info.file.name} 上传成功!`);
        // }else if (info.file.status === 'error') {
        //   this.$message.error(`${info.file.name} 上传失败.`);
        // }else if(info.file.status === 'removed'){
        //   this.handleDelete(info.file)
        // }
        this.fileList = fileList.filter((item,ind) => ind === fileList.length-1)
        this.handlePathChange()
        // if(info.file.status==='done' || info.file.status === 'removed'){
        //   this.handlePathChange()
        // }
      },
      handleDelete(file){
        //如有需要新增 删除逻辑
        console.log(file)
      },
    },
    model: {
      prop: 'value',
      event: 'change'
    }
  }
</script>

<style scoped>

</style>