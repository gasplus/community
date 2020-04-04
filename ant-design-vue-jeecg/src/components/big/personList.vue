<template>
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

    <div class="close_btn" @click="close"></div>
    <div class="card1_body">
      <div class="room_box">
        <div class="room_title">
          {{roomData.fangJianHao}}室
        </div>
        <div class="room_person_list">
          <div class="room_person_item" v-for="(item,index) in personListData" :key="index">
            <div class="room_person_bg"></div>
            <div class="room_person_photo">
<!--              <viewer :images="['https://img-blog.csdn.net/20180831102732255?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d1b3poYW5ncWlhbmc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70']">-->
<!--                <img src="https://img-blog.csdn.net/20180831102732255?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d1b3poYW5ncWlhbmc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70" alt="">-->
<!--              </viewer>-->
              <viewer :images="[imagePath + item.zhaoPian]">
                <img :src="imagePath + item.zhaoPian" alt="">
              </viewer>
            </div>
            <div class="room_person_body">
<!--              户籍、出生日期、身份证号、电话、标签、车辆-->
              <a-row>
                <a-col :span="8">
                  <div class="room_person_info r_l">
                    <div class="room_person_info_label">姓名</div>
                    <div class="room_person_info_content">{{item.xingMing}}</div>
                  </div>
                </a-col>
                <a-col :span="8">
                  <div class="room_person_info r_l">

                    <div class="room_person_info_label">性别</div>
                    <div class="room_person_info_content">{{item.xingBie}}</div>
                  </div>
                </a-col>
                <a-col :span="8">
                  <div class="room_person_info">
                    <div class="room_person_info_label">年龄</div>
                    <div class="room_person_info_content">{{item.age}}</div>
                  </div>
                </a-col>
                <a-col :span="8">
                  <div class="room_person_info r_l">

                    <div class="room_person_info_label">关系</div>
                    <div class="room_person_info_content">{{item.guanXi}}</div>
                  </div>
                </a-col>

                <a-col :span="16">
                  <div class="room_person_info">
                    <div class="room_person_info_label">身份证号</div>
                    <div class="room_person_info_content">
                      {{item.sfzh}}
                    </div>
                  </div>
                </a-col>
                <a-col :span="8">
                  <div class="room_person_info r_l">
                    <div class="room_person_info_label">民族</div>
                    <div class="room_person_info_content">{{item.minZu}}</div>
                  </div>
                </a-col>
                <a-col :span="16">
                  <div class="room_person_info">
                    <div class="room_person_info_label">电话</div>
                    <div class="room_person_info_content">{{item.lxdh}}</div>
                  </div>
                </a-col>

                <a-col :span="8">
                  <div class="room_person_info" style="padding: 5px 20px;border-right:none;">
                      <!--                      <a-tag color="#f50" v-if="item.type.indexOf('A01A04')>=0">{{personKeyMap[item.type]}}</a-tag>-->
                      <!--                      <a-tag color="#87d068" v-if="item.type === 'A01A01'">{{personKeyMap[item.type]}}</a-tag>-->
                      <!--                      <a-tag color="#2db7f5" v-if="item.type === 'A01A02'">{{personKeyMap[item.type]}}</a-tag>-->
                      <!--                      <a-tag color="#108ee9" v-if="item.type === 'A01A03'">{{personKeyMap[item.type]}}</a-tag>-->
                      <a-tag color="#2db7f5">{{item.typeText}}</a-tag>
                      <a-tag color="#87d068">{{item.rkxz}}</a-tag>
                  </div>
                </a-col>
                <a-col :span="16">
                  <div class="room_person_info" style="padding: 5px 20px;text-align: right;border-left:none;">
                    <a-tag color="#2db7f5" @click="drawLine(item)">查看轨迹</a-tag>
                  </div>
                </a-col>
              </a-row>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "personList",
        props:['roomData','personListData'],
        data() {
          return {
            imagePath: window._CONFIG['imgDomainURL'] + '/',
            personList: [],
            personKeyMap:{
              'A01A04': '重点关注人口',
              'A01A04A03': '刑满释放',
              'A01A04A02': '精神病人',
              'A01A04A01': '五类外执人员',
              'A01A03': '重点人口',
              'A01A02': '流动人口',
              'A01A01': '常住人口'
            },
          }
        },
        methods: {
          drawLine(item) {
            item.personId = item.id
            this.$emit('close')
            this.$emit('drawLine', item)
          },
          changeDY(index) {
            this.currentIndex = index
          },
          close() {
            this.$emit('close')
          }
        }
    }
</script>

<style scoped>
  .card1 *{
    box-sizing:content-box;
  }
  .close_btn{
    position:absolute;
    top:5px;
    right:10px;
    width:20px;
    height:20px;
    cursor: pointer;
    z-index: 1000;
    background-image: url("~@/assets/images/icon_close.png");
    background-repeat: no-repeat;
    background-position: 0 0;
    background-size: 100% 100%;
    opacity: 0.8;
  }
  .card1{
    position:absolute;
    width:700px;
    left:50%;
    top:50%;
    min-height:500px;
    margin-top:-250px;
    margin-left:-140px;
    z-index: 3;
  }
  .card1_bg{
    position:absolute;
    left:0;
    right:0;
    top:0;
    bottom:0;
    overflow: hidden;
  }
  .card1_bg_l_t{
    position:absolute;
    left:0;
    top:0;
    width:248px;
    height:175px;
    background-image: url("~@/assets/images/bg-l_t.png");
    background-position:0 0;
    background-size:248px 175px;
    background-repeat: no-repeat;
  }
  .card1_bg_l_c{
    position:absolute;
    left:0;
    top:175px;
    bottom:175px;
    width:248px;
    background-image: url("~@/assets/images/bg-l_c.png");
    background-position:0 0;
    background-size:248px 1px;
    background-repeat: repeat-y;
  }
  .card1_bg_l_b{
    position:absolute;
    left:0;
    bottom:0;
    width:248px;
    height:175px;
    background-image: url("~@/assets/images/bg-l_b.png");
    background-position:0 0;
    background-size:248px 175px;
    background-repeat: no-repeat;
  }
  .card1_bg_r_t{
    position:absolute;
    right:0;
    top:0;
    width:248px;
    height:175px;
    background-image: url("~@/assets/images/bg-r_t.png");
    background-position:0 0;
    background-size:248px 175px;
    background-repeat: no-repeat;
  }
  .card1_bg_r_c{
    position:absolute;
    right:0;
    top:175px;
    bottom:175px;
    width:248px;
    background-image: url("~@/assets/images/bg-r_c.png");
    background-position:0 0;
    background-size:248px 1px;
    background-repeat: repeat-y;
  }
  .card1_bg_r_b{
    position:absolute;
    right:0;
    bottom:0;
    width:248px;
    height:175px;
    background-image: url("~@/assets/images/bg-r_b.png");
    background-position:0 0;
    background-size:248px 175px;
    background-repeat: no-repeat;
  }

  .card1_bg_t_c{
    position:absolute;
    right:248px;
    left:248px;
    top:0;
    height:175px;
    background-image: url("~@/assets/images/bg-t_c.png");
    background-position:0 0;
    background-size:1px 175px;
    background-repeat: repeat-x;
  }
  .card1_bg_b_c{
    position:absolute;
    right:248px;
    left:248px;
    bottom:0;
    height:175px;
    background-image: url("~@/assets/images/bg-b_c.png");
    background-position:0 0;
    background-size:1px 175px;
    background-repeat: repeat-x;
  }
  .card1_bg_c{
    position:absolute;
    right:248px;
    top:175px;
    left:248px;
    bottom:175px;
    background-image: url("~@/assets/images/bg-c.png");
    background-position:0 0;
    background-size:153px 78px;
    background-repeat: repeat;
  }

  .card1_body{
    position:absolute;
    top:20px;
    left:20px;
    right:20px;
    bottom:20px;
  }


  .card2{
    position:absolute;
    top: 65px;
    bottom:38px;
    left:185px;
    right:30px;
  }
  .card2_bg{
    position:absolute;
    left:0;
    right:0;
    top:0;
    bottom:0;
    overflow: hidden;
  }
  .card2_bg_l_t{
    position:absolute;
    left:0;
    top:0;
    width:30px;
    height:30px;
    background-image: url("~@/assets/images/card_jiao.png");
    background-position:0 0;
    background-size:30px 30px;
    background-repeat: no-repeat;
  }
  .card2_bg_l_c{
    position:absolute;
    left:0;
    top:30px;
    bottom:30px;
    width:30px;
    background-image: url("~@/assets/images/card_bian2.png");
    background-position:0 0;
    background-size:30px 1px;
    background-repeat: repeat-y;
  }
  .card2_bg_l_b{
    position:absolute;
    left:0;
    bottom:0;
    width:30px;
    height:30px;
    background-image: url("~@/assets/images/card_jiao.png");
    background-position:0 0;
    background-size:30px 30px;
    background-repeat: no-repeat;
    -moz-transform: scaleY(-1);
    -webkit-transform: scaleY(-1);
    -o-transform: scaleY(-1);
    transform: scaleY(-1);
  }
  .card2_bg_r_t{
    position:absolute;
    right:0;
    top:0;
    width:30px;
    height:30px;
    background-image: url("~@/assets/images/card_jiao.png");
    background-position:0 0;
    background-size:30px 30px;
    background-repeat: no-repeat;

    -moz-transform: scaleX(-1);
    -webkit-transform: scaleX(-1);
    -o-transform: scaleX(-1);
    transform: scaleX(-1);

  }
  .card2_bg_r_c{
    position:absolute;
    right:0;
    top:30px;
    bottom:30px;
    width:30px;
    background-image: url("~@/assets/images/card_bian2.png");
    background-position:0 0;
    background-size:30px 1px;
    background-repeat: repeat-y;
    -moz-transform: scaleX(-1);
    -webkit-transform: scaleX(-1);
    -o-transform: scaleX(-1);
    transform: scaleX(-1);
  }
  .card2_bg_r_b{
    position:absolute;
    right:0;
    bottom:0;
    width:30px;
    height:30px;
    background-image: url("~@/assets/images/card_jiao.png");
    background-position:0 0;
    background-size:30px 30px;
    background-repeat: no-repeat;

    -moz-transform: scaleY(-1) scaleX(-1);
    -webkit-transform: scaleY(-1) scaleX(-1);
    -o-transform: scaleY(-1) scaleX(-1);
    transform: scaleY(-1) scaleX(-1);
  }

  .card2_bg_t_c{
    position:absolute;
    right:30px;
    left:30px;
    top:0;
    height:30px;
    background-image: url("~@/assets/images/card_bian1.png");
    background-position:0 0;
    background-size:1px 30px;
    background-repeat: repeat-x;
  }
  .card2_bg_b_c{
    position:absolute;
    right:30px;
    left:30px;
    bottom:0;
    height:30px;
    background-image: url("~@/assets/images/card_bian1.png");
    background-position:0 0;
    background-size:1px 30px;
    background-repeat: repeat-x;

    -moz-transform: scaleY(-1);
    -webkit-transform: scaleY(-1);
    -o-transform: scaleY(-1);
    transform: scaleY(-1);
  }
  .card2_bg_c{
    position:absolute;
    right:30px;
    top:30px;
    left:30px;
    bottom:30px;
    background-image: url("~@/assets/images/card_c.png");
    background-position:0 0;
    background-size:1px 1px;
    background-repeat: repeat;
  }

  .card2_body{
    position:absolute;
    left:10px;
    top:10px;
    right:10px;
    bottom:10px;
    overflow-y: auto;
    overflow-x: hidden;
  }
  .dialog_top_info{
    position:relative;
    padding:21px 31px 0 39px;
    line-height: 24px;
    height:24px;
    color:#fff;
  }
  .dialog_top_info_left{
    float:left;
    font-size:16px;
  }
  .dialog_top_info_left b{
    font-size:24px;
  }
  .dialog_top_info_right{
    float:right;
    font-size:14px;
  }
  .shangfang{
    padding-left:20px;
    margin-left:10px;
    float:left;
    background-image: url("~@/assets/images/dialog_person.png");
    background-position:0 center;
    background-size:16px 16px;
    background-repeat: no-repeat;
  }

  .children{
    padding-left:20px;
    margin-left:10px;
    float:left;
    background-image: url("~@/assets/images/dialog_children.png");
    background-position:0 center;
    background-size:16px 16px;
    background-repeat: no-repeat;
  }

  .keyPerson{
    padding-left:20px;
    margin-left:10px;
    float:left;
    background-image: url("~@/assets/images/dialog_key_person.png");
    background-position:0 center;
    background-size:16px 16px;
    background-repeat: no-repeat;
  }
  .gudu{
    padding-left:20px;
    margin-left:10px;
    float:left;
    background-image: url("~@/assets/images/dialog_old_person.png");
    background-position:0 center;
    background-size:16px 16px;
    background-repeat: no-repeat;
  }
  .dialog_danyuan_box{
    position:relative;
    margin:20px 24px;
    width:143px;
  }
  .dialog_danyuan_line{
    position:absolute;
    top:4px;
    left:0px;
    width:10px;
    height:89px;
  }
  .dialog_danyuan_body{
    margin-left:13px;
    position:relative;
    padding-left:2px;
    width:128px;
    margin-bottom:65px;
  }
  .dialog_danyuan_body_line{
    position:absolute;
    width:2px;
    height:100%;
    margin-top:-20px;
    background:#004C95;
  }
  .dialog_danyuan_item{
    width:91px;
    height:40px;
    margin-bottom:22px;
    background: url("~@/assets/images/dialog_card_left_item_default.png") 0 0 no-repeat;
    color:#fff;
    font-size:16px;
    line-height: 40px;
    padding-left:37px;
    text-align: center;
  }
  .dialog_danyuan_item.active,.dialog_danyuan_item:hover{
    background: url("~@/assets/images/dialog_card_left_item_active.png") 0 0 no-repeat;
    cursor: pointer;
  }
  .dialog_danyuan_item:hover{
    opacity: 0.8;
  }
  .room_list{
    position:relative;
  }
  .room_item{
    border:1px solid #0F80D4;
    border-top-width:2px;
    height:60px;
    width:88px;
    box-sizing: border-box;
    margin-right:10px;
    float:left;
    margin-bottom:20px;
  }
  .room_item_type{
    display: inline-block;
    padding:0 5px 0 10px;
    text-align: center;
    background:#0F80D4;
    color:#fff;
    font-size:14px;
    height:13px;
    position:relative;
    float:left;
  }
  .room_item_title{
    clear: both;
    height:36px;
    line-height: 36px;
    color:#fff;
    font-size:18px;
    text-align: center;
  }
  .room_item_type_r{
    position:absolute;
    width:8px;
    height:19px;
    right:-8px;
    top:0px;
    z-index: 1;
    background-image: url("~@/assets/images/tag_r.png");
    background-size:8px 19px;
    background-repeat: no-repeat;
    background-position: 0 0;
  }
  .room_item_type_b{
    position:absolute;
    height:8px;
    left:0;
    right:0;
    bottom:-8px;
    z-index: 1;
    background-image: url("~@/assets/images/tag_b.png");
    background-size:100% 8px;
    background-repeat: no-repeat;
    background-position: 0 0;
  }
  .room_item_type_name{
    line-height: 19px;
    z-index: 2;
    position:relative;
  }
  .room_item_title{
    position:relative;
    padding-top:7px;
  }


  .room_person_list::-webkit-scrollbar {
    /*滚动条整体样式*/
    width : 6px;  /*高宽分别对应横竖滚动条的尺寸*/
    height: 1px;
  }
  .room_person_list::-webkit-scrollbar-thumb {
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
  .room_person_list::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    box-shadow   : inset 0 0 3px rgba(0, 0, 0, 0.2);
    background   : #0D1B61;
    border-radius: 6px;
  }
  .room_title{
    height:40px;
    text-align: center;
    font-size:20px;
    font-weight: bold;
    color:#fff;
  }
  .room_person_list{
    position:absolute;
    top:40px;
    bottom:20px;
    left:0;
    right:0;
    overflow: auto;
  }
  .room_person_item{
    position:relative;
    min-height: 100px;
    margin-right:5px;
    margin-bottom:10px;
  }
  .room_person_bg{
    position:absolute;
    z-index: 0;
    background:#222773;
    border:1px solid #00E3FF;
    top:0;
    bottom:0;
    left:0;
    right:0;
    border-radius: 10px;
  }
  .room_person_photo{
    position:absolute;
    left:20px;
    top:10px;
    height:80px;
    width:65px;
    border:1px solid #00E3FF;
  }
  .room_person_photo img{
    width:65px;
    height:80px;
  }
  .room_person_body{
    margin-left:95px;
    margin-right:10px;
    padding:10px 0;
  }
  .room_person_info{
    position:relative;
    border: 1px solid rgba(255,255,255,0.2);
    margin: -1px 0 0 -1px;
  }
  .room_person_info_label{
    height:30px;
    line-height: 30px;
    position:absolute;
    top:0;
    left:0;
    width:70px;
    color:#fff;
    text-align: right;
    font-size:12px;
  }
  .r_l{
    border-right:1px solid rgba(255,255,255,0.2);
  }
  .room_person_info_content{
    margin-left:80px;
    padding:5px 0;
    line-height: 20px;
    color:#fff;
    font-size:12px;
    min-height:20px;
  }
  .mask{
    position:fixed;
    top:0;
    bottom:0;
    left:0;
    right:0;
    z-index: 0;
    background: rgba(0,0,0,0.6);
  }
</style>