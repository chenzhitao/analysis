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
      default: '340px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    },
    chartTitle: {
      type: Array,
      required: true
    },
    chartLegend: {
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
        this.setOptions(val)
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
      this.chart = echarts.init(this.$el, 'macarons')
      // this.setOptions(this.chartData)
      this.setOptions(this.chartData,this.chartTitle,this.chartLegend);
    },
    setOptions(expectedData,actualData,legendData) {
    // setOptions({ expectedData,actualData,legendData } = {}) {
      // console.log(expectedData);
      // console.log(actualData);
      // console.log(legendData);
      this.chart.setOption({
        xAxis: {
          // data: ['4-11','4-12', '4-13', '4-14', '4-15', '4-16', '1-17'],
          data: expectedData,
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        title:{
          text:'',
          x:'center',
          y: 'bottom',
          marginTop:20,
          textStyle:{
            fontSize:12,
            color:'#464646'
          }
        },
        grid: {
          left: 10,
          right: 20,
          bottom: 25,
          top: 30,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5,5]
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value}'
          },
          axisTick: {
            show: false
          }
        },
        legend: {
          data: legendData
        },
        series: [
        {
          name: '',
          smooth: false,
          type: 'line',
          itemStyle: {
            normal: {
              color: '#3888fa',
              lineStyle: {
                color: '#3888fa',
                width: 1
              }
            }
          },
          data: actualData,
          animationDuration: 2800,
          animationEasing: 'quadraticOut'
        }]
      })
    }
  }
}
</script>
