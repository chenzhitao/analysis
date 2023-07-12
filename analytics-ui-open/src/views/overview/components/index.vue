<template>
	<!-- <div class="overviewComponent"> -->
		<component :is="componentInstance" :setupProps='setupProps'></component>
		<!-- {{componentInstance}} -->
	<!-- </div> -->
</template>

<script>
	// import event from './event.vue'
	// import attr from './attr.vue'
	// import funnel from './funnel.vue'
	// import interval from './interval.vue'
	// import leave from './leave.vue'
	// import ltv from './ltv.vue'
	// import path from './path.vue'
	// import reason from './reason.vue'
	// import spread from './spread.vue'
	import {getAllSiteFlowList,getChartDataOptions} from "@/api/analytics/basemeasure";
	
	export default {
		name: "overviewComponent",
		components: {
			// event,
			// attr,
			// funnel,
			// interval,
			// leave,
			// ltv,
			// path,
			// reason,
			// spread,
		},
		props: {
			setupProps: {
				required: true,
				type: Object
			}
		},

		data() {
			return {
				// current: 'event'
			}
		},
		computed: {		
			current() {
				return this.setupProps.analyticsType
			},
			
			componentInstance () {
				const name = this.current
				// const file = () => require(`./${name}.vue`).default
				const file = require(`./${name}.vue`).default
				return file
			}
		},
		created() {		
		},
		methods: {

		},
	};
</script>
<style lang="scss">

</style>
