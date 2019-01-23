<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>图片列表</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/js/bootstrap-3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/js/bootstrap-table/bootstrap-table.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/js/jquery-confirm/jquery-confirm.min.css" />
    <style type="text/css">
        .dn {
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
        <button type="button" class="btn btn-primary" id="addImage">添加</button>
    </div>
    <table id="table"></table>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel2" aria-hidden="true" style="top: 10%">
    <div class="modal-dialog" style="">
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
                        <input name="tupian" id="tupian" type="file" class="form-control"
                               style="width:250px; display: none;"/>
                        <span class="btn btn-primary" id="selectImg">请选择一张图片</span>
                        <span class="btn btn-primary dn" id="startUpload">请选择一张图片</span>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<!-- 模态框2（Modal） -->
<div class="modal fade" id="addImageModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel3" aria-hidden="true" style="top: 10%">
    <div class="modal-dialog" style="">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">添加</h4>
            </div>
            <div id="detailDiv" class="modal-body" style="min-height:100px;">
                <form role="form">
                    <div class="input-group" style="width:100%">
                        <span class="" style="">标题</span>
                        <input class="form-control" id="title2" placeholder="输入标题" />
                        <input class="form-control" id="title3" type="hidden" />
                        <input class="btn" type="button" value="复用上一个标题" onclick="javascript:$('#title2').val($('#title3').val())" />
                    </div>
                    <div class="input-group" style="width:100%">
                        <input class="form-control" id="sina_url" placeholder="输入新浪地址" />
                        <input class="form-control" id="qiniu_url" placeholder="输入七牛云地址" />
                    </div>
                </form>
                <button class="btn btn-primary btn-lg" onclick="save()">保存</button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>



<script type="text/javascript" src="${basePath}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/static/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${basePath}/static/js/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${basePath}/static/js/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>

<script type="text/javascript" src="${basePath}/static/js/jquery-confirm/jquery-confirm.min.js"></script>

<!-- 上传组件 -->
<script type="text/javascript" src="${basePath}/static/js/jquery-fileupload/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${basePath}/static/js/jquery-fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${basePath}/static/js/jquery-fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="${basePath}/static/js/jquery-fileupload/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="${basePath}/static/js/jquery-fileupload/jquery.fileupload-validate.js"></script>

<script type="text/javascript">

    var basePath = "${basePath}";
    $(function () {
        loadTable();

//
        $("#add").click(function () {
            $('#addModal').modal();
        });

        $("#addImage").click(function () {
            $('#addImageModal').modal();
        });

        $("#selectImg").click(function () {
            $("#tupian").click();
        });

        $('#tupian').fileupload({
            url: basePath + '/image/upload',
            dataType: 'json',
            formData: {
                title: $("#title").val(),
                isDel: 2
            },
            add: function (e, data) {
                var uploadErrors = [];
                // console.log(data.originalFiles[0]['size']) ;
                /* if (data.originalFiles[0]['size'] > 1*1024*1024) {
                      uploadErrors.push('上传文件不能超过1M');
                    } */
                var acceptFileTypes = /(\.|\/)(jpe?g|png|mp4|gif)$/i;
                if (!acceptFileTypes.test(data.originalFiles[0]['name'])) {
                    uploadErrors.push('文件类型不正确(jpg|jpeg|png|mp4|gif)！');
                }
                /*
                var reader = new FileReader();
                reader.onload = function(e) {
                    console.log('123')
                    console.log(this)
                    //加载图片获取图片真实宽度和高度
                    var image = new Image();
                    image.onload=function(){
                        var width = image.width;
                        var height = image.height;
                        console.log(image)
                        console.log(width)
                        console.log(height)
                        if(true){
                            alert('请选择宽高比为4:3的图片');
                            data.abort();
                        }
                    }
                    image.src= e.target.result;
                }
                reader.readAsDataURL(data.files[0])
                */
                if (uploadErrors.length > 0) {
                    alertMsg(uploadErrors.join("\n"));
                } else {
                    $("#selectImg").hide();
                    $("#startUpload").text("上传");
                    $("#startUpload").show();
                    $("#startUpload").click(function () {

                        $("#startUpload").text("上传中...");
                        // 触发上传
                        data.submit();
                        $('#startUpload').unbind('click'); // 立即解绑，避免重复绑定事件
                    });
                }
            },
            done: function (e, data) {
                if (data.result.code == 200) {
                    if(data.result.data.code == 200){
                        $('#table').bootstrapTable("refresh");
                    }
                } else {
                    alertMsg("上传失败")
                }
//                console.log(data)
                $("#selectImg").show();
                // $("#startUpload").text("请选择一种图片");
                $("#startUpload").text("上传");
                $("#startUpload").hide();
            }
            // 进度条
            , progressall: function (e, data) {
                // var progress = parseInt(data.loaded / data.total * 100, 10);
                // $('#progress1').css('width', progress + '%');
                // //
                // if(progress == 100){
                //     setTimeout(function(){
                //         $('#progress1').css('width', '0');
                //     },2000);
                // }
            }
        });
    });

    function loadTable() {
        $('#table').bootstrapTable({
            method: "post",
            url: basePath + "/image/lists",
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
                    field: 'id', // 返回json数据中的name
                    title: 'ID', // 表格表头显示文字
                    align: 'center', // 左右居中
                    valign: 'middle' // 上下居中
                }, {
                    field: 'title',
                    title: '标题',
                    align: 'center',
                    valign: 'middle',
                    width: 200
                }, {
                    field: 'imgType', // 返回json数据中的name
                    title: '类型', // 图片类型，0:gif，1:jpg，2:jpeg，3:png，4:mp4,5:
                    align: 'center', // 左右居中
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var text = '未知';
                        if (value == 0) {
                            text = "gif";
                        } else if (value == 1) {
                            text = "jpg";
                        } else if (value == 2) {
                            text = "jpeg";
                        } else if (value == 3) {
                            text = "png";
                        } else if (value == 4) {
                            text = "mp4";
                        }
                        return text;
                    }
                }, {
                    field: 'imgUrl',
                    title: '图片地址',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) { // 单元格格式化函数
                        if (value == '') {
                            return "没有资源";
                        }
                        if (row.imgType == 4) {
//                                return "mp4格式";
                            var html = '<video width="100" controls>';
                            html += '<source src="' + value + '" type="video/mp4">';
                            html += '您的浏览器不支持Video标签。';
                            html += '</video>';
                            return html;
                        }
                        return '<img src="' + value + '" style="width:100px;" />';
                    }
                }, {
                    field: 'sinaimgUrl',
                    title: '新浪地址',
                    align: 'center',
                    valign: 'middle',
                    width: 160,
                    formatter: function (value, row, index) {
                        if (value == '') {
                            return "没有资源";
                        }
                        return '<img src="' + value + '" style="width:100px;" />';
                    }
                }, {
                    field: 'qiniuImgUrl',
                    title: '七牛云地址',
                    align: 'center',
                    valign: 'middle',
                    width: 160,
                    formatter: function (value, row, index) {
                        if (value == '') {
                            return "没有资源";
                        }
                        return '<img src="' + value + '" style="width:100px;" />';
                    }
                }, {
                    field: 'id',
                    title: '尺寸',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) { // 单元格格式化函数
                        var text = '宽度：' + row.width;
                        text += '<br/>';
                        text += '高度：' + row.height;
                        return text;
                    }
//                }, {
//                    title: "操作",
//                    align: 'center',
//                    valign: 'middle',
//                    formatter: function (value, row, index) {
//                        var cls = 'btn btn-primary btn-sm';
//                        var btn_update = '<button class="' + cls + '" onclick="update(\'' + row.id + '\')">编辑</button>';
//                        var btn_img = '<button class="' + cls + '" onclick="albumDetail(\'' + row.id + '\')">详情</button>';
//                        var btn = btn_update + btn_img;
//                        return btn;
//                    }
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
        var title = $("#title2").val();
        $("#title3").val(title);

        var sina_url = $("#sina_url").val();
        var qiniu_url = $("#qiniu_url").val();

        $.ajax({
            type : "post",
            url : basePath + "/image/addImage",
            data : {
                title: title,
                sinaimgUrl: sina_url,
                qiniuImgUrl: qiniu_url
            },
            dataType:"JSON",
            success : function(data){
                if(data.code == 200){
                    $("#addImageModal").modal('hide');
                    $('#table').bootstrapTable("refresh");
                    $.alert({
                        title: false,
                        backgroundDismiss: true,
                        content: "保存成功",
                        confirmButton: '确认',
                        confirm: function(){
                            // $("#add").click()
                            $("#title2").val("");
                            $("#sina_url").val("");
                            $("#qiniu_url").val("");
                            $('#addImageModal').modal();

                        }
                    });
                }else{
                    alertMsg("保存失败");
                }
            }
        });
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
