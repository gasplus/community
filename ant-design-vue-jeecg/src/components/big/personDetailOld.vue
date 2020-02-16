<template>
  <div class="card1" @mouseleave="leave">
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
    <div class="card1_body">
      <div class="person_card_box">
        <div class="body_result">
          <div class="person_photo">
              <img v-if="bodyInfo.picture" :src="imagePath+bodyInfo.picture" alt="">
          </div>
          <div class="person_info">
            <div class="person_name">外套颜色：{{bodyInfo.coat}}</div>
            <div class="person_name">年龄：{{bodyInfo.age}}</div>
            <div class="person_name">头发：{{bodyInfo.hair}}</div>
<!--            <div class="person_type">-->
<!--              &lt;!&ndash;        <a-tag color="#2db7f5">#2db7f5</a-tag>&ndash;&gt;-->
<!--              &lt;!&ndash;        <a-tag color="#87d068">#87d068</a-tag>&ndash;&gt;-->
<!--              <a-tag color="#108ee9">重点人员</a-tag>-->
<!--              <a-tag color="#f50">关怀人员</a-tag>-->
<!--            </div>-->
          </div>
        </div>
        <div class="face_result">
          <div class="person_photo">
            <img v-if="bodyInfo.picture" :src="imagePath+faceInfo.picture" alt="">
          </div>
          <div class="person_info">
            <div class="person_name">性别：{{faceInfo.gender}}</div>
            <div class="person_name">年龄：{{faceInfo.age}}</div>
            <div class="person_name">面部遮盖：{{faceInfo.mask}}</div>
          </div>
        </div>

<!--        <div class="person_other">-->
<!--          <div class="person_birthday">1988-08-08</div>-->
<!--          <div class="person_address">123</div>-->
<!--        </div>-->
      </div>

    </div>
  </div>
</template>

<script>
    export default {
        name: "personDetail",
        props: ['personData', 'bodyInfo', 'faceInfo'],
        data() {
          return {
            photoUrl: '',
            imagePath: window._CONFIG['imgDomainURL'] + '/',
            loudongData: {
              ld: '',
              childrenCount: 0,
              danYuan: [],
              keyPersonCount: 0,
              oldPersonCount:0,
              totalCount: 0,
              zcCount: 0
            },
            currentIndex: undefined,
            danyuanMap: {
              1: '一',
              2: '二',
              3: '三',
              4: '四',
              5: '五',
              6: '六',
              7: '七',
              8: '八',
              9: '九',
              10: '十'
            }
          }
        },
        methods: {
          changeDY(index) {
            this.currentIndex = index;
          },
          setLouDongData(data) {
            for(let key in data) {
              this.loudongData[key] = data[key];
            }
            if(this.loudongData.danYuan && this.loudongData.danYuan.length > 0) {
              this.currentIndex = 0;
            } else {
              this.currentIndex = undefined;
            }
            console.log(this.currentIndex)
          },
          leave() {
            this.$emit('leave')
          }
        }
    }
</script>

<style scoped>
  .card1 *{
    box-sizing:content-box;
  }
  .card1{
    position:relative;
    width:400px;
    min-height:200px;
    /*margin-top:-210px;*/
    /*margin-left:-325px;*/
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
    width:200px;
    height:100px;
    background-image: url("~@/assets/images/bg-l_t.png");
    background-position:0 0;
    background-size:248px 175px;
    background-repeat: no-repeat;
  }
  .card1_bg_l_c{
    position:absolute;
    left:0;
    top:100px;
    bottom:100px;
    width:200px;
    background-image: url("~@/assets/images/bg-l_c.png");
    background-position:0 0;
    background-size:248px 1px;
    background-repeat: repeat-y;
  }
  .card1_bg_l_b{
    position:absolute;
    left:0;
    bottom:0;
    width:200px;
    height:100px;
    background-image: url("~@/assets/images/bg-l_b.png");
    background-position:left 0 bottom -23px;
    background-size:248px 175px;
    background-repeat: no-repeat;
  }
  .card1_bg_r_t{
    position:absolute;
    right:0;
    top:0;
    width:200px;
    height:100px;
    background-image: url("~@/assets/images/bg-r_t.png");
    background-position:right 0 top 0;
    background-size:248px 175px;
    background-repeat: no-repeat;
  }
  .card1_bg_r_c{
    position:absolute;
    right:0;
    top:100px;
    bottom:100px;
    width:200px;
    background-image: url("~@/assets/images/bg-r_c.png");
    background-position:right 0 top 0;
    background-size:248px 1px;
    background-repeat: repeat-y;
  }
  .card1_bg_r_b{
    position:absolute;
    right:0;
    bottom:0;
    width:200px;
    height:100px;
    background-image: url("~@/assets/images/bg-r_b.png");
    background-position:right 0 bottom -23px;
    background-size:248px 175px;
    background-repeat: no-repeat;
  }

  .card1_bg_t_c{
    position:absolute;
    right:200px;
    left:200px;
    top:0;
    height:175px;
    background-image: url("~@/assets/images/bg-t_c.png");
    background-position:0 0;
    background-size:1px 175px;
    background-repeat: repeat-x;
  }
  .card1_bg_b_c{
    position:absolute;
    right:200px;
    left:200px;
    bottom:0;
    height:175px;
    background-image: url("~@/assets/images/bg-b_c.png");
    background-position:0 0;
    background-size:1px 175px;
    background-repeat: repeat-x;
  }
  .card1_bg_c{
    position:absolute;
    right:200px;
    top:175px;
    left:200px;
    bottom:175px;
    background-image: url("~@/assets/images/bg-c.png");
    background-position:0 0;
    background-size:153px 78px;
    background-repeat: repeat;
  }


  .card1_body{
    position:relative;
    padding:10px 20px;
    z-index:100;
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


  .card1_body::-webkit-scrollbar {
    /*滚动条整体样式*/
    width : 6px;  /*高宽分别对应横竖滚动条的尺寸*/
    height: 1px;
  }
  .card1_body::-webkit-scrollbar-thumb {
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
  .card1_body::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    box-shadow   : inset 0 0 3px rgba(0, 0, 0, 0.2);
    background   : #0D1B61;
    border-radius: 6px;
  }
  .person_photo{
    width:100px;
    height:100px;
    border-radius: 10px;
    overflow: hidden;
    position:absolute;
    left:10px;
    top:10px;
    background:rgba(255,255,255,0.2)
  }
  .person_photo img{
    width:100px;
    height:100px;
  }
  .person_name{
    height:32px;
    line-height: 32px;
    color:#fff;
    font-size:14px;
    font-weight: bold;
  }
  .person_type{
    height:30px;
    line-height: 30px;
  }
  .person_birthday{
    height:30px;
    line-height: 30px;
    padding-left:40px;
    background-image: url("~@/assets/images/icon_person_card.png");
    background-size:22px 16px;
    background-position:10px center;
    background-repeat: no-repeat;
    color:#fff;
    font-size:14px;
    position:relative;
    margin-top:15px;
  }
  .person_info{
    padding-left:130px;
    height:100px;
    padding-top:10px;
  }
  .person_address{
    line-height: 30px;
    padding-left:40px;
    padding-right:30px;
    background-image: url("~@/assets/images/icon_person_address.png");
    background-size:16px 16px;
    background-position:13px 7px;
    background-repeat: no-repeat;
    color:#fff;
    font-size:14px;
    position:relative;
  }

  .person_card_box{
    position:relative;
  }
  .body_result{
    position:relative;
    height:120px;
    border-bottom:1px dashed rgba(255,255,255,0.2);
  }
  .face_result{
    position:relative;
    height:120px;
  }

</style>