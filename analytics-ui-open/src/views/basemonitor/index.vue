<template>
  <div class="dashboard-editor-container">
    <el-row :gutter="4" class="panel-group">

      <el-col :span="16"  style="margin-bottom:5px;text-align: right;float:right;">
        <el-select v-model="params.applicationCode" collapse-tags multiple placeholder="请选择应用" @change="handleDateQuery">
          <el-option
            v-for="dict in applicationList"
            :key="dict.appCode"
            :label="dict.name"
            :value="dict.appCode"
          ></el-option>
        </el-select>
        <el-date-picker
          v-model="params.dateRange"
          @select=""
          size="small"
          style="width: 240px;height:35px"
          value-format="yyyy-MM-dd"
          type="daterange"
          @change="handleDateQuery"
          range-separator="-"
          :picker-options="pickerOptions"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>


<!--        <el-tooltip content="筛选条件" effect="dark" placement="bottom">-->
<!--          <el-button icon="el-icon-search" style="width:35px;height:34px" size="mini" @click="handleFilterQuery"></el-button>-->
<!--        </el-tooltip>-->
        <el-tooltip content="刷新数据" effect="dark" placement="bottom">
          <el-button icon="el-icon-refresh" style="width:35px;height:34px;margin-left:1px" size="mini" @click="handleDateQuery"></el-button>
        </el-tooltip>
      </el-col>
    </el-row>
    <el-row :gutter="10" class="panel-group">
      <el-col :xs="12" :sm="16" :lg="6" class="card-panel-col">
        <div id="siteFlow" v-loading="setFlowLoading" class="card-panel" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <div >
              <router-link  :to="{ path: '/report/event', query: { applicationCode: params.applicationCode,dateRange:params.dateRange }}" class="panel-head">全站流量</router-link>
            </div>
            <div class="content-range">
              <span>{{totalNumData.beginTime}}~{{totalNumData.endTime}} </span>| <span v-html="totalNumData.today"></span>

            </div>
            <span class="content-today" v-html="totalNumData.showDate"></span>
            <div>
              <span class="number" v-html="totalNumData.totalNum"></span>次
            </div>
            <div class="content-combine">
              环比
            <span class="content-percent-top" v-if="totalNumData.totalNum>=totalNumData.totalNumA"><i class="el-icon-caret-top"></i>
            {{calculateIncreaseFun(totalNumData.totalNum,totalNumData.totalNumA)}}
            </span>&nbsp;
            <span class="content-percent-low" v-if="totalNumData.totalNum<totalNumData.totalNumA"><i class="el-icon-caret-bottom"></i>
            {{calculateIncreaseFun(totalNumData.totalNum,totalNumData.totalNumA)}}
            </span>&nbsp;
              同比
              <span class="content-percent-top" v-if="totalNumData.totalNum>=totalNumData.totalNumB"><i class="el-icon-caret-top"></i>
              {{calculateIncreaseFun(totalNumData.totalNum,totalNumData.totalNumB)}}</span>
              <span class="content-percent-low" v-if="totalNumData.totalNum<totalNumData.totalNumB"><i class="el-icon-caret-bottom"></i>
              {{calculateIncreaseFun(totalNumData.totalNum,totalNumData.totalNumB)}}</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div v-loading="allSiteFlowLoading" class="card-panel" @click="handleSetLineChartData('messages')">
          <div class="panel-content">
            <div ><a class="panel-head">全站日活</a></div>
            <div class="content-range">
              <span>{{dayNumData.beginTime}}~{{dayNumData.endTime}} </span>| <span v-html="dayNumData.today"></span>
            </div>
            <span class="content-today" v-html="dayNumData.showDate"></span>
            <div>
              <span class="number" v-html="dayNumData.dayNum"></span>次
            </div>
            <div class="content-combine">
              环比
              <span class="content-percent-top" v-if="dayNumData.dayNum>=dayNumData.dayNumA"><i class="el-icon-caret-top"></i>
              {{calculateIncreaseFun(dayNumData.dayNum,dayNumData.dayNumA)}}
              </span>&nbsp;
              <span class="content-percent-low" v-if="dayNumData.dayNum<dayNumData.dayNumA"><i class="el-icon-caret-bottom"></i>
              {{calculateIncreaseFun(dayNumData.dayNum,dayNumData.dayNumA)}}
              </span>&nbsp;
              同比
              <span class="content-percent-top" v-if="dayNumData.dayNum>=dayNumData.dayNumB"><i class="el-icon-caret-top"></i>
              {{calculateIncreaseFun(dayNumData.dayNum,dayNumData.dayNumB)}}</span>
              <span class="content-percent-low" v-if="dayNumData.dayNum<dayNumData.dayNumB"><i class="el-icon-caret-bottom"></i>
              {{calculateIncreaseFun(dayNumData.dayNum,dayNumData.dayNumB)}}
              </span>

            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div v-loading="regFlowLoading" class="card-panel" @click="handleSetLineChartData('messages')">
          <div class="panel-content">
            <div ><a class="panel-head">注册用户数</a></div>
            <div class="content-range">
              <span>{{loginUsernumData.beginTime}}~{{loginUsernumData.endTime}} </span>| <span>上线至今</span>
            </div>
            <span class="content-today"  v-html="loginUsernumData.showDate"></span>
            <div>
              <span class="number" v-html="loginUsernumData.loginUserNum"></span>次
            </div>
            <div class="content-combine">
              环比
              <span class="content-percent-top" v-if="loginUsernumData.loginUserNum>=loginUsernumData.loginUserNumA"><i class="el-icon-caret-top"></i>
              {{calculateIncreaseFun(loginUsernumData.loginUserNum,loginUsernumData.loginUserNumA)}}
              </span>&nbsp;
              <span class="content-percent-low" v-if="loginUsernumData.loginUserNum<loginUsernumData.loginUserNumA"><i class="el-icon-caret-bottom"></i>
              {{calculateIncreaseFun(loginUsernumData.loginUserNum,loginUsernumData.loginUserNumA)}}
              </span>&nbsp;
              同比
              <span class="content-percent-top" v-if="loginUsernumData.loginUserNum>=loginUsernumData.loginUserNumB"><i class="el-icon-caret-top"></i>
              {{calculateIncreaseFun(loginUsernumData.loginUserNum,loginUsernumData.loginUserNumB)}}
              </span>&nbsp;
              <span class="content-percent-low" v-if="loginUsernumData.loginUserNum<loginUsernumData.loginUserNumB"><i class="el-icon-caret-bottom"></i>
              {{calculateIncreaseFun(loginUsernumData.loginUserNum,loginUsernumData.loginUserNumB)}}
              </span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div v-loading="subOrderFlowLoading" class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="panel-content">
            <div ><a class="panel-head">下单人数</a></div>
            <div class="content-range">
              <span>{{submitOrderNumData.beginTime}}~{{submitOrderNumData.endTime}} </span>| <span>上线至今</span>
            </div>
            <span class="content-today" v-html="submitOrderNumData.showDate"></span>
            <div>
              <span class="number" v-html="submitOrderNumData.submitOrderNum"></span>人
            </div>
            <div class="content-combine">
              环比
              <span class="content-percent-top" v-if="submitOrderNumData.submitOrderNum>=submitOrderNumData.submitOrderNumA"><i class="el-icon-caret-top"></i>
              {{calculateIncreaseFun(submitOrderNumData.submitOrderNum,submitOrderNumData.submitOrderNumA)}}
              </span>&nbsp;
              <span class="content-percent-low" v-if="submitOrderNumData.submitOrderNum<submitOrderNumData.submitOrderNumA"><i class="el-icon-caret-bottom"></i>
              {{calculateIncreaseFun(submitOrderNumData.submitOrderNum,submitOrderNumData.submitOrderNumA)}}
              </span>&nbsp;
              同比
              <span class="content-percent-top" v-if="submitOrderNumData.submitOrderNum>=submitOrderNumData.submitOrderNumB"><i class="el-icon-caret-top"></i>
              {{calculateIncreaseFun(submitOrderNumData.submitOrderNum,submitOrderNumData.submitOrderNumB)}}
              </span>&nbsp;
              <span class="content-percent-low" v-if="submitOrderNumData.submitOrderNum<submitOrderNumData.submitOrderNumB"><i class="el-icon-caret-bottom"></i>
              {{calculateIncreaseFun(submitOrderNumData.submitOrderNum,submitOrderNumData.submitOrderNumB)}}
              </span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row style="background:#fff;margin-top:5px;margin-bottom:5px;">
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div v-loading="gmvFlowLoading" class="card-panel" style="height:370px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content" style="padding-top:5px">
            <div ><a class="panel-head">GMV</a></div>
            <div class="content-range">
              <span>{{gmvSevenData.gmvStartDate}}~{{gmvSevenData.gmvEndDate}} </span>| <span>过去7天</span>
            </div>
            <span class="content-today" style="margin-top:8px;">{{gmvSevenData.gmvEndDate}}（{{gmvSevenData.gmvWeek}}）</span>
            <el-row>
              <el-col  :lg="5" class="card-panel-col">
                <div>
                  <span class="number">{{gmvSevenData.gmvData}}</span>万
                </div>
              </el-col>
              <el-col  :lg="4" class="card-panel-col">
                <div class="content-combine" style="line-height:24px;">
                  环比<span class="content-percent-top" v-if="gmvSevenData.gmvDataA>0"><i class="el-icon-caret-top"></i>{{gmvSevenData.gmvDataA}}%</span>
                  <span class="content-percent-low" v-if="gmvSevenData.gmvDataA<=0"><i class="el-icon-caret-bottom"></i>{{gmvSevenData.gmvDataA}}%</span><br>
                  同比<span class="content-percent-top" v-if="gmvSevenData.gmvDataB>0"><i class="el-icon-caret-top"></i>{{gmvSevenData.gmvDataB}}%</span>
                  <span class="content-percent-low" v-if="gmvSevenData.gmvDataB<=0"><i class="el-icon-caret-bottom"></i>{{gmvSevenData.gmvDataB}}%</span>
                </div>
              </el-col>
              <el-col  :lg="6" class="card-panel-col" :offset="8">
                <div>
                  合计 <span class="content-number">{{ gmvSevenData.gmvTotal }} </span>万<br>
                  均值 <span class="content-number">{{ gmvSevenData.gmvAvg }} </span>万
                </div>
              </el-col>
            </el-row>
            <event-line-chart :chart-data="gmv7ChartData" :chart-title="gmv7ChartTitle" :style="{width:'100%',height:'250px'}"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <el-row :gutter="10" style="padding-top:10px;">
          <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
            <div v-loading="orderNumFlowLoading" class="card-panel" @click="handleSetLineChartData('newVisitis')">
              <div class="panel-content">
                <div ><a class="panel-head">订单量</a></div>
                <div class="content-range">
                  <span>{{orderVolumeNumData.beginTime}}~{{orderVolumeNumData.endTime}} </span>| <span>上线至今</span>
                </div>
                <span class="content-today" v-html="orderVolumeNumData.showDate"></span>
                <div>
                  <span class="number" v-html="orderVolumeNumData.orderVolumeNum"></span>次
                </div>
                <div class="content-combine">
                  环比
                  <span class="content-percent-top" v-if="orderVolumeNumData.orderVolumeNum>=orderVolumeNumData.orderVolumeNumA"><i class="el-icon-caret-top"></i>
                  {{calculateIncreaseFun(orderVolumeNumData.orderVolumeNum,orderVolumeNumData.orderVolumeNumA)}}
                  </span>&nbsp;
                  <span class="content-percent-low" v-if="orderVolumeNumData.orderVolumeNum<orderVolumeNumData.orderVolumeNumA"><i class="el-icon-caret-bottom"></i>
                  {{calculateIncreaseFun(orderVolumeNumData.orderVolumeNum,orderVolumeNumData.orderVolumeNumA)}}
                  </span>&nbsp;
                  同比
                   <span class="content-percent-top" v-if="orderVolumeNumData.orderVolumeNum>=orderVolumeNumData.orderVolumeNumB"><i class="el-icon-caret-top"></i>
                  {{calculateIncreaseFun(orderVolumeNumData.orderVolumeNum,orderVolumeNumData.orderVolumeNumB)}}
                  </span>&nbsp;
                  <span class="content-percent-low" v-if="orderVolumeNumData.orderVolumeNum<orderVolumeNumData.orderVolumeNumB"><i class="el-icon-caret-bottom"></i>
                  {{calculateIncreaseFun(orderVolumeNumData.orderVolumeNum,orderVolumeNumData.orderVolumeNumB)}}
                  </span>
                </div>
              </div>
            </div>
          </el-col>
          <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
            <div v-loading="orderTransFlowLoading" class="card-panel" @click="handleSetLineChartData('newVisitis')">
              <div class="panel-content">
                <div ><a class="panel-head">下单转化率</a></div>
                <div class="content-range">
                  <span>{{payOrderNumData.beginTime}}~{{payOrderNumData.endTime}} </span>| <span>今日</span>
                </div>
                <span class="content-today" v-html="payOrderNumData.showDate"></span>
                <div>
                  <span class="number" v-html="payOrderNumData.payOrderNum"></span>%
                </div>
                <div class="content-combine">
                  环比
                  <span class="content-percent-top" v-if="payOrderNumData.payOrderNumA>=payOrderNumData.payOrderNum"><i class="el-icon-caret-top"></i>
                      <span v-html="payOrderNumData.payOrderNumA"></span>%
                  </span>&nbsp;
                  <span class="content-percent-low" v-if="payOrderNumData.payOrderNumA<payOrderNumData.payOrderNum"><i class="el-icon-caret-bottom"></i>
                      <span v-html="payOrderNumData.payOrderNumA"></span>%
                  </span>&nbsp;

                  同比
                  <span class="content-percent-top" v-if="payOrderNumData.payOrderNumB>=payOrderNumData.payOrderNum"><i class="el-icon-caret-top"></i>
                   <span v-html="payOrderNumData.payOrderNumB"></span>%
                  </span>&nbsp;
                  <span class="content-percent-low" v-if="payOrderNumData.payOrderNumB<payOrderNumData.payOrderNum"><i class="el-icon-caret-bottom"></i>
                      <span v-html="payOrderNumData.payOrderNumB"></span>%
                  </span>&nbsp;

                </div>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="10" style="margin-top:10px;">
          <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
            <div v-loading="avgPriceFlowLoading" class="card-panel" @click="handleSetLineChartData('newVisitis')">
              <div class="panel-content">
                <div ><a class="panel-head">客单价</a></div>
                <div class="content-range">
                  <span>{{avgPriceNumData.beginTime}}~{{avgPriceNumData.endTime}} </span>| <span>本周</span>
                </div>
                <span class="content-today" v-html="avgPriceNumData.showDate"></span>
                <div>
                  <span class="number" v-html="avgPriceNumData.avgPriceNum"></span>元
                </div>
                <div class="content-combine">
                  环比
                  <span class="content-percent-top" v-if="avgPriceNumData.avgPriceNum>=avgPriceNumData.avgPriceNumA"><i class="el-icon-caret-top"></i>
                  {{calculateIncreaseFun(avgPriceNumData.avgPriceNum,avgPriceNumData.avgPriceNumA)}}
                  </span>&nbsp;
                  <span class="content-percent-low" v-if="avgPriceNumData.avgPriceNum<avgPriceNumData.avgPriceNumA"><i class="el-icon-caret-bottom"></i>
                  {{calculateIncreaseFun(avgPriceNumData.avgPriceNum,avgPriceNumData.avgPriceNumA)}}
                  </span>&nbsp;
                  同比
                  <span class="content-percent-top" v-if="avgPriceNumData.avgPriceNum>=avgPriceNumData.avgPriceNumB"><i class="el-icon-caret-top"></i>
                  {{calculateIncreaseFun(avgPriceNumData.avgPriceNum,avgPriceNumData.avgPriceNumB)}}
                  </span>&nbsp;
                  <span class="content-percent-low" v-if="avgPriceNumData.avgPriceNum<avgPriceNumData.avgPriceNumB"><i class="el-icon-caret-bottom"></i>
                  {{calculateIncreaseFun(avgPriceNumData.avgPriceNum,avgPriceNumData.avgPriceNumB)}}
                  </span>

                </div>
              </div>
            </div>
          </el-col>
          <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
            <div v-loading="avgEventFlowLoading" class="card-panel" @click="handleSetLineChartData('newVisitis')">
              <div class="panel-content">
                <div ><a class="panel-head">App平均使用时长</a></div>
                <div class="content-range">
                  <span>{{avgEventTimeNumData.beginTime}}~{{avgEventTimeNumData.endTime}} </span>| <span>今日</span>
                </div>
                <span class="content-today" v-html="avgEventTimeNumData.showDate"></span>
                <div>
                  <span class="number" v-html="avgEventTimeNumData.avgEventTimeNum"></span>
                </div>
                <div class="content-combine">
                  环比
                  <span class="content-percent-top" v-if="avgEventTimeNumData.avgEventTimeNum>=avgEventTimeNumData.avgEventTimeNumA"><i class="el-icon-caret-top"></i>
                  {{calculateIncreaseFun(avgEventTimeNumData.avgEventTimeNum,avgEventTimeNumData.avgEventTimeNumA)}}
                  </span>&nbsp;
                  <span class="content-percent-low" v-if="avgEventTimeNumData.avgEventTimeNum<avgEventTimeNumData.avgEventTimeNumA"><i class="el-icon-caret-bottom"></i>
                  {{calculateIncreaseFun(avgEventTimeNumData.avgEventTimeNum,avgEventTimeNumData.avgEventTimeNumA)}}
                  </span>&nbsp;
                  同比
                  <span class="content-percent-top" v-if="avgEventTimeNumData.avgEventTimeNum>=avgEventTimeNumData.avgEventTimeNumB"><i class="el-icon-caret-top"></i>
                  {{calculateIncreaseFun(avgEventTimeNumData.avgEventTimeNum,avgEventTimeNumData.avgEventTimeNumB)}}
                  </span>&nbsp;
                  <span class="content-percent-low" v-if="avgEventTimeNumData.avgEventTimeNum<avgEventTimeNumData.avgEventTimeNumB"><i class="el-icon-caret-bottom"></i>
                  {{calculateIncreaseFun(avgEventTimeNumData.avgEventTimeNum,avgEventTimeNumData.avgEventTimeNumB)}}
                  </span>&nbsp;
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>

    <!--    GMV近30天变化趋势-->
    <el-row style="background:#fff;margin-top:5px;padding:5px 5px 0;margin-bottom:5px;">
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div v-loading="gmvThirtyFlowLoading" class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <div ><a class="panel-head">GMV近30天变化趋势</a></div>
            <div class="content-range">
              <span>{{gmvThirtyData.gmvStartDate}}~{{gmvThirtyData.gmvEndDate}} </span>| <span>过去 30 天</span>
            </div>

            <span class="content-today">{{gmvThirtyData.gmvEndDate}}（{{gmvThirtyData.gmvWeek}}）</span>
            <el-row>
              <el-col  :lg="5" class="card-panel-col">
                <div>
                  <span class="number">{{gmvThirtyData.gmvData}}</span>万
                </div>
              </el-col>
              <el-col  :lg="4" class="card-panel-col">
                <div class="content-combine" style="line-height:24px;">
                  环比<span class="content-percent-top" v-if="gmvThirtyData.gmvDataA>0"><i class="el-icon-caret-top"></i>{{gmvThirtyData.gmvDataA}}%</span>
                  <span class="content-percent-low" v-if="gmvThirtyData.gmvDataA<=0"><i class="el-icon-caret-bottom"></i>{{gmvThirtyData.gmvDataA}}%</span><br>
                  同比<span class="content-percent-top" v-if="gmvThirtyData.gmvDataB>0"><i class="el-icon-caret-top"></i>{{gmvThirtyData.gmvDataB}}%</span>
                  <span class="content-percent-low" v-if="gmvThirtyData.gmvDataB<=0"><i class="el-icon-caret-bottom"></i>{{gmvThirtyData.gmvDataB}}%</span>
                </div>
              </el-col>
              <el-col  :lg="6" class="card-panel-col" :offset="8">
                <div>
                  合计 <span class="content-number">{{ gmvThirtyData.gmvTotal }} </span>万<br>
                  均值 <span class="content-number">{{ gmvThirtyData.gmvAvg }} </span>万
                </div>
              </el-col>
            </el-row>
            <event-line-chart :chart-data="gmv30ChartData" :chart-title="gmv30ChartTitle" :style="{width:'100%',height:'250px'}"/>
          </div>
        </div>
      </el-col>

       <!--    过去7天日活-->
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div v-loading="dailyLifeFlowLoading" class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <div ><a class="panel-head">过去7天日活</a></div>
            <div class="content-range">
              <span>{{dailyLifeListData.gmvStartDate}}~{{dailyLifeListData.gmvEndDate}} </span>| <span>过去 7 天</span>
            </div>

            <span class="content-today">{{dailyLifeListData.gmvEndDate}}（{{dailyLifeListData.gmvWeek}}）</span>
            <el-row>
              <el-col  :span="8" class="card-panel-col">
                <div>
                  <span class="number">{{dailyLifeListData.gmvData}}</span>人
                </div>
              </el-col>
              <el-col  :span="8" class="card-panel-col">
                <div class="content-combine" style="line-height:24px;">
                  环比<span class="content-percent-top" v-if="dailyLifeListData.gmvDataA>0"><i class="el-icon-caret-top"></i>{{dailyLifeListData.gmvDataA}}%</span>
                  <span class="content-percent-low" v-if="dailyLifeListData.gmvDataA<=0"><i class="el-icon-caret-bottom"></i>{{dailyLifeListData.gmvDataA}}%</span><br>
                  同比<span class="content-percent-top" v-if="dailyLifeListData.gmvDataB>0"><i class="el-icon-caret-top"></i>{{dailyLifeListData.gmvDataB}}%</span>
                  <span class="content-percent-low" v-if="dailyLifeListData.gmvDataB<=0"><i class="el-icon-caret-bottom"></i>{{dailyLifeListData.gmvDataB}}%</span>
                </div>
              </el-col>
              <el-col  :span="8" class="card-panel-col">
                <div>
                  合计 <span class="content-number">{{ dailyLifeListData.gmvTotal }} </span>人<br>
                  均值 <span class="content-number">{{ dailyLifeListData.gmvAvg }}</span>人
                </div>
              </el-col>
            </el-row>
            <event-line-chart :chart-data="dailyLifeListChartData" :chart-title="dailyLifeListChartTitle" :style="{width:'100%',height:'250px'}"/>
          </div>
        </div>
      </el-col>
    </el-row>

    <!--    各端过去7天日活-->
    <el-row style="background:#fff;margin-top:5px;padding:5px 5px 0;margin-bottom:5px;">
<!--      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">-->
<!--        <div class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">-->
<!--          <div class="panel-content">-->
<!--            <div ><a class="panel-head">各端过去7天日活</a></div>-->
<!--            <div class="content-range">-->
<!--              <span>{{platFormlistData.gmvStartDate}}~{{platFormlistData.gmvEndDate}} </span>| <span>过去 7 天</span>-->
<!--            </div>-->
<!--             <span class="content-today">{{platFormlistData.gmvEndDate}}（{{platFormlistData.gmvWeek}}）</span>-->
<!--                <el-row>-->
<!--                  <el-col  :lg="5" class="card-panel-col">-->
<!--                    <div>-->
<!--                      <span class="number">{{platFormlistData.gmvData}}</span>人-->
<!--                    </div>-->
<!--                  </el-col>-->
<!--                  <el-col  :lg="4" class="card-panel-col">-->
<!--                    <div class="content-combine" style="line-height:24px;">-->
<!--                      环比<span class="content-percent-top" v-if="platFormlistData.gmvDataA>0"><i class="el-icon-caret-top"></i>{{platFormlistData.gmvDataA}}%</span>-->
<!--                      <span class="content-percent-low" v-if="platFormlistData.gmvDataA<=0"><i class="el-icon-caret-bottom"></i>{{platFormlistData.gmvDataA}}%</span><br>-->
<!--                      同比<span class="content-percent-top" v-if="platFormlistData.gmvDataB>0"><i class="el-icon-caret-top"></i>{{platFormlistData.gmvDataB}}%</span>-->
<!--                      <span class="content-percent-low" v-if="platFormlistData.gmvDataB<=0"><i class="el-icon-caret-bottom"></i>{{platFormlistData.gmvDataB}}%</span>-->
<!--                    </div>-->
<!--                  </el-col>-->
<!--                  <el-col  :lg="6" class="card-panel-col" :offset="8">-->
<!--                    <div>-->
<!--                      合计 <span class="content-number">{{ platFormlistData.gmvTotal }} </span>人<br>-->
<!--                      均值 <span class="content-number">{{ platFormlistData.gmvAvg }}</span>人-->
<!--                    </div>-->
<!--                  </el-col>-->
<!--                </el-row>-->

<!--            <event-line-chart :chart-data="platFormlistChartData" :chart-title="platFormlistChartTitle" :style="{width:'100%',height:'250px'}"/>-->
<!--          </div>-->
<!--        </div>-->
<!--      </el-col>-->
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <div ><a class="panel-head">各端过去7天日活</a></div>
            <div class="content-range">
              <span>{{platFormlistData.gmvStartDate}}~{{platFormlistData.gmvEndDate}}</span>| <span>过去 7 天</span>

            </div>
            <event-line-chart :chart-data="sevenDayLiveChartData" :chart-legend="legend" :chart-title="sevenDayLiveChartTitle" :style="{width:'100%',height:'350px'}"/>
          </div>
        </div>
      </el-col>

      <!--过去7天新访问用户数-->
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div v-loading="newUserLoading" class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <div ><a class="panel-head">过去7天新访问用户数</a></div>
            <div class="content-range">
              <span>{{newUserlistData.gmvStartDate}}~{{newUserlistData.gmvEndDate}} </span>| <span> 过去 7 天</span>

            </div>
            <span class="content-today">{{newUserlistData.gmvEndDate}}（{{newUserlistData.gmvWeek}}）</span>
            <el-row>
              <el-col  :lg="5" class="card-panel-col">
                <div>
                  <span class="number">{{newUserlistData.gmvData}}</span>人
                </div>
              </el-col>
              <el-col  :lg="4" class="card-panel-col">
                <div style="line-height:24px;">
                  环比<span class="content-percent"><i class="el-icon-caret-top"></i>{{newUserlistData.gmvDataA}}%</span><br>
                  同比<span class="content-percent-low"><i class="el-icon-caret-top"></i>{{newUserlistData.gmvDataB}}%</span>
                </div>
              </el-col>
              <el-col  :lg="6" class="card-panel-col" :offset="8">
                <div>
                  合计 <span class="content-number">{{ newUserlistData.gmvTotal }} </span>人<br>
                  均值 <span class="content-number">{{ newUserlistData.gmvAvg }} </span>人
                </div>
              </el-col>
            </el-row>
            <event-line-chart :chart-data="newUserlistChartData" :chart-title="newUserlistChartTitle" :style="{width:'100%',height:'250px'}"/>
          </div>
        </div>
      </el-col>
    </el-row>

    <!--   过去7天新访问用户占比-->
    <el-row style="background:#fff;margin-top:5px;padding:5px 5px 0;margin-bottom:5px;">
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div v-loading="userTagemLoading" class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <div ><a class="panel-head">过去7天新访问用户占比</a></div>
            <div class="content-range">
              <span>{{userTagemListData.gmvStartDate}}~{{userTagemListData.gmvEndDate}} </span>| <span> 过去 7 天</span>
            </div>
            <span class="content-today">{{userTagemListData.gmvEndDate}}（{{userTagemListData.gmvWeek}}）</span>
            <el-row>
              <el-col  :span="10" class="card-panel-col">
                <div>
                  <span class="number">{{userTagemListData.gmvData}}</span>%
                </div>
              </el-col>
              <el-col  :span="8" class="card-panel-col">
                <div class="content-combine" style="line-height:24px;">
                  环比<span class="content-percent-top" v-if="userTagemListData.gmvDataA>0"><i class="el-icon-caret-top"></i>{{userTagemListData.gmvDataA}}%</span>
                  <span class="content-percent-low" v-if="userTagemListData.gmvDataA<=0"><i class="el-icon-caret-bottom"></i>{{userTagemListData.gmvDataA}}%</span><br>
                  同比<span class="content-percent-top" v-if="userTagemListData.gmvDataB>0"><i class="el-icon-caret-top"></i>{{userTagemListData.gmvDataB}}%</span>
                  <span class="content-percent-low" v-if="userTagemListData.gmvDataB<=0"><i class="el-icon-caret-bottom"></i>{{userTagemListData.gmvDataB}}%</span>
                </div>
              </el-col>
              <el-col  :span="6" class="card-panel-col">
                <div>
                  合计 <span class="content-number">{{ userTagemListData.gmvTotal }}  </span>人<br>
                  均值 <span class="content-number">{{ userTagemListData.gmvAvg }} </span>人
                </div>
              </el-col>
            </el-row>
           <event-line-chart :chart-data="userTagemListChartData" :chart-title="userTagemListChartTitle" :style="{width:'100%',height:'250px'}"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div v-loading="keepInfoLoading" class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <a class="panel-head">留存率</a>
              </el-col>
              <el-col :span="1.5"  :offset="16">
                <a v-bind:class="tableShowFlag==1?'active':'unActive'" @click="showTableReport(1)">表格</a>
              </el-col>
              <el-col :span="1.5">
                <a v-bind:class="tableShowFlag==2?'active':'unActive'" @click="showTableReport(2)">变化</a>
              </el-col>
              <el-col :span="1.5">
                <a v-bind:class="tableShowFlag==3?'active':'unActive'" @click="showTableReport(3)">趋势</a>
              </el-col>
            </el-row>
            <div class="content-range">
              <span>{{ keepInfoData.startDate }}~{{ keepInfoData.endDate }}</span>| <span>过去 {{ keepInfoData.keepDay }} 天</span>

            </div>
            <el-row>
              <!-- :sortable="item.sortable"   :summary-method="getKeepData" -->
              <el-table
                v-show="tableShowFlag==1"
                :data="keepTableData"
                border
                height="320"

                show-summary
                style="width: 100%; margin-top:10px">
                <el-table-column
                  v-for="(item, index) in keepTableTitle"
                  :key="index"
                  :prop="item.columnName"
                  :width="100"
                  :label="item.columnLabel"
                />
              </el-table>
              <other-line-chart :chart-data="lineChartData" width="620px" v-show="tableShowFlag==2"/>
              <other-line-chart :chart-data="lineChartData" width="620px" v-show="tableShowFlag==3"/>
            </el-row>

          </div>
        </div>
      </el-col>
    </el-row>

    <el-row style="background:#fff;margin-top:5px;padding:5px 5px 0;margin-bottom:5px;">
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div v-loading="weekVisitLoading" class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <a class="panel-head">周访问频次</a>
              </el-col>
              <el-col :span="1.5"  :offset="16">
                <a v-bind:class="weekVisitFlag==1?'active':'unActive'" @click="weekVisitReport(1)">图表</a>
              </el-col>
              <el-col :span="1.5">
                <a v-bind:class="weekVisitFlag==2?'active':'unActive'" @click="weekVisitReport(2)">表格</a>
              </el-col>
            </el-row>
            <div class="content-range">
              <span>2021-04-10~2021-04-16</span>| <span>过去 7 天</span>

            </div>
            <el-row>
              <div class="chart-wrapper" v-show="weekVisitFlag==1">
                <bar-chart />
              </div>
              <el-table
                v-show="weekVisitFlag==2"
                :data="tableData"
                border
                height="320"
                :summary-method="getSummaries"
                show-summary
                style="width: 100%; margin-top:10px">
                <el-table-column
                  prop="id"
                  label="总体"
                  width="100">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="总人数">
                </el-table-column>
                <el-table-column
                  prop="amount1"
                  label="第一天">
                </el-table-column>
                <el-table-column
                  prop="amount2"
                  label="第二天">
                </el-table-column>
                <el-table-column
                  prop="amount3"
                  label="第三天">
                </el-table-column>
                <el-table-column
                  prop="amount3"
                  label="第四天">
                </el-table-column>
                <el-table-column
                  prop="amount3"
                  label="第五天">
                </el-table-column>
                <el-table-column
                  prop="amount3"
                  label="第六天">
                </el-table-column>
                <el-table-column
                  prop="amount3"
                  label="第七天">
                </el-table-column>
              </el-table>
            </el-row>
          </div>
        </div>
      </el-col>


      <!--   Web端平均访问时长-->
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div v-loading="webAvgDurationLoading" class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <div ><a class="panel-head">Web端平均访问时长</a></div>
            <div class="content-range">
              <span>{{webAvgDurationListData.gmvStartDate}}~{{webAvgDurationListData.gmvEndDate}} </span>| <span>过去 7 天</span>
            </div>
             <span class="content-today">{{webAvgDurationListData.gmvEndDate}}（{{webAvgDurationListData.gmvWeek}}）</span>
            <el-row>
              <el-col  :span="10" class="card-panel-col">
                <div>
                  <span class="number">{{webAvgDurationListData.gmvData}}</span>秒
                </div>
              </el-col>
              <el-col  :span="8" class="card-panel-col">
                <div class="content-combine" style="line-height:24px;">
                  环比<span class="content-percent-top" v-if="webAvgDurationListData.gmvDataA>0"><i class="el-icon-caret-top"></i>{{webAvgDurationListData.gmvDataA}}%</span>
                  <span class="content-percent-low" v-if="webAvgDurationListData.gmvDataA<=0"><i class="el-icon-caret-bottom"></i>{{webAvgDurationListData.gmvDataA}}%</span><br>
                  同比<span class="content-percent-top" v-if="webAvgDurationListData.gmvDataB>0"><i class="el-icon-caret-top"></i>{{webAvgDurationListData.gmvDataB}}%</span>
                  <span class="content-percent-low" v-if="webAvgDurationListData.gmvDataB<=0"><i class="el-icon-caret-bottom"></i>{{webAvgDurationListData.gmvDataB}}%</span>
                </div>
              </el-col>
              <el-col  :span="6" class="card-panel-col">
                <div>
                  合计 <span class="content-number">{{ webAvgDurationListData.gmvTotal }} </span>秒<br>
                  均值 <span class="content-number">{{ webAvgDurationListData.gmvAvg }}  </span>秒
                </div>
              </el-col>
            </el-row>
            <event-line-chart :chart-data="webAvgDurationListChartData" :chart-title="webAvgDurationListChartTitle" :style="{width:'100%',height:'250px'}"/>
          </div>
        </div>
      </el-col>

    </el-row>

    <!--   Web端平均访问深度-->
    <el-row style="background:#fff;margin-top:5px;padding:5px 5px 0;margin-bottom:5px;">
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <div ><a class="panel-head">Web端平均访问深度</a></div>
            <div class="content-range">
              <span>2021-04-10~2021-04-16 </span>| <span> 过去 7 天</span>
            </div>
            <span class="content-today">4-16 (五)</span>
            <el-row>
              <el-col  :lg="5" class="card-panel-col">
                <div>
                  <span class="number">4.55</span>
                </div>
              </el-col>
              <el-col  :lg="4" class="card-panel-col">
                <div style="line-height:24px;">
                  环比<span class="content-percent-low"><i class="el-icon-caret-bottom"></i>4.646</span><br>
                  同比<span class="content-percent-low"><i class="el-icon-caret-bottom"></i>4.64</span>
                </div>
              </el-col>
              <el-col  :lg="6" class="card-panel-col" :offset="8">
                <div>
                  合计 <span class="content-number">270 </span>秒<br>
                  均值 <span class="content-number">270.64 </span>秒
                </div>
              </el-col>
            </el-row>
            <line-chart :chart-data="lineChartData" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <a class="panel-head">注册转化漏斗</a>
              </el-col>
              <el-col :span="1.5"  :offset="16">
                <a v-bind:class="showFilterFlag==1?'active':'unActive'" @click="registerTransReport(1)">漏斗</a>
              </el-col>
              <el-col :span="1.5">
                <a v-bind:class="showFilterFlag==2?'active':'unActive'" @click="registerTransReport(2)">趋势</a>
              </el-col>
            </el-row>
            <div class="content-range">
              <span>{{funnelZcInfoData.gmvStartDate}}~{{funnelZcInfoData.gmvEndDate}}</span>| <span>过去 7 天</span>

            </div>
            <div class="content-trans">
              <a class="panel-head">总体转化率</a><span class="content-number" style="display: inline-block;float: right">{{funnelZcInfoData.allTransPercent}}</span>
            </div>
            <el-row v-show="showFilterFlag==1" >
              <div  style="height:300px;overflow: auto;">
                <!-- 1 $启动APP -->
<!--				        {{index+1}} {{item.stepName}}-->
                <!-- {{funnelZcData.personNum}} -->
<!--                <el-progress :text-inside="true" :stroke-width="26" stroke-linecap="square" :percentage="item.personNum"></el-progress>-->
                <el-progress :text-inside="true" :stroke-width="26" stroke-linecap="square" :percentage="60"></el-progress>
                <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
<!--                  <span>{{item.transPercent}}</span>-->
                  <span>10%</span>
                  </span>
                </div>
                <!-- 2 App 浏览页面-->
                <el-progress :text-inside="true" :stroke-width="24" stroke-linecap="square" :percentage="46" status="success"></el-progress>
                <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
                  <span>0.88%</span>
                  </span>
                </div>
                3 注册结果
                <el-progress :text-inside="true" :stroke-width="22" stroke-linecap="square" :percentage="13" status="warning"></el-progress>
                <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
                  <span>1.29%</span>
                  </span>
                </div>
                4 提交订单
                <el-progress :text-inside="true" :stroke-width="22" stroke-linecap="square" :percentage="0" status="warning"></el-progress>
                <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
                  <span>-</span>
                  </span>
                </div>
                5 支付订单
                <el-progress :text-inside="true" :stroke-width="22" stroke-linecap="square" :percentage="0" status="warning"></el-progress>

			      </div>
              </el-row>
            <div class="chart-wrapper" v-show="showFilterFlag==2">
              <line-chart :chart-data="lineChartData" style="width:620px;"/>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
      <el-row style="background:#fff;margin-top:5px;padding:5px 5px 0;margin-bottom:5px;">
        <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
          <div v-loading="wtotalNumLoading" class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
            <div class="panel-content">
              <div ><a class="panel-head">全站流量的用户数</a></div>
              <div class="content-range">
                <span>{{wtotalNumListData.gmvStartDate}}~{{wtotalNumListData.gmvEndDate}} </span>| <span> 过去 7 天</span>
              </div>
              <span class="content-today">{{wtotalNumListData.gmvEndDate}}（{{wtotalNumListData.gmvWeek}}）</span>
              <el-row>
                <el-col  :span="10" class="card-panel-col">
                  <div>
                    <span class="number">{{wtotalNumListData.gmvData}}</span>
                  </div>
                </el-col>
                <el-col  :span="8" class="card-panel-col">
                  <div class="content-combine" style="line-height:24px;">
                    环比<span class="content-percent-top" v-if="wtotalNumListData.gmvDataA>0"><i class="el-icon-caret-top"></i>{{wtotalNumListData.gmvDataA}}%</span>
                    <span class="content-percent-low" v-if="wtotalNumListData.gmvDataA<=0"><i class="el-icon-caret-bottom"></i>{{wtotalNumListData.gmvDataA}}%</span><br>
                    同比<span class="content-percent-top" v-if="wtotalNumListData.gmvDataB>0"><i class="el-icon-caret-top"></i>{{wtotalNumListData.gmvDataB}}%</span>
                    <span class="content-percent-low" v-if="wtotalNumListData.gmvDataB<=0"><i class="el-icon-caret-bottom"></i>{{wtotalNumListData.gmvDataB}}%</span>
                  </div>
                </el-col>
                <el-col  :span="6" class="card-panel-col">
                  <div>
                    合计 <span class="content-number">{{ wtotalNumListData.gmvTotal }}  </span>人<br>
                    均值 <span class="content-number">{{ wtotalNumListData.gmvAvg }} </span>人
                  </div>
                </el-col>
              </el-row>
              <event-line-chart :chart-data="wtotalNumListChartData" :chart-title="wtotalNumListChartTitle" :style="{width:'100%',height:'250px'}"/>
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
          <div class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
            <div class="panel-content">
              <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                  <a class="panel-head" >购买转化漏斗</a>
                </el-col>
                <el-col :span="1.5"  :offset="16">
                  <a v-bind:class="showBuyTransFlag==1?'active':'unActive'" @click="buyTransReport(1)">漏斗</a>
                </el-col>
                <el-col :span="1.5">
                  <a v-bind:class="showBuyTransFlag==2?'active':'unActive'" @click="buyTransReport(2)">趋势</a>
                </el-col>
              </el-row>
              <div class="content-range">
                <span>2021-04-10~2021-04-16</span>| <span>过去 7 天</span>

              </div>
              <div class="content-trans">
                <a class="panel-head">总体转化率</a><span class="content-number" style="display: inline-block;float: right">0.29%</span>
              </div>
              <el-row v-show="showBuyTransFlag==1">
                1 浏览商品详情页
                <el-progress :text-inside="true" :stroke-width="26" stroke-linecap="square" :percentage="70"></el-progress>
                <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
                  <span>9.95%</span>
                  </span>
                </div>
                2 开始购买
                <el-progress :text-inside="true" :stroke-width="24" stroke-linecap="square" :percentage="46" status="success"></el-progress>
                <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
                  <span>2.88%</span>
                  </span>
                </div>
                3 支付订单详情
                <el-progress :text-inside="true" :stroke-width="22" stroke-linecap="square" :percentage="13" status="warning"></el-progress>
                <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
                  <span>1.29%</span>
                  </span>
                </div>
              </el-row>
              <div class="chart-wrapper" v-show="showBuyTransFlag==2">
                <line-chart :chart-data="lineChartData" style="width:620px;"/>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-row style="background:#fff;margin-top:5px;padding:5px 5px 0;margin-bottom:5px;">
      <el-col :xs="12" :sm="16" :lg="12" class="card-panel-col">
        <div class="card-panel" style="height:400px" @click="handleSetLineChartData('newVisitis')">
          <div class="panel-content">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <a class="panel-head">下单转化漏斗</a>
              </el-col>
              <el-col :span="1.5"  :offset="14">
                <a v-bind:class="showOperBuyTransFlag==1?'active':'unActive'" @click="operBuyTransReport(1)">漏斗</a>
              </el-col>
              <el-col :span="1.5">
                <a v-bind:class="showOperBuyTransFlag==2?'active':'unActive'"  @click="operBuyTransReport(2)">趋势</a>
              </el-col>
            </el-row>

            <div class="content-range">
              <span>{{funnelXdInfoData.gmvStartDate}}~{{funnelXdInfoData.gmvEndDate}}</span>| <span>过去 7 天</span>

            </div>
            <div class="content-trans">
              <a class="panel-head">总体转化率</a><span class="content-number" style="display: inline-block;float: right">{{funnelXdInfoData.allTransPercent}}</span>
            </div>
            <el-row v-show="showOperBuyTransFlag==1">
              1 点击暑期热卖
              <el-progress :text-inside="true" :stroke-width="26" stroke-linecap="square" :percentage="50.44"></el-progress>
              <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
                  <span>50.44%</span>
                  </span>
              </div>
              2 开始购买
              <el-progress :text-inside="true" :stroke-width="24" stroke-linecap="square" :percentage="40.36" status="success"></el-progress>
              <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
                  <span>40.36%</span>
                  </span>
              </div>
              3 提交订单
              <el-progress :text-inside="true" :stroke-width="22" stroke-linecap="square" :percentage="31.18" status="warning"></el-progress>
              <div class="rate">
                <span>
                  <svg width="72px" height="25px" viewBox="0 0 72 25" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" opacity="1">
                      <g id="widget" transform="translate(-1546.000000, -8147.000000)" fill="#F2F5FA">
                        <path d="M1549,8147 L1615,8147 L1615,8147 C1616.65685,8147 1618,8148.34315 1618,8150 L1618,8163.79271 L1618,8163.79271 C1618,8165.26834 1616.92688,8166.52485 1615.46943,8166.75575 L1582.83247,8171.92638 L1582.83247,8171.92638 C1582.52456,8171.97516 1582.21094,8171.97565 1581.90288,8171.92783 L1548.53984,8166.74908 L1548.53984,8166.74908 C1547.07816,8166.5222 1546,8165.26377 1546,8163.78458 L1546,8150 L1546,8150 C1546,8148.34315 1547.34315,8147 1549,8147 Z" id="Rectangle-15"></path>
                      </g>
                    </g>
                  </svg>
                  <span>31.18%</span>
                  </span>
              </div>
              4 支付订单
              <el-progress :text-inside="true" :stroke-width="22" stroke-linecap="square" :percentage="13" status="warning"></el-progress>
            </el-row>
            <div class="chart-wrapper" v-show="showOperBuyTransFlag==2">
              <line-chart :chart-data="lineChartData" style="width:620px;"/>
            </div>
          </div>
        </div>
      </el-col>


    </el-row>

    <el-dialog :title="title" :visible.sync="open" width="750px" v-if="open"
               :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" label-width="120px" style="height:500px;">
        <el-card class="box-card" style="height:95%;overflow: auto;">
          <div>事件属性</div>
          <el-form-item label=""  label-width="60px">
            <p v-for="(item,index) in queryItems">
              <el-select v-model="item.eventType" value-key="id" style="width:120px">
                <el-option
                  v-for="dict in eventTypeOptions"
                  :key="dict.id"
                  :label="dict.eventTypeName"
                  :value="dict"
                />
              </el-select>
              <el-select v-model="item.eventItem" style="width:120px" value-key="id">
                <el-option
                  v-for="dict in allEventItemListOpts[item.eventType.id]"
                  :key="dict.id"
                  :label="dict.eventItemName"
                  :value="dict"
                />
              </el-select>
              <el-select v-model="item.eventItemExpression" style="width:120px">
                <el-option
                  v-for="dict in eventPropertysOptions[item.eventItem.valueType]"
                  :key="dict.eventItemCode"
                  :label="dict.eventItemExpression"
                  :value="dict.eventItemCode"
                />
              </el-select>
              <el-input v-model="item.value1Str" type="text" controls-position="right" style="width:100px"
                        v-show="item.eventItemExpression!='between'"></el-input>
              <span v-show="item.eventItemExpression=='between'">
                <el-input v-model="item.value1Str" type="text" controls-position="right" style="width:90px"></el-input>与
                <el-input v-model="item.value2Str" type="text" controls-position="right" style="width:90px"></el-input>之间
            </span>
              <el-button @click="addRule" v-show="index == (queryItems.length-1)" type="primary" icon="el-icon-plus"></el-button>
              <el-button @click="removeRule(index)" v-show="index == (queryItems.length-1)" type="warning" icon="el-icon-remove"></el-button>
            </p>

          </el-form-item>
          <div>用户属性</div>
          <el-form-item label=""  label-width="60px">
            <p v-for="(item,index) in userAttributes">
              <el-select v-model="item.userAttribute" value-key="id" style="width:120px">
                <el-option
                  v-for="dict in userAttributeOptions"
                  :key="dict.id"
                  :label="dict.attributeRemark"
                  :value="dict"
                />
              </el-select>
              <el-select v-model="item.eventItemExpression" style="width:120px">
                <el-option
                  v-for="dict in eventPropertysOptions[item.userAttribute.dataType]"
                  :key="dict.eventItemCode"
                  :label="dict.eventItemExpression"
                  :value="dict.eventItemCode"
                />
              </el-select>
              <el-input v-model="item.value1Str" type="text" controls-position="right" style="width:100px"
                        v-show="item.eventItemExpression!='between'"></el-input>
              <span v-show="item.eventItemExpression=='between'">
                <el-input v-model="item.value1Str" type="text" controls-position="right" style="width:90px"></el-input>与
                <el-input v-model="item.value2Str" type="text" controls-position="right" style="width:90px"></el-input>之间
            </span>
              <el-button @click="addAttributeRule" v-show="index == (userAttributes.length-1)" type="primary" icon="el-icon-plus"></el-button>
              <el-button @click="removeAttributeRule(index)" v-show="index == (userAttributes.length-1)" type="warning" icon="el-icon-remove"></el-button>
            </p>

          </el-form-item>
        </el-card>
        <el-row>
          <div class="dialog-footer" style="float:right;padding-top:10px;">
            <el-button @click="confirmSearch">确定</el-button>
            <el-button @click="cancelFun">取 消</el-button>
          </div>
        </el-row>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import { Loading } from 'element-ui';
import EventLineChart from "@/views/report/event/comments/EventLineChart";
import LineChart from './comments/LineChart'
import OtherLineChart from './comments/OtherLineChart'
import BarChart from './comments/BarChart'
import {getAllSiteFlowList,getTotalNumData,getDayNumData,getLoginUsernumData,getSubmitOrderNumData,getOrderVolumeNumData,getPayOrderNumData,getAvgPriceNumData,getAvgEventTimeNumData,getKeepDataFun,getGMVSevenData,getGMVThirtyData,getDailyLifeListData,getNewUserlistData,getPlatFormlistData,getUserTagemListData,getWebAvgDurationListData,getWtotalNumListData,getFunnelChartDataFun} from "@/api/analytics/basemeasure";
import {allEventItemsOptions, eventItemsOptions, eventTypeOptions} from "@/api/config/item";
import {listAllPropertyResult, listPropertyByType} from "@/api/config/property";
import {isNumeric} from "echarts/lib/util/number";
import {listApplication} from "@/api/config/application";
import {getAllEventMethodResult} from "@/api/config/emethod";
import {parseTime} from "@/utils/ruoyi";
import {getUserAttributeList} from "@/api/config/userattribute";

const lineChartData = {
  newVisitis: {
    actualData: []
  }
}

export default {
  name: 'Index',
  components: {
    EventLineChart,
    OtherLineChart,
    LineChart,
    BarChart
  },
  data() {
    return {
      legend: {
        // data: ['小象优选商城APP', '小象电商小程序', '小象商城IOS']
        data: []
      },
      // sevenDayLiveChartTitle:['08-21', '08-22', '08-23', '08-24', '08-25', '08-26', '08-27'],
      sevenDayLiveChartTitle:[],
      sevenDayLiveChartData:[
        // {
        //   name: '小象优选商城APP',
        //   type: 'line',
        //   stack: '总量',
        //   data: [120, 132, 101, 134, 90, 230, 210]
        // },
        // {
        //   name: '小象电商小程序',
        //   type: 'line',
        //   stack: '总量',
        //   data: [220, 182, 191, 234, 290, 330, 310]
        // },
        // {
        //   name: '小象商城IOS',
        //   type: 'line',
        //   stack: '总量',
        //   data: [150, 232, 201, 154, 190, 330, 410]
        // }
      ],
      setFlowLoading:false,
      allSiteFlowLoading:false,
      regFlowLoading:false,
      subOrderFlowLoading:false,
      gmvFlowLoading:false,
      orderNumFlowLoading:false,
      orderTransFlowLoading:false,
      avgPriceFlowLoading:false,
      avgEventFlowLoading:false,
      gmvThirtyFlowLoading:false,
      dailyLifeFlowLoading:false,
      newUserLoading:false,
      userTagemLoading:false,
      keepInfoLoading:false,
      //weekVisitLoading:false,
      webAvgDurationLoading:false,
      wtotalNumLoading:false,
      //GMV7
      gmv7ChartTitle:[],
      gmv7ChartData:[],
      gmvSevenData:{},
      //GMV7
      gmv30ChartTitle:[],
      gmv30ChartData:[],
      gmvThirtyData:{},
      //过去7天日活
      dailyLifeListChartTitle:[],
      dailyLifeListChartData:[],
      dailyLifeListData:{},
      //过去7天新用户访问数
      newUserlistChartTitle:[],
      newUserlistChartData:[],
      newUserlistData:{},
      //各端过去7天日活
      platFormlistChartTitle:[],
      platFormlistChartData:[],
      platFormlistData:{},
      //过去7天新访问用户占比
      userTagemListChartTitle:[],
      userTagemListChartData:[],
      userTagemListData:{},
      //Web端平均访问时长
      webAvgDurationListChartTitle:[],
      webAvgDurationListChartData:[],
      webAvgDurationListData:{},
      // 遮罩层
      loading: true,
      //全站流量
      totalNumData:{},
      dayNumData:{},
      loginUsernumData:{},
      submitOrderNumData:{},
      orderVolumeNumData:{},
      payOrderNumData:{},
      avgPriceNumData:{},
      avgEventTimeNumData:{},
      wtotalNumListData:{},
      wtotalNumListChartData:[],
      wtotalNumListChartTitle:[],
      applicationList:[{"appCode":"","name":"小象电商"},{"appCode":"","name":"小象商城APP"}],
      activeName:'1',
      typeValue:1,
      tableShowFlag:1,
      weekVisitFlag:1,
      showFilterFlag:1,
      showBuyTransFlag:1,
      showOperBuyTransFlag:1,
      params:{
        applicationCode:[],
        dateRange:[parseTime(new Date(),'{y}-{m}-{d}'),parseTime(new Date(),'{y}-{m}-{d}')],
        queryItems:[],
        userAttributes:[],
        funnelId:0,
        fromQuery:'base'
      },
      lineChartData: {
        actualData:[]
      },
      open:false,
      title:'概览筛选',
      form:{},
      eventTypeOptions: [],
      allEventItemListOpts:{},
      eventPropertysOptions:{},
      referrerTypeOptions:[],
      userAttributeOptions:[],
      queryItems:
        [
          {
            eventType:{},
            eventItem:{},
            eventItemExpression:'',
            valueType:'',
            value1Str:'',
            value2Str:''
          }
        ],
      userAttributes:[
        {
          userAttribute:{},
          eventItemExpression:'',
          valueType:'',
          value1Str:'',
          value2Str:''
        }
      ],
      //留存
      keepTableData:[],
      keepTableTitle: [],
      keepInfoData:{},
      //漏斗-注册转化
      funnelZcData: [],
      funnelZctableData: [],
      funnelZctableTitle: [],
      funnelZcchartTitle: [],
      funnelZcchartData: [],
      funnelZcInfoData:{},
	  //漏斗-下单转化
      funnelXdData: [],
      funnelXdtableData: [],
      funnelXdtableTitle: [],
      funnelXdchartTitle: [],
      funnelXdchartData: [],
      funnelXdInfoData:{},
	  //漏斗-购买转化
      funnelGmData: [],
      funnelGmtableData: [],
      funnelGmtableTitle: [],
      funnelGmchartTitle: [],
      funnelGmchartData: [],
      funnelGmInfoData:{},

      tableData: [{
        id: '2020-04-13',
        name: '1327',
        amount1: '234',
        amount2: '3.2',
        amount3: 10
      }, {
        id: '2020-04-14',
        name: '1337',
        amount1: '165',
        amount2: '4.43',
        amount3: 12
      }, {
        id: '2020-04-15',
        name: '1287',
        amount1: '324',
        amount2: '1.9',
        amount3: 9
      }, {
        id: '2020-04-16',
        name: '1394',
        amount1: '621',
        amount2: '2.2',
        amount3: 17
      }, {
        id: '2020-04-17',
        name: '1340',
        amount1: '539',
        amount2: '4.1',
        amount3: 15
      }, {
        id: '2020-04-18',
        name: '1392',
        amount1: '539',
        amount2: '4.1',
        amount3: 15
      }, {
        id: '2020-04-19',
        name: '1397',
        amount1: '539',
        amount2: '4.1',
        amount3: 15
      }],
      pickerOptions: {
        shortcuts: [{
          text: '今日',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            picker.$emit('pick', [start, end]);
          }
        },{
          text: '昨日',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 1);
            picker.$emit('pick', [start, end]);
          }
        },{
          text: '本周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            const day=start.getDay()|| 7;
            var newStart=new Date(start.getFullYear(),start.getMonth(), start.getDate() + 1 - day);
            picker.$emit('pick', [newStart, end]);
          }
        },{
          text: '上周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        },{
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
        }
        ]
      }
    }
  },
  created() {
    //this.getAllFlowList();
    this.getEventTypeList();
    this.getAllEventItemsList();
    this.getUserAttributeList();
    this.getApplicationList();
    this.handleDateQuery();

  },
  methods: {
	getApplicationList() {
	    // var param = {status: 'Y'};
      // listApplication(param).then(response => {
      //   this.applicationList = response.rows;
      //   let that=this;
      //   this.applicationList.forEach(function(app){
      //     if(app.defaultFlag==='Y'){
      //       that.params.applicationCode.push(app.appCode);
      //     }
      //   });
      //   that.initAnalyticsDataFun();
      // });
    },

    initAnalyticsDataFun(){
      this.getGMVSevenChartData();
      this.getGMVThirtyChartData();
      this.getTotalNumData();
      this.getDayNumData();
      this.getLoginUsernumData();
      this.getSubmitOrderNumData();
      this.getOrderVolumeNumData();
      this.getPayOrderNumData();
      this.getAvgPriceNumData();
      this.getAvgEventTimeNumData();
      this.getDailyLifeListData();
      this.getNewUserlistData();
      this.getPlatFormlistData();
      this.getUserTagemListData();
      this.getWebAvgDurationListData();
      this.getWtotalNumListData();
      this.getKeepData();
      this.getFunnelZcData();
      this.getFunnelXdData();
      this.getFunnelGmData();
    },
    getUserAttributeList() {
      // var param = {status: 'Y'};
      // getUserAttributeList(param).then(response => {
      //   this.userAttributeOptions = response.data;
      // });
    },
    getAllEventItemsList(){
      // allEventItemsOptions().then(response => {
      //   this.allEventItemListOpts = response.data;
      // });
      // listAllPropertyResult().then(response => {
      //   this.eventPropertysOptions = response.data;
      // });
    },
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },

    handleDateQuery(){
      this.typeValue=2;
      this.confirmSearch();
    },

    handleFilterQuery(){
      this.open=true;
    },

    getAllFlowList(){
      //this.weekVisitLoading = true;
      // getAllSiteFlowList(this.parseParamStr()).then(response => {
      //   this.lineChartData.actualData=response.data;
      //   //this.weekVisitLoading = false;
      // });
    },

    parseParamStr(){
      let requestJson=JSON.stringify(this.params);
      var param={requestJson:requestJson};
      return param;
    },

    //全站流量
    getTotalNumData(){
      this.setFlowLoading=true;
      getTotalNumData(this.parseParamStr()).then(response => {
         this.totalNumData=response.data;
        this.setFlowLoading=false;
      }).catch(v=>{
        //loadingInstance.close();
      });
    },

     //全站日活
    getDayNumData(){
	    this.allSiteFlowLoading=true;
      getDayNumData(this.parseParamStr()).then(response => {
         this.dayNumData=response.data;
        this.allSiteFlowLoading=false;
      }).catch(v=>{
        //loadingInstance.close();
      });
    },
     //注册用户数
    getLoginUsernumData(){
	    this.regFlowLoading=true;
      getLoginUsernumData(this.parseParamStr()).then(response => {
         this.loginUsernumData=response.data;
         this.regFlowLoading=false;
      }).catch(v=>{
        //loadingInstance.close();
      });
    },

    //下单人数
    getSubmitOrderNumData(){
	    this.subOrderFlowLoading=true;
      getSubmitOrderNumData(this.parseParamStr()).then(response => {
         this.submitOrderNumData=response.data;
         this.subOrderFlowLoading=false;
      }).catch(v=>{
        //loadingInstance.close();
      });
    },

    //订单量
    getOrderVolumeNumData(){
	  this.orderNumFlowLoading=true;
      getOrderVolumeNumData(this.parseParamStr()).then(response => {
         this.orderVolumeNumData=response.data;
         this.orderNumFlowLoading=false;
      }).catch(v=>{
        //loadingInstance.close();
      });
    },

     //下单转换
    getPayOrderNumData(){
	  this.orderTransFlowLoading=true;
      getPayOrderNumData(this.parseParamStr()).then(response => {
         this.payOrderNumData=response.data;
         this.orderTransFlowLoading=false;
      }).catch(v=>{
        //loadingInstance.close();
      });
    },

    //客单价
    getAvgPriceNumData(){
	  this.avgPriceFlowLoading=true;
      getAvgPriceNumData(this.parseParamStr()).then(response => {
         this.avgPriceNumData=response.data;
         this.avgPriceFlowLoading=false;
      }).catch(v=>{
        //loadingInstance.close();
      });
    },

   //app使用时长
    getAvgEventTimeNumData(){
	    this.avgEventFlowLoading=true;
      getAvgEventTimeNumData(this.parseParamStr()).then(response => {
         this.avgEventTimeNumData=response.data;
         this.avgEventFlowLoading=false;
      }).catch(v=>{
        //loadingInstance.close();
      });
    },

    //GMV7天
    getGMVSevenChartData(){
	    this.gmvFlowLoading=true;
      getGMVSevenData(this.parseParamStr()).then(response => {
        this.gmv7ChartData = response.data.chartData;
        this.gmv7ChartTitle = response.data.chartTitle;
        this.gmvSevenData = response.data.gmvSevenData;
        this.gmvFlowLoading=false;
      }).catch(v=>{
        //loadingInstance.close();
      });
    },

    //GMV30天
    getGMVThirtyChartData(){
	    this.gmvThirtyFlowLoading=true;
      getGMVThirtyData(this.parseParamStr()).then(response => {
        this.gmv30ChartData = response.data.chartData;
        this.gmv30ChartTitle = response.data.chartTitle;
        this.gmvThirtyData = response.data.gmvSevenData;
        this.gmvThirtyFlowLoading=false;
      }).catch(v=>{
          //loadingInstance.close();
      });
    },

     //过去7天日活
    getDailyLifeListData(){
	    this.dailyLifeFlowLoading=true;
      getDailyLifeListData(this.parseParamStr()).then(response => {
        this.dailyLifeListChartData = response.data.chartData;
        this.dailyLifeListChartTitle = response.data.chartTitle;
        this.dailyLifeListData = response.data.gmvSevenData;
        this.dailyLifeFlowLoading=false;
      }).catch(v=>{
          //loadingInstance.close();
      });
    },
    //过去7天新用户访问数
    getNewUserlistData(){
	  this.newUserLoading=true;
      getNewUserlistData(this.parseParamStr()).then(response => {
        this.newUserlistChartData = response.data.chartData;
        this.newUserlistChartTitle = response.data.chartTitle;
        this.newUserlistData = response.data.gmvSevenData;
        this.newUserLoading=false;
      }).catch(v=>{
          //loadingInstance.close();
      });
    },
    //各端过去7天日活
    getPlatFormlistData(){
      getPlatFormlistData(this.parseParamStr()).then(response => {
        this.sevenDayLiveChartData = response.data.chartData;
        this.sevenDayLiveChartTitle = response.data.chartTitle;
        this.platFormlistData = response.data.gmvSevenData;
        this.legend.data=response.data.chartLegend;
      }).catch(v=>{
          //loadingInstance.close();
      });
    },
    //过去7天新访问用户占比
    getUserTagemListData(){
	    this.userTagemLoading=true;
      getUserTagemListData(this.parseParamStr()).then(response => {
        this.userTagemListChartData = response.data.chartData;
        this.userTagemListChartTitle = response.data.chartTitle;
        this.userTagemListData = response.data.gmvSevenData;
        this.userTagemLoading=false;
      }).catch(v=>{
          //loadingInstance.close();
      });
    },
    //Web端平均访问时长
    getWebAvgDurationListData(){
	    this.webAvgDurationLoading=true;
      getWebAvgDurationListData(this.parseParamStr()).then(response => {
        this.webAvgDurationListChartData = response.data.chartData;
        this.webAvgDurationListChartTitle = response.data.chartTitle;
        this.webAvgDurationListData = response.data.gmvSevenData;
        this.webAvgDurationLoading=false;
      }).catch(v=>{
          //loadingInstance.close();
      });
    },

    //全站流量用户数
    getWtotalNumListData(){
	    this.wtotalNumLoading=true;
      getWtotalNumListData(this.parseParamStr()).then(response => {
        this.wtotalNumListChartData = response.data.chartData;
        this.wtotalNumListChartTitle = response.data.chartTitle;
        this.wtotalNumListData = response.data.gmvSevenData;
        this.wtotalNumLoading=false;
      }).catch(v=>{
          //loadingInstance.close();
      });
    },

    //获取留存率
    getKeepData(param){
      // this.keepInfoLoading=true;
      // getKeepDataFun(this.parseParamStr()).then(response => {
      //   this.keepTableTitle= response.data.tableTitle;
      //   this.keepTableData= response.data.tableChildData;
      //   this.keepInfoData = response.data.keepInfoData;
      //   this.keepInfoLoading=false;
      // }).catch(v=>{
      // });
    },
    //注册转化漏斗
    getFunnelZcData(){
      // this.params.funnelId=1;
      // let requestJson=JSON.stringify(this.params);
      // var param={requestJson:requestJson};
      // getFunnelChartDataFun(param).then(response => {
      //   this.funnelZctableTitle= response.data.tableTitle;
      //   this.funnelZctableData= response.data.tableData;
      //   this.funnelZcData= response.data.funnelData;
      //   console.log(this.funnelZcData);
      //   this.funnelZcchartTitle= response.data.chartTitle;
      //   this.funnelZcchartData= response.data.chartData;
      //   this.funnelZcInfoData= response.data.infoData;
      // }).catch(v=>{
      //   //loadingInstance.close();
      // });
    },
	//下单转化漏斗
    getFunnelXdData(){
      // this.params.funnelId=4;
      // let requestJson=JSON.stringify(this.params);
      // var param={requestJson:requestJson};
      // getFunnelChartDataFun(param).then(response => {
      //   this.funnelXdtableTitle= response.data.tableTitle;
      //   this.funnelXdtableData= response.data.tableData;
      //   this.funnelXdData= response.data.funnelData;
      //   this.funnelXdchartTitle= response.data.chartTitle;
      //   this.funnelXdchartData= response.data.chartData;
      //   this.funnelXdInfoData= response.data.infoData;
      // }).catch(v=>{
      //   //loadingInstance.close();
      // });
    },
	//购买转化漏斗
    getFunnelGmData(){
      // this.params.funnelId=2;
      // let requestJson=JSON.stringify(this.params);
      // var param={requestJson:requestJson};
      // getFunnelChartDataFun(param).then(response => {
      //   this.funnelGmtableTitle= response.data.tableTitle;
      //   this.funnelGmtableData= response.data.tableData;
      //   this.funnelGmData= response.data.funnelData;
      //   this.funnelGmchartTitle= response.data.chartTitle;
      //   this.funnelGmchartData= response.data.chartData;
      //   this.funnelGmInfoData= response.data.infoData;
      // }).catch(v=>{
      //   //loadingInstance.close();
      // });
    },
    getSummaries(param) {
      const { columns, data } = param;
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

    showTableReport(type){
      this.tableShowFlag=type;
      this.getAllFlowList();

    },
    weekVisitReport(type){
      this.weekVisitFlag=type;
      this.getAllFlowList();
    },
    registerTransReport(type){
      this.showFilterFlag=type;
      this.getAllFlowList();
    },
    buyTransReport(type){
      this.showBuyTransFlag=type;
      this.getAllFlowList();
    },
    operBuyTransReport(type){
      this.showOperBuyTransFlag=type;
      this.getAllFlowList();
    },

    getEventTypeList(){
      // var param={status:'Y'};
      // eventTypeOptions(param).then(response => {
      //   this.eventTypeOptions = response.data;
      // });
    },
    getEventItemsList(type){
      // var param={eventType:type};
      // eventItemsOptions(param).then(response => {
      //   this.eventItemsOptions = response.data;
      // });
    },
    getItemsPropertyList(type){
      // var param={valueType:type};
      // listPropertyByType(param).then(response => {
      //   this.eventPropertysOptions = response.data;
      // });
    },
    addRule(){
     let item= {
        eventType:{},
        eventItem:{},
        eventItemExpression:'',
          valueType:'',
        value1Str:'',
        value2Str:''
      };
     this.queryItems.push(item);
    },

    addAttributeRule(){
      let attribute={
        userAttribute:{},
        eventItemExpression:'',
        valueType:'',
        value1Str:'',
        value2Str:''
      };
      this.userAttributes.push(attribute);
    },

    removeRule(index){
      if(this.queryItems.length===1){
        this.msgSuccess("最后一个不能删除哦");
        return false;
      }
      this.queryItems.splice(index,1);
    },

    removeAttributeRule(index){
      if(this.userAttributes.length===1){
        this.msgSuccess("最后一个不能删除哦");
        return false;
      }
      this.userAttributes.splice(index,1);
    },
    confirmSearch(){
      this.params.queryItems=this.queryItems;
      this.params.userAttributes=this.userAttributes;
      this.open=false;
      this.getGMVSevenChartData();
      this.getGMVThirtyChartData();
      this.getTotalNumData();
      this.getDayNumData();
      this.getLoginUsernumData();
      this.getSubmitOrderNumData();
      this.getOrderVolumeNumData();
      this.getPayOrderNumData();
      this.getAvgPriceNumData();
      this.getAvgEventTimeNumData();
      this.getDailyLifeListData();
      this.getNewUserlistData();
      this.getPlatFormlistData();
      this.getUserTagemListData();
      this.getWebAvgDurationListData();
      this.getWtotalNumListData();
      this.getKeepData();
      this.getFunnelZcData();
	  this.getFunnelXdData();
	  this.getFunnelGmData();
    },
    cancelFun(){
      this.open=false;
    },

    //计算同比，环比的方法，num1 本期、num2 同期
    calculateIncreaseFun(num1,num2){
        let result='0.00%';
        if(!isNumeric(num1)){
          num1=0;
        }
        if(!isNumeric(num2)){
          return result;
        }
        if(num2==0){
          return num1+'%';
        }
        let resultNum=(Math.abs(num1-num2)/num2).toFixed(4);
        result=(resultNum*100).toFixed(2)+'%';
        return result;
    }

  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 5px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
  .card-panel {
    height: 170px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    line-height: 1.42857143;
    color: #666;
    background: #fff;
    border-radius: 3px;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    .panel-content{
      padding: 14px 20px;
    }

    .content-today{
      display: inline-block;
      font-family: Roboto,'Helvetica Neue',Helvetica,Arial,sans-serif;
      font-weight: 400;
      margin-top: 16px;
      position: relative;
      color: #8492A6;
      font-size: 13px;
    }
    .content-range{
      font-size: 12px;
      color: #8492a6;
      margin-top:5px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .content-combine{
      font-family: Roboto,'Helvetica Neue',Helvetica,Arial,sans-serif;
      font-weight: 400;
      position: relative;
      color: #8492A6;
      font-size: 13px;
      .content-percent-top{
        color: #FC6772;
      }
      .content-percent-low{
        color: #2DCA93;
      }
      .content-percent{
        color: #2DCA93;
      }
    }


    .content-number{
      color: #475669;
      font-size: 18px;
    }
    .panel-line{
      height:400px !important;
    }

    .content-trans{
      line-height:30px;
      margin-bottom:5px;
      text-align: left;
      font-size: 16px;
      color: #1f2d3d;
      font-weight: 400;
    }
    &:hover {
      .panel-content{
        background: #fff;
        box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
        border: 2px #1ab394 solid;
        height: 100%;
      }
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }


    }
    .panel-head{
      font-weight: 400;
      font-size: 14px;
      color: #5f6e82;
      text-decoration: none;
    }
    .number{
      font-size:40px;
      color:#475669;
    }

    .rate{
      text-align: center;
      height:26px;
      margin-top: 5px;
      color:#475669;
    }
    .rate span{
      display: inline-block;
      width: 72px;
      height: 25px;
      position: relative;
      line-height: 23px;
    }
    .rate span span {
      position: relative;
      display: block;
      top:-30px;
    }
    .unActive{
      font-weight: 400;
      font-size: 14px;
      color: #5f6e82;
      text-decoration: none;
    }

    .active {
      color: #04CB94;
      font-weight: 400;
      font-size: 14px;
      text-decoration: none;
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
