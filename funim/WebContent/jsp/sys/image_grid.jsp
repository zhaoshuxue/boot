<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片列表</title>

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
                <a class="mini-button" iconCls="icon-save" plain="true">保存</a>
                <a class="mini-button" iconCls="icon-close" plain="true">关闭</a>
                <span class="separator"></span>
                <a class="mini-button" iconCls="icon-reload" plain="true">刷新</a>
            </td>
            <td style="white-space:nowrap;"><label style="font-family:Verdana;">名称: </label>
                <input class="mini-textbox" />
                <a class="mini-button" iconCls="icon-search" plain="true" onclick="onSearch()">查询</a>
                </td>
            </tr>
        </table>
    </div>
    <!--撑满页面-->
    <div class="mini-fit" >
        
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" 
            url="${webroot }/image/getImageListJson2"  idField="id" allowResize="true"
            sizeList="[5,10,20,50]" pageSize="10" sortField="uploadTime" sortOrder="desc"
        >
            <div property="columns">
                <div type="indexcolumn" ></div>
                <div field="id" visible="false" >ID</div>    
                <div field="name" headerAlign="center" allowSort="true">名称</div>    
                <div field="secondName" headerAlign="center" allowSort="true">第二名称</div>                            
                <div field="url" align="center" headerAlign="center">链接地址</div>
                <div field="userId"  allowSort="true">用户ID</div>                                    
                <div field="type"  allowSort="true">类型</div>
                <div field="uploadTime"  headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss" allowSort="true">创建日期</div>                
                <div field="remark"  headerAlign="center" ">备注</div>                
            </div>
        </div> 

    </div>


</body>
<script>
$(function(){ 
	//
	
});

	mini.parse();
	var grid = mini.get("datagrid1");
	grid.load();
	
	function onSearch() {
	    mini.open({
	        //url: bootPATH + "../demo/CommonLibs/SelectGridWindow.html"
	        url : "${webroot }/image/imagegrid"
	    });
	}



</script>
</html>