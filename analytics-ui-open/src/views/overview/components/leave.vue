<template>
	<div class="leave">
		<div class="leave-header">
			<div class="leave-title-range">
				<div class="leave-title">{{setupProps.componentName}}</div>
				<div class="leave-range">{{setupProps.createTime}}</div>
			</div>
			<div class="operation">
				<span class="shift" :class="{'active': choosenIndex == 1}" @click='choosenIndex = 1'>表格</span>
				<span class="shift" :class="{'active': choosenIndex == 2}" @click='choosenIndex = 2'>变化</span>
				<span class="shift" :class="{'active': choosenIndex == 3}" @click='choosenIndex = 3'>趋势</span>
				<DropdownMenu :setupProps="setupProps"></DropdownMenu>
			</div>
		</div>
		<div class="leave-body">

			<div class="progress" v-if="choosenIndex == 1">
				<tableMode height="250px"></tableMode>
			</div>
			<div class="line-chart" v-else-if="choosenIndex == 2">
				<lineChart v-if="exist" height="260px" :chartData="chartData" :chartTitle="chartTitle"></lineChart>
			</div>
			<div class="line-chart" v-else-if="choosenIndex == 3">
				<singlineChart v-if="exist" height="260px"></singlineChart>
			</div>
		</div>
	</div>
</template>

<script>
	import DropdownMenu from './dropdownMenu.vue'
	
	import singlineChart from './chart/singlineChart.vue'
	import lineChart from './chart/lineChart.vue'
	import tableMode from './chart/tableMode.vue'
	import mixins from './mixins/index.js'
	
	import {getAllSiteFlowList, getKeepDataFun} from "@/api/analytics/basemeasure";
	
	export default {
		mixins: [ mixins ],
		name: "leave",
		components: {
			singlineChart,
			lineChart,
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
				choosenIndex: 1,
				chartTitle: ['chartTitle'],
				chartData: [],
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
		},

		created() {
			this.queryKeepDtaFun()
		},
		
		methods: {
			queryKeepDtaFun(){
				getKeepDataFun({requestJson: this.setupProps.queryRule }).then(response => {
					console.log("get leave response" , response.data);
					this.eventChartData = response.data.chartData;
					this.eventChartTitle = response.data.chartTitle;
					this.tableTitle= response.data.tableTitle;
					this.tableData= response.data.tableData;
				});
			}
		},
	};
</script>
<style lang="scss">
	.leave {
		.leave-header {
			width: 100%;
			display: flex;
			height: 47px;

			.leave-title-range {
				color: #5f6e82;
				font-weight: 400;
				font-size: 14px;
				cursor: pointer;
				width: calc(100% - 126px);

				.leave-title {
					height: 25px;
				}

				.leave-range {
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

		.leave-body {
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

	.leave:hover .leave-header .operation .setup {
		display: block;
	}
</style>
