<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="搜索标题">
              <a-input placeholder="请输入搜索标题" v-model="queryParam.searchTitle"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a-button @click="handleAdd" type="primary" icon="plus" style="margin-left:8px">新增</a-button>
              <a-dropdown v-if="selectedRowKeys.length > 0">
                <a-menu slot="overlay">
                  <a-menu-item key="1" @click="batchDel">
                    <a-icon type="delete"/>
                    删除
                  </a-menu-item>
                </a-menu>
                <a-button style="margin-left: 8px"> 批量操作
                  <a-icon type="down"/>
                </a-button>
              </a-dropdown>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
      <div>
        <a-tabs type="card">
          <a-tab-pane key="1">
            <span slot="tab">
              <a-icon type="table" style="font-size:20px;"/>
              表格
            </span>

            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :columns="columns"
              :dataSource="dataSource"
              :pagination="ipagination"
              :loading="loading"
              :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"

              @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <viewer>
            <img :src="text"
                 :bigImg="text"
                 height="25px" alt=""
                 style="max-width:80px;font-size: 12px;font-style: italic;"/>
          </viewer>

              </template>
              <template slot="fileSlot" slot-scope="text">
                <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
                <a-button
                  v-else
                  :ghost="true"
                  type="primary"
                  icon="download"
                  size="small"
                  @click="uploadFile(text)">
                  下载
                </a-button>
              </template>

              <span slot="action" slot-scope="text, record">
                <a @click="handleEdit(record)">编辑</a>

                <a-divider type="vertical"/>

                      <!--<a @click="handleView(record)">查看监控</a>-->
                <a @click="handleResult(record)">查看结果</a>

                <a-divider type="vertical"/>
                <a-dropdown>
                  <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
                  <a-menu slot="overlay">
                    <a-menu-item>
                      <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                        <a>删除</a>
                      </a-popconfirm>
                    </a-menu-item>
                  </a-menu>
                </a-dropdown>
              </span>

            </a-table>
          </a-tab-pane>
          <a-tab-pane key="2">
          <span slot="tab">
            <a-icon type="appstore" style="font-size:20px;"/>
            列表
          </span>
            <a-list :grid="{ gutter: 16, xs: 1, sm: 2, md: 2, lg: 3, xl: 3, xxl: 3 }"
                    size="small"
                    @change="handleListChange"
                    :data-source="dataSource">
              <a-list-item slot="renderItem" slot-scope="item, index">
                <a-card :title="''">
                  <div class="person_card">
                    <div class="person_card_photo">
                      <!--                    <img :src="getPanoramaImgViewRecord(item.imgSlot,item)"-->
                      <!--                         @click="showPanelImg(item)"-->
                      <!--                         alt="图片不存在"-->
                      <!--                         style="max-width:500px;font-size: 12px;font-style: italic;"/>-->

                      <viewer>
                        <img :src="item.searchImgBase64"
                             :bigImg="item.searchImgBase64"
                             alt=""
                             style="max-width:500px;font-size: 12px;font-style: italic;"/>
                      </viewer>
<!--                      <img src="/manager.png"-->
<!--                           alt="图片不存在"-->
<!--                           style="max-width:500px;font-size: 12px;font-style: italic;"/>-->

                    </div>
                    <div class="person_card_info">
                      <div class="person_card_info_row">
                        <a-row>
                          <a-col span="24">
                          <span style="font-size:16px;padding:10px 0;line-height: 20px;">
                             {{item.searchTitle}}
                          </span>
                          </a-col>
                        </a-row>
                      </div>
<!--                      <div class="person_card_info_row">-->
<!--                        开始时间：{{item.beginTime}}-->
<!--                      </div>-->
<!--                      <div class="person_card_info_row">-->
<!--                        结束时间：{{item.endTime}}-->
<!--                      </div>-->
                      <div class="person_card_info_row">
                        创建时间：{{item.createTime}}
                      </div>
                      <div class="person_card_info_row">
                        状态：{{item.searchStatus?filterMultiDictText(dictOptions['monitor_search_status'],
                        item.searchStatus+""):''}}
                      </div>
                      <div class="person_card_info_row">
                        <a @click="handleEdit(item)">编辑</a>

                        <a-divider type="vertical"/>

                        <!--<a @click="handleView(record)">查看监控</a>-->
                        <a @click="handleResult(item)">查看结果</a>

                        <a-divider type="vertical"/>
                        <a-dropdown>
                          <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
                          <a-menu slot="overlay">
                            <a-menu-item>
                              <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(item.id)">
                                <a>删除</a>
                              </a-popconfirm>
                            </a-menu-item>
                          </a-menu>
                        </a-dropdown>
                      </div>
                    </div>
                  </div>
                </a-card>
              </a-list-item>
            </a-list>
            <div style="text-align: right;">
              <a-pagination v-model="ipagination.current"
                            v-bind:pageSize="ipagination.pageSize"
                            v-bind:showTotal="ipagination.showTotal"
                            v-bind:total="ipagination.total"
                            v-bind:pageSizeOptions="ipagination.pageSizeOptions"
                            v-bind:showQuickJumper="ipagination.showQuickJumper"
                            v-bind:showSizeChanger="ipagination.showSizeChanger"
                            @change="handleListChange"
                            @showSizeChange="handleListChange"
                            size="small"
              ></a-pagination>
            </div>

          </a-tab-pane>
        </a-tabs>
      </div>
    </div>
    <a-modal
      v-if="selectRecord"
      title="监控记录"
      :width="1200"
      v-model="jkVisible"
      :footer="null">
      <SelectMonitorPersonRecordList v-if="jkVisible"
                                     :selectId="selectRecord.searchContent"></SelectMonitorPersonRecordList>
    </a-modal>
    <a-modal
      title="结果记录"
      :width="1200"
      v-model="resultShow"
      :footer="null">
      <div>
        <span style="padding-right:20px;">检索结果数量：<span style="color:#1890ff;">{{getResultCount()}}</span></span>
        <span v-if="searchStatus">
        搜索状态：<span :style="'color:#'+(searchStatus==='30'?'8ada54':'1890ff')+';'">{{statusMap[searchStatus]}}
        <span v-if="time>0">.</span>
        <span v-if="time%5===1">.</span>
        <span v-if="time%5===2">..</span>
        <span v-if="time%5===3">...</span>
        <span v-if="time%5===4">....</span>
        </span></span>
      </div>
      <div style="padding:10px 0">
        <a-button type="primary" style="margin-right:10px;" @click="refreshResult">刷新</a-button>
      </div>
      <MonitorSearchResultList ref="resultList"
                               v-if="resultShow"
                               :searchId="selectRecord.id"></MonitorSearchResultList>
    </a-modal>
    <monitorSearchTask-modal
      ref="modalForm" @ok="modalFormOk"></monitorSearchTask-modal>
  </a-card>
</template>

<script>

  import {filterObj} from '@/utils/util';
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import SelectMonitorPersonRecordList from './modules/SelectMonitorPersonRecordList'
  import MonitorSearchTaskModal from './modules/MonitorSearchTaskModal'
  import MonitorSearchResultList from './MonitorSearchResultList'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import {getAction} from '@/api/manage'

  export default {
    name: "MonitorSearchTaskList",
    mixins: [JeecgListMixin],
    components: {
      SelectMonitorPersonRecordList,
      MonitorSearchTaskModal,
      MonitorSearchResultList
    },
    watch: {
      resultShow(v) {
        if (!v) {
          this.initData()
          this.loadData()
        }
      }
    },
    data() {
      return {
        statusMap: {
          10: '搜索中',
          20: '搜索中',
          30: '已完成'
        },
        searchStatus: '',
        resultShow: false,
        selectRecord: undefined,
        description: '搜索任务管理页面',
        jkVisible: false,
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: "center",
            customRender: function (t, r, index) {
              return parseInt(index) + 1;
            }
          },
          {
            title: '搜索标题',
            align: "center",
            dataIndex: 'searchTitle'
          },
          {
            title: '搜索图片',
            align: "center",
            dataIndex: 'searchImgBase64',
            scopedSlots: {customRender: 'imgSlot'}
          },
          // {
          //   title: '开始时间',
          //   align: "center",
          //   dataIndex: 'beginTime'
          // },
          // {
          //   title: '结束时间',
          //   align: "center",
          //   dataIndex: 'endTime'
          // },
          {
            title: '创建时间',
            align: "center",
            dataIndex: 'createTime'
          },
          {
            title: '状态',
            align: "center",
            dataIndex: 'searchStatus',
            customRender: (text) => {
              if (!text) {
                return ''
              } else {
                return filterMultiDictText(this.dictOptions['monitor_search_status'], text + "")
              }
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'}
          }
        ],
        url: {
          getStatus: "/monitor/monitorSearchTask/queryById",
          list: "/monitor/monitorSearchTask/list",
          delete: "/monitor/monitorSearchTask/delete",
          deleteBatch: "/monitor/monitorSearchTask/deleteBatch",
          exportXlsUrl: "/monitor/monitorSearchTask/exportXls",
          importExcelUrl: "monitor/monitorSearchTask/importExcel",
        },
        time: 0,
        timeInterval: undefined,
        dictOptions: {
          monitor_search_status: []
        },
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      filterMultiDictText,
      handleListChange(page, pageSize) {
        console.log("handleListChange")
        this.ipagination.current = page;
        this.ipagination.pageSize = pageSize;
        this.loadData();
      },
      modalFormOk(record) {
        this.loadData(1)
        this.handleResult(record)
      },
      // showPanelImg(data) {
      //   const panelData = data
      //   this.imgUrl = window._CONFIG['imgDomainRecordURL']+(panelData.panorama || panelData.photoUrl)
      //   this.panelTitle = panelData.address
      //   this.panelImgShow = true
      //
      //   console.log(panelData)
      // },
      // closePanelImg() {
      //   this.panelImgShow = false
      // },
      showTimeCount() {
        this.time = 1
        if (this.timeInterval) {
          clearInterval(this.timeInterval)
        }
        this.searchStatus = '10'
        const that = this
        this.timeInterval = setInterval(() => {
          this.time++
          if (this.time % 5 === 0) {
            that.searchResultList()
          }
        }, 1000)
      },
      getResultStatus() {
        getAction(this.url.getStatus, {id: this.selectRecord.id}).then(res => {
          console.log(res)
          if (res.success) {
            this.searchStatus = res.result.searchStatus + ''
            if (this.searchStatus === '30') {
              if (this.timeInterval) {
                clearInterval(this.timeInterval)
                this.time = 0
              }
            }
          }
        })
      },
      initData(status) {
        this.timeType = '0'
        this.time = 0
        this.searchStatus = status || ''
        if (this.timeInterval) {
          clearInterval(this.timeInterval)
        }
      },
      searchResultList() {
        this.$nextTick(() => {
          this.getResultStatus()
          this.$refs.resultList.loadData()
        })
      },
      refreshResult() {
        this.$nextTick(() => {
          this.$refs.resultList.loadData(1)
        })
      },
      getResultCount() {
        if (this.$refs.resultList) {
          return this.$refs.resultList.ipagination.total
        }
        return 0
      },
      getTimeStr() {
        if (this.time / (3600) >= 1) {
          return Math.floor(this.time / 3600) + '小时' + Math.floor(this.time % 3600 / 60) + '分钟' + this.time % 60 + '秒'
        } else if (this.time / (60) >= 1) {
          return Math.floor(this.time % 3600 / 60) + '分钟' + this.time % 60 + '秒'
        } else if (this.time > 0) {
          return this.time % 60 + '秒'
        }
      },
      getQueryParams() {
        //获取查询条件
        let sqp = {}
        if (this.superQueryParams) {
          sqp['superQueryParams'] = encodeURI(this.superQueryParams)
        }
        var param = Object.assign(sqp, this.queryParam, this.isorter, this.filters);
        if (param.searchTitle != null) {
          param.searchTitle = "*" + param.searchTitle + "*"
        }

        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      handleView(record) {
        this.selectRecord = record
        this.jkVisible = true
      },
      handleResult(record) {
        this.selectRecord = record
        console.log(this.selectRecord)
        this.$nextTick(() => {
          this.initData(record.searchStatus)
          this.resultShow = true
          if(record.searchStatus === '30') {
            this.$refs.resultList.loadData()
          } else {
            console.log(record)
            // if( === '30') {
            //   this.searchStatus = record.searchStatus
            // }
            this.showTimeCount()
          }

        })

        //MonitorSearchResultList
      },
      initDictConfig() {

        initDictOptions('monitor_search_status').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'monitor_search_status', res.result)
          }
        })
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';

  .person_card {
    position: relative;
    margin: -24px -32px;
  }

  .person_card_photo {
    position: absolute;
    left: 10px;
    top: 10px;
    width: 200px;
    height: 220px;
    line-height: 30px;
    text-align: center;
  }

  .person_card_photo img {
    display: block;
    width: 200px;
    height: 200px;
    padding: 0;
    margin: 0;
  }

  .person_card_info {
    margin-left: 210px;
    min-height: 220px;
    padding: 10px;
  }

  .ant-card-wider-padding .ant-card-body {
    padding: 0;
  }

  .person_card_info_row {
    line-height: 20px;
    padding: 4px 0px;
  }
</style>