<template>
<el-container>
  <el-header>顶栏容器</el-header>
  <el-main>主要区域容器


    <div v-for="o in 4" :key="o" class="text item">
      {{'列表内容 ' + o }}
    </div>

		<el-button @click="getTableData"></el-button>

  </el-main>
  <el-footer>底栏容器</el-footer>
</el-container>
</template>

<script>
export default {
  data() {
    return {
      logining: false,
      checked: true,


      value1: '',
      value2: '',
      value3: ''
    };
  },
  methods: {
    handleReset2() {
      this.$refs.ruleForm2.resetFields();
    },
    getTableData: function() {
      var self = this;
      $.ajax({
        type: "post",
        dataType: "json",
        contentType: "application/json",
        url: "https://www.funimg.top/funimg/video/lists",
        success: function(json) {
					console.log(json)
          // self.tableData = json.fitomatos;
        },
        error: function(json) {
          alert("加载失败");
        }
      });
    }

  }
}
</script>

<style >
body {
  margin: 0;
}

.el-container {
  height: 100%;
}

.el-header {
  background-color: #515152;
}


.el-main {
  background-color: #369;
}

.el-footer {
  width: 100%;
  background-color: #ccc;
  position: fixed;
  bottom: 0;
}
</style>
