<template>

<el-container>
  <el-header>顶栏容器</el-header>
  <el-main>主要区域容器


    <div v-for="item in dataList" :key="item.title" class="text item">
      {{item.title}}<br/>

<video controls="">

<source :src="item.videoLink" type="video/mp4">
您的浏览器不支持Video标签。
</video>
            
	    <br/><br/>
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
      dataList: [],
      logining: false,
      checked: true,


      value1: '',
      value2: '',
      value3: ''
    };
  },
  mounted(){var self = this;
  
	// 注册scroll事件并监听            
	window.addEventListener('scroll',function(){                
	console.log(document.documentElement.clientHeight+'-----------'+window.innerHeight); // 可视区域高度  
	console.log(document.body.scrollTop); // 滚动高度    
	var scrollTop=document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
	console.log(scrollTop); 
	console.log(document.body.offsetHeight); // 文档高度

	// 判断是否滚动到底部
		if(scrollTop + window.innerHeight >= document.body.offsetHeight) {
			console.log(new Date())

			      
			      $.ajax({
				type: "post",
				dataType: "json",
				contentType: "application/json",
				//url: "https://www.funimg.top/funimg/video/lists",
				url: "http://127.0.0.1:18080/video/lists",
				data: {
				    pageNum: 1,
				    pageSize: 3
				},
				success: function(json) {
					console.log(json)
					
					console.log()
					console.log(self.dataList)

					let list = self.dataList
					

					$.each(json.rows, function(e,v){
					
						list.push(v)
					
					});


					self.dataList = list;




//				  
				},
				error: function(json) {
				  alert("加载失败");
				}
			      });
		}

	});

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
        //url: "https://www.funimg.top/funimg/video/lists",
	url: "http://127.0.0.1:18080/video/lists",
	data: {
	    pageNum: 1,
	    pageSize: 3
	},
        success: function(json) {
		console.log(json)
          self.dataList = json.rows;
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
  /*position: fixed;*/
  bottom: 0;
}
</style>
