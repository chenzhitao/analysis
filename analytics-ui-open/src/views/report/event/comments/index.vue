<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <div class="bottomLine">
        <el-form-item label="应用:" label-width="40">
          <el-select v-model="queryParams.applicationCode" multiple placeholder="请选择应用">
            <el-option
              v-for="dict in applicationList"
              :key="dict.appCode"
              :label="dict.name"
              :value="dict.appCode"
            ></el-option>
          </el-select>
        </el-form-item>
        <div style="width:80px;display: block;float: right;">
          <el-link :underline="false" @click="resetQueryItem">
            <i class="el-icon-refresh"></i>重置
          </el-link>
        </div>
      </div>
<!--      <div  class="bottomLine">事件</div>-->
      <el-row ref="divSelectorA" id="divSelectorA" v-for="(formItem,indexA) in bussinessFilterOptions">

        <div style="line-height:30px;">
          <span class="index_letterJar_2bEZs">{{ formItem.itemName }}</span>
          <span v-show="formItem.labelFlag" class="typeItemLabel">{{formItem.eventTypeLabel}}</span>
          <el-input v-model="formItem.eventTypeLabel" style="width:300px" v-show="!formItem.labelFlag" @blur="cancelEditLabel(formItem)"></el-input>
          <el-link @click="editItemLabel(formItem)"><i class="el-icon-edit"></i></el-link>
        </div>
        <el-form-item label="" prop="status" style="display:block;padding-left:40px;">
          <el-popover
            placement="bottom-start"
            width="800"
            trigger="click"
          >
            <PanelSelect ref="panelSelect" @selected="selected" :eventObj="formItem"/>
            <el-input slot="reference" v-model="formItem.typeName" placeholder="点击选择事件" readonly
                      style="width: 200px;" suffix-icon="el-icon-arrow-down">
            </el-input>
          </el-popover>
          的
          <el-cascader
            v-model="formItem.eventMethod"
            :options="allEventItemMethodOpts[formItem.typeId]"
            :props="{ expandTrigger: 'hover',multiple:false}"
            @change="selectItemMethod(formItem,indexA)"
            ></el-cascader>
          <el-link type="primary" :underline="false" style="padding-left:20px;" @click="removeFormItem(indexA)">
            <i class="el-icon-remove"></i>
          </el-link>
           <el-link type="primary" :underline="false" style="padding-left:20px;" @click="addRule(formItem.eventItems)">
             <i class="el-icon-plus"> 筛选</i>
           </el-link>
           <!--
           <el-link type="primary" :underline="false" style="padding-left:20px;">
             <i class="el-icon-document-copy"> 复制</i>
           </el-link>
          <el-link type="primary" :underline="false" style="padding-left:20px;">
             <i class="el-icon-warning"> 预警</i>
           </el-link>
           -->
        </el-form-item>
        <el-form-item label="" style="display: block;padding-left:80px;" label-width="60px">
          <p v-for="(eventItem,index) in formItem.eventItems">
            <el-select v-model="eventItem.eventItem" style="width:120px" value-key="id">
              <el-option
                v-for="dict in  allEventItemListOpts[formItem.typeId]"
                :key="dict.id"
                :label="dict.eventItemName"
                :value="dict"
              />
            </el-select>
            <el-select v-model="eventItem.eventItemExpression" style="width:120px" >
              <el-option
                v-for="dict in eventPropertysOptions[eventItem.eventItem.valueType]"
                :key="dict.id"
                :label="dict.eventItemExpression"
                :value="dict.eventItemCode"
              />
            </el-select>
            <el-input v-model="eventItem.value1Str" type="text" controls-position="right"
                      style="width:100px"
                      v-show="eventItem.eventItemExpression!='between'"></el-input>
            <span v-show="eventItem.eventItemExpression=='between'">
              <el-input v-model="eventItem.value1Str" type="text" controls-position="right"
                        style="width:90px"></el-input>与
              <el-input v-model="eventItem.value2Str" type="text" controls-position="right"
                        style="width:90px"></el-input>之间
          </span>
            <el-link type="primary" :underline="false" style="padding-left:20px;font-size:18px"
                     @click="removeRule(formItem.eventItems,index)">
              <i class="el-icon-remove"></i>
            </el-link>
          </p>
        </el-form-item>
      </el-row>
      <el-row class="bottomLine">
        <div style="line-height:30px;">
          <el-link :underline="false" @click="addBussinssLine"><span class="index_letterJar_2bEZs">+ </span>指标</el-link>
        </div>
      </el-row>
      <!-- *************************目前是1.0阶段，先把功能按钮影藏，后续需要这些条件把注释放开即可****************************
      <el-form-item label="" prop="status" class="bottomLine">
        <el-link type="primary" :underline="false" style="padding-left:20px;" @click="globalAddRule">
          <i class="el-icon-plus"></i> <span class="innerContent">筛选条件</span>
        </el-link>
      </el-form-item>-->
      <el-row class="bottomLine" style="padding-left:20px;" v-show="globalRuleTypes1.length>0">
        <el-form-item label="" label-width="60px">
          <p v-for="(v,index) in globalRuleTypes1">
            <el-select v-model="globalRuleTypes1[index]" style="width:120px"
                       @change="eventTypeChange(globalRuleTypes1[index],index)">
              <el-option
                v-for="dict in eventTypeOptions"
                :key="dict.eventType"
                :label="dict.eventTypeName"
                :value="dict.eventType"
              />
            </el-select>
            <el-select v-model="globalRuleTypes2[index]" style="width:120px"
                       @change="eventItemChange(globalRuleTypes2[index],index)">
              <el-option
                v-for="dict in eventItemsOptions"
                :key="dict.eventItemCode"
                :label="dict.eventItemName"
                :value="dict.eventItemCode"
              />
            </el-select>
            <el-select v-model="globalRuleTypes3[index]" style="width:120px"
                       @change="globalEventPropertyChange(globalRuleTypes3[index],index)">
              <el-option
                v-for="dict in eventPropertysOptions"
                :key="dict.eventItemCode"
                :label="dict.eventItemExpression"
                :value="dict.eventItemCode"
              />
            </el-select>
            <el-input v-model="globalRuleTypes4[index]" type="text" controls-position="right" style="width:100px"
                      v-show="globalRuleTypes3[index]!='between'"></el-input>
            <span v-show="globalRuleTypes3[index]=='between'">
                <el-input v-model="globalRuleTypes5[index]" type="text" controls-position="right"
                          style="width:90px"></el-input>与
                <el-input v-model="globalRuleTypes6[index]" type="text" controls-position="right"
                          style="width:90px"></el-input>之间
            </span>
            <el-link type="primary" :underline="false" style="padding-left:20px;font-size:18px"
                     @click="globalRemoveRule(index)">
              <i class="el-icon-remove"></i>
            </el-link>
          </p>

        </el-form-item>
      </el-row>
      <!-- *************************目前是1.0阶段，先把功能按钮影藏，后续需要这些条件把注释放开即可****************************
            <el-form-item label="按" prop="status" class="bottomLine">
              <el-tag
                :key="tag.eventType"
                v-for="tag in dynamicTags"
                closable
                :disable-transitions="false"
                @close="handleClose(tag)">
                {{ tag.eventTypeName }}
              </el-tag>
              <el-select style="width:120px"
                         v-if="inputVisible"
                         v-model="inputValue"
                         ref="saveTagInput"
                         size="small"
                         value-key="id"
                         @keyup.enter.native="handleInputConfirm"
                         @change="handleInputConfirm">
                <el-option
                  v-for="dict in eventTypeOptions"
                  :key="dict.eventType"
                  :label="dict.eventTypeName"
                  :value="dict"
                />
              </el-select>
              <el-button v-else class="button-new-tag" size="small" @click="showInput">+</el-button>
              <el-link type="primary" :underline="false" style="padding-left:20px;">
                <i class="el-icon-setting"></i> <span class="innerContent">查看</span>
              </el-link>
            </el-form-item>-->
    </el-form>
    <el-container>

      <el-main>
        <el-row :gutter="10">
          <el-col :span="2">
            <el-select v-model="queryParams.timeType" placeholder="请选分析模型" clearable size="small">
              <el-option label="按天" value="01"></el-option>
              <el-option label="按周" value="02"></el-option>
              <el-option label="按月" value="03"></el-option>
              <el-option label="按季度" value="04"></el-option>
              <el-option label="按年" value="05"></el-option>
              <el-option label="按小时" value="06"></el-option>
              <el-option label="按分钟" value="07"></el-option>
            </el-select>
          </el-col>
          <el-col :span="5">
            <el-date-picker
              v-model="queryParams.dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              :picker-options="pickerOptions"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-col>
          <!--
          <el-col :span="2" v-show="!comparisonDateFlag">
            <el-dropdown v-model="queryParams.combine" @command="handleCommand" >
              <span class="el-dropdown-link">
                 <span class="innerContent">
                   <i class="el-icon-plus"></i>对比时间</span><i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="01">上一段时间</el-dropdown-item>
                <el-dropdown-item command="02">去年同期</el-dropdown-item>
                <el-dropdown-item command="03">自定义时间</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
          <el-col :span="8" v-show="comparisonDateFlag">
            <span style="margin-right:10px;">VS</span>
              <el-date-picker
                v-model="queryParams.comparisonDateRange"
                size="small"
                style="width: 240px"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="-"
                :picker-options="comparPickerOptions"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            <el-link type="primary" :underline="false" style="padding-left:10px;font-size:18px"
                     @click="removeComparisonData">
              <i class="el-icon-remove"></i>
            </el-link>
          </el-col>
          -->
          <el-col :span="6" style="float: right">
            <el-button-group>
              <el-button type="primary" plain @click="getChartDataList(1)">
                <svg-icon icon-class="chart-line" class-name='custom-class'/>
              </el-button>
              <el-button type="primary" plain  @click="getChartDataList(2)">
                <svg-icon icon-class="chart-histogram" class-name='custom-class'/>
              </el-button>
              <el-button type="primary" plain  @click="getChartDataList(3)">
                <svg-icon icon-class="chart-pie" class-name='custom-class'/>
              </el-button>
              <el-button type="primary" plain  @click="getChartDataList(4)">
                <svg-icon icon-class="chart-line-area" class-name='custom-class'/>
              </el-button>
            </el-button-group>


          </el-col>
        </el-row>
        <el-row :gutter="10">
          <div style="line-height:30px;width:300px;float:left;" v-for="(formItem,indexA) in bussinessFilterOptions">
            <span class="index_letterJar_2bEZs">{{ formItem.itemName }} </span>
            <el-tooltip :content="formItem.eventTypeLabel" effect="dark" placement="bottom">

              <el-link >
                {{formItem.eventTypeLabel}}
              </el-link>
            </el-tooltip>
          </div>
        </el-row>
        <event-line-chart :chart-data="eventChartData" :chart-title="eventChartTitle" :style="{width:'100%'}" v-if="chartTypeFlag==1"/>
        <event-bar-chart :chart-data="eventChartData" :chart-title="eventChartTitle" :style="{width:'100%'}" v-if="chartTypeFlag==2"/>
        <event-pie-chart :chart-data="eventChartData" :style="{width:'100%'}" v-if="chartTypeFlag==3"/>
        <event-area-chart :chart-data="eventChartData" :chart-title="eventChartTitle" :style="{width:'100%'}" v-if="chartTypeFlag==4"/>
        <el-table
          :data="eventTableData"
          style="width: 100%">
          <el-table-column
            v-for="(item, index) in eventTableTitle"
            :sortable="item.sortable"
            :key="index"
            :prop="item.columnName"
            :label="item.columnLabel"
            :formatter="formatCellDataFun"
            >
            <template slot="header" slot-scope="scope">
              <span v-if="queryParams.combine===''">
                <el-select v-model="queryParams.statisticsType" @change="staticticsTableDataFun" placeholder="请选分析模型" clearable size="small" style="width:100px" v-if="index===1">
                  <el-option label="合计" value="sum"></el-option>
                  <el-option label="均值" value="avg"></el-option>
                  <el-option label="最大值" value="max"></el-option>
                  <el-option label="最小值" value="min"></el-option>
                </el-select>
                <label v-else>{{item.columnLabel}}</label>
              </span>
              <label v-else>{{item.columnLabel}}</label>
            </template>
            <template slot-scope="scope">
              <span v-html="formatCellDataFun(scope.row,item.columnName)"></span>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :total="eventTableData.length"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="eventTableData"
        />
      </el-main>

    </el-container>
  </div>
</template>

<script>
import {getAllSiteFlowList,getChartDataOptions} from "@/api/analytics/basemeasure";
import EventLineChart from "@/views/report/event/comments/EventLineChart";
import EventBarChart from "@/views/report/event/comments/EventBarChart";
import EventPieChart from "@/views/report/event/comments/EventPieChart";
import EventAreaChart from "@/views/report/event/comments/EventAreaChart";
import PanelSelect from "@/components/PanelSelect";
import {allEventItemsOptions, eventItemsByType, eventItemsOptions, eventTypeOptions} from "@/api/config/item";
import {listAllPropertyResult, listPropertyByType} from "@/api/config/property";
import {getAllEventMethodResult, getEventMethodOptions} from "@/api/config/emethod";
import {listApplication} from "@/api/config/application";
import {parseTime} from "@/utils/ruoyi";
import {getItems} from "@/api/overview/items";

export default {
  name: "ascribe",
  components: {
    EventAreaChart,
    EventPieChart,
    EventBarChart,
    EventLineChart,
    PanelSelect
  },
  data() {
    let that = this
    return {
      showNum: 7,
      windowWidth:document.documentElement.clientWidth,
      checked: '',
      eventTypeObj:{},
      switchValue: false,
      startFlag: 'start',
      chartTypeFlag:1,
      allEventItemMethodOpts:{},
      allEventItemListOpts:{},
      eventPropertysOptions:{},
      applicationList:[],
      filterCodeOptions: [{itemName: 'A'}, {itemName: 'B'}, {itemName: 'C'}, {itemName: 'D'}, {itemName: 'E'}, {itemName: 'F'}, {itemName: 'G'}, {itemName: 'H'}, {itemName: 'I'}, {itemName: 'J'}],
      bussinessFilterOptions: [
        {
          typeName: '',
          typeId: '',
          typeItem: [],
          typeItemLabel:'',
          editLabelFlag:false,
          labelFlag:true,
          eventTypeLabel:(this.typeName?this.typeName:'任意事件')+'的'+(this.typeItem?this.typeItemLabel:'总次数'),
          itemName: 'A',
          eventMethod:'',
          eventItems:[]
        }
      ],
      typeItemoptions: [],
      statusOptions: [],
      params: {
        dateRange: ''
      },
      // 遮罩层
      loading: true,
      dynamicTags: [],
      inputVisible: false,
      inputValue: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        comparisonDateRange:[],
        dateRange:[parseTime(new Date(),'{y}-{m}-{d}'),parseTime(new Date(),'{y}-{m}-{d}')],
        combine:'',
        pageSize: 10,
        timeType:'01',
        applicationCode:[],
        statisticsType:'sum',
        name: undefined,
        status: undefined,
      },
      pickerOptions: {
        shortcuts: [{
          text: '今日',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '昨日',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 1);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '本周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            const day = start.getDay() || 7;
            var newStart = new Date(start.getFullYear(), start.getMonth(), start.getDate() + 1 - day);
            picker.$emit('pick', [newStart, end]);
          }
        }, {
          text: '上周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        } ]
      },
      comparPickerOptions: {
        shortcuts: [ {
          text: '上一段时间',
          onClick(picker) {
            that.pickerDataFun(picker,7);
          }
        }, {
          text: '去年同期',
          onClick(picker) {
            that.pickerDataFun(picker,365);
          }
        }]
      },
      drawer: false,
      direction: 'rtl',
      formLabelWidth: '90px',
      index: 1,
      methodList:[],
      eventItemMethosOpts:[],
      eventTypeOptions: [],
      eventItemsOptions: [],
      referrerTypeOptions: [],
      globalRuleTypes1: [],
      globalRuleTypes2: [],
      globalRuleTypes3: [],
      globalRuleTypes4: [],
      globalRuleTypes5: [],
      globalRuleTypes6: [],
      formItems: [
        {
          typeName: '',
          typeItem: '',
          ruleTypes1: [],
          ruleTypes2: [],
          ruleTypes3: [],
          ruleTypes4: [],
          ruleTypes5: [],
          ruleTypes6: [],
        }
      ],
      // 表单参数
      form: {},
      eventChartTitle:[],
      eventTableTitle:[],
      eventTableData:[],
      eventChartData:[],
      comparisonDateFlag:false,
      dateRangeData:[]
    };
  },
  watch: {
  },
  mounted() {
    const that = this
    window.onresize = () => {
      return (() => {
        window.screenWidth = document.body.clientWidth
        that.screenWidth = window.screenWidth
        this.windowWidth=that.screenWidth;
      })()
    }
		this.$EventBus.$on('askComponentQueryJson', () =>{
			this.askComponentQueryJson()
		})
	},

	beforeDestroy() {
		this.$EventBus.$off('askComponentQueryJson')
	},
  created() {
    let dataRange=this.$route.query.dateRange;
    let pageItemId=this.$route.query.pageItemId;
    if(pageItemId){
      getItems(pageItemId).then(response => {
        let queryObjs=JSON.parse(response.data.queryRule);
        this.bussinessFilterOptions=queryObjs.queryItems;
      });
    }
    if(dataRange){
      this.queryParams.dateRange=dataRange;
    }
    this.getEventTypeList();
    this.getAllMethodList();
    this.getAllEventItemsList();
    this.getApplicationList();
    this.getChartDataList(this.chartTypeFlag);
  },
  methods: {
    getApplicationList() {
      listApplication({status: 'Y'}).then(response => {
        this.applicationList = response.rows;
        let that=this;
        this.applicationList.forEach(function(app){
          if(app.defaultFlag==='Y'){
            that.queryParams.applicationCode.push(app.appCode);
          }
        });
        let appArray=that.$route.query.applicationCode;
        if(appArray instanceof Array){
          that.queryParams.applicationCode=appArray;
        }
        else if(appArray){
          that.queryParams.applicationCode=[];
          that.queryParams.applicationCode.push(appArray)
        }
      });
    },

		//提交queryJson给 sensorsdata
		askComponentQueryJson() {
			this.queryParams.queryItems=this.bussinessFilterOptions;
			let requestJson=JSON.stringify(this.queryParams);
			this.$EventBus.$emit('commitComponentQueryJson', requestJson)
		},
    getEventTypeList() {
      var param = {status: 'Y'};
      eventTypeOptions(param).then(response => {
        this.eventTypeOptions = response.data;
      });
    },
    getAllEventItemsList(){
      allEventItemsOptions().then(response => {
        this.allEventItemListOpts = response.data;
      });
      listAllPropertyResult().then(response => {
        this.eventPropertysOptions = response.data;
      });
      getAllEventMethodResult().then(response => {
        this.allEventItemMethodOpts = response.data;
      });
    },
    getSummaries(param) {
      const {columns, data} = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总价';
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
          sums[index] += ' 元';
        } else {
          sums[index] = 'N/A';
        }
      });

      return sums;
    },
    getItemsListByType(item) {
      let index=this.filterCodeOptions.findIndex((t) => t.itemName === item.itemName);
      let param = {eventType: item.eventType};
      eventItemsByType(param).then(response => {
        let ary=new Array();
        ary.push(response.data[0]);
        console.log('&*&*&*&*&*&&&&&&&&&&&&&&&&&')
        console.log(ary);
        this.eventItemMethosOpts[index]=ary;
      });
    },
    getAllMethodList(){
      var param={status:'Y'};
      getEventMethodOptions(param).then(response => {
        this.methodList = response.data;
      });
    },

    getChartDataList(type){
      this.chartTypeFlag=type;
      this.queryParams.chartType=type;
      this.queryParams.queryItems=this.bussinessFilterOptions;
      let requestJson=JSON.stringify(this.queryParams);
      this.queryParams.filterTags=this.dynamicTags;
      getChartDataOptions({requestJson:requestJson}).then(response => {
        this.eventChartData = response.data.chartData;
        this.eventChartTitle = response.data.chartTitle;
        this.eventTableTitle= response.data.tableTitle;
        this.eventTableData= response.data.tableData;
      });
    },
    selected(item,eventItem) {
      eventItem.typeName = item.eventTypeName;
      eventItem.eventType=item.eventType;
      eventItem.typeId=item.id;
      this.getItemsListByType(eventItem);
      //this.$alert(item.eventTypeName);
    },

    addBussinssLine() {
      if(this.bussinessFilterOptions.length>9){
        this.$notify({
          title: '警告',
          message: '目前仅支持查询10个事件分析维度！',
          type: 'warning'
        });
      }else{
        var itemFilter={
          typeName: '',
          typeItem: '',
          editLabelFlag:false,
          labelFlag:true,
          eventTypeLabel:(this.typeName?this.typeName:'任意事件')+'的'+(this.typeItem?this.typeItemLabel:'总次数'),
          itemName: this.filterCodeOptions[this.bussinessFilterOptions.length].itemName,
          eventItems:[]
        };
        this.bussinessFilterOptions.push(itemFilter);
      }
    },
    addRule(formItem) {
      let eventItem={
          eventId:undefined,
          eventItem:{},
          eventItemExpression:"",
          valueType:"",
          value1Str:"",
          value2Str:""
      };
      formItem.push(eventItem);
    },

    removeRule(formItems,index) {
      formItems.splice(index, 1);
    },

    globalAddRule() {
      this.globalRuleTypes1.push(this.globalRuleTypes1[this.globalRuleTypes1.length - 1]);
      this.globalRuleTypes2.push(this.globalRuleTypes2[this.globalRuleTypes2.length - 1]);
      this.globalRuleTypes3.push(this.globalRuleTypes3[this.globalRuleTypes3.length - 1]);
      this.globalRuleTypes4.push(this.globalRuleTypes4[this.globalRuleTypes4.length - 1]);
      this.globalRuleTypes5.push(this.globalRuleTypes5[this.globalRuleTypes5.length - 1]);
      this.globalRuleTypes6.push(this.globalRuleTypes6[this.globalRuleTypes6.length - 1]);
    },

    globalRemoveRule(index) {
      this.globalRuleTypes1.splice(index, 1);
      this.globalRuleTypes2.splice(index, 1);
      this.globalRuleTypes3.splice(index, 1);
      this.globalRuleTypes4.splice(index, 1);
      this.globalRuleTypes5.splice(index, 1);
      this.globalRuleTypes6.splice(index, 1);
    },


    getTemplateRow(index, row) {
      this.templateRadio = row.id;
      this.userTagsId = row.id;
    },
    globalEventPropertyChange(val, index) {

    },
    getEventItemsList(type) {
      var param = {eventType: type};
      eventItemsOptions(param).then(response => {
        this.eventItemsOptions = response.data;
      });
    },
    getItemsPropertyList(type) {
      var param = {valueType: type};
      listPropertyByType(param).then(response => {
        this.eventPropertysOptions = response.data;
      });
    },
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag.eventTypeName), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm(val) {
      if (val) {
        this.dynamicTags.push(val);
      }
      console.log(this.dynamicTags)
      this.inputVisible = false;
      this.inputValue = '';
    },
    editItemLabel(formItem){
      formItem.labelFlag=false;
      formItem.editLabelFlag=true;
    },
    cancelEditLabel(formItem){
      formItem.labelFlag=true;
    },

    selectMethodFun(formItem) {
      var obj = {}
      obj = this.methodList.find(function(item){
        return item.id === formItem.typeItem;
      });
      formItem.typeItemLabel=obj.methodName;
    },
    removeFormItem(index){
      this.bussinessFilterOptions.splice(index, 1);
      for(let bindex=0;bindex<this.bussinessFilterOptions.length;bindex++){
         if(bindex>=index){
           this.bussinessFilterOptions[bindex].itemName=this.filterCodeOptions[bindex].itemName;
         }
      }

    },
    resetQueryItem(){
      this.bussinessFilterOptions.splice(1, this.bussinessFilterOptions.length-1);
      this.bussinessFilterOptions[0].eventItems=[];
      this.globalRuleTypes1.splice(0, this.globalRuleTypes1.length);
      this.globalRuleTypes2.splice(0, this.globalRuleTypes2.length);
      this.globalRuleTypes3.splice(0, this.globalRuleTypes3.length);
      this.globalRuleTypes4.splice(0, this.globalRuleTypes4.length);
      this.globalRuleTypes5.splice(0, this.globalRuleTypes5.length);
      this.globalRuleTypes6.splice(0, this.globalRuleTypes6.length);
    },
    staticticsTableDataFun(){
      if(this.queryParams.statisticsType==='sum'){
        this.eventTableData.forEach((data,index)=>{
           let rowResult=0;
          for(let j=0;j<this.eventTableTitle.length;j++){
            if(j>1) {
              rowResult+=data[this.eventTableTitle[j]].data;
            }
          }
          data['statictics'].data=Number(rowResult).toFixed(2);
        });
      }
      else if(this.queryParams.statisticsType==='avg'){
        this.eventTableData.forEach((data,index)=>{
          let rowResult=0;
          for(let j=0;j<this.eventTableTitle.length;j++){
            if(j>1) {
              rowResult+=data[this.eventTableTitle[j]].data;
            }
          }
          let avgData=rowResult/(this.eventTableTitle.length-2);
          data['statictics'].data=Number(avgData).toFixed(2);
        });
      }
      else if(this.queryParams.statisticsType==='max'){
        this.eventTableData.forEach((data,index)=>{
          let rowResult=0;
          for(let j=0;j<this.eventTableTitle.length;j++){
            if(j>1) {
              let cellData=data[this.eventTableTitle[j]].data;
              if(rowResult<cellData){
                rowResult=cellData;
              }
            }
          }
          data['statictics'].data=rowResult;
        });
      }
      else if(this.queryParams.statisticsType==='min'){
        this.eventTableData.forEach((data,index)=>{
          let rowResult=999999999;
          for(let j=0;j<this.eventTableTitle.length;j++){
            if(j>1) {
              let cellData=data[this.eventTableTitle[j]].data;
              if(rowResult>cellData){
                rowResult=cellData;
              }
            }
          }
          data['statictics'].data=rowResult;
        });
      }
    },
    formatCellDataFun(row, column){
      let cellDataValue;
      if(column==='statictics'){
        if(this.queryParams.statisticsType==='sum') {
          let rowResult = 0;
          for (let j = 0; j < this.eventTableTitle.length; j++) {
            if (j > 1) {
              rowResult += row[this.eventTableTitle[j]].data;
            }
          }
          cellDataValue= Number(rowResult).toFixed(2);
        }else{
          cellDataValue= row[column].data;
        }
      }else{
        cellDataValue=  row[column].data;
      }
      if(this.queryParams.combine==='01' || this.queryParams.combine==='02'){
          if(column==='指标'){
            return cellDataValue;
          }
          let comparData=Number(row[column].comparison).toFixed(2);
          let percent=((cellDataValue-comparData)/cellDataValue).toFixed(2);
          let dataStr="<div><div style='font-weight: 400;color: #1f2d3d;width:60%;text-align: right;margin-left: 8px;float:left;margin-right:5px;'>"
            +cellDataValue+"</div><div style='font-weight: 400;color: #8492a6;width:60%;text-align: right;margin-left: 8px;float:left;margin-right:5px;'>"
            +comparData+"</div><div style='width: 30%;text-align: right;white-space: nowrap;color: #00bf8a;height:40px;line-height:40px;'>+"+percent+"%</div></div>";
          return dataStr;
      }
      else{
        return cellDataValue;
      }
    },

    selectItemMethod(formItem,index){
      let itemArray=this.allEventItemMethodOpts[formItem.typeId];
      if(formItem.eventMethod.length===2){
        let method=formItem.eventMethod[1];
        let m=this.methodList.find(m=>m.methodStr===method);
        formItem.eventTypeLabel=formItem.typeName+'的'+m.methodName;
      }else{
        let method=formItem.eventMethod[0];
        let m=this.methodList.find(m=>m.methodStr===method);
        formItem.eventTypeLabel=formItem.typeName+'的'+m.methodName;
      }

    },

    handleCommand(command) {
      this.queryParams.combine=command;
      this.comparisonDateFlag=true;
      //this.$message('click on item ' + command);
    },
    removeComparisonData(){
      this.comparisonDateFlag=false;
    },

    pickerDataFun(picker,day){
      const start = new Date(this.queryParams.dateRange[0]);
      const end = new Date(this.queryParams.dateRange[1]);
      start.setTime(start.getTime() - 3600 * 1000 * 24 * day);
      end.setTime(end.getTime() - 3600 * 1000 * 24 * day);
      picker.$emit('pick', [start, end]);
    }
  }


};
</script>
<style lang="scss" scoped>
.el-form-item {
  margin-bottom: 10px;
}

.innerContent {
  font-size: 14px;
  line-height: 20px;
  color: #307ef2;
  cursor: pointer;
}

.bottomLine {
  width: 100%;
  display: inline-block;
  border-bottom: 1px solid #e9f0f7;
  margin-bottom: 10px;
  padding-bottom: 10px;
}

.index_letterJar_2bEZs {
  border-radius: 100%;
  display: inline-block;
  width: 20px;
  height: 20px;
  background: #04cb94;
  line-height: 20px;
  font-size: 12px;
  margin: 0 8px 0 6px;
  color: #fff;
  text-align: center;
}

.typeItemLabel{
  line-height:25px;
  margin-right:10px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  font-size: 20px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.index-cell_data3{
  width: 52px;display: inline-block;text-align: right;white-space: nowrap;color: #00bf8a;
}

</style>
