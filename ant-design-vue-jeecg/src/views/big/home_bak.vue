<template>
  <div id="big_home">
    <div class="home_head">
      <div class="home_head_title">龙崮社区治安态势智慧感知中心</div>
      <div class="home_head_info">
        <div class="home_head_info_item">{{dateData.getFullYear()}}-{{dateData.getMonth()+1}}-{{dateData.getDate()}}</div>
        <div class="home_head_info_item">{{dateData.getHours()}}:{{dateData.getMinutes()}}:{{dateData.getSeconds()}}</div>
        <div class="home_head_info_item">星期{{weekMap[dateData.getDay()]}}</div>
      </div>
    </div>
    <!--<div class="home_l_t">-->
      <!--<div class="home_common_title">-->
        <!--<div class="home_common_title_info">今日人员进出统计</div>-->
      <!--</div>-->
      <!--<div class="home_common_number_box">-->
        <!--<div class="home_common_number_title">总次数</div>-->
        <!--<div class="home_common_number_count">-->
          <!--<div class="count" v-for="(item,index) in userToday.totalCount" :key="index"><span :class="'count_'+item"></span></div>-->
        <!--</div>-->
      <!--</div>-->

      <!--<div class="home_common_number_box">-->
        <!--<div class="home_common_number_title"></div>-->
        <!--<div class="home_common_number_count">-->
        <!--</div>-->
      <!--</div>-->

      <!--<div class="home_common_number_box">-->
        <!--<div class="home_common_number_title">已登记人数</div>-->
        <!--<div class="home_common_number_count">-->
          <!--<div class="count" v-for="(item,index) in userToday.normalCount" :key="index"><span :class="'count_'+item"></span></div>-->
        <!--</div>-->
      <!--</div>-->

      <!--<div class="home_common_number_box">-->
        <!--<div class="home_common_number_title">未登记人数</div>-->
        <!--<div class="home_common_number_count">-->
          <!--<div class="count" v-for="(item,index) in userToday.anonymousCount" :key="index"><span :class="'count_'+item"></span></div>-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->
    <!--<div class="home_l_b">-->
      <!--<div class="home_bottom_title">信息列表</div>-->
      <!--<div class="home_bottom_box scroll_body">-->
        <!--<a-popover  placement="right" trigger="hover" v-for="item in userList" :key="item.id">-->
          <!--<template slot="content">-->
            <!--<PersonDetail></PersonDetail>-->
          <!--</template>-->
          <!--<div class="home_bottom_item">-->
            <!--<div class="home_bottom_item_jiao1"></div>-->
            <!--<div class="home_bottom_item_jiao2"></div>-->
            <!--<div class="home_bottom_item_jiao3"></div>-->
            <!--<div class="home_bottom_item_jiao4"></div>-->
            <!--<div class="home_bottom_item_body">-->
                  <!--<div class="home_bottom_item_img">-->
                    <!--<img :src="imagePath+(item.photoUrl?item.photoUrl:'1.jpeg')" alt="">-->
                  <!--</div>-->
                  <!--<div class="home_bottom_item_info"><span>姓名：</span>{{item.personName}}</div>-->
                  <!--<div class="home_bottom_item_info"><span>地点：</span>{{item.address}}</div>-->
                  <!--&lt;!&ndash;            <div class="home_bottom_item_info"><span>方式：</span>{{item.outInType}}</div>&ndash;&gt;-->
                  <!--<div class="home_bottom_item_info"><span>时间：</span>{{item.outInTime}}</div>-->

            <!--</div>-->
          <!--</div>-->
        <!--</a-popover>-->
      <!--</div>-->
    <!--</div>-->
    <div class="home_r_t">
      <div class="home_common_title">
        <div class="home_common_title_info">今日车辆进出统计</div>
      </div>
      <div class="home_common_number_box">
        <div class="home_common_number_title">进入车辆</div>
        <div class="home_common_number_count">
          <div class="count"><span class="count_1"></span></div>
          <div class="count"><span class="count_4"></span></div>
          <div class="count"><span class="count_6"></span></div>
          <div class="count"><span class="count_0"></span></div>
        </div>
      </div>

      <div class="home_common_number_box">
        <div class="home_common_number_title">外出车辆</div>
        <div class="home_common_number_count">
          <div class="count"><span class="count_1"></span></div>
          <div class="count"><span class="count_4"></span></div>
          <div class="count"><span class="count_6"></span></div>
          <div class="count"><span class="count_0"></span></div>
        </div>
      </div>

      <div class="home_common_number_box">
        <div class="home_common_number_title">已登记车辆</div>
        <div class="home_common_number_count">
          <div class="count"><span class="count_1"></span></div>
          <div class="count"><span class="count_4"></span></div>
          <div class="count"><span class="count_6"></span></div>
          <div class="count"><span class="count_0"></span></div>
        </div>
      </div>

      <div class="home_common_number_box">
        <div class="home_common_number_title">未登记车辆</div>
        <div class="home_common_number_count">
          <div class="count"><span class="count_1"></span></div>
          <div class="count"><span class="count_4"></span></div>
          <div class="count"><span class="count_6"></span></div>
          <div class="count"><span class="count_0"></span></div>
        </div>
      </div>
    </div>
    <div class="home_r_b">
      <div class="home_bottom_title">信息列表</div>
      <div class="home_bottom_box scroll_body">

        <div class="home_bottom_item">
          <div class="home_bottom_item_jiao1"></div>
          <div class="home_bottom_item_jiao2"></div>
          <div class="home_bottom_item_jiao3"></div>
          <div class="home_bottom_item_jiao4"></div>
          <div class="home_bottom_item_body">
            <div class="home_bottom_item_img"></div>
            <div class="home_bottom_item_info"><span>车牌号：</span>鲁A962ZP</div>
            <div class="home_bottom_item_info"><span>地点：</span>1号门</div>
            <div class="home_bottom_item_info"><span>方式：</span>进入</div>
            <div class="home_bottom_item_info"><span>时间：</span>2019-11-11 22:22:22</div>
          </div>
        </div>
        <div class="home_bottom_item">
          <div class="home_bottom_item_jiao1"></div>
          <div class="home_bottom_item_jiao2"></div>
          <div class="home_bottom_item_jiao3"></div>
          <div class="home_bottom_item_jiao4"></div>
          <div class="home_bottom_item_body">
            <div class="home_bottom_item_img"></div>
            <div class="home_bottom_item_info"><span>车牌号：</span>鲁A962ZP</div>
            <div class="home_bottom_item_info"><span>地点：</span>1号门</div>
            <div class="home_bottom_item_info"><span>方式：</span>进入</div>
            <div class="home_bottom_item_info"><span>时间：</span>2019-11-11 22:22:22</div>
          </div>
        </div>
        <div class="home_bottom_item">
          <div class="home_bottom_item_jiao1"></div>
          <div class="home_bottom_item_jiao2"></div>
          <div class="home_bottom_item_jiao3"></div>
          <div class="home_bottom_item_jiao4"></div>
          <div class="home_bottom_item_body">
            <div class="home_bottom_item_img"></div>
            <div class="home_bottom_item_info"><span>车牌号：</span>鲁A962ZP</div>
            <div class="home_bottom_item_info"><span>地点：</span>1号门</div>
            <div class="home_bottom_item_info"><span>方式：</span>进入</div>
            <div class="home_bottom_item_info"><span>时间：</span>2019-11-11 22:22:22</div>
          </div>
        </div>
      </div>
    </div>
    <div class="home_c" ref="center" @mouseleave="leave">
      <div class="home_c_l_t"></div>
      <div class="home_c_r_t"></div>

      <div class="home_c_line_l_b"></div>
      <div class="home_c_line_b"></div>
      <div class="home_c_line_r_b"></div>
      <div class="home_tongji_list">
        <div class="home_tongji_item" v-if="xiaoquData.fangJianCount!==undefined">
          <div class="home_tongji_item_box">
            <div class="home_tongji_item_title">实有房间</div>
            <div class="home_tongji_item_info">
              <span>{{xiaoquData.fangJianCount}}</span>个
            </div>
          </div>
        </div>
        <div class="home_tongji_item" v-if="xiaoquData.personCount!==undefined">
          <div class="home_tongji_item_box">
            <div class="home_tongji_item_title">实有人口</div>
            <div class="home_tongji_item_info">
              <span>{{xiaoquData.personCount}}</span>人
            </div>
          </div>
        </div>

        <div class="home_tongji_item" v-if="xiaoquData.dw !== undefined">
          <div class="home_tongji_item_box">
            <div class="home_tongji_item_title">实有单位</div>
            <div class="home_tongji_item_info">
              <span>{{xiaoquData.dw}}</span>个
            </div>
          </div>
        </div>
        <div class="home_tongji_item" v-if="xiaoquData.cl !== undefined">
          <div class="home_tongji_item_box">
            <div class="home_tongji_item_title">实有车辆</div>
            <div class="home_tongji_item_info">
              <span>{{xiaoquData.cl}}</span>辆
            </div>
          </div>
        </div>

        <div class="home_tongji_item" v-if="xiaoquData.cz !== undefined">
          <div class="home_tongji_item_box">
            <div class="home_tongji_item_title">出租房</div>
            <div class="home_tongji_item_info">
              <span>{{xiaoquData.cz}}</span>套
            </div>
          </div>
        </div>

        <div class="home_tongji_item" v-if="xiaoquData.gz !== undefined">
          <div class="home_tongji_item_box">
            <div class="home_tongji_item_title">关注人员</div>
            <div class="home_tongji_item_info">
              <span>{{xiaoquData.gz}}</span>人
            </div>
          </div>
        </div>
      </div>
      <DialogCard :position="position" v-show="dialogShow" @leave="leave" ref="dialogDom"></DialogCard>
      <div class="home_c_body">
        <iframe :src="mapUrl" frameborder="0" scrolling="no" style="border:0px;"></iframe>
      </div>
    </div>


  </div>
</template>

<script>

  import {
    getPersonMonitorList,
    getTodayStat,
    getLouDongInfo,
    getMonitorPersonStat
  } from "@/api/big"
  import DialogCard from '@/components/big/dialogCard'
  import PersonList from '@/components/big/personList'
  import PersonDetail from '@/components/big/personDetail'

    export default {
      name: "home",
      data () {
        return {
          xiaoquData: {},
          louDongData: {
            childrenCount: "0",
            danYuan: [],
            keyPersonCount: "0",
            oldPersonCount: "0",
            totalCount: "0",
            zcCount: "0"
          },
          centerHeight: 0,
          mapUrl: '',
          // mapUrl: 'https://www.thingjs.com/pp/2cf4c765df4d31d45a5e20ab',
          imagePath: '/jeecg-boot/sys/common/view/',
          dialogShow: false,
          userList: [],
          userToday: {},
          dateData: new Date(),
          weekMap: {
            1: '一',
            2: '二',
            3: '三',
            4: '四',
            5: '五',
            6: '六',
            7: '日'
          },
          position:{
            left: '-100px',
            top: '100px'
          }
        }
      },
      components:{
        DialogCard,
        PersonList,
        PersonDetail
      },
      mounted() {
        getMonitorPersonStat().then(rel => {
          if(rel.code === 200) {
            this.xiaoquData = rel.result
          }
        })
        this.getPersonMonitorList()
        this.getTodayStat()
        this.$nextTick(() => {
          this.centerHeight = this.$refs.center.offsetHeight
        })
        const that = this
        window.onresize = () => {
          return (() => {
            that.$nextTick(() => {
              that.centerHeight = that.$refs.center.offsetHeight
            })
          })()
        }
      },
      created() {
        window.addEventListener('message', (event) => {
          if(event.data.funcName === 'showDialog'){
            this.$nextTick(() => {

              let x = event.data.x
              let y = event.data.y
              const dialogHeight = 420
              const centerHeight = this.centerHeight
              if(y + dialogHeight >= centerHeight) {
                y = centerHeight - dialogHeight;
              }
              this.position.left = x + 'px'
              this.position.top = y + 'px'
              const _data = event.data.data;
              console.log(_data)

              this.getLouDongInfo({
                louDongHao: _data.ld
              }, (data1) => {
                data1.ld = _data.ld
                this.$nextTick(() => {
                  this.$refs.dialogDom.setLouDongData(data1)
                  this.dialogShow = true
                })
              })

            })
          }else {
            this.dialogShow = false
          }

        }, false);
      },
      methods: {
        getLouDongInfo(params, cb) {
          getLouDongInfo(params).then(rel => {
            if(rel.code === 200) {
              console.log(rel)
              this.louDongData = rel.result
              console.log(rel)
              if(cb && typeof cb === 'function') {
                cb(rel.result)
              }
            }
          })
        },
        getTodayStat() {
          getTodayStat().then(rel => {
            if(rel.code === 200) {
              const o = rel.result || {}
              this.userToday = {
                anonymousCount: o.anonymousCount?(o.anonymousCount+'').split(''):['0'],
                normalCount: o.normalCount?(o.normalCount+'').split(''):['0'],
                totalCount: o.totalCount?(o.totalCount+'').split(''):['0']
              }
            }
          })
        },
        getPersonMonitorList() {
          getPersonMonitorList().then(rel => {
            if(rel.code === 200) {
              this.userList = rel.result.records || []
            }
          })
        },
        leave() {
          this.dialogShow = false
        }
      }
    }
</script>
<style>
  body{
    overflow-y: hidden!important;
  }
</style>
<style scoped>
  .home_c_body iframe{
    position:absolute;
    left:0;
    top:0;
    width:100%;
    height:100%;
  }
  #big_home *{
    box-sizing:content-box;
  }
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
  #big_home{
    padding:0;
    margin:0;
    position:absolute;
    left:0;
    top:0;
    right:0;
    bottom:0;
    width:auto;
    height:auto;
    background-image: url("~@/assets/images/home_bg.png");
    background-size:1920px 1080px;
    background-repeat: no-repeat;
    background-position:0 0;
  }
  .home_l_t{
    position:absolute;
    top:97px;
    left:20px;
    height:274px;
    width:460px;
    z-index: 1;
  }
  .home_l_b{
    position:absolute;;
    top:371px;
    left:20px;
    width:460px;
    bottom:70px;
    z-index: 1;
  }

  .home_r_t{
    position:absolute;
    top:97px;
    right:20px;
    height:274px;
    width:460px;
    z-index: 1;
  }
  .home_r_b{
    position:absolute;;
    top:371px;
    right:20px;
    width:460px;
    bottom:70px;
    z-index: 1;
  }
  .home_c{
    position:absolute;
    top:97px;
    bottom:70px;
    left:500px;
    right:500px;
    z-index: 1;
  }
  .home_common_title{
    height:46px;
    line-height: 46px;
    background-image: url("~@/assets/images/home_title_bg.png");
    background-size:340px 46px;
    background-repeat: no-repeat;
    background-position:0 0;
    padding-bottom:29px;
  }

  .home_common_title_info{
    float:left;
    height:46px;
    line-height: 46px;
    padding-left:30px;
    background:
      linear-gradient(92deg,
      rgba(0,114,255,1) 0%,
      rgba(0,234,255,1) 48.8525390625%,
      rgba(1,170,255,1) 100%);
    -webkit-background-clip:text;
    -webkit-text-fill-color:transparent;
    font-size:22px;
    color:rgba(0,184,255,1);
  }
  .home_common_number_box{
    width:50%;
    height:90px;
    margin-bottom:10px;
    float:left;
  }
  .home_common_number_title{
    height:19px;
    line-height: 19px;
    padding-bottom:11px;
    color:#fff;
    font-size:16px;
  }
  .home_common_number_count>.count{
    position:relative;
    width:31.5px;
    height:39px;
    margin-right:14px;
    background-image: url("~@/assets/images/home_number_bg.png");
    background-position:0 0;
    background-size:31.5px 39px;
    background-repeat: no-repeat;
    float:left;
  }
  .count>span{
    display: block;
    position:relative;
    width:31.5px;
    height:39px;
    background-position:center center;
    background-size:12px 19.5px;
    background-repeat: no-repeat;
  }
  .count_0{
    background-image: url("~@/assets/images/0.png");
  }
  .count_1{
    background-image: url("~@/assets/images/1.png");
  }
  .count_2{
    background-image: url("~@/assets/images/2.png");
  }
  .count_3{
    background-image: url("~@/assets/images/3.png");
  }
  .count_4{
    background-image: url("~@/assets/images/4.png");
  }
  .count_5{
    background-image: url("~@/assets/images/5.png");
  }
  .count_6{
    background-image: url("~@/assets/images/6.png");
  }
  .count_7{
    background-image: url("~@/assets/images/7.png");
  }
  .count_8{
    background-image: url("~@/assets/images/8.png");
  }
  .count_9{
    background-image: url("~@/assets/images/9.png");
  }
  .home_head{
    background-image:url("~@/assets/images/home_head_bg.png");
    background-size:1920px 76px;
    background-position:center 0;
    background-repeat: no-repeat;
    height:76px;
    position:relative;
  }
  .home_head_title{
    position:absolute;
    width:600px;
    left:50%;
    margin-left:-300px;
    height:54px;
    top:22px;
    line-height: 54px;
    font-size:36px;
    text-align: center;
    color:#fff;
    color:rgba(255,255,255,1);
    background:linear-gradient(0deg,rgba(81,151,255,1) 0%, rgba(128,242,255,1) 100%);
    -webkit-background-clip:text;
    -webkit-text-fill-color:transparent;
  }
  .home_head_info{
    position:absolute;
    right:20px;
    top:41px;
    height:29px;
    line-height: 29px;
    font-size:16px;
    color:rgba(255,255,255,1);
    background:linear-gradient(0deg,rgba(141,211,255,1) 0%, rgba(255,255,255,1) 100%);
    -webkit-background-clip:text;
    -webkit-text-fill-color:transparent;
  }
  .home_head_info_item{
    float:left;
    margin-left:10px;
  }
  .home_bottom_title{
    height:18px;
    padding-left:30px;
    background-image: url("~@/assets/images/home_title_icon.png");
    background-size:15px 18px;
    background-position: 0 center;
    background-repeat: no-repeat;
    font-size:18px;
    font-family:Microsoft YaHei;
    font-weight:bold;
    color:rgba(63,219,231,1);
    line-height: 18px;
  }
  .home_bottom_box{
    position:absolute;
    top:48px;
    bottom:0;
    left:0;
    right:0;
    overflow-y: auto;
    overflow-x:hidden;
    padding-right:20px;
  }
  .home_bottom_item{
    height:142px;
    position:relative;
    margin-bottom:30px;
    cursor: pointer;
  }
  .home_bottom_item_body{
    position:absolute;
    left:0;
    right:0;
    top:0;
    bottom:0;
    background:rgba(6,7,107,0);
    border:1px solid rgba(55, 85, 218, 0.3);
    z-index: 1;
    padding:20px;
  }
  .home_bottom_item_jiao1,.home_bottom_item_jiao2,.home_bottom_item_jiao3,.home_bottom_item_jiao4{
    position:absolute;
    width:16px;
    height:16px;
    background-image: url("~@/assets/images/home_info_jiao.png");
    background-size:16px 16px;
    background-repeat: no-repeat;
    background-position: 0 0 ;
    z-index: 2;
  }
  .home_bottom_item_jiao1{
    left:0;
    top:0;
  }
  .home_bottom_item_jiao2{
    left:0;
    bottom:0;
    -moz-transform: scaleY(-1);
    -webkit-transform: scaleY(-1);
    -o-transform: scaleY(-1);
    transform: scaleY(-1);
  }
  .home_bottom_item_jiao3{
    right:0;
    top:0;
    -moz-transform: scaleX(-1);
    -webkit-transform: scaleX(-1);
    -o-transform: scaleX(-1);
    transform: scaleX(-1);
  }
  .home_bottom_item_jiao4{
    right:0;
    bottom:0;
    -moz-transform: scaleY(-1) scaleX(-1);
    -webkit-transform: scaleY(-1) scaleX(-1);
    -o-transform: scaleY(-1) scaleX(-1);
    transform: scaleY(-1) scaleX(-1);
  }
  .home_bottom_item_img{
    position:absolute;
    left:20px;
    top:20px;
    width:90px;
    height:100px;
    background:rgba(255,255,255,0.2);
  }
  .home_bottom_item_img img{
    position:absolute;
    left:0;
    top:0;
    display: block;
    padding:0;
    margin:0;
    width:100%;
    height:100%;
  }
  .home_bottom_item_info{
    padding-left:90px;
    font-size:16px;
    font-family:Microsoft YaHei;
    font-weight:400;
    color:rgba(24,189,253,1);
    line-height: 16px;
    padding-bottom:10px;
  }
  .home_bottom_item_info span{
    display: inline-block;
    height:16px;
    width:80px;
    text-align: right;
  }
  .home_c_l_t{
    position:absolute;
    background-image: url("~@/assets/images/home_card_l_t.png");
    background-size: 91px 283px;
    background-repeat: no-repeat;
    background-position: 0 0;
    left:0;
    top:0;
    width:91px;
    height:283px;
    z-index: 2;
  }

  .home_c_r_t{
    position:absolute;
    background-image: url("~@/assets/images/home_card_r_t.png");
    background-size: 175px 5px;
    background-repeat: no-repeat;
    background-position: 0 0;
    right:0;
    top:0;
    z-index: 2;
    width:175px;
    height:5px;
  }
  .home_c_body{
    position:absolute;
    top:0;
    bottom:0;
    left:0;
    right:0;
    background: rgba(255,255,255,0.2);
    z-index: 1;
  }
  .home_c_line_l_b{
    position:absolute;
    bottom:0;
    left:0;
    height:80px;
    width:1px;
    background:#59C3DC;
    z-index: 2;
  }
  .home_c_line_b{
    position:absolute;
    bottom:0;
    left:0;
    right:0;
    width:auto;
    height:1px;
    background:#59C3DC;
    z-index: 2;
  }
  .home_c_line_r_b{
    position:absolute;
    bottom:0;
    right:0;
    height:80px;
    width:1px;
    background:#59C3DC;
    z-index: 2;
  }
  .home_tongji_list{
    position:absolute;
    bottom:0;
    display: flex;
    flex-direction: row;
    left:20px;
    right:20px;
    height:119px;
    z-index:3;
  }
  .home_tongji_item{
    flex: 1;
  }
  .home_tongji_item_box{
    margin:0 auto;
    width:119px;
    height:96px;
    background-image: url("~@/assets/images/home_tip_bg.png");
    background-size:100% 100%;
    background-position:0 0 ;
    background-repeat: no-repeat;
  }
  .home_tongji_item_title{
    padding-top:15px;
    line-height:35px;
    height:35px;
    font-size:14px;
    color:#00E3FF;
    text-align: center;
  }
  .home_tongji_item_info{
    line-height:20px;
    height:20px;
    font-size:14px;
    color:#00E3FF;
    text-align: center;
  }
  .home_tongji_item_info span{
    font-size:24px;
  }

</style>
<style>
  .ant-popover-inner{
    background:none;
  }
  .ant-popover-inner-content{
    padding:0;
  }
  .ant-popover-placement-top > .ant-popover-content > .ant-popover-arrow, .ant-popover-placement-topLeft > .ant-popover-content > .ant-popover-arrow, .ant-popover-placement-topRight > .ant-popover-content > .ant-popover-arrow{
    border-right-color: rgba(44,92,235,0.6);
    border-bottom-color: rgba(44,92,235,0.6);
  }
  .ant-popover-placement-right > .ant-popover-content > .ant-popover-arrow, .ant-popover-placement-rightTop > .ant-popover-content > .ant-popover-arrow, .ant-popover-placement-rightBottom > .ant-popover-content > .ant-popover-arrow{

    /*border-right-color: #00F6FF;*/
    border-bottom-color: #00F6FF;
    border-left-color:#00F6FF;
  }
</style>