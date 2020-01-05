<template>
  <div class="message_list scroll_body">
    <div class="message_list_item" v-for="(item, index) in list" :key="index">
      <div :class="'message_list_item_content '+messageTypeMap[item.messageType]">{{item.content}}</div>
      <div class="message_list_item_btn">
        <!--<a-tag color="#87d068">查看</a-tag>-->
        {{item.createTime}}
      </div>
    </div>
  </div>
</template>

<script>

  export default {
    name: "MessageBox",
    props: ['list'],
    data() {
      return {
        messageTypeMap: {
          1:"message_icon_msr",
          20:"message_icon_gdlr"
        }
      }
    },
    methods: {
    }
  }
</script>

<style scoped>
  .message_list {
    margin-top: -15px;
    width: 300px;
    min-height: 40px;
    max-height: 300px;
    background: rgba(44, 92, 235, 1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    position: relative;
    border-radius: 5px;
    overflow: auto;
  }

  .scroll_body::-webkit-scrollbar {
    /*滚动条整体样式*/
    width: 6px; /*高宽分别对应横竖滚动条的尺寸*/
    height: 1px;
  }

  .scroll_body::-webkit-scrollbar-thumb {
    /*滚动条里面小方块*/
    border-radius: 6px;
    background-color: #0F80D4;
    background-image: -webkit-linear-gradient(
      45deg,
        /*rgba(255, 255, 255, 0.5) 25%,*/ transparent 25%,
      transparent 50%,
        /*rgba(255, 255, 255, 0.5) 50%,*/ /*rgba(255, 255, 255, 0.5) 75%,*/ transparent 75%,
      transparent
    );
  }

  .scroll_body::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.2);
    background: #0D1B61;
    border-radius: 6px;
  }

  .message_list_item {
    min-height: 20px;
    line-height: 20px;
    padding:5px 0;
    border-bottom: 1px dashed #2eabff;
    position: relative;
  }

  .message_list_item:hover {
    background: rgba(13, 23, 97, 0.5);
    cursor: pointer;
  }

  .message_list .message_list_item:nth-last-child(1) {
    border-bottom: none;
  }

  .message_list_item_btn {
    position: absolute;
    right: 0;
    top: 50%;
    margin-top:-20px;
    font-size: 10px;
    line-height: 15px;
    padding: 5px 5px 5px 0;
    color: rgba(255, 255, 255, 0.8);
    width: 80px;
    text-align: right;
  }

  .message_list_item_content {
    position: relative;
    margin-right: 80px;
    min-height: 20px;
    line-height: 20px;
    padding:5px 0 5px 40px;
    color: #fff;
  }

  .message_icon_gdlr {
    background-image: url("~@/assets/images/icon_message_gudulaoren.png");
    background-size: 24px 24px;
    background-repeat: no-repeat;
    background-position: 8px center;
  }

  .message_icon_msr {
    background-image: url("~@/assets/images/icon_message_moshengren.png");
    background-size: 24px 24px;
    background-repeat: no-repeat;
    background-position: 8px center;
  }

  .message_icon_mscl {
    background-image: url("~@/assets/images/icon_message_moshengcheliang.png");
    background-size: 24px 24px;
    background-repeat: no-repeat;
    background-position: 8px center;
  }
</style>