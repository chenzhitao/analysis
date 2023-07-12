<template>
	<div class="event" :class="{onlyShowNumbers: setupProps.chartType==6}">
		<div class="event-header">
			<div class="event-title-range">
				<div class="event-title">{{setupProps.componentName}}</div>
				<div class="event-range">{{setupProps.createTime}}</div>
			</div>
			<div class="operation">
				<!-- <i class="el-icon-more"></i> -->
				<DropdownMenu :setupProps='setupProps'></DropdownMenu>
			</div>
		</div>
		<div class="event-body" :class="[`${setupProps.windowType}-layout`, setupProps.chartType==6?'number-chart':'']">
			<div class="side" v-if="setupProps.chartType!=5">
				<div class="left-side">
					<div class="chart-date">
						9-4(周六)
					</div>
					<div class="number-content">
						<div class="main-num">
							<span class="big-num">450</span>
							<span class="">次</span>
						</div>
						<div class="cycle">
							<div class="">环比<i class="el-icon-caret-bottom"></i>0%</div>
							<div class="">同比<i class="el-icon-caret-bottom"></i>0%</div>
						</div>
					</div>
				</div>
				<div class="right-side">
					<div>合计<span class='medium-num'>450</span>次</div>
					<div>均值<span class='medium-num'>450</span>次</div>
				</div>
			</div>
			<div class="main" :class="{tableMain: setupProps.chartType == 5}" v-if="setupProps.chartType != 6">
				<component :is="current" :chartData='chartData' :height='height' :chartTitle='chartTitle' v-if="exist"></component>
			</div>
		</div>
		
	</div>
</template>

<script>
	import lineChart from './chart/lineChart.vue'
	import barChart from './chart/barChart.vue'
	import eventPieChart from '@/views/report/event/comments/EventPieChart.vue'
	import tableMode from './chart/tableMode.vue'
	import DropdownMenu from './dropdownMenu.vue'
	import mixins from './mixins/index.js'
	import {getAllSiteFlowList,getChartDataOptions} from "@/api/analytics/basemeasure";
	
	// import lineChart from '@/views/analytics/basemonitor/comments/LineChart'
	// import lineChart from '@/views/analytics/basemonitor/comments/LineChart'
	
	const chartTypeMapper = {
		1: {
			chartName: '线图',
			component: 'lineChart'
		},
		2: {
			chartName: '柱图',
			component: 'barChart'
		},
		3: {
			chartName: '环图',
			component: 'eventPieChart'
		},
		4: {
			chartName: '累计图',
			component: 'lineChart'
		},
		5: {
			chartName: '表格',
			component: 'tableMode'
		},
		6: {
			chartName: '数值',
			component: 'none'
		},
		
	}
	
	export default {
		mixins: [ mixins ],
		name: "event",
		components: {
			lineChart,
			barChart,
			eventPieChart,
			tableMode,
			DropdownMenu
		},
		props: {
			setupProps: {
				required: true,
				type: Object
			}
		},
		data() {
			return {
				chartData: [],
				chartTitle: ['测试用图标'],
				eventChartData: null,
				eventChartTitle: null,
				eventTableTitle: null,
				eventTableData: null,
			}
		},
		computed: {
			height() {
				if(this.setupProps.chartType == 5) {
					return '260px'
				}else if(this.setupProps.windowType == 'medium') {
					return '200px'
				}else {
					return '300px'
				}
			},
			
			current() {
				let index = this.setupProps.chartType
				return chartTypeMapper[index].component
			}
		},
		created() {
			this.getChartDataList()
		},
		methods: {
			getChartDataList(){
			  getChartDataOptions({requestJson:this.setupProps.queryRule}).then(response => {
					console.log('event get response', response);
			    this.eventChartData = response.data.chartData
			    this.eventChartTitle = response.data.chartTitle
			    this.eventTableTitle = response.data.tableTitle
			    this.eventTableData = response.data.tableData
			  });
			},
		},
	};
</script>
<style lang="scss">
	.event{
		.event-header{
			width: 100%;
			display: flex;
			height: 47px;
			.event-title-range{
				color: #5f6e82;
				font-weight: 400;
				font-size: 14px;
				cursor: pointer;
				width: calc(100% - 30px);
				.event-title{
					height: 25px;
				}
				.event-range{
					font-size: 12px;
					color: #8492a6;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
				}
			}
		
			.operation{
				display: none;
				color: #5f6e82;
			}
		}
		
		.event-body{
			display: flex;
			.side{
				// width: 30%;
				// background-color: red;
				font-size: 13px;
				color: #8492A6;
				font-weight: 400;
				.main-num{
					height: 57px;
					line-height: 57px;
				}
				.cycle{
					&>div{
						height: 18px;
						line-height: 18px;
					}
				}
				.big-num{
					font-size: 40px;
					color: #475669;
				}
				.medium-num{
					color: #475669;
					font-size: 18px;
				}
			}
			.main{
				// background-color: #eeeeee;
			}
		}
		.medium-layout{
			flex-direction: column;
			.side{
				width: 100%;
				height: 72px;
				display: flex;
				.left-side{
					width: 67%;
					border-right: 1px solid #E9F0F7;
					.number-content{
						display: flex;
						align-items: center;
						.main-num{
							margin-right: 20px;
						}
					}

				}
				.right-side{
					width: 34%;
					box-sizing: border-box;
					padding: 12px 0 12px 11px;
					&>div{
						height: 25px;
						line-height: 25px;
					}
				}
			}
			.main{
				height: 200px;
			}
		}
		.big-layout{
			flex-direction: row;
			height: 300px;
			width: 100%;
			.side{
				width: 20%;
				height: 100%;
				.left-side{
					padding-bottom: 28px;
					border-bottom: 1px solid #E9F0F7;
					box-sizing: border-box;
				}
				.right-side{
					margin-top: 28px;
				}
				.big-num{
					max-width: calc(100% - 30px);
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
			}
			.main{
				width: 80%;
				height: 100%;
				// background-color: #eeeeee;
			}
		}
		.small-layout{
			width: 100%;
			height: 80px;

		}

		.number-chart{
			display: flex;
			align-items: center;
			flex-direction: row;
			.side{
				width: 100%;
				height: 100px;
				display: flex;
				.left-side{
					width: 67%;
					border-right: 1px solid #E9F0F7;
					border-bottom: none;
					.number-content{
						display: flex;
						align-items: center;
						.main-num{
							margin-right: 20px;
						}
					}

				}
				.right-side{
					width: 34%;
					box-sizing: border-box;
					padding: 12px 0 12px 11px;
					&>div{
						height: 25px;
						line-height: 25px;
					}
				}
			}
		}
		
	}
	.event:hover{
		.operation{
			display: block;
		}
	}
	.onlyShowNumbers{
		.big-layouy{
			width: 100%;
			height: 275px;
		}
		.medium-layout{
			width: 100%;
			height: 275px;
		}
	}
	
	.event .big-layout .tableMain{
		width: 100%;
	}

</style>
