<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相册列表</title>

<script src="${pageContext.request.contextPath}/jslib/miniui/miniui.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/jslib/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/jslib/miniui/themes/icons.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/jslib/miniui/themes/blue2003/skin.css" rel="stylesheet" type="text/css" />

<style type="text/css">
    html, body{
        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
    }    
</style>
</head>
<body>
	<div class="mini-toolbar" style="padding:2px;border-bottom:0;">
        <table style="width:100%;">
            <tr>
            <td style="width:100%;">
                <a class="mini-button" id="addBtn" iconCls="icon-add" plain="true">添加</a>
                <span class="separator"></span>
                <a class="mini-button" id="savBtn" iconCls="icon-save" plain="true">保存</a>
                <span class="separator"></span>
                <a class="mini-button" id="delBtn" iconCls="icon-remove" plain="true">删除</a>
            </td>
        </table>
    </div>
    <!--撑满页面-->
    <div class="mini-fit" >
        
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
            url="${webroot }/image/getAlbumListJson"  idField="id" allowResize="true"
            sizeList="[5,10,20,50]" pageSize="10" sortField="modifyTime" sortOrder="desc"
            multiSelect="false"
            
            allowCellEdit="true" allowCellSelect="true"
            
        >
            <div property="columns">
                <div type="indexcolumn" ></div>
                <div field="id" visible="false" >ID</div>    
                <div field="title" headerAlign="center" allowSort="true">标题
                	<input property="editor" class="mini-textbox"/>
                </div>    
                <div field="author" headerAlign="center" allowSort="true">作者
                </div>                            
                <div field="createTime" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true" >创建时间</div>
                <div field="modifyTime" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true" >修改时间</div>                                    
                <div field="cover" headerAlign="center" >封面
                	<input property="editor" class="mini-textbox"/>
                </div>
                <div field="description"  headerAlign="center">描述信息
                	<input property="editor" class="mini-textbox"/>
                </div>                
                                
            </div>
        </div> 

    </div>


</body>
<script>
//
mini.parse();
var grid = mini.get("datagrid1");
grid.load();

$(function(){ 
	//
	$("#addBtn").click(function(){
		var newRow = { author: "zhao" };
	    grid.addRow(newRow, 0);
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