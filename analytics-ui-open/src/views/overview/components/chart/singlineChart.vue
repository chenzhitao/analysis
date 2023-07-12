<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
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
      required: true
    },
    chartTitle: {
      type: Array,
      required: true
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
        this.setOptions(val,this.chartTitle)
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
      this.setOptions(this.chartData,this.chartTitle)
    },
    setOptions(chartData,chartTitle) {
      this.chart.setOption({
				legend: {
					show: true,
					data: ['总体']
				},
				grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
				},
				xAxis: {
						type: 'category',
						data: ['Mon', 'Tue', 'Wed', ]
				},
				yAxis: {
						type: 'value'
				},
				series: [{
						data: [1, 0.87, 0.4, ],
						type: 'line',
						smooth: false
				}]
			})
    }
  }
}
</script>
