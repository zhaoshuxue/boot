<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>首页</title>

    <link rel="stylesheet" type="text/css" href="/static/js/bootstrap-3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/static/js/bootstrap-table/bootstrap-table.min.css"/>
    <link rel="stylesheet" type="text/css" href="/static/js/datepicker/skin/WdatePicker.css" />
    <link rel="stylesheet" type="text/css" href="/static/js/jquery-confirm/jquery-confirm.min.css" />
    <style type="text/css">
        .dn{
            display: none;
        }
        .fixed-table-body {
            height: auto;
        }
    </style>
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
                    <div role="form">
                        <div class="input-group">
                            <span class="" style="">标题</span>
                            <input class="form-control" id="title" placeholder="输入标题" />
                        </div>
                        <div class="input-group">
                            <span class="" style="">发布日期</span>
                            <input class="form-control" type="text" id="publish_date" onFocus="WdatePicker()"/>
                        </div>
                        <div class="input-group">
                            <input name="tupian" id="tupian"  type="file" class="form-control" style="width:250px; display: none;" />
                            <span class="btn btn-primary btn-sm" id="selectImg">请选择一张图片</span>
                            <span class="btn btn-primary btn-sm dn" id="startUpload">请选择一张图片</span>

                            <img id="loadedImgUrl" src="" style="" alt="暂无图片"/>

                            <input type="hidden" id="imgUrl" />
                            <input type="hidden" id="imgUuid" />

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
<script type="text/javascript" src="/static/js/jquery-confirm/jquery-confirm.min.js"></script>

    <!-- 上传组件 -->
    <script type="text/javascript" src="/static/js/jquery-fileupload/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="/static/js/jquery-fileupload/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="/static/js/jquery-fileupload/jquery.fileupload.js"></script>
    <script type="text/javascript" src="/static/js/jquery-fileupload/jquery.fileupload-process.js"></script>
    <script type="text/javascript" src="/static/js/jquery-fileupload/jquery.fileupload-validate.js"></script>


    <script type="text/javascript">
        var basePath = "${basePath}";
        $(function () {
            loadTable();

//
            $("#add").click(function () {
                $('#addModal').modal();
            });

            $("#selectImg").click(function(){
                $("#tupian").click();
            });

            $('#tupian').fileupload({
                url : basePath + '/image/upload',
                dataType: 'json',
                formData:{ title : $("#title").val() },
                add: function (e, data) {
                    var uploadErrors = [];
                    var acceptFileTypes = /(\.|\/)(jpe?g|png|gif)$/i;
                    if(!acceptFileTypes.test(data.originalFiles[0]['name'])) {
                        uploadErrors.push('文件类型不正确(jpg|jpeg|png|gif)！');
                    }
                    if(uploadErrors.length > 0) {
                        alertMsg(uploadErrors.join("\n"));
                    } else {
                        $("#selectImg").hide();
                        $("#startUpload").text("上传");
                        $("#startUpload").show();
                        $("#startUpload").click(function(){
                            $("#startUpload").text("上传中...");
                            // 触发上传
                            data.submit();
                            $('#startUpload').unbind('click'); // 立即解绑，避免重复绑定事件
                        });
                    }
                },
                done: function (e, data) {
                    console.log(data)
                    if(data.result.code == 200){
                        if(data.result.data.code == 200){
                            $("#imgUrl").val(data.result.data.data.imgUrl);
                            $("#imgUuid").val(data.result.data.data.imgUuid);
                            $("#loadedImgUrl").attr("src", data.result.data.data.imgUrl);
                        }
                    }else{
                        alertMsg("上传失败")
                    }
                    $("#selectImg").show();
                    // $("#startUpload").text("请选择一种图片");
                    $("#startUpload").text("上传");
                    $("#startUpload").hide();
                }
                // 进度条
                ,progressall: function(e, data){
                }
            });

        });

        function loadTable() {
            $('#table').bootstrapTable({
                method: "post",
                url: basePath + "/album/lists",
                cache: false,
                striped: true,
                pagination: true,
                pageList: [5, 10, 20],
                pageSize: 5,
                pageNumber: 1,
                sidePagination: 'server',//设置为服务器端分页
                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType: "undefined",
                queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
                    return {
                        pageSize: params.pageSize, // 每页要显示的数据条数
                        pageNum: params.pageNumber, // 每页显示数据的开始行号
                        sort: params.sortName, // 要排序的字段
                        sortOrder: params.sortOrder, // 排序规则
//                        districtId: $("#districtIdSelect").val(), // 额外添加的参数
//                        summary: $("#summary").val(),
//                        village: $("#village").val(),
//                        status: $("#status").val()
                    }
                },
                sortName: 'id', // 要排序的字段
                sortOrder: 'desc', // 排序规则
                columns: [
                    {
                        //     checkbox: true, // 显示一个勾选框
                        //     align: 'center' // 居中显示
                        // }, {
                        field: 'publishDate', // 返回json数据中的name
                        title: '发布日期', // 表格表头显示文字
                        align: 'center', // 左右居中
                        valign: 'middle' // 上下居中
                    }, {
                        field: 'title', // 返回json数据中的name
                        title: '标题', // 表格表头显示文字
                        align: 'center', // 左右居中
                        valign: 'middle' // 上下居中
                    }, {
                        field: 'imgUrl',
                        title: '图片地址',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) { // 单元格格式化函数
                            if (value == '') {
                                return "图片加载失败";
                            }
                            return '<img src="' + value + '" style="width:100px;" />';
                        }
                    }, {
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) { // 单元格格式化函数
                            var text = '-';
                            if (value == 0) {
                                text = "发布中";
                            } else if (value == 1) {
                                text = "未发布";
                            }
                            return text;
                        }
                    }, {
                        title: "操作",
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            var cls = 'btn btn-primary btn-sm';
                            var btn_update = '<button class="' + cls + '" onclick="update(\'' + row.id + '\')">编辑</button>';

                            var btn_status = '<button class="' + cls + '" onclick="updateStatus(\'' + row.id + '\', 0)">发布</button>';
                            if(row.status == 0){
                                btn_status = '<button class="' + cls + '" onclick="updateStatus(\'' + row.id + '\', 1)">撤销发布</button>';
                            }
                            var btn_img = '<button class="' + cls + '" onclick="albumDetail(\'' + row.id + '\')">详情</button>';
                            var btn = btn_update + btn_status + btn_img;
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
            var imgUrl = $("#imgUrl").val();
            var imgUuid = $("#imgUuid").val();

            $.ajax({
                type : "post",
                url : basePath + "/album/add",
                data : {
                    title: title,
                    imgUrl: imgUrl,
                    imgUuid: imgUuid,
                    publish_date: publish_date
                },
                dataType:"JSON",
                success : function(data){
                    if(data.code == 200){
                        alertMsg("保存成功");
                        $("#addModal").modal('hide');
                        $('#table').bootstrapTable("refresh");
                    }else{
                        alertMsg("保存失败");
                    }
                }
            });
        }

        function updateStatus(id, status) {
            $.ajax({
                type : "post",
                url : basePath + "/album/updateAlbumStatus",
                data : {
                    albumId: id,
                    status: status
                },
                dataType:"JSON",
                success : function(data){
                    if(data.code == 200){
                        alertMsg("修改成功");
                        $('#table').bootstrapTable("refresh");
                    }else{
                        alertMsg("修改失败");
                    }
                }
            });
        }

        function albumDetail(id) {
            window.location.href = basePath + "/albumDetail?id=" + id
        }

        function alertMsg(msg) {
            $.alert({
                title: false,
                content: msg,
                confirmButton: '确认'
            });
        }
    </script>
</body>

</html>