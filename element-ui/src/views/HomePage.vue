<template>
<el-container>
  <el-header>
    <div style="float: left;">
      <img src="https://highness-1-1253922088.cos.ap-beijing.myqcloud.com/zhao/logo.png"></img>
    </div>
  </el-header>
  <el-main>

    <div v-if="1==1" style="font-size: 16px; font-weight: 600; margin-bottom: 10px;">开心快乐每一天</div>

    <div v-for="item in dataList" :key="item.title" class="item">

      <div class='title'>{{item.title}}</div>

      <div v-for="img in item.imgList" :key="" class="">
        <div v-if="img.type != 4">
          <img :src="img.imgUrl"></img>
        </div>
        <div v-if="img.type == 4">
          <video autoplay muted="muted" loop>
				        <source :src="img.imgUrl" type="video/mp4"> 您的浏览器不支持Video标签。 </video>
        </div>
      </div>
    </div>

    <!-- <el-button @click="getTableData"></el-button> -->

  </el-main>
  <el-footer>
    <div style="display:none">power by zsx 2014-2018</div>
  </el-footer>
</el-container>
</template>

<script>
export default {
  data() {
    return {
      dataList: [],
      logining: false,
      checked: true,


      pageNum: 1,
      pageSize: 4,
      pages: 1
    };
  },
  mounted() {
    var self = this;

    this.getTableData();

    // 注册scroll事件并监听
    window.addEventListener('scroll', function() {
      //console.log(document.documentElement.clientHeight + '-----------' + window.innerHeight); // 可视区域高度
      //console.log(document.body.scrollTop); // 滚动高度
      var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
      //console.log(scrollTop);
      //console.log(document.body.offsetHeight); // 文档高度

      // 判断是否滚动到底部
      if (scrollTop + window.innerHeight >= document.body.offsetHeight) {
        // console.log(new Date())
         self.getTableData();
      }

    });

  },
  methods: {
    handleReset2() {
      //this.$refs.ruleForm2.resetFields();
    },
    getTableData: function() {
      var self = this;
      $.ajax({
        type: "post",
        // dataType: "json",
        // contentType: "application/json",
        url: "https://www.funimg.top/funimg/image/hotImages",
        // url: "http://127.0.0.1:18080/image/hotImages",
        data: {
          pageNum: self.pageNum,
          pageSize: self.pageSize
        },
        success: function(json) {
          // console.log(json)
          // self.dataList = json.list;

          let dataList = self.dataList;

          dataList = dataList.concat(json.list);

          // $.each(json.rows, function(e, v) {
          //   list.push(v)
          // });

          self.dataList = dataList;
          self.pages = json.pages;
          self.pageNum = json.pageNum + 1;

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
  font: 14px/1.3 Arial,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","WenQuanYi Micro Hei",sans-serif;
}

.el-container {
  height: 100%;
}

.el-header {
  background-color: #000;
}

.el-header img {
  height: 60px;
}

@media (max-width: 600px) {
  body {
    font: 12px/1.3 Arial,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","WenQuanYi Micro Hei",sans-serif;
  }

  .el-header {
    height: 30px !important;
  }

  .el-header img {
    height: 30px;
  }
}

.el-main {
  /*background-color: #369;*/
  padding-top: 10px;
}

.el-main .item {
  border-bottom: 1px solid #ccc;
  padding-bottom: 10px;
}

.el-main img,
.el-main video {
  width: auto;
}


@media screen and (max-width: 600px) {
  /*当屏幕尺寸小于600px时，应用下面的CSS样式*/
  .el-main {
    /*background: #ccc;*/
  }

  .el-main img,
  .el-main video {
    width: 100%;
  }
}


.el-footer {
  width: 100%;
  background-color: #000;
  bottom: 0;
}
</style>
