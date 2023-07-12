export default {
  data() {
    return {
			exist: true,
    }
  },
	computed: {
		windowType() {
			return this.setupProps.windowType
		},
	},
	watch:{
		windowType(newval, oldval) {
			this.reloadFun()
		},
	},
  methods: {
		reloadFun() {
			console.log('reload running')
			let self = this
			this.exist = false
			setTimeout(() =>{
				self.exist = true
			}, 300)
		},
  }
}
