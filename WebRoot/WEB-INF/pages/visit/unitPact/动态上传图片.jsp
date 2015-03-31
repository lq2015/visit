<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/imagePreview.js"></script>
<body>
	<div style="height:30px;vertical-align: top;">
		<input type="button" value="添加图片" id="btnAdd"
			onclick="selectAttachment();" style="float:left;" /> 
		<input type="button" value="清空图片" id="btnClear"
			style="margin-left:8px;float:left;display: none"
			onclick="clearAttachment();" />
	</div>
	
	<div id="attachmentList"
        style="margin: 3px 0px 0px 0px; padding: 4px 3px 4px 3px;  display: none;">
    </div>
	
	<div id="preview" style="height: 100px;display:none" >
		<!-- 图片预览区域 -->
	</div>

	<script type="text/javascript">
	var i=0;
	function selectAttachment() {
		// 先清除无效动态生成的多余upfile
		cleanInvalidUpfile();
		
		// 动态创建上传控件并与span对应
		var upfile = '<input type="file" style="display:none" onchange="addAttachmentToList(this);" id="_upfile' + i + '">';
		document.body.insertAdjacentHTML('beforeEnd', upfile);
		G('_upfile' + i).click();
	}
	
	function cleanInvalidUpfile() {
		var o = document.body.getElementsByTagName('input');
		for (var i = o.length - 1; i >= 0; i--)
			if (o[i].type == 'file' && o[i].id.indexOf('_upfile') == 0) {
				if (!G('_attachment' + o[i].id.substr(7)))
					document.body.removeChild(o[i]);
			}
	}
	
	function G(id) {
		return document.getElementById(id);
	}
	
	function findAttachment(fn) {
		var o = G('attachmentList').getElementsByTagName('span');
		for (var i = 0; i < o.length; i++)
			if (o[i].title == fn)
				return true;
		return false;
	}
	
	function addAttachmentToList(file) {
		var theEvent = window.event || arguments.callee.caller.arguments[0];
		obj = theEvent.srcElement ? theEvent.srcElement : theEvent.target;
		
		if (findAttachment(obj.value)){
			alert("已经在上传队列，请勿重复上传！！");
			return; //如果此文档已在附件列表中则不再添加
		}

		var src=file.target || window.event.srcElement; //获取事件源，兼容chrome/IE
		previewImg(file);
		
		// 动态创建附件信息栏并添加到附件列表中
		var span = document.createElement('span');
		span.id = '_attachment' + i;

		span.innerHTML = extractFileName(obj.value)
				+ '&nbsp;<a href="javascript:delAttachment(' + (i++)
				+ ')"><font color="blue">删除</font></a><br/>';
		span.title = obj.value;
		G('attachmentList').appendChild(span);

		// 显示附件列表并变换添加附件按钮文本
		if (G('attachmentList').style.display == 'none') {
			G('btnAdd').value = '继续添加';
			G('attachmentList').style.display = 'block';
			G('btnClear').style.display = 'block';
		}
	}
	
	function previewImg(file) {
		var div = $('#preview').show();
		div.append("<div id='div"+i+"' style='border:2px solid #8A9BA8;width:80px;height:80px;margin: 15px 10px 10px 0px;float:left;padding-top: 1px;padding-bottom: 1px;'><img id='imghead"+i+"' style='display:none'></img></div>");
		$.miaxisTools.imagePreview(file, 'div'+i, 'imghead'+i, 80, 80);
	}
	
	function extractFileName(fn) {
		return fn.substr(fn.lastIndexOf('\\') + 1);
	}
	
	function delAttachment(id) {
		alert(id);
		var div = $('#preview');
		var child = div.children();
		for(var j = 0 ;j<G('attachmentList').children.length;j++){
			if(child[j].id.indexOf(id)!=-1){
				 var p = child[j].parentNode;
				  p.removeChild(child[j]);
			}
		}
		
		G('attachmentList').removeChild(G('_attachment' + id));
		document.body.removeChild(G('_upfile' + id));
		
		// 当附件列表为空则不显示并且变化添加附件按钮文本
		if (G('attachmentList').children.length == 0) {
			G('btnAdd').value = '添加附件';
			G('attachmentList').style.display="none";
			G('btnClear').style.display="none";
			div.hide();
		}
	}
	
	function clearAttachment() {
		var o = G('attachmentList').childNodes;
		for (var i = o.length - 1; i >= 0; i--)
			G('attachmentList').removeChild(o[i]);

		o = document.body.getElementsByTagName('input');
		for (var i = o.length - 1; i >= 0; i--)
			if (o[i].type == 'file' && o[i].id.indexOf('_upfile') == 0) {
				document.body.removeChild(o[i]);
			}

		var div = $('#preview');
		div.children().remove();
		div.hide();
		G('btnAdd').value = '添加附件';
		G('attachmentList').style.display = 'none';
		G('btnClear').style.display = 'none';
	}

	</script>
</body>
