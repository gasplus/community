<template>
  <a-card :bordered="false" class="no_padding">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <div class="person_list_table">
      <a-row v-for="(o,row) in new Array(dataSource.length%2===0?dataSource.length/2:(dataSource.length+1)/2)" :key="row">
        <a-col :span="12" v-for="(person,index) in dataSource.filter((itt,ind)=> ind>=row*2&&ind<=row*2+1)" :key="index">
          <div class="person_item">
            <div class="person_item_img">
              <img :src="imgBasePath+person.photoUrl"
                   slot="cover" alt="">
            </div>
            <div class="person_item_info">
              <div class="person_item_p">
                <div class="person_item_p_l">姓名：</div>
                <div class="person_item_p_r">{{person.personName}}</div>
              </div>
              <div class="person_item_p">
                <div class="person_item_p_l">身份证号：</div>
                <div class="person_item_p_r">{{person.personIdCard}}</div>
              </div>
              <div class="person_item_p">
                <div class="person_item_p_l">户籍地址：</div>
                <div class="person_item_p_r">{{person.hjdz}}</div>
              </div>
              <div class="person_item_p">
                <div class="person_item_p_l">进出时间：</div>
                <div class="person_item_p_r">{{person.outInTime}}</div>
              </div>
              <div class="person_item_p">
                <div class="person_item_p_l">进出地址：</div>
                <div class="person_item_p_r">{{person.address}}安联大厦会计法拉可接受的弗拉基数了大富科技阿里上看到街坊邻居</div>
              </div>
            </div>
          </div>
        </a-col>
      </a-row>
      <div style="text-align: right;">
        <a-pagination
          showSizeChanger
          :pageSize.sync="ipagination.pageSize"
          @showSizeChange="onShowSizeChange"
          :total="ipagination.total"
          v-model="ipagination.current"
          @change="changePage"
        />
      </div>
    </div>

    <!-- table区域-begin -->
    <!--
    <div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"

        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="imgBasePath+text" height="25px" alt="图片不存在"
               style="max-width:80px;font-size: 12px;font-style: italic;"/>
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
        <span slot="idCard" slot-scope="text">
          {{text==='anonymous'?'':text}}
        </span>

      </a-table>
    </div>
    -->

    <monitorSearchResult-modal ref="modalForm" @ok="modalFormOk"></monitorSearchResult-modal>
  </a-card>
</template>

<script>

  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import MonitorSearchResultModal from './modules/MonitorSearchResultModal'

  export default {
    name: "MonitorSearchResultList",
    mixins: [JeecgListMixin],
    components: {
      MonitorSearchResultModal
    },
    props: {
      searchId: {
        type: String,
        default() {
          return '123'
        }
      }
    },
    data() {
      return {
        imgBasePath: window._CONFIG['imgDomainRecordURL'],
        description: '查询结果管理页面',
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
            title: '姓名',
            align: "center",
            dataIndex: 'personName'
          },
          {
            title: '身份证',
            align: "center",
            dataIndex: 'personIdCard',
            scopedSlots: {customRender: 'idCard'}
          },
          {
            title: '户籍地址',
            align: "center",
            dataIndex: 'hjdz'
          },
          {
            title: '进出时间',
            align: "center",
            dataIndex: 'outInTime'
          },
          {
            title: '图片地址',
              align: "center",
            dataIndex: 'photoUrl',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title: '进出地址',
              align: "center",
            dataIndex: 'address'
          }
        ],
        url: {
          list: "/monitor/monitorSearchResult/list",
          delete: "/monitor/monitorSearchResult/delete",
          deleteBatch: "/monitor/monitorSearchResult/deleteBatch",
          exportXlsUrl: "/monitor/monitorSearchResult/exportXls",
          importExcelUrl: "monitor/monitorSearchResult/importExcel",
        },
        dictOptions: {},
        disableMixinCreated: true
      }
    },
    mounted() {
      console.log(this.searchId)
      if(this.searchId){
        this.queryParam.searchId = this.searchId
        this.loadData();
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      onShowSizeChange(page, pageSize) {
        this.ipagination.pageSize = pageSize
        this.loadData(1)
      },
      changePage(current, size) {
        this.loadData(current)
      },
      initDictConfig() {
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'

  .person_list_table{
    padding:10px 0;
    margin-bottom:10px;
  }
  .person_item{
    margin-right:10px;
    position:relative;
    background:rgba(24,144,255,0.4);
    min-height: 140px;
    border-radius: 5px;
    margin-bottom:10px;
    background:#f7f7f7;
    border:1px solid #ccc;
  }
  .person_item_img{
    width:100px;
    height:120px;
    position:absolute;
    left:10px;
    top:10px;
    background:#ccc;
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
    border-bottom:1px dashed #ccc;
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
    min-height: 18px;
  }
</style>
<style>
  .no_padding.ant-card-wider-padding .ant-card-body{
    padding:0;
  }
</style>