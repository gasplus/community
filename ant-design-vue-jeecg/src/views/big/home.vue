<template>
  <div id="big_home">
    <div class="home_head">
      <div class="home_head_title">龙堌中心社区智慧感知中心</div>
      <div class="home_head_info">
        <a-popover  placement="bottom" trigger="hover">
          <template slot="content">
            <message-box :list="messageList"></message-box>
          </template>
          <div class="home_head_info_item message_icon" style="padding-left:30px;">{{messageTotal}}</div>
        </a-popover>
        <div class="home_head_info_item">{{dateData.getFullYear()}}-{{dateData.getMonth()+1}}-{{dateData.getDate()}}</div>
        <div class="home_head_info_item">{{dateData.getHours()}}:{{dateData.getMinutes()}}:{{dateData.getSeconds()}}</div>
        <div class="home_head_info_item">星期{{weekMap[dateData.getDay()]}}</div>
      </div>
    </div>
    <div class="home_r">
      <collapse :selected="active1" ref="c1" @collapse-open="changeActiveIndex('0')">
        <div slot="collapse-header">
          <div class="accordion_head" style="width:100%;">
            <div class="accordion_head_title accordion_person">
              今日人员统计
            </div>
            <div class="accordion_head_info" style="padding:5px;display: block;position:relative;">
              <div class="home_bottom_item" style="position:absolute;left:-10px;right:-10px;top:0;min-height:80px;height:80px!important;display: block;margin:0;">
                <div class="home_bottom_item_jiao1"></div>
                <div class="home_bottom_item_jiao2"></div>
                <div class="home_bottom_item_jiao3"></div>
                <div class="home_bottom_item_jiao4"></div>
                <div class="home_bottom_item_body" style="padding:0;">
                  <div class="tongji_item">
                    <div class="tongji_item_t">
                      <div class="tongji_item_t_label">总次数</div>
                      <div class="tongji_item_t_number">{{userToday.totalCount}}</div>
                    </div>
                    <div class="tongji_item_b">
                      <div class="tongji_item_b_tongji">已登记 {{userToday.normalCount}} 人</div>
                      <div class="tongji_item_b_tongji">未登记 {{userToday.anonymousCount}} 人</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div slot="collapse-body" style="height:calc(100vh - 300px);position:relative;">
          <div class="scroll_body tongji_list" style="padding:10px;overflow: auto;">
<!--            <a-popover  placement="right" trigger="hover">-->
<!--              <template slot="content">-->
<!--                <PersonDetail :personData="item" :bodyInfo="JSON.parse(item.bodyInfo)"  :faceInfo="JSON.parse(item.faceInfo)"></PersonDetail>-->
<!--              </template>-->
              <div class="home_bottom_item" v-for="item in userList" :key="item.id">
                <div class="home_bottom_item_jiao1"></div>
                <div class="home_bottom_item_jiao2"></div>
                <div class="home_bottom_item_jiao3"></div>
                <div class="home_bottom_item_jiao4"></div>
                <div class="home_bottom_item_body" v-if="item.personId!=='anonymous'">
                  <div class="home_bottom_item_img">
                    <img v-if="item.photoUrl" :src="jkImagePath+item.photoUrl" alt="">
<!--                    <img class="moshengren_photo" v-if="!item.photoUrl" src="@/assets/images/icon_message_moshengren.png" alt="">-->
                  </div>
                  <div class="home_bottom_item_btn" >
                    <a-tag color="blue" @click="drawLine(item)">查看轨迹</a-tag>
                  </div>
                  <div class="home_bottom_item_info card_name">{{item.personName}}</div>
                  <div class="home_bottom_item_info card_id_card">{{item.personIdCard}}1</div>
                  <div class="home_bottom_item_info card_address">{{item.hjdz}}2</div>
                  <div class="home_bottom_item_info card_find_address">{{item.address}}3</div>
                  <!--            <div class="home_bottom_item_info"><span>方式：</span>{{item.outInType}}</div>-->
                  <div class="home_bottom_item_info card_time">{{item.outInTime}}</div>
                </div>

                <div class="home_bottom_item_body" v-if="item.personId==='anonymous'">
                  <div class="home_bottom_item_img">
                    <img :src="jkImagePath+item.photoUrl" alt="">
                  </div>
                  <div class="home_bottom_item_btn"  >
                    <a-tag color="blue" @click="showPoint(item)">查看位置</a-tag>
                  </div>
                  <div class="home_bottom_item_info card_name">陌生人</div>
                  <div class="home_bottom_item_info card_address">{{item.address}}</div>
                  <!--            <div class="home_bottom_item_info"><span>方式：</span>{{item.outInType}}</div>-->
                  <div class="home_bottom_item_info card_time">{{item.outInTime}}</div>
                </div>
              </div>
<!--            </a-popover>-->
          </div>
        </div>
      </collapse>
      <collapse :selected="active2" ref="c2" @collapse-open="changeActiveIndex('1')">
        <div slot="collapse-header">
          <div class="accordion_head" style="width:100%;">
            <div class="accordion_head_title accordion_car">
              车辆进出统计
            </div>
            <div class="accordion_head_info" style="padding:5px;display: block;position:relative;">
              <div class="home_bottom_item" style="position:absolute;left:-10px;right:-10px;top:0;height:80px;display: block;margin:0;">
                <div class="home_bottom_item_jiao1"></div>
                <div class="home_bottom_item_jiao2"></div>
                <div class="home_bottom_item_jiao3"></div>
                <div class="home_bottom_item_jiao4"></div>
                <div class="home_bottom_item_body" style="padding:0;">
                  <div class="tongji_item">
                    <div class="tongji_item_t">
                      <div class="tongji_item_t_label">总次数</div>
                      <div class="tongji_item_t_number">1000</div>
                    </div>
                    <div class="tongji_item_b">
                      <div class="tongji_item_b_tongji">已登记 1000 辆</div>
                      <div class="tongji_item_b_tongji">未登记 1000 辆</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div slot="collapse-body" style="height:calc(100vh - 300px);position:relative;">
          <div class="scroll_body tongji_list" style="padding:10px;overflow: auto;">
            <a-popover  placement="right" trigger="hover" v-for="item in userList" :key="item.id">
              <template slot="content">
                <PersonDetail></PersonDetail>
              </template>
              <div class="home_bottom_item">
                <div class="home_bottom_item_jiao1"></div>
                <div class="home_bottom_item_jiao2"></div>
                <div class="home_bottom_item_jiao3"></div>
                <div class="home_bottom_item_jiao4"></div>
                <div class="home_bottom_item_body">
                  <div class="home_bottom_item_img">
                    <img :src="imagePath+'/'+item.photoUrl" alt="">
                  </div>
                  <div class="home_bottom_item_info card_name">{{item.personName}}</div>
                  <div class="home_bottom_item_info card_address">{{item.address}}</div>
                  <!--            <div class="home_bottom_item_info"><span>方式：</span>{{item.outInType}}</div>-->
                  <div class="home_bottom_item_info card_time">{{item.outInTime}}</div>
                </div>
              </div>
            </a-popover>
          </div>
        </div>
      </collapse>
    </div>
<!--    <div class="home_c" ref="center" @mouseleave="leave">-->
    <div class="home_c" ref="center">
      <div class="home_c_l_t"></div>
      <div class="home_c_r_t"></div>

      <div class="home_c_line_l_b"></div>
      <div class="home_c_line_b"></div>
      <div class="home_c_line_r_b"></div>
      <div class="home_tongji_list">
        <div class="home_tongji_item" v-for="(key,index) in tongjiList" :key="key" v-if="xiaoquData[key]||xiaoquData[key]===0">
          <div class="home_tongji_item_box">
            <div class="home_tongji_item_title">{{tongjiKeyMap[key]}}</div>
            <div class="home_tongji_item_info">
              <span>{{xiaoquData[key]}}</span>人
            </div>
          </div>
        </div>
      </div>
      <DialogCard :position="position" v-show="dialogShow" @show="showPersonList" @leave="leave" ref="dialogDom"></DialogCard>
      <PersonList v-show="personListShow" :personListData="personListData" :roomData="roomData" @close="closePersonList"></PersonList>
      <div class="home_c_body">
        <iframe ref="mapIframe" :src="mapUrl" frameborder="0" scrolling="no" style="border:0px;"></iframe>
      </div>
    </div>


  </div>
</template>

<script>
  import Collapse from 'vue-collapse'
  import MessageBox from '@/components/big/MessageBox'
  import {
    getPersonMonitorList,
    getTodayStat,
    getLouDongInfo,
    getMonitorPersonTypeStat,
    getMonitorMessage,
    getFangJianPerson
  } from "@/api/big"
  import DialogCard from '@/components/big/dialogCard'
  import PersonList from '@/components/big/personList'
  import PersonDetail from '@/components/big/personDetail'

    export default {
      name: "home",
      data () {
        return {
          roomData: {},
          interval: undefined,
          tongjiList: [
            'count', 'A01A01', 'A01A02', 'A01A03', 'A01A04'
            // , 'A01A04A01', 'A01A04A02', 'A01A04A03'
          ],
          tongjiKeyMap:{
            'A01A04': '重点人员',
            'A01A04A03': '刑满释放',
            'A01A04A02': '精神病人',
            'A01A04A01': '五类外执人员',
            'A01A03': '孤寡老人',
            'A01A02': '留守儿童',
            'A01A01': '普通',
            'count': '实有人口'
          },
          personListData: undefined,
          personListShow: false,
          active1: true,
          active2: false,
          activeIndex: '0',
          text: `A dog is a type of domesticated animal.Known for its loyalty and faithfulness,it can be found as a welcome guest in many households across the world.`,
          xiaoquData: {},
          louDongData: {
            childrenCount: "0",
            danYuan: [],
            keyPersonCount: "0",
            oldPersonCount: "0",
            totalCount: "0",
            zcCount: "0"
          },
          messageTotal: 0,
          messageList: [],
          // accordionHeight:400,
          centerHeight: 0,
          // mapUrl: '',
          // mapUrl: 'https://www.thingjs.com/pp/2cf4c765df4d31d45a5e20ab',
          mapUrl: 'http://20.36.24.100:9000',
          imagePath: window._CONFIG['imgDomainURL'],
          jkImagePath: window._CONFIG['imgDomainRecordURL'],
          dialogShow: false,
          userList: [],
          userToday: {},
          dateData: new Date(),
          timeStep: 10,
          weekMap: {
            1: '一',
            2: '二',
            3: '三',
            4: '四',
            5: '五',
            6: '六',
            0: '日'
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
        PersonDetail,
        Collapse,
        MessageBox
      },
      mounted() {
        getMonitorPersonTypeStat().then(rel => {
          if(rel.code === 200) {
            this.xiaoquData = rel.result
          }
        })
        this.getPersonMonitorList()
        this.getTodayStat()
        this.getMonitorMessage()
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
        // setTimeout(() => {
        //   this.dialogShow = true
        // },1000)
        //
        // this.getLouDongInfo({
        //   louDongHao: '14'
        // }, (data1) => {
        //   data1.ld = '14'
        //   this.dialogShow = true
        //   if(this.$refs.dialogDom.setLouDongData) {
        //     this.$refs.dialogDom.setLouDongData(data1)
        //   }
        // })
      },
      created() {
        window.addEventListener('message', (event) => {
          if(event.data.funcName === 'showDialog'){
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
            // return
            this.getLouDongInfo({
              louDongHao: _data.ID
            }, (data1) => {
              data1.ld = _data.ID
              this.dialogShow = true
              if(this.$refs.dialogDom.setLouDongData) {
                this.$refs.dialogDom.setLouDongData(data1)
              }
            })
          }else {
            this.dialogShow = false
          }

        }, false);
        if(this.interval) {
          clearInterval(this.interval);
        }
        this.interval = setInterval(() => {
          this.getTodayStat()
          this.getPersonMonitorList()
          this.getMonitorMessage()
        }, this.timeStep * 1000)
      },
      methods: {
        showPoint(item) {
          console.log(item)
          this.$refs.mapIframe.contentWindow.postMessage({funcName:'createPanel',data:[item]},'*');
        },
        drawLine(item) {
          console.log(item)
          getPersonMonitorList({
            pageSize: 20,
            pageNo: 1,
            personId: item.personId
          }).then(rel => {
            if(rel.code === 200) {
              this.$refs.mapIframe.contentWindow.postMessage({funcName:'drawLine',data:rel.result.records},'*');
            }
          })
          // this.$refs.mapIframe.contentWindow.postMessage({funcName:'drawLine',data:[{
          //     deviceId: 'LG00100505',
          //     outInTime: '2019-09-09',
          //     address: '1'
          //   },{
          //     deviceId: 'LG00100602',
          //     outInTime: '2019-09-09',
          //     address: '2'
          //   },{
          //     deviceId: 'LG00100703',
          //     outInTime: '2019-09-09',
          //     address: '3'
          //   }]},'*');
        },
        getFangJianPerson(params,cb) {
          getFangJianPerson(params).then(rel => {
            if(rel.code === 200) {
              this.personListData = rel.result
              if(cb && typeof cb === 'function') {
                cb(rel.result)
              }
            }
          })
        },
        closePersonList() {
          this.$nextTick(() => {
            this.$refs.dialogDom.clearRoomSelect();
          })
          this.personListShow = false
        },
        showPersonList(data,params) {
          this.getFangJianPerson(params,() => {
            this.roomData = data
            this.personListShow = true
          })
        },
        closeDialog() {
          this.$nextTick(() => {
            this.$refs.mapIframe.contentWindow.postMessage({funcName:'closeDialog'},'*');
          })

        },
        changeActiveIndex(index) {
          console.log(index)
          if (index === '0') {

            this.$nextTick(() => {
              this.$refs.c1.active = true
              this.$refs.c2.active = false
            })
          } else {
            this.$nextTick(() => {
              this.$refs.c1.active = false
              this.$refs.c2.active = true
            })
          }
        },
        changeActivekey(key) {
          console.log(key);
        },
        getMonitorMessage() {
          getMonitorMessage({
            pageSize:10
          }).then(rel => {
            if(rel.code === 200) {
              console.log(rel)
              this.messageTotal = rel.result.total
              this.messageList = rel.result.records
            }
          })
        },
        getLouDongInfo(params, cb) {
          getLouDongInfo(params).then(rel => {
            if(rel.code === 200) {
              this.louDongData = rel.result
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
                anonymousCount: o.anonymousCount?(o.anonymousCount+''):'0',
                normalCount: o.normalCount?(o.normalCount+''):'0',
                totalCount: o.totalCount?(o.totalCount+''):'0'
              }
            }
          })
        },
        getFormatDate(date) {
          let month = (date.getMonth() + 1) + ''
          let day = date.getDate() + ''
          if(month.length === 1) {
            month = '0' + month
          }
          if(day.length === 1) {
            day = '0' + day
          }
          return date.getFullYear() + '-' + month + '-' + day
        },
        getPersonMonitorList() {
          const nowDateStr = this.getFormatDate(new Date())
          getPersonMonitorList({
            outInTime_begin: nowDateStr + ' 00:00:00',
            outInTime_end: nowDateStr + ' 23:59:59',
            column: 'outInTime',
            pageSize:10,
            order: 'desc'
          }).then(rel => {
            if(rel.code === 200) {
              this.userList = rel.result.records || []
            }
          })
        },
        leave() {
          this.closeDialog()
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
    top:70px;
    bottom:10px;
    left:10px;
    right:420px;
    z-index: 1;
  }
  .home_r{
    position:absolute;
    top:70px;
    bottom:10px;
    right:10px;
    width:400px;
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
    background-position:center bottom 0;
    background-repeat: no-repeat;
    height:60px;
    position:relative;
  }
  .home_head_title{
    position:absolute;
    width:600px;
    left:50%;
    margin-left:-300px;
    height:54px;
    top:6px;
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
    top:25px;
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
    min-height:102px;
    position:relative;
    margin-bottom:10px;
    cursor: pointer;
  }
  .home_bottom_item_body{
    position:relative;
    left:0;
    right:0;
    top:0;
    bottom:0;
    background:rgba(6,7,107,0);
    border:1px solid rgba(55, 85, 218, 0.3);
    z-index: 1;
    padding:10px;
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
    left:10px;
    top:10px;
    width:70px;
    height:70px;
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
  .home_bottom_item_img img.moshengren_photo{
    width:50px;
    height:50px;
    left:10px;
    top:10px;
  }
  .home_bottom_item_info{
    margin-left:80px;
    font-size:12px;
    font-family:Microsoft YaHei;
    font-weight:400;
    color:rgba(24,189,253,1);
    line-height: 20px;
    padding:5px 0 5px 25px;
    /*overflow: hidden;*/
    /*text-overflow:ellipsis;*/
    /*white-space: nowrap;*/
    opacity: 0.9;
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
  .tongji_list{
    position:absolute;
    top:10px;
    right:0;
    bottom:0;
    left:0;
    border:1px solid rgba(255,255,255,0.2);
    box-shadow: 0 0 10px rgba(255,255,255,0.2) inset;
  }
  .accordion_car{
    padding-left:25px;
    background-image: url("~@/assets/images/home_accordion_icon_car.png");
    background-size:20px 20px;
    background-position:0px center;
    background-repeat: no-repeat;
  }

  .accordion_person{
    padding-left:25px;
    background-image: url("~@/assets/images/home_accordion_icon_person.png");
    background-size:20px 20px;
    background-position:0px center;
    background-repeat: no-repeat;
  }
  .card_name{
    background-image: url("~@/assets/images/card_name.png");
    background-size:16px 16px;
    background-position:4px 7px;
    background-repeat: no-repeat;
  }

  .card_address{
    background-image: url("~@/assets/images/card_address.png");
    background-size:16px 16px;
    background-position:4px 7px;
    background-repeat: no-repeat;
  }
  .card_find_address{
    background-image: url("~@/assets/images/icon_person_find_address.png");
    background-size:18px 18px;
    background-position:3px 7px;
    background-repeat: no-repeat;
  }
  .card_id_card{
    background-image: url("~@/assets/images/icon_person_card.png");
    background-size:24px 18px;
    background-position:0px 7px;
    background-repeat: no-repeat;
  }

  .card_time{
    background-image: url("~@/assets/images/card_time.png");
    background-size:16px 16px;
    background-position:4px 7px;
    background-repeat: no-repeat;
  }
  .home_bottom_item_btn{
    position:absolute;
    right:5px;
    top:15px;
    z-index:200;
  }
  .message_icon{
    font-size:10px;
    margin-right:10px;
    padding-top:10px;
    line-height: 20px;
    background-image: url("~@/assets/images/message_icon.png");
    background-size:30px 30px;
    background-position:0px 0px;
    background-repeat: no-repeat;
    cursor: pointer;
  }
  .message_box{
    line-height: 40px;
    font-size:10px;
    font-weight: bold;
    position:relative;
    width:40px;
    height:30px;
  }
  .message_box .message_number{
    display: block;
    position:absolute;
    top:0;
    right:0;
    font-size:12px;
    width:30px;
    color:#fff;
    text-align: center;
    z-index: 100;
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
    display: none;
  }
  .ant-popover-placement-right > .ant-popover-content > .ant-popover-arrow, .ant-popover-placement-rightTop > .ant-popover-content > .ant-popover-arrow, .ant-popover-placement-rightBottom > .ant-popover-content > .ant-popover-arrow{
    border-bottom-color: #00F6FF;
    border-left-color:#00F6FF;
    display: none;
  }
  .ant-popover-placement-left > .ant-popover-content > .ant-popover-arrow, .ant-popover-placement-leftTop > .ant-popover-content > .ant-popover-arrow, .ant-popover-placement-leftBottom > .ant-popover-content > .ant-popover-arrow{
    border-top-color: #00F6FF;
    border-right-color:#00F6FF;
  }
  .collapse .collapse-header::before{
    top:0;
    line-height: 20px;
  }
  .collapse .collapse-header{
    background:none;
  }
  .accordion_head{
    height:110px;
    color:#fff;
    display: block!important;
  }
  #big_home .accordion_head * {
    display: block;
  }
  .accordion_head_title{
    height:30px;
    line-height: 30px;
  }
  .collapse .collapse-header{
    padding:0 10px;
  }
  .collapse .collapse-content-box{
    border:none;
    padding:0;
    color:#fff;
  }
  .tongji_item{
    margin:5px;
    box-sizing: border-box;
    border:2px solid rgba(44,92,235,0.6);
  }
  .tongji_item_b{
    height:24px;
    line-height: 24px;
    background:rgba(44,92,235,0.6);
    clear: both;
    position:relative;
  }
  .tongji_item_b_tongji{
    height:24px;
    line-height: 24px;
    width:50%;
    float:left;
    position:relative;
    text-align: center;
    color:#fff;
    font-size:12px;
  }
  .tongji_item_t{
    height:40px;
    background:rgba(255,255,255,0.1);
  }
  .tongji_item_t_label{
    height:40px;
    line-height: 40px;
    width:45%;
    text-align: right;
    float:left;
    font-size:16px;
  }
  .tongji_item_t_number{
    height:40px;
    line-height: 40px;
    width:50%;
    margin-left:5%;
    text-align: left;
    float:left;
    font-size:24px;
    color:#fadb14;
  }
  .collapse .collapse-header .tongji_item_b::before{
    -webkit-transition: all .2s;
    transition: all .2s;
    content: url(/img/arrow-down.de28a4e4.svg);
    position: absolute;
    font-size: 0.4em;
    top: calc(50% - 0.4em);
    left: calc(50% - 0.4em);
    color: #c5c9d0;
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }
  .collapse .collapse-header .tongji_item_b::before{
    top: 0;
    line-height: 30px;
  }
  .collapse.is-active .collapse-header  .tongji_item_b::before {
    -webkit-transform: rotate(-180deg);
    transform: rotate(-180deg);
  }
  .collapse .collapse-header::before{
    display: none;
  }
</style>