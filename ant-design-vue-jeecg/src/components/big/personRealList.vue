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
        <div class="person_list_box">
          <a-row>
            <a-col :span="6">
              <div style="margin-right:40px;">
                <a-input placeholder="请输入人员姓名" v-model="xingming"></a-input>
              </div>
            </a-col>
            <a-col :span="6">
              <div style="margin-right:40px;">
                <a-input placeholder="请输入人员身份证" v-model="idCard"></a-input>
              </div>
            </a-col>
            <a-col :span="6">

              <div style="margin-right:40px;">
                <a-input placeholder="请输入联系电话" v-model="lxdh"></a-input>
              </div>
            </a-col>
            <a-col :span="6" style="padding-top:3px;">
              <a-button type="primary" @click="refreshList" icon="search">查询</a-button>
            </a-col>
          </a-row>
          <div class="person_list_table scroll_body" style="max-height:450px;overflow: auto;">
            <a-row>
              <a-col :span="8" v-for="(person,index) in personList.filter((item,ind) => ind<3)" :key="index">
                <div class="person_item">
                  <div class="person_item_img">
                    <img :src="baseImgUrl+'/'+person.zhaoPian"
                         slot="cover" alt="">
                  </div>
                  <div class="person_item_btn">
                    <a-button type="primary" size="small" @click="showLD(person)">查看</a-button>
                  </div>
                  <div class="person_item_info">
                    <div class="person_item_p">
                      <div class="person_item_p_l">姓名：</div>
                      <div class="person_item_p_r">{{person.xingMing}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">身份证号：</div>
                      <div class="person_item_p_r">{{person.sfzh}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">户籍地址：</div>
                      <div class="person_item_p_r">{{person.hjdz}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">联系电话：</div>
                      <div class="person_item_p_r">{{person.lxdh}}</div>
                    </div>
                  </div>
                </div>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="8" v-for="(person,index) in personList.filter((item,ind) => ind>=3 && ind<6)" :key="index">
                <div class="person_item">
                  <div class="person_item_img">
                    <img :src="baseImgUrl+'/'+person.zhaoPian"
                         slot="cover" alt="">
                  </div>
                  <div class="person_item_btn">
                    <a-button type="primary" size="small" @click="showLD(person)">查看</a-button>
                  </div>
                  <div class="person_item_info">
                    <div class="person_item_p">
                      <div class="person_item_p_l">姓名：</div>
                      <div class="person_item_p_r">{{person.xingMing}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">身份证号：</div>
                      <div class="person_item_p_r">{{person.sfzh}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">户籍地址：</div>
                      <div class="person_item_p_r">{{person.hjdz}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">联系电话：</div>
                      <div class="person_item_p_r">{{person.lxdh}}</div>
                    </div>
                  </div>
                </div>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="8" v-for="(person,index) in personList.filter((item,ind) => ind>=6 && ind<9)" :key="index">
                <div class="person_item">
                  <div class="person_item_img">
                    <img :src="baseImgUrl+'/'+person.zhaoPian"
                         slot="cover" alt="">
                  </div>
                  <div class="person_item_btn">
                    <a-button type="primary" size="small" @click="showLD(person)">查看</a-button>
                  </div>
                  <div class="person_item_info">
                    <div class="person_item_p">
                      <div class="person_item_p_l">姓名：</div>
                      <div class="person_item_p_r">{{person.xingMing}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">身份证号：</div>
                      <div class="person_item_p_r">{{person.sfzh}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">户籍地址：</div>
                      <div class="person_item_p_r">{{person.hjdz}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">联系电话：</div>
                      <div class="person_item_p_r">{{person.lxdh}}</div>
                    </div>
                  </div>
                </div>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="8" v-for="(person,index) in personList.filter((item,ind) => ind>=9 && ind<12)" :key="index">
                <div class="person_item">
                  <div class="person_item_img">
                    <img :src="baseImgUrl+'/'+person.zhaoPian"
                         slot="cover" alt="">
                  </div>
                  <div class="person_item_btn">
                    <a-button type="primary" size="small" @click="showLD(person)">查看</a-button>
                  </div>
                  <div class="person_item_info">
                    <div class="person_item_p">
                      <div class="person_item_p_l">姓名：</div>
                      <div class="person_item_p_r">{{person.xingMing}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">身份证号：</div>
                      <div class="person_item_p_r">{{person.sfzh}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">户籍地址：</div>
                      <div class="person_item_p_r">{{person.hjdz}}</div>
                    </div>
                    <div class="person_item_p">
                      <div class="person_item_p_l">联系电话：</div>
                      <div class="person_item_p_r">{{person.lxdh}}</div>
                    </div>
                  </div>
                </div>
              </a-col>
            </a-row>
          </div>
          <div style="text-align: center;" >
            <div v-if="pagination.total===0" style="color:rgba(255,255,255,0.5);font-size:30px;line-height:440px;text-align: center">暂无数据</div>
            <a-pagination v-if="pagination.total!==0" :defaultCurrent="pagination.page" :pageSize="pagination.pageSize" :total="pagination.total" @change="onPageChange" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>


  import {
    getMonitorPersonList
  } from "@/api/big"

  export default {
    name: "personRealList",
    props: ['position','personType'],
    data() {
      return {
        baseImgUrl: window._CONFIG['imgDomainURL'],
        pagination: {
          pageSize: 12,
          page: 1,
          total: 0
        },
        xingming: '',
        lxdh: '',
        idCard: '',
        personList: []
      }
    },
    mounted() {
      this.refreshList()
    },
    methods: {
      refreshList() {
        this.pagination.page = 1
        this.getMonitorPersonList()
      },
      onPageChange(pageNumber) {
        this.pagination.page = pageNumber
        this.getMonitorPersonList()
      },
      getMonitorPersonList() {
        const params = {
          pageNo: this.pagination.page,
          pageSize: this.pagination.pageSize,
          column: 'createTime',
          order: 'desc'
        }
        if(this.personType){
          params.type = this.personType==='count'?'':this.personType
        }
        if(this.xingming){
          const xingming = this.xingming
          params.xingMing = `*${xingming}*`
        }
        if(this.idCard){
          const sfzh = this.idCard
          params.sfzh = `*${sfzh}*`
        }
        if(this.lxdh){
          const lxdh = this.lxdh
          params.lxdh = `*${lxdh}*`
        }
        getMonitorPersonList(params).then(rel => {
          this.personList = rel.result.records;
          this.pagination.total = rel.result.total
        })
      },
      leave() {
        this.$emit('leave')
      },
      showLD(person) {
        this.$emit('showLD',person)
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
    width: 1235px;
    height: 582px;
    top: 50%;
    left: 50%;
    margin-left: -408px;
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
  .person_list_table{
    padding:10px 0;
    margin-bottom:10px;
  }
  .person_item{
    margin-right:10px;
    position:relative;
    background:rgba(255,255,255,0.8);
    min-height: 140px;
    border-radius: 5px;
    margin-bottom:10px;
  }
  .person_item_img{
    width:100px;
    height:120px;
    position:absolute;
    left:10px;
    top:10px;
    background:rgba(0,0,0,0.2);
  }
  .person_item_img img{
    width:100%;
    height:100%;
    position: absolute;
  }
  .person_item_info{
    margin:0 10px 0 120px;
    padding:10px 0;
  }
  .person_item_p{
    position:relative;
    min-height: 18px;
    line-height: 18px;
    padding:4px 0;
    z-index: 1;
  }
  .person_item_p_l{
    position:absolute;
    width:90px;
    left:0;
    top:4px;
  }
  .person_item_p_r{
    padding-left:90px;
    word-break: break-all;
  }
  .person_item_btn{
    position:absolute;
    right:10px;
    top:10px;
    z-index:100;
  }
</style>