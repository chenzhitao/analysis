<!-- @author zhengjie -->
<template>
  <div class="icon-body">
      <el-container>
        <el-aside class="icon-list" width="190px">
          <div style="width:170px;" v-for="(item, index) in teamOptions" :key="index" @click="selectedIcon(item)">
            <span class="leftItem"><span class="leftItemText" :class="clickTeamCode==item.teamCode?'leftItemTextClick':'leftItemText'">
              <span style="margin-left:10px;">{{ item.teamName }}</span>
              <span class="leftItemNum">{{ item.typeCount }}</span>
            </span></span>
          </div>
        </el-aside>
        <el-main class="mainClass">
          <el-input v-model="name" style="position: relative;" clearable placeholder="请输入事件名称" @change="filterIcons" @clear="filterIcons" @input.native="filterIcons">
            <i slot="suffix" class="el-icon-search el-input__icon" />
          </el-input>
          <div v-for="(item, index) in eventTypeOptions" :key="index" class="rightItem" @click="selectedItem(item)" >
            <span class="rightItemText">{{  item.eventTypeName }}</span>
          </div>
        </el-main>
      </el-container>
  </div>
</template>

<script>
import {eventTeamDataFun} from "@/api/config/eventteam";
import {eventTypeOptions} from "@/api/config/item";
export default {
  name: 'PanelSelect',
  props:['eventObj'],
  data() {
    return {
      name: '',
      clickTeamCode:'',
      eventItemObj:{},
      eventTypeOptions:[],
      teamOptions:[]
    }
  },
  created() {
    this.getEventTeamList();
  },
  methods: {
    getEventTeamList(){
      var param={status:'Y'};
      eventTeamDataFun(param).then(response => {
        this.teamOptions = response.data;
      });
    },
    getEventTypeList(teamCode){
      var param={status:'Y',teamCode:teamCode};
      eventTypeOptions(param).then(response => {
        this.eventTypeOptions = response.data;
      });
    },
    filterIcons() {
      if (this.name) {
        this.eventTypeOptions = this.eventTypeOptions.filter(item => item.eventTypeName.includes(this.name))
      } else {
        this.getEventTypeList(this.clickTeamCode);
      }
    },
    selectedIcon(name) {
      //this.$emit('selected', name)
      this.clickTeamCode=name.teamCode;
      this.getEventTypeList(name.teamCode);
      //document.body.click()
    },
    selectedItem(item){
      console.log('----------------------------');
      console.log(this.eventObj);
      this.$emit('selected', item,this.eventObj);
      document.body.click()
    },
    reset() {
      this.name = ''
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
aside {
  padding:0px;
  margin:0px;
  border-radius: 2px;
  display: block;
  line-height: 32px;
  font-size: 16px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Fira Sans", "Droid Sans", "Helvetica Neue", sans-serif;
  color: #2c3e50;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
  .icon-body {
    padding:0px;
    margin:0px;
    width: 100%;
    .icon-list {
      height: 400px;
      overflow-y: scroll;
      .leftItem{
        height: 30px;
        width:170px;
        margin-bottom: 12px;
        cursor: pointer;
        padding:5px 0px 5px 0px;
        line-height: 30px;
        font-size: 14px;
        display:inline-block;
        .leftItemText{
          display:block;
          text-overflow: ellipsis;
          overflow: hidden;
        }
        .leftItemTextClick{
          display:block;
          text-overflow: ellipsis;
          overflow: hidden;
          color: #fff;
          background: #00bf8a;
          font-weight: 500;
        }
        .leftItemNum{
          display:inline-block;
          background: #f2f5fa;
          float: right;
          width: 26px;
          line-height: 16px;
          height: 16px;
          border-radius: 40px;
          text-align: center;
          color: #8492a6;
          font-size: 12px;
          margin-top: 6px;
          margin-right:3px;
        }
        &:hover {
          .leftItemText{
            color: #fff;
            background: #00bf8a;
            font-weight: 500;
          }

        }
      }
    }
    .mainClass{
      height:400px;
      overflow: auto;
      .rightItem{
        margin-top:8px;
        display: inline-flex;
        height:30px;
        width: 50%;
        border-radius: 3px;
        line-height:30px;
        cursor: pointer;
        .rightItemText{
          width:100%;
          padding-left:5px;
          border-radius: 3px;
          display: inline-block;
        }
        &:hover {
          .rightItemText{
            background: #b4bccc;
            font-weight: 500;
          }

        }
      }
    }

  }
</style>
