<template>
	<div class="funnel">
		<div class="funnel-header">
			<div class="funnel-title-range">
				<div class="funnel-title">{{setupProps.componentName}}</div>
				<div class="funnel-range">{{setupProps.createTime}}</div>
			</div>
			<div class="operation">
				<span class="shift" :class="{'active': choosenIndex == 1}" @click='choosenIndex = 1'>图表</span>
				<span class="shift" :class="{'active': choosenIndex == 2}" @click='choosenIndex = 2'>表格</span>
				<DropdownMenu :setupProps="setupProps"></DropdownMenu>
			</div>
		</div>
		<div class="funnel-body">

			<div class="progress" v-if="choosenIndex == 1">
				<component v-if="exist" :is="current" :chartData='chartData' :height='height' :chartTitle='chartTitle'></component>
			</div>
			<div class="line-chart" v-else-if="choosenIndex == 2">
				<tableMode :height="'250'"></tableMode>
			</div>
		</div>
	</div>
</template>

<script>
	import barChart from './chart/barChart.vue'
	import tableMode from './chart/tableMode.vue'
	import DropdownMenu from './dropdownMenu.vue'
	import mixins from './mixins/index.js'
	
	import {getAllSiteFlowList, getKeepDataFun, getSpreadDataFun} from "@/api/analytics/basemeasure";
		
	export default {
		mixins: [ mixins ],
		name: "spread",
		components: {
			barChart,
			tableMode,
			DropdownMenu,
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
				current: barChart,
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
		mounted() {
		},
		created() {
			this.querySpreadDtaFun()
		},
		methods: {
			querySpreadDtaFun(){
				getSpreadDataFun({requestJson: this.setupProps.queryRule}).then(response => {
					console.log("spread get response" ,response.data);
					this.tableTitle= response.data.tableTitle;
					this.tableData= response.data.tableData;
				});
			}
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
