<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片列表</title>

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
    
    <button id="addBtn">添加</button>
    <button id="updBtn">修改</button>
    
    <table id="imageList"></table>
	<div id="gridPager"></div>
	
	<!-- 添加 -->
	<div id="add_dialog" title="添加窗口">
		<form id="add_form" method="post" enctype="multipart/form-data">
			<table class="">
				<tr>
					<td style="color: greenyellow;">文件名</td>
					<td><input name="name" id="add_name" type="text" /></td>
				</tr>
				<tr>
					<td style="color: greenyellow;">网络名称</td>
					<td><input name="secondName" id="add_secondName" type="text" /></td>
				</tr>
				<tr>
					<td style="color: greenyellow;">图片地址</td>
					<td><input name="url" id="add_url" type="text" /></td>
				</tr>
				<tr>
					<td style="color: greenyellow;">备注</td>
					<td><input name="remark" id="add_remark" type="text" /></td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 修改 -->
	<div id="upd_dialog" title="修改窗口">
		<form id="upd_form" method="post" enctype="multipart/form-data">
			<table class="">
				<input name="id" id="upd_id" type="hidden" />
				<tr>
					<td style="color: greenyellow;">文件名</td>
					<td><input name="name" id="upd_name" type="text" /></td>
				</tr>
				<tr>
					<td style="color: greenyellow;">网络名称</td>
					<td><input name="secondName" id="upd_secondName" type="text" /></td>
				</tr>
				<tr>
					<td style="color: greenyellow;">图片地址</td>
					<td><input name="url" id="upd_url" type="text" /></td>
				</tr>
				<tr>
					<td style="color: greenyellow;">备注</td>
					<td><input name="remark" id="upd_remark" type="text" /></td>
				</tr>
			</table>
		</form>
	</div>

</body>
<script>
$(function(){ 
	$("#imageList").jqGrid({
        url:"${webroot }/image/getImageListJson3",
        datatype:"json", 
        mtype:"POST",
        height: '80%' ,
        autowidth:true,
        colNames:['id','文件名', '名称', '图片地址','用户ID','上传时间','修改时间','备注'],
        colModel:[
            {name:'id',index:'id', hidden:true, align:'center' },
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'secondName',index:'secondName', width:'15%',align:'center'},
            {name:'url',index:'url', width:'30%', align:"center", sortable:false},
            {name:'userId',index:'userId', width:'10%', align:"left", sortable:false},
            {name:'uploadTime',index:'uploadTime', width:'10%',align:"center"},
            {name:'modifyTime',index:'modifyTime', width:'10%',align:"center"},
            {name:'remark',index:'remark', width:'20%',align:"center"}
        ],
        rownumbers:true,//添加左侧行号
        sortname:'uploadTime',
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
	$("#add_dialog" ).dialog({
		resizable: true,
		width:600,
		height:400,
		modal: true,
		autoOpen: false,//默认为：true
		show : true,
		hide: { effect: "blind", duration: 1000 },
		//按钮
		buttons: {
			"取消": function() {
				$(this).dialog("close");
			},
			"确定": function() {
				$.post("${webroot }/image/saveImage",
						{
							"data" : $('#add_form').serialize()
						},
						function(data){
							//alert(data.msg);
							$('#imageList').trigger('reloadGrid');
							$("#add_dialog").dialog("close");
						}
				);
			}
		}
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
				$.post("${webroot }/image/updateImage",
						{
							"id" : $("#upd_id").val(),
							"name" : $("#upd_name").val(),
							"secondName" : $("#upd_secondName").val(),
							"url" : $("#upd_url").val(),
							"remark" : $("#upd_remark").val()
						},
						function(data){
							alert(data.msg);
							$('#imageList').trigger('reloadGrid');
							$("#upd_dialog").dialog("close");
						}
				);
			}
		}
	});
	
	
	//
	$("#addBtn").click(function(){
		$("#add_dialog").dialog("open");
    });
	
	//
	$("#updBtn").click(function(){
		if($('#imageList').jqGrid('getGridParam', 'selrow') === null){
			alert('请选择一行数据');
		}else{
			var rowId = $('#imageList').jqGrid('getGridParam', 'selrow');
			var data =  $('#imageList').jqGrid('getRowData', rowId);
			
			$("#upd_id").val(data.id);
			$("#upd_name").val(data.name);
			$("#upd_secondName").val(data.secondName);
			$("#upd_url").val(data.url);
			$("#upd_remark").val(data.remark);
			
			$("#upd_dialog").dialog("open");
		}
    });
	
	//
	/* $("#delBtn").click(function(){
		var row = grid.getSelected();
	    if (row !== undefined) {
	    	mini.confirm('确认删除吗？','警告',function(action){
				//action  ok 或者 cancel
				if(action === 'ok'){
					grid.removeRow(row, true);
				}
			});
	    }
    }); */
	
	
});

	


</script>
</html>