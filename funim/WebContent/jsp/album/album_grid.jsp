<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相册列表</title>

<link href="${webroot}/jslib/jquery-ui/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${webroot}/jslib/jquery-ui/jqgrid/ui.jqgrid.css" rel="stylesheet" type="text/css" />

<script src="${webroot}/jslib/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="${webroot}/jslib/jquery-ui/jqgrid/grid.locale-cn.js" type="text/javascript"></script>
<script src="${webroot}/jslib/jquery-ui/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>


<style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
</style>
</head>
<body>
    
    <button id="updBtn">修改</button>
    
    <table id="list"></table>
	
	<div id="gridPager"></div>
	
	<!--  -->
	<div id="upd_dialog" title="修改窗口">
		<form id="upd_form" method="post" enctype="multipart/form-data">
			<table class="">
				<input name="id" id="upd_id" type="hidden" />
				<tr>
					<td><input name="title" id="upd_title" type="text" /></td>
				</tr>
				<tr>
					<td><input name="cover" id="upd_cover" type="text" /></td>
				</tr>
				<tr>
					<td><input name="description" id="upd_description" type="text" /></td>
				</tr>
			</table>
		</form>
	 
	</div>

</body>
<script>
$(function(){ 
	$("#list").jqGrid({
        url:"${webroot }/image/getAlbumListJson2",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height: '80%' ,//高度，表格高度。可为数值、百分比或'auto'
        //width:1000,//这个宽度不能为百分比
        autowidth:true,//自动宽
        colNames:['id','标题', '作者', '创建时间','修改时间','封面','描述信息'],
        colModel:[
            {name:'id',index:'id', hidden:true, align:'center' },
            {name:'title',index:'title', width:'20%',align:'center'},
            {name:'author',index:'author', width:'15%',align:'center'},
            {name:'createTime',index:'createTime', width:'20%', align:"center"},
            {name:'modifyTime',index:'modifyTime', width:'35%', align:"left", sortable:false},
            {name:'cover',index:'cover', width:'10%',align:"center", sortable:false},
            {name:'description',index:'description', width:'10%',align:"center", sortable:false}
        ],
        rownumbers:true,//添加左侧行号
        sortname:'modifyTime',
        sortorder:'desc',
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:5,//每页显示记录数
        rowList:[5,10,15,20,25],//用于改变显示行数的下拉列表框的元素数组。
        jsonReader:{
        	total : "totalpages", //总页数
        	page : "currpage", //当前页
        	records : "totalrecords", // 查询出的记录数
        	root : "data",	//包含实际数据的数组
            repeatitems : false
        },
        pager:$('#gridPager')
    });

	
	//
	$("#upd_dialog" ).dialog({
		resizable: true,
		width:600,
		height:400,
		modal: true,
		autoOpen: false,//默认为：true
		show : true,
		hide: { effect: "explode", duration: 1000 },
		//按钮
		buttons: {
			"取消": function() {
				$(this).dialog("close");
			},
			"确定": function() {
				$.post("${webroot }/image/updateAlbum",
						{
							"id" : $("#upd_id").val(),
							"title" : $("#upd_title").val(),
							"cover" : $("#upd_cover").val(),
							"description" : $("#upd_description").val()
						},
						function(data){
							alert(data.msg);
							$('#list').trigger('reloadGrid');
							$("#upd_dialog").dialog("close");
						}
				);
			}
		}
	});
	
	
	//
	$("#addBtn").click(function(){
		var newRow = { author: "zhao" };
	    grid.addRow(newRow, 0);
    });
	
	//
	$("#updBtn").click(function(){
		if($('#list').jqGrid('getGridParam', 'selrow') === null){
			alert('请选择一行数据');
		}else{
			var rowId = $('#list').jqGrid('getGridParam', 'selrow');
			var data =  $('#list').jqGrid('getRowData', rowId);
			
			$("#upd_id").val(data.id);
			$("#upd_title").val(data.title);
			$("#upd_cover").val(data.cover);
			$("#upd_description").val(data.description);
			
			$("#upd_dialog").dialog("open");
		}
    });
	
	//
	$("#delBtn").click(function(){
		var row = grid.getSelected();
	    if (row !== undefined) {
	    	mini.confirm('确认删除吗？','警告',function(action){
				//action  ok 或者 cancel
				if(action === 'ok'){
					grid.removeRow(row, true);
				}
			});
	    }
    });
	
	//
	$("#savBtn").click(function(){
		grid.commitEdit();
		var data = grid.getChanges(null,false);
		var json = mini.encode(data);
		grid.loading("保存中，请稍后......");        
	    $.ajax({
	        url: "${webroot }/image/savePost",
	        data: { data: json },
	        type: "post",
	        success: function (text) {
	        	console.log(text);
	            grid.reload();
	            grid.unmask();
	        },
	        error: function (jqXHR, textStatus, errorThrown) {
	            alert(jqXHR.responseText);
	            grid.unmask();
	        }
	    });
    });
	
});

	


</script>
</html>