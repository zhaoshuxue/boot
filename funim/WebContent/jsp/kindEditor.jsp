<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			//resizeType : 1,
			resizeType : 0,//不许自己调整编辑大小
			autoHeightMode: true, //自动高度模式开启
			//创建完毕过后设置为自动高度
			afterCreate: function () {
				this.loadPlugin('autoheight');
				/*
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					//document.forms['example'].submit();
					alert(K('#editor_id').val());
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					alert(K('#editor_id').val());
				});
				*/
			},
			allowPreviewEmoticons : false,
			items : 
			[
					'preview','|', 
					'formatblock', 'fontname', 'fontsize', '|',
					'forecolor', 'hilitecolor', 'bold',
					'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 
					'undo', 'redo',
					'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
					'justifyfull', 'selectall', '|', 
					'hr', 'emoticons','link', '|', 
					'fullscreen', '|',
					'about'
			]
		});
	});
</script>
<!-- 编辑区 -->
<textarea name="content" id="editor_id" style="width:735px;height:200px;visibility:hidden;">
</textarea>
<!--  
(提交快捷键: Ctrl + Enter)
<input type="button" value="editor.html()" onclick="alert(editor.html());">
<input type="button" value="editor.html('HTML内容');" onclick="editor.html('HTML内容');">
 -->
 
 