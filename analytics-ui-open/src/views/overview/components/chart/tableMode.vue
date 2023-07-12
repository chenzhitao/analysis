<template>
	<el-table :data="keepTableData" :height="tableHeihgt" show-summary
		style="width: 100%; margin-top:10px">
		<el-table-column v-for="(item, index) in keepTableTitle" :key="index" :prop="item.columnName" :width="100"
			:label="item.columnLabel" />

	</el-table>
</template>

<script>
	import { getKeepDataFun } from "@/api/analytics/basemeasure"
	export default {
		name: 'tableMode',
		props: {
			height: {
				default: '400px',
				type: String,
			}
		},
		data() {
			return {
				keepTableData: [],
				keepTableTitle: '',
				keepInfoData: null,
			}
		},
		computed: {
			tableHeihgt() {
				return parseInt(this.height)
			}
		},
		watch: {},
		mounted() {},
		beforeDestroy() {},
		created() {
			this.getKeepData()
		},
		methods: {
			parseParamStr(){
			  let requestJson=JSON.stringify(this.params);
			  var param={requestJson:requestJson};
			  return param;
			},
			
			//获取留存率
			getKeepData(param){
			  getKeepDataFun(this.parseParamStr()).then(response => {
			    this.keepTableTitle= response.data.tableTitle;
			    this.keepTableData= response.data.tableChildData;
			    this.keepInfoData = response.data.keepInfoData;
			    this.keepInfoLoading=false;
			  }).catch(v=>{
			  });
			},

		},
	}
</script>
<style type="text/css">
	
</style>
