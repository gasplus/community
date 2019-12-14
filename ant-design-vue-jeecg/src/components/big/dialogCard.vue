<template>
  <div class="card1" :style="'left:'+position.left+';top:'+position.top">
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
      <div class="dialog_top_info">
        <div class="dialog_top_info_left">当前目标：<b>18栋</b>（50人）</div>
        <div class="dialog_top_info_right">
          <div class="shangfang">上访人数：1人</div>
          <div class="gudu">孤独老人：1人</div>
        </div>
      </div>
      <div class="dialog_danyuan_box">
        <div class="dialog_danyuan_line"></div>
        <div class="dialog_danyuan_body">
          <div class="dialog_danyuan_body_line"></div>
          <div class="dialog_danyuan_item active">
            一单元
          </div>
          <div class="dialog_danyuan_item">
            二单元
          </div>
          <div class="dialog_danyuan_item">
            三单元
          </div>
          <div class="dialog_danyuan_item">
            四单元
          </div>
          <div class="dialog_danyuan_item">
            五单元
          </div>

          <!--                <div class="dialog_danyuan_item">-->
          <!--                    六单元-->
          <!--                </div>-->
          <!--                <div class="dialog_danyuan_item">-->
          <!--                    七单元-->
          <!--                </div>-->
        </div>
      </div>
      <div class="card2">
        <div class="card2_bg">
          <div class="card2_bg_l_t"></div>
          <div class="card2_bg_l_c"></div>
          <div class="card2_bg_l_b"></div>
          <div class="card2_bg_r_t"></div>
          <div class="card2_bg_r_c"></div>
          <div class="card2_bg_r_b"></div>
          <div class="card2_bg_t_c"></div>
          <div class="card2_bg_b_c"></div>
          <div class="card2_bg_c"></div>
        </div>
        <div class="card2_body">
          <div class="room_list">
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>
            <div class="room_item">
              <div class="room_item_type">
                <div class="room_item_type_name">合租</div>
                <div class="room_item_type_r"></div>
                <div class="room_item_type_b"></div>
              </div>
              <div class="room_item_title">
                101室
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: "dialogCard",
        props:['position']
    }
</script>

<style scoped>
  .card1 *{
    box-sizing:content-box;
  }
  .card1{
    position:absolute;
    left:0;
    top:0;
    width:650px;
    min-height:420px;
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
    padding-left:24px;
    margin-left:20px;
    float:left;
    background-image: url("~@/assets/images/dialog_person.png");
    background-position:0 center;
    background-size:16px 16px;
    background-repeat: no-repeat;
  }
  .gudu{
    padding-left:24px;
    margin-left:20px;
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
  .dialog_danyuan_item.active{
    background: url("~@/assets/images/dialog_card_left_item_active.png") 0 0 no-repeat;
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


  .card2_body::-webkit-scrollbar {
    /*滚动条整体样式*/
    width : 6px;  /*高宽分别对应横竖滚动条的尺寸*/
    height: 1px;
  }
  .card2_body::-webkit-scrollbar-thumb {
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
  .card2_body::-webkit-scrollbar-track {
    /*滚动条里面轨道*/
    box-shadow   : inset 0 0 3px rgba(0, 0, 0, 0.2);
    background   : #0D1B61;
    border-radius: 6px;
  }
</style>