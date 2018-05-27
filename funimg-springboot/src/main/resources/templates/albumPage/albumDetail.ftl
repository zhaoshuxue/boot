<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>详细页面</title>
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
        <button type="button" class="btn btn-primary" id="add">新增</button>
        &nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-info" onclick="updateSort()">保存排序</button>
    </div>
    <table id="table"></table>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="showModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel2" aria-hidden="true" style="top: 10%">
    <div class="modal-dialog" style="">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">图片列表</h4>
            </div>
            <div  class="modal-body" style="min-height:100px;">
                <input type="hidden" id="album_detail_id" />
                <input type="text" id="album_title" style="width:100%;" />
                <button class="btn btn-danger" onclick='javascript: $("#album_title").val($("#file_name").val())'>USE</button>
                <input type="text" id="file_name" style="width:100%;" disabled/>
                <input type="text" id="imgUuids" style="width:100%;"/>

                <div class="input-group">
                    <input name="tupian" id="tupian" type="file" class="form-control"
                           style="width:250px; display: none;"/>
                    <span class="btn btn-info" id="selectImg">请选择一张图片</span>
                    <span class="btn btn-success dn" id="startUpload">请选择一张图片</span>
                </div>
                <button class="btn btn-danger" onclick="save()">保存</button>
                <div id="newImgDiv">

                </div>
                <div id="detailDiv">

                </div>

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
            $("#album_detail_id").val("0")
            $('#showModal').modal();
        });

        $("#selectImg").click(function () {
            $("#tupian").click();
        });

        $('#tupian').fileupload({
            url: basePath + '/image/upload',
            dataType: 'json',
            formData: {title: $("#album_title").val()},
            add: function (e, data) {
                var uploadErrors = [];
                var acceptFileTypes = /(\.|\/)(jpe?g|png|mp4|gif)$/i;
                if (!acceptFileTypes.test(data.originalFiles[0]['name'])) {
                    uploadErrors.push('文件类型不正确(jpg|jpeg|png|mp4|gif)！');
                }
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
//                        console.log(data.result.data.data.id)
                        var imgUuids = $("#imgUuids").val();
                        $("#imgUuids").val(imgUuids + "," + data.result.data.data.id);
                        $("#file_name").val(data.result.data.data.title)
                        var html = ""
                        if(data.result.data.data.imgType == 4){
                            html += '<video width="150" controls>';
                            html += '<source src="' + data.result.data.data.imgUrl + '" type="video/mp4">';
                            html += '您的浏览器不支持Video标签。';
                            html += '</video>';
                        }else{
                            html += '<img src="' + data.result.data.data.imgUrl + '" style="width:150px;" /><br/>';
                        }
                        $("#newImgDiv").append(html);
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
            }
        });

    });

    function loadTable() {
        $('#table').bootstrapTable({
            method: "post",
            url: basePath + "/album/albumDetailList",
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
            uniqueId: 'id',
            columns: [
                {
                    checkbox: true, // 显示一个勾选框
                    align: 'center' // 居中显示
                }, {
                    field: 'id', // 返回json数据中的name
                    title: 'ID', // 表格表头显示文字
                    align: 'center', // 左右居中
                    valign: 'middle' // 上下居中
                }, {
                    field: 'sort',
                    title: '排序',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'title',
                    title: '标题',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'imgUuids',
                    title: '图片UUIDS',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var images = row.images;
                        if(images){
                            var imgUrlArr = new Array();
                            var imgTypeArr = new Array();
                            for(var i = 0, len = images.length; i < len; i++){
                                imgUrlArr.push(images[i].imgUrl);
                                imgTypeArr.push(images[i].imgType);
                            }
                            row.imgList = imgUrlArr.join("!@#");
                            row.imgTypeList = imgTypeArr.join("!@#");
                        }
                        return value;
                    }
                }, {
                    title: "操作",
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var cls = 'btn btn-primary btn-sm';
                        var btn_up = '<button class="' + cls + '" onclick="sort(\'' + row.id + '\', ' + index + ', 1)">上移</button>';
                        var btn_down = '<button class="' + cls + '" onclick="sort(\'' + row.id + '\', ' + index + ', 2)">下移</button>';
                        var btn_del = '<button class="' + cls + '" onclick="del(\'' + row.id + '\')">删除</button>';
                        var btn_img = '<button class="' + cls
                                + '" onclick="albumDetail(\'' + row.id + '\',\'' + row.title + '\',\'' + row.imgUuids + '\',\'' + row.imgList + '\',\'' + row.imgTypeList + '\')">详情</button>';
                        var btn = btn_up + btn_down + btn_del + btn_img;
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
        var album_detail_id = $("#album_detail_id").val();
        var album_title = $("#album_title").val();
        var imgUuids = $("#imgUuids").val();
        if(imgUuids[0] == ','){
            imgUuids = imgUuids.substring(1)
        }

        $.ajax({
            type: "post",
            url: basePath + "/album/saveAlbumDetail",
            data: {
                id: album_detail_id,
                albumId: "${albumId}",
                title: album_title,
                imgUuids: imgUuids
            },
            dataType: "JSON",
            success: function (data) {
                console.log(data)
                if(data.code == 200){
                    alertMsg("保存成功");
                    $("#showModal").modal('hide');
                    $('#table').bootstrapTable("refresh");
                }else{
                    alertMsg("保存失败");
                }
            }
        });
    }

    function albumDetail(id, title, imgUuids, imgList, imgTypeList) {
        $("#album_detail_id").val(id)
        $("#album_title").val(title)
        $("#imgUuids").val(imgUuids)

        var html = "";
        var imageList = imgList.split("!@#");
        var imageTypeList = imgTypeList.split("!@#");
        for(var i=0,len=imageList.length; i<len; i++){
            if(imageTypeList[i] == 4){
                html += '<video width="200" controls>';
                html += '<source src="' + imageList[i] + '" type="video/mp4">';
                html += '您的浏览器不支持Video标签。';
                html += '</video><br/>';
            }else{
                html += '<img src="' + imageList[i] + '" style="width:200px;" /><br/>';
            }
        }
        $("#detailDiv").html(html);
        $('#showModal').modal();
    }

    function sort(id, index, type) {
        var $tb = $('#table');
        var data = $tb.bootstrapTable('getRowByUniqueId', id);
        if (type == 1) {
            index = index - 1;
        } else {
            index = index + 1;
        }
        $tb.bootstrapTable('removeByUniqueId', id);
        $tb.bootstrapTable('insertRow', {
            index: index,
            row: data
        });
    }


    function updateSort() {
        var $tb = $('#table');
        var data = $tb.bootstrapTable('getData');
        var arr = new Array();
        for(var i=0,len=data.length; i<len; i++){
            arr.push(data[i].id)
        }
        $.ajax({
            type : "post",
            url : basePath + "/album/sortAlbumDetail",
            data : {ids:arr.join(",")},
            dataType:"JSON",
            async : false,
            success : function(data){
//                console.log(data)
                if (data.code == 200) {
                    alertMsg(data.message)
                    $('#table').bootstrapTable("refresh");
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