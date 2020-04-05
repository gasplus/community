<template>
  <page-layout :avatar="avatar">
    <div slot="headerContent">
      <div class="title">{{ timeFix }}，{{ nickname() }}</div>
      <div></div>
    </div>
    <div slot="extra">
      <a-row class="more-info">
        <a-col :span="2">
        </a-col>
        <a-col :span="4" @click="go2Page('modules-community-MonitorPersonList','')" style="cursor:pointer;">
          <head-info title="实有人口" :content="tongji.count+''" :center="false" :bordered="false"/>
        </a-col>
        <a-col :span="4" @click="go2Page('modules-community-MonitorPersonList','A01A01')" style="cursor:pointer;">
          <head-info title="常住人口" :content="tongji.A01A01+''" :center="false" :bordered="false"/>
        </a-col>
        <a-col :span="4" @click="go2Page('modules-community-MonitorPersonList','A01A02')" style="cursor:pointer;">
          <head-info title="流动人口" :content="tongji.A01A02+''" :center="false"/>
        </a-col>
        <a-col :span="4" @click="go2Page('modules-community-MonitorPersonList','A01A03')" style="cursor:pointer;">
          <head-info title="重点人口" :content="tongji.A01A03+''" :center="false"/>
        </a-col>
        <a-col :span="6" @click="go2Page('modules-community-MonitorPersonList','A01A04')" style="cursor:pointer;">
          <head-info title="重点关注人口" :content="tongji.A01A04+''" :center="false"/>
        </a-col>
      </a-row>
    </div>

    <div>
      <a-row :gutter="24">
        <a-col :xl="16" :lg="24" :md="24" :sm="24" :xs="24">
          <a-card
            class="project-list"
            :loading="loading"
            style="margin-bottom: 24px;"
            :bordered="false"
            title="进行中的任务"
            :body-style="{ padding: 0 }">
            <a slot="extra" @click="go2Project">更多</a>
            <div>
              <a-card-grid class="project-card-grid" :key="i" v-for="(item, i) in projects">
                <a-card :bordered="false" :body-style="{ padding: 0 }">
                  <a-card-meta>
                    <div slot="title" class="card-title">
                      <a-spin size="small" :spinning="!item.imgLoading">
                        <a-avatar size="default"
                                  :src="item.alarmType!=='10'?'/car.png':(item.personPhoto||'/person.png')"
                        />
                        <a><div style="position:absolute;font-weight:400;right:0px;top:0;text-align:right;width:100px;height:20px;line-height: 20px;color:#ccc;">{{item.createBy}}</div>{{ item.title }}<a-tag style="margin-left:10px;">{{ !item.alarmRuleType?'':filterMultiDictText(dictOptions['alarmRuleType'], item.alarmRuleType + "") }}<span v-if="item.alarmRuleType==='10'">（{{item.intervalDays}}天）</span></a-tag> </a>
                      </a-spin>
                    </div>
                    <template slot="description">
                      <a-row>
                        <a-col :span="12" style="text-align: left;color:#333;">
                          <a-spin size="small" :spinning="!item.loading">
                            告警 {{item.count||0}} 次
                          </a-spin>
                        </a-col>
                        <a-col :span="12" style="text-align: right;color:#ccc;">{{item.createTime}}</a-col>
                      </a-row>
                    </template>
                  </a-card-meta>
                </a-card>
              </a-card-grid>
            </div>
          </a-card>

          <a-card :loading="loading" title="增加代办任务" :bordered="false">
            <a slot="extra" @click="go2Active">更多</a>
            <a-list>
              <a-list-item :key="index" v-for="(item, index) in activities" style="cursor: pointer">
                <a-list-item-meta>
                  <a-avatar slot="avatar" :src="getMessageImg(item)"/>
                  <div slot="title">
                    <span>{{ item.content }}</span>&nbsp;
                    <span style="float:right" v-if="item.messageType === '50'&&item.dataContentObj.carNumber" @click="showCarRelation(item.dataContentObj)"><a-tag color="blue">{{item.dataContentObj.carNumber}}</a-tag></span>
                    <span style="float:right" v-if="item.messageType === '40'&&item.dataContentObj.personName" @click="showPersonRelation(item.dataContentObj)"><a-tag color="blue">{{item.dataContentObj.personName}}</a-tag></span>
                  </div>
                  <div slot="description">
                    {{ item.createTime }}
                    <span style="float:right"><a-tag @click="showPanelImg(item.dataContentObj)">查看图片</a-tag></span>
                  </div>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </a-card>
        </a-col>
        <a-col
          style="padding: 0 12px"
          :xl="8"
          :lg="24"
          :md="24"
          :sm="24"
          :xs="24">
          <a-card title="平安指数" style="margin-bottom: 24px" :loading="radarLoading" :bordered="false"
                  :body-style="{ padding: 0 }">
            <div style="min-height: 400px;">
              <!-- :scale="scale" :axis1Opts="axis1Opts" :axis2Opts="axis2Opts"  -->
              <v-chart :forceFit="true" :height="400" :data="radarData" :padding="[20, 20, 95, 20]" :scale="scale">
                <v-tooltip></v-tooltip>
                <v-axis :dataKey="axis1Opts.dataKey" :line="axis1Opts.line" :tickLine="axis1Opts.tickLine" :grid="axis1Opts.grid"/>
                <v-axis :dataKey="axis2Opts.dataKey" :line="axis2Opts.line" :tickLine="axis2Opts.tickLine" :grid="axis2Opts.grid"/>
                <v-legend dataKey="user" marker="circle" :offset="30"/>
                <v-coord type="polar" radius="0.8"/>
                <v-line position="item*score" color="user" :size="2"/>
                <v-point position="item*score" color="user" :size="4" shape="circle"/>
              </v-chart>
            </div>
          </a-card>
        </a-col>
      </a-row>
      <PersonRelation v-if="personRelationShow" :selectPersonId="selectPersonId"
                      @close="closePersonRelation"></PersonRelation>
      <CarRelation v-if="carRelationShow" :selectCarId="selectCarId" :selectCarNumber="selectCarNumber"
                   :selectCarColor="selectCarColor" :selectCarType="selectCarType" :selectPhotoUrl="selectPhotoUrl"
                   @close="closeCarRelation"></CarRelation>
      <panelImg :center="false" v-if="panelImgShow" @leave="closePanelImg" :title="panelTitle" :imgUrl="imgUrl"></panelImg>

    </div>
  </page-layout>
</template>

<script>
  import {timeFix} from "@/utils/util"
  import {mapGetters} from "vuex"
  import panelImg from '@/components/big/panelImg'

  import PageLayout from '@/components/page/PageLayout'
  import HeadInfo from '@/components/tools/HeadInfo'
  import Radar from '@/components/chart/Radar'
  import {getRoleList, getServiceList,getAction,putAction} from "@/api/manage"
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import PersonRelation from '@/views/modules/community/modules/PersonRelation'
  import CarRelation from '@/views/modules/community/modules/CarRelation'
  import {
    getPersonMonitorList,
    getCarMonitorList,
    getTodayStat,
    getTodayCarStat,
    getMonitorPersonTypeStat,
    getMonitorMessage,
    getFangJianPerson,
    getMonitorCarStat,
    getPersonList,
    getCarList,
    getDeviceList
  } from "@/api/big"

  const DataSet = require('@antv/data-set')

  export default {
    name: "Workplace",
    components: {
      PageLayout,
      panelImg,
      HeadInfo,
      CarRelation,
      PersonRelation,
      Radar
    },
    data() {
      const axis1Opts = {
        dataKey: 'item',
        line: null,
        tickLine: null,
        grid: {
          lineStyle: {
            lineDash: null
          },
          hideFirstLine: false
        }
      }
      const axis2Opts = {
        dataKey: 'score',
        line: null,
        tickLine: null,
        grid: {
          type: 'polygon',
          lineStyle: {
            lineDash: null
          }
        }
      }

      const scale = [
        {
          dataKey: 'score',
          min: 0,
          max: 100
        }, {
          dataKey: 'user',
          alias: '类型'
        }
      ]

      const sourceData = []
      return {
        timeFix: timeFix(),
        axis1Opts,
        axis2Opts,
        scale,
        avatar: '',
        user: {},
        tongji: {
          count: 0,
          A01A01: 0,
          A01A02: 0,
          A01A03: 0,
          A01A04: 0
        },
        projects: [],
        loading: true,
        radarLoading: true,
        activities: [],
        teams: [],
        radarData: sourceData,
        radarSourceData: [],
        url: {
          messageList: '/monitor/monitorMessage/list',
          personDetail: 'monitor/monitorPerson/queryById',
          readMessage: '/monitor/monitorMessage/read',
          projectList: "/monitor/monitorAlarmConfig/list",
        },
        imgUrl: '',
        panelTitle: '',
        panelImgShow: false,
        carRelationShow: false,
        personRelationShow: false,
        selectPersonId: '',
        selectCarId: '',
        selectCarNumber: '',
        selectCarColor: '',
        selectCarType: '',
        selectPhotoUrl: '',
        dictOptions: {
          alarmType: [],
          alarmRuleType: []
        },
      }
    },
    computed: {
      userInfo() {
        return this.$store.getters.userInfo
      }
    },
    created() {
      this.user = this.userInfo
      this.avatar = "/manager.png"
      console.log('this.avatar :' + this.avatar)

      getRoleList().then(res => {
        console.log('workplace -> call getRoleList()', res)
      })

      getServiceList().then(res => {
        console.log('workplace -> call getServiceList()', res)
      })

      this.initDictConfig()

      this.initRadar()
    },
    mounted() {
      this.getTongjiData()
      this.getProjects()
      this.getActivity()
      this.getTeams()
    },
    watch: {
      radarSourceData(list) {
        const arr = []
        let sum = 0
        list.forEach(item => {
          sum += item.score
        })
        list.forEach(item => {
          const o = {
            item: item.item,
            score: Math.floor(item.score*100/sum)
          }
          arr.push(o)
        })
        this.radarData = arr
      }
    },
    methods: {
      ...mapGetters(["nickname", "welcome"]),
      filterMultiDictText: filterMultiDictText,
      showPanelImg(data) {
        const panelData = data
        this.imgUrl = window._CONFIG['imgDomainRecordURL']+(panelData.panorama || panelData.photoUrl)
        this.panelTitle = panelData.address
        this.panelImgShow = true

        console.log(panelData)
      },
      closePanelImg() {
        this.panelImgShow = false
      },
      showPersonRelation(obj) {
        this.selectPersonId = obj.personId
        this.personRelationShow = true
      },
      closePersonRelation() {
        this.personRelationShow = false
      },
      showCarRelation(obj) {
        this.selectCarId = obj.carId
        this.selectCarNumber = obj.carNumber
        this.selectCarColor = obj.carColor
        this.selectCarType = obj.carType2
        this.selectPhotoUrl = obj.photoUrl
        this.carRelationShow = true
      },
      closeCarRelation() {
        this.carRelationShow = false
      },
      getMessageImg(item) {
        const messageMap = {
          1: '/message_yellow.png',
          2: '/message_yellow.png',
          20: '/message_green.png',
          30: '/message_green.png',
          40: '/message_red.png',
          50: '/message_red.png'
        }
        return messageMap[item.messageType] || ''
      },
      go2Page(name, type) {
        this.$router.push({
          name: name,
          params: {
            type: type
          }
        })
      },
      go2Project() {
        this.$router.push({
          path: '/modules/community/MonitorAlarmConfigList'
        })
      },
      go2Active() {
        this.$router.push({
          path: '/modules/community/MonitorMessageList'
        })
      },
      readMessage(id) {
        putAction(this.url.readMessage, {id:id,status:1}).then(res => {
          this.getActivity()
        })
      },
      initDictConfig() {
        initDictOptions('monitor_alarm_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'alarmType', res.result)
          }
        })
        initDictOptions('alarm__rule_type').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'alarmRuleType', res.result)
          }
        })
      },
      getProjects() {
        getAction(this.url.projectList, {pageNo:1,pageSize:6,status: 'Y'}).then(res => {
          this.projects = res.result.records
          this.getRecordCount()
          this.loading = false
        })
        /*
        this.$http.get('/api/list/search/projects')
          .then(res => {
            this.projects = res.result && res.result.data
            this.loading = false
          })*/
      },
      getRecordCount() {
        this.projects.forEach((item,index) => {
          getAction(this.url.messageList, {pageNo:1,pageSize:10,alarmConfigId: item.id}).then(res => {
            item.count = res.result.total
            item.loading = true
            if(item.alarmType === '10'){
              item.isPerson = true
              getAction(this.url.personDetail, {id: item.dataId}).then(res1 => {
                const data = res1.result || {}
                if(data.zhaoPian){
                  item.personPhoto = window._CONFIG['imgDomainURL']+'/'+data.zhaoPian
                }
                item.imgLoading = true
                this.$forceUpdate()
              })
            }else{
              item.imgLoading = true
            }
            this.$forceUpdate()
          })

        })
      },
      getTongjiData() {
        getMonitorPersonTypeStat().then(rel => {
          if(rel.code === 200) {
            const {count, A01A01,A01A02,A01A03,A01A04} = rel.result
            this.tongji.count = count || 0
            this.tongji.A01A01 = A01A01 || 0
            this.tongji.A01A02 = A01A02 || 0
            this.tongji.A01A03 = A01A03 || 0
            this.tongji.A01A04 = A01A04 || 0
          }
        })
      },
      getActivity() {
        getMonitorMessage({
          page: 1,
          column: 'createTime',
          status: 0,
          order: 'desc',
          pageSize: 20
        }).then(rel => {
          if(rel.code === 200) {
            const list = rel.result.records
            list.forEach(item => {
              if(item.dataContent&&item.dataContent.indexOf('{')>=0){
                item.dataContentObj = JSON.parse(item.dataContent)
              }else{
                item.dataContentObj = {}
              }
            })
            this.activities = list
          }
        })
      },
      getTeams() {
        this.$http.get('/api/workplace/teams')
          .then(res => {
            this.teams = res.result
          })
      },
      initRadar() {
        this.radarLoading = true
        this.radarSourceData = []
        getAction('/monitor/monitorPersonRecord/getTodayStat',{xiaoQuId:'1'}).then(res => {
          this.radarSourceData.push({
            item: '陌生人数量',
            score: res.result.anonymousCount || 0
          })
          this.radarSourceData.push({
            item: '社区成员数量',
            score: res.result.normalCount || 0
          })
        })
        getAction('/monitor/monitorCarRecord/getTodayStat',{xiaoQuId:'1'}).then(res => {
          this.radarSourceData.push({
            item: '陌生车辆数量',
            score: res.result.anonymousCount || 0
          })
          this.radarSourceData.push({
            item: '社区车辆数量',
            score: res.result.normalCount || 0
          })
        })

        getAction('/monitor/monitorDevice/getMonitorCarStat',{xiaoQuId:'1'}).then(res => {
          this.radarSourceData.push({
            item: '正常设备',
            score: res.result.zcCount || 0
          })
        })
        this.radarLoading = false
      }
    }
  }
</script>

<style lang="scss" scoped>
  .project-list {

    .card-title {
      font-size: 0;

      a {
        color: rgba(0, 0, 0, 0.85);
        margin-left: 12px;
        line-height: 24px;
        height: 24px;
        display: inline-block;
        vertical-align: top;
        font-size: 14px;

        &:hover {
          color: #1890ff;
        }
      }
    }

    .card-description {
      color: rgba(0, 0, 0, 0.45);
      height: 44px;
      line-height: 22px;
      overflow: hidden;
    }

    .project-item {
      display: flex;
      margin-top: 8px;
      overflow: hidden;
      font-size: 12px;
      height: 20px;
      line-height: 20px;

      a {
        color: rgba(0, 0, 0, 0.45);
        display: inline-block;
        flex: 1 1 0;

        &:hover {
          color: #1890ff;
        }
      }

      .datetime {
        color: rgba(0, 0, 0, 0.25);
        flex: 0 0 auto;
        float: right;
      }
    }

    .ant-card-meta-description {
      color: rgba(0, 0, 0, 0.45);
      height: 44px;
      line-height: 22px;
      overflow: hidden;
    }
  }

  .item-group {
    padding: 20px 0 8px 24px;
    font-size: 0;

    a {
      color: rgba(0, 0, 0, 0.65);
      display: inline-block;
      font-size: 14px;
      margin-bottom: 13px;
      width: 25%;
    }
  }

  .members {
    a {
      display: block;
      margin: 12px 0;
      line-height: 24px;
      height: 24px;

      .member {
        font-size: 14px;
        color: rgba(0, 0, 0, .65);
        line-height: 24px;
        max-width: 100px;
        vertical-align: top;
        margin-left: 12px;
        transition: all 0.3s;
        display: inline-block;
      }

      &:hover {
        span {
          color: #1890ff;
        }
      }
    }
  }

  .mobile {

    .project-list {

      .project-card-grid {
        width: 100%;
      }
    }

    .more-info {
      border: 0;
      padding-top: 16px;
      margin: 16px 0 16px;

    }

    .headerContent .title .welcome-text {
      display: none;
    }
  }
  .head-info{
    text-align: center;
  }

</style>