<template>
  <div class="dashboard-editor-container">

    <el-tabs v-model="activeName">
      <el-tab-pane label="场景应用价值" name="first">
        <span slot="label"><i class="el-icon-money"></i> 场景应用价值</span>
        <img src="../assets/images/help/image1.png" class="index_img_3koli" alt="场景应用价值"></img>
      </el-tab-pane>
      <el-tab-pane label="如何分析" name="second">
        <span slot="label"><i class="el-icon-data-line"></i> 如何分析</span>
        <img src="../assets/images/help/image2.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/image3.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/imageB1.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/imageB.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/image5.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/image6.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/image7.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/image8.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/image9.png" class="index_img_3koli" alt="如何分析"></img>
      </el-tab-pane>
      <el-tab-pane label="如何配置" name="third">
        <span slot="label"><i class="el-icon-setting"></i> 如何配置</span>
        <img src="../assets/images/help/image11.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/image12.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/image13.png" class="index_img_3koli" alt="如何分析"></img>
        <img src="../assets/images/help/image14.png" class="index_img_3koli" alt="如何分析"></img>
      </el-tab-pane>
    </el-tabs>


  </div>
</template>

<script>




export default {
  name: 'Index',
  components: {

  },
  data() {
    return {
      activeName: 'first'
    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.index_img_3koli {
  max-width: 90%;
  margin-left:10px;
  display: block;
  height: auto;
}
</style>
