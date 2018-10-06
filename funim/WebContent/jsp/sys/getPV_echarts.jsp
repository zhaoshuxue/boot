<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath }/jslib/echarts/echarts.min.js" type="text/javascript"></script>
</head>
<body>
<div id="main" style="height:555px;"></div>

</body>
<script>
$(function(){ 
	//
	$.get("${pageContext.request.contextPath }/image/pvDataList",
			function(data){
				showEcharts(data);
			},"JSON"
	);
	
});

var showEcharts = function(data){
	var option = {
			toolbox: {
				show: true,
				feature: {
					mark: {show: true},
					dataView: {show: true, readOnly: false},
					magicType: {show: true, type: ['line', 'bar']},
					restore: {show: true},
					saveAsImage: {show: true}
				}
			},
			xAxis: [
				{
					type: 'category',
					boundaryGap: false,
					data: data.x
				}
			],
			yAxis: [
				{
					type: 'value',
					max: 50
				}
			],
			dataZoom: [
	        {
	            type: 'slider',
	            show: true,
	            xAxisIndex: [0],
	            start: 60,
	            end: 100
	        },
	        {
	            type: 'inside',
	            xAxisIndex: [0],
	            start: 60,
	            end: 100
	        }
	    ],
			series : [
				{
					name: 'PV',
					type: 'line',
					label: {
						normal: {
							show: true,
							position: 'top'
						}
					},
					data: data.y
				}
			]
		};
	
	var chart = echarts.init(document.getElementById('main'));

	chart.setOption(option);
};

</script>
</html>