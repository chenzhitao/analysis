<template>
	<div class="overviewComponent">
		<p>{{setupProps.componentName}}</p>
		<p>{{setupProps.sortNo}}</p>	
	</div>
</template>

<script>
	export default {
		name: "reason",
		components: {
			
		},
		props: {
			setupProps: {
				required: true,
				type: Object
			}
		},
		data() {
			return {
			}
		},
		computed: {
		},
		created() {
			console.log(this.setupProps)
		},
		methods: {

		},
	};
</script>
<style lang="scss">

</style>
