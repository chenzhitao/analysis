<template>
	<div :class="className" :style="{height:height,width:width}" />
</template>

<script>
	import * as echarts from 'echarts';
	require('echarts/theme/macarons') // echarts theme
	import resize from './mixins/resize'

	export default {
		mixins: [resize],
		props: {
			className: {
				type: String,
				default: 'chart'
			},
			width: {
				type: String,
				default: '100%'
			},
			height: {
				type: String,
				default: '200px'
			},
			autoResize: {
				type: Boolean,
				default: true
			},
			chartData: {
				type: Array,
				// required: true
				default: []
			},
			chartTitle: {
				type: Array,
				default: []
				// required: true
			}
		},
		data() {
			return {
				chart: null
			}
		},
		watch: {
			chartData: {
				deep: true,
				handler(val) {
					this.setOptions(val, this.chartTitle)
				}
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initChart()
			})
		},
		beforeDestroy() {
			if (!this.chart) {
				return
			}
			this.chart.dispose()
			this.chart = null
		},
		methods: {
			initChart() {
				this.chart = echarts.init(this.$el, 'macarons');
				this.setOptions(this.chartData, this.chartTitle)
			},
			setOptions(chartData, chartTitle) {
				this.chart.setOption({
					legend: {},
					tooltip: {},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					dataset: {
						source: [
							['product', '2015', '2016', '2017'],
							['Matcha Latte', 43.3, 85.8, 93.7],
							['Milk Tea', 83.1, 73.4, 55.1],
							['Cheese Cocoa', 86.4, 65.2, 82.5],
							['Walnut Brownie', 72.4, 53.9, 39.1]
						]
					},
					xAxis: {
						type: 'category'
					},
					yAxis: {},
					// Declare several bar series, each will be mapped
					// to a column of dataset.source by default.
					series: [{
							type: 'bar'
						},
						{
							type: 'bar'
						},
						{
							type: 'bar'
						}
					]
				})
			}
		}
	}
</script>
