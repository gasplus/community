<template>
  <!--  <div class="card1" :style="'left:'+position.left+';top:'+position.top" @mouseleave="leave">-->
  <div class="card1">
    <div class="mask"></div>
    <div class="card1_bg">
      <div class="card1_bg_l_t"></div>
      <div class="card1_bg_l_c"></div>
      <div class="card1_bg_l_b"></div>
      <div class="card1_bg_r_t"></div>
      <div class="card1_bg_r_c"></div>
      <div class="card1_bg_r_b"></div>
      <div class="card1_bg_t_c"></div>
      <div class="card1_bg_b_c"></div>
      <div class="card1_bg_c"></div>
    </div>
    <div class="close_btn" @click="leave"></div>
    <div class="card1_body">
      <div style="position:absolute;left:20px;right:20px;top:20px;bottom:36px;">
        <div class="device_video">
          <iframe ref="videoIframe" :src="videoIframeUrl" frameborder="0" style="width:935px;height:525px;"></iframe>
        </div>
      </div>
      <div class="device_id">
        {{deviceId}}
      </div>
    </div>
  </div>
</template>

<script>

  import videojs from 'video.js'
  import 'videojs-contrib-hls'

  import {
    getDeviceList,
    getVideoUrlConfig,
    setVideo
  } from "@/api/big"

  export default {
    name: "deviceDetail",
    data() {
      return {
        deviceId: '',
        videoIframeUrl: 'http://20.36.24.110:19888/video.html'
      }
    },
    mounted() {
    },
    methods: {
      // handleTableChange(pagination, filters, sorter) {
      //   console.log(pagination);
      //   const pager = { ...this.pagination };
      //   pager.current = pagination.current;
      //   this.pagination = pager;
      //   this.getDeviceList({
      //     size: pagination.pageSize,
      //     current: pagination.current
      //   });
      // },
      showVideo(deviceId) {
        this.deviceId = deviceId
        const params = {
          deviceId: deviceId
        }
        getVideoUrlConfig(params).then(rel => {
          const setting = rel.result
          this.$refs.videoIframe.contentWindow.postMessage({
            funcName:'changeVideo',
            setting: setting
          },'*');
        })
        // this.videoUrl = 'http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4'
      },
      leave() {
        this.$emit('leave')
      }
    }
  }
</script>

<style scoped>
  .scroll_body::-webkit-scrollbar {
    /*滚动条整体样式*/
    width : 6px;  /*高宽分别对应横竖滚动条的尺寸*/
    height: 1px;
  }
  .scroll_body::-webkit-scrollbar-thumb {
    /*滚动条里面小方块*/
    border-radius   : 6px;
    background-color: #0F80D4;
    background-image: -webkit-linear-gradient(
      45deg,
        /*rgba(255, 255, 255, 0.5) 25%,*/
      transparent 25%,
      transparent 50%,
        /*rgba(255, 255, 255, 0.5) 50%,*/
        /*rgba(255, 255, 255, 0.5) 75%,*/
      transparent 75%,
      transparent
    );
  }
  .scroll_body::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    box-shadow   : inset 0 0 3px rgba(0, 0, 0, 0.2);
    background   : #0D1B61;
    border-radius: 6px;
  }
  .mask {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 0;
    background: rgba(0, 0, 0, 0.6);
  }

  .card1 * {
    box-sizing: content-box;
  }

  .close_btn {
    position: absolute;
    top: 5px;
    right: 10px;
    width: 20px;
    height: 20px;
    cursor: pointer;
    z-index: 1000;
    background-image: url("~@/assets/images/icon_close.png");
    background-repeat: no-repeat;
    background-position: 0 0;
    background-size: 100% 100%;
    opacity: 0.8;
  }

  .card1 {
    position: absolute;
    width: 980px;
    height: 582px;
    top: 50%;
    left: 50%;
    margin-left: -380px;
    margin-top: -291px;
    /*margin-top:-210px;*/
    /*margin-left:-325px;*/
    z-index: 4;
  }

  .card1_bg {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    overflow: hidden;
  }

  .card1_bg_l_t {
    position: absolute;
    left: 0;
    top: 0;
    width: 248px;
    height: 175px;
    background-image: url("~@/assets/images/bg-l_t.png");
    background-position: 0 0;
    background-size: 248px 175px;
    background-repeat: no-repeat;
  }

  .card1_bg_l_c {
    position: absolute;
    left: 0;
    top: 175px;
    bottom: 175px;
    width: 248px;
    background-image: url("~@/assets/images/bg-l_c.png");
    background-position: 0 0;
    background-size: 248px 1px;
    background-repeat: repeat-y;
  }

  .card1_bg_l_b {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 248px;
    height: 175px;
    background-image: url("~@/assets/images/bg-l_b.png");
    background-position: 0 0;
    background-size: 248px 175px;
    background-repeat: no-repeat;
  }

  .card1_bg_r_t {
    position: absolute;
    right: 0;
    top: 0;
    width: 248px;
    height: 175px;
    background-image: url("~@/assets/images/bg-r_t.png");
    background-position: 0 0;
    background-size: 248px 175px;
    background-repeat: no-repeat;
  }

  .card1_bg_r_c {
    position: absolute;
    right: 0;
    top: 175px;
    bottom: 175px;
    width: 248px;
    background-image: url("~@/assets/images/bg-r_c.png");
    background-position: 0 0;
    background-size: 248px 1px;
    background-repeat: repeat-y;
  }

  .card1_bg_r_b {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 248px;
    height: 175px;
    background-image: url("~@/assets/images/bg-r_b.png");
    background-position: 0 0;
    background-size: 248px 175px;
    background-repeat: no-repeat;
  }

  .card1_bg_t_c {
    position: absolute;
    right: 248px;
    left: 248px;
    top: 0;
    height: 175px;
    background-image: url("~@/assets/images/bg-t_c.png");
    background-position: 0 0;
    background-size: 1px 175px;
    background-repeat: repeat-x;
  }

  .card1_bg_b_c {
    position: absolute;
    right: 248px;
    left: 248px;
    bottom: 0;
    height: 175px;
    background-image: url("~@/assets/images/bg-b_c.png");
    background-position: 0 0;
    background-size: 1px 175px;
    background-repeat: repeat-x;
  }

  .card1_bg_c {
    position: absolute;
    right: 248px;
    top: 175px;
    left: 248px;
    bottom: 175px;
    background-image: url("~@/assets/images/bg-c.png");
    background-position: 0 0;
    background-size: 153px 78px;
    background-repeat: repeat;
  }

  .card1_body {
  }
  .device_list_box{
    position:absolute;
    left:0;
    top:0;
    width:340px;
    bottom:0;
    overflow: auto;
    background:rgba(255,255,255,1);
  }
  .device_video{
    position:absolute;
    left:0;
    top:0;
    width:935px;
    height:525px;
    overflow: hidden;
    background:rgba(255,255,255,0.1);
  }
  .device_id{
    position:absolute;
    left:220px;
    bottom:0;
    right:220px;
    line-height: 40px;
    height:40px;
    font-size:20px;
    color:#fff;
    text-align: center;
  }
  .device_video video{
    width:935px;
    height:525px;
  }
</style>