<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>详细页面</title>
    <link rel="stylesheet" type="text/css" href="/static/js/bootstrap-3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/static/js/bootstrap-table/bootstrap-table.min.css"/>
    <link rel="stylesheet" type="text/css" href="/static/js/datepicker/skin/WdatePicker.css" />
</head>
<body>
    <div class="container">
        <div>
            <button type="button" class="btn btn-default" id="search">查询</button>
            <button type="button" class="btn btn-primary" id="add">新增</button>
        </div>
        <table id="table"></table>
    </div>


    <!-- 模态框（Modal） -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel2" aria-hidden="true" style="top: 10%">
        <div class="modal-dialog"  style="">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">添加</h4>
                </div>
                <div id="detailDiv" class="modal-body" style="min-height:100px;">
                    <form role="form">
                        <div class="input-group">
                            <span class="" style="">标题</span>
                            <input class="form-control" id="title" placeholder="输入标题" />
                        </div>
                        <div class="input-group">
                            <span class="" style="">发布日期</span>
                            <input class="form-control" type="text" id="publish_date" onFocus="WdatePicker()"/>
                        </div>
                        <button class="btn btn-primary" onclick="save()">保存</button>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

<script type="text/javascript" src="/static/js/jquery.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>

<script type="text/javascript" src="/static/js/datepicker/WdatePicker.js"></script>

    <script type="text/javascript">
        var basePath = "";
        $(function () {
            loadTable();

//
            $("#add").click(function () {
                $('#addModal').modal();
            });

        });

        function loadTable() {
            $('#table').bootstrapTable({
                method: "post",
                url: "http://localhost:18080/album/albumDetailList",
                cache: false,
                striped: true,
                sidePagination: 'server',//设置为服务器端分页
                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType: "undefined",
                queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
                    return {
                        albumId: "${albumId}"
                    }
                },
                columns: [
                    {
                        //     checkbox: true, // 显示一个勾选框
                        //     align: 'center' // 居中显示
                        // }, {
                        field: 'title', // 返回json数据中的name
                        title: '标题', // 表格表头显示文字
                        align: 'center', // 左右居中
                        valign: 'middle' // 上下居中
                    }, {
                        field: 'imgList',
                        title: '图片',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) { // 单元格格式化函数
                            if (value) {
                                var html = '';
                                for(var i=0,len=value.length; i<len; i++){
                                    html += '<img src="' + value[i].imgUrl + '" style="width:100px;" />';
                                }
                                return html;
                            }
                            return "图片加载失败";
                        }
                    }, {
                        title: "操作",
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            var cls = 'btn btn-primary btn-sm';
                            var btn_update = '<button class="' + cls + '" onclick="update(\'' + row.id + '\')">编辑</button>';
                            var btn_img = '<button class="' + cls + '" onclick="albumDetail(\'' + row.id + '\')">详情</button>';
                            var btn = btn_update + btn_img;
                            return btn;
                        }
                    }
                ],
                onLoadSuccess: function () {  //加载成功时执行
                    console.info("加载成功");
                },
                onLoadError: function () {  //加载失败时执行
                    console.info("加载数据失败");
                }
            });
            
        }


        function save() {
            var title = $("#title").val();
            var publish_date = $("#publish_date").val();

            $.ajax({
                type : "post",
                url : "http://localhost:18080/album/add",
                data : {
                    title:title,
                    imgUrl:'',
                    publish_date:publish_date
                },
                dataType:"JSON",
                success : function(data){
                    console.log(data)
                }
            });
        }

        function albumDetail(id) {
            window.location.href = basePath + "/house/houseEditPage?houseId=" + id
        }

    </script>
</body>

</html>