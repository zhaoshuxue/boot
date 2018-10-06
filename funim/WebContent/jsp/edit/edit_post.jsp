<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<script type="text/javascript" src="http://bcs.duapp.com/extend/js/backtop.js" charset="utf-8"></script>
	</head>
	<body>
		<form id="Form" method="post">
			
			<input type="hidden" name="postId" value="<%=request.getParameter("postId") %>">
		
			<input id="sort" name="sort" type="number" min="1" placeholder="选择显示顺序" style="width:120px;"/>
			<br>
			<input id="title" name="title" type="text" placeholder="填写标题" style="width:120px;"/>
			<br>
			<div id="image">
				<div style="border:1px solid #888; height:200px;">
					<img id="img_prev" src="" width="285px" height="160px" style="float:left; margin:15px 0 0 170px;"/>
					<span class="" style="float:left;margin:78px 0 0 30px;">
						<label for="file" style="padding: 8px;cursor: pointer;color: #fff;background-color: #428bca;border-color: #357ebd;">浏览</label>
						<br>
						<input type='file' name="img" id="file" onchange="readURL(this,'img_prev');" style="display:none;" />  
					</span>
				</div>
				<br>
			</div>
			<br>
			<button type="button" onclick="add_image();" style="margin: 1px 50px 20px 100px; padding: 10px 50px;">添加图片</button>
			<br>
			<div>
				<jsp:include page="../kindEditor.jsp"></jsp:include>
			</div>
			
		
	
		</form>
		<button type="button" onclick="save();" style="margin: 20px 0 200px 500px;padding: 20px 100px;">save</button>
		
		<!-- 遮罩层 -->
		<jsp:include page="../loading.jsp"></jsp:include>
	</body>
	<script type="text/javascript">
//HTML5
function readURL(input,img) {  
	if(window.FileReader) {  
		if (input.files && input.files[0]) { 
			var reader = new FileReader();  
			reader.onload = function (e) {   
				document.getElementById(img).src = e.target.result;
			}; 
			reader.readAsDataURL(input.files[0]);  
		} 
	}  
	else {  
		alert("Not supported by your browser!");  
	}
}

var i = 1;
function add_image(){
	$.post("${webroot }/jsp/edit/image_upload.jsp",
			{
				"file" : 'file' + i,
				"img_prev" : 'img_prev' + i
			},
			function(data){
				$("#image").append(data);
			}
	);
	i++;
}

function save(){
	$('#fade').show();
	//setTimeout("$('#fade').hide()",5000);
	var formData = new FormData(document.getElementById("Form"));
	
	formData.append('editor' , editor.html());
	
    $.ajax({
        url: '${pageContext.request.contextPath}/image/addItem', 
        type: 'POST',
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        success: function(result){
    			if(result.success){
    				$('#fade').hide();
    				alert('保存成功！！！');
    			}
    	},
        dataType:'json'
    });
}

	</script>
</html>
