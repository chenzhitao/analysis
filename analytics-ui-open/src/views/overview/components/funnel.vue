<template>
	<div class="funnel">
		<div class="funnel-header">
			<div class="funnel-title-range">
				<div class="funnel-title">{{setupProps.componentName}}</div>
				<div class="funnel-range">{{setupProps.createTime}}</div>
			</div>
			<div class="operation">
				<span class="shift" :class="{'active': choosenIndex == 1}" @click='choosenIndex = 1'>漏斗</span>
				<span class="shift" :class="{'active': choosenIndex == 2}" @click='choosenIndex = 2'>趋势</span>
				<DropdownMenu :setupProps="setupProps" class='setup'></DropdownMenu>
			</div>
		</div>
		<div class="funnel-body">
			<div class="content-trans">
				<a class="panel-head">总体转化率</a><span class="content-number">97%</span>
			</div>
			<div class="progress" v-if="choosenIndex == 1">
				1 点击暑期热卖
				<el-progress :text-inside="true" :stroke-width="26" stroke-linecap="square" :percentage="50.44">
				</el-progress>
				<div class="rate">
					<span>50.44%</span>
				</div>
				2 开始购买
				<el-progress :text-inside="true" :stroke-width="24" stroke-linecap="square" :percentage="40.36"
					status="success"></el-progress>
				<div class="rate">
					<span>40.36%</span>
				</div>
				3 提交订单
				<el-progress :text-inside="true" :stroke-width="22" stroke-linecap="square" :percentage="31.18"
					status="warning"></el-progress>
				<div class="rate">
					<span>31.18%</span>
				</div>
				4 支付订单
				<el-progress :text-inside="true" :stroke-width="22" stroke-linecap="square" :percentage="13"
					status="warning"></el-progress>
			</div>
			<div class="line-chart" v-else-if="choosenIndex == 2">
				<singlineChart v-if="exist" :chartTitle='chartTitle' :chartData='chartData' :height='height'></singlineChart>
			</div>
		</div>
	</div>
</template>

<script>
	import singlineChart from './chart/singlineChart.vue'
	import DropdownMenu from './dropdownMenu.vue'
	import {getAllSiteFlowList, getFunnelChartDataFun, getKeepDataFun} from "@/api/analytics/basemeasure";
	import mixinsFun from './mixins/index.js'
		
	export default {
		mixins: [ mixinsFun ],
		name: "funnel",
		components: {
			singlineChart,
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
				choosenIndex: 1,
				chartTitle: ['chartTitle'],
				chartData: [],
				// exist: true,
			}
		},
		computed: {
			height() {
				if(this.setupProps.windowType == 'medium') {
					return '200px'
				}else {
					return '300px'
				}
			},
			windowType() {
				return this.setupProps.windowType
			},
		},
		watch:{
			windowType(newval, oldval) {
				this.reloadFun()
			},
		},
		created() {
			this.queryFunnelData()
		},
		methods: {
			// reloadFun() {
			// 	console.log('reload running')
			// 	let self = this
			// 	this.exist = false
			// 	setTimeout(() =>{
			// 		self.exist = true
			// 	}, 300)
			// },
			
			queryFunnelData(){
			  getFunnelChartDataFun({requestJson:this.setupProps.queryRule}).then(response => {
					console.log( 'running get funnel response' ,response);
			    this.tableTitle = response.data.tableTitle;
			    this.tableData = response.data.tableData;
			    this.funnelData = response.data.funnelData;
			    this.chartTitle = response.data.chartTitle;
			    this.chartData = response.data.chartData;
			  });
			},
		},
	};
</script>
<style lang="scss">
	.funnel {
		.funnel-header {
			width: 100%;
			display: flex;
			height: 47px;

			.funnel-title-range {
				color: #5f6e82;
				font-weight: 400;
				font-size: 14px;
				cursor: pointer;
				width: calc(100% - 106px);

				.funnel-title {
					height: 25px;
				}

				.funnel-range {
					font-size: 12px;
					color: #8492a6;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
				}
			}

			.operation {
				color: #5f6e82;
				display: flex;
				cursor: pointer;

				.shift {
					color: #475669;
					font-size: 13px;
					font-weight: 400;
					margin-right: 5px;
				}

				.active {
					color: #04CB94;
				}

				.setup {
					display: none;
					padding-left: 5px;
				}
			}
		}

		.funnel-body {
			height: 275px;
			width: 100%;
			font-size: 13px;
			color: #475669;
			.content-trans{
				text-align: left;
				font-size: 16px;
				color: #1f2d3d;
				font-weight: 400;
				display: flex;
				justify-content: space-between;
				height: 24px;
				margin-bottom: 15px;
			}
			.progress{
				overflow-y: hidden;
				.rate{
					width: 100%;
					text-align: center;
					margin-top: 6px;
					&>span{
						background-color: #E6EBF5;
						padding: 4px;
					}
				}
			}
		}
	}

	.funnel:hover .funnel-header .operation .setup {
		display: block;
	}
</style>
