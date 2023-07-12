<template>
	<div class="dropdownMenu">
		<el-dropdown @command="handleCommand">
		  <span class="el-dropdown-link">
		    <i class="el-icon-more"></i>
		  </span>
		  <el-dropdown-menu slot="dropdown">
		    <el-dropdown-item command="small" disabled>小</el-dropdown-item>
		    <el-dropdown-item command="medium">中</el-dropdown-item>
		    <el-dropdown-item command="big">大</el-dropdown-item>
		    <el-dropdown-item command="delete">删除</el-dropdown-item>
		  </el-dropdown-menu>
		</el-dropdown>
	</div>
</template>

<script>
	import { listItems, getItems, delItems, addItems, updateItems, exportItems } from "@/api/overview/items";
	
	export default {
		name: "dropdownMenu",
		components: {

		},
		props: {
			setupProps: {
				required: true,
				type: Object
			},
		},
		data() {
			return {

			}
		},
		computed: {

		},
		created() {
		},
		methods: {
			handleCommand(handleType) {
				if(['small', 'medium', 'big'].indexOf(handleType) !== -1) {
					this.changeSize(handleType)
				}else if(handleType === 'delete') {
					this.handleDelete()
				}
			},
					
			changeSize(handleType) {
				let params = Object.assign({}, this.setupProps)
				if(params.windowType !== handleType) {
					params.windowType = handleType
					updateItems(params).then(response => {
					  this.msgSuccess("修改成功");
						this.$EventBus.$emit("refreshOverviews", params);
					});
				}
			},
			
			handleDelete() {
				const ids = this.setupProps.id
				this.$confirm('是否确认删除此组件?', "警告", {
				    confirmButtonText: "确定",
				    cancelButtonText: "取消",
				    type: "warning"
				  }).then(function() {
						console.log('running delete')
				    return delItems(ids);
				  }).then(() => {
						this.$EventBus.$emit("refreshOverviews");
				    this.msgSuccess("删除成功");
				  }).catch(() => {});
			},
		},
	};
</script>
<style lang="scss">

</style>
