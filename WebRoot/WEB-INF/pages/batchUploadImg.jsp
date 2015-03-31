<%@ page pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/imagePreview.js"></script>
<body>
	<div style="height:30px;vertical-align: top;display:none">
		<input type="button" value="添加图片" id="btnAdd"
			onclick="selectAttachment();" style="float:left;display:none" /> 
		<input type="button" value="清空图片" id="btnClear"
			style="margin-left:8px;float:left;display:none"
			onclick="clearAttachment();" />
	</div>
	
	<div id="preview" style="height: 100px;display:none" >
		<!-- 图片预览区域 -->
	</div>

	<script type="text/javascript">
	var i=0;
	
	function selectAttachment() {
		// 动态创建上传控件并与span对应
		var upfile = '<input type="file" style="display:none" onchange="addAttachmentToList(this);" id="_upfile' + i + '">';
		document.body.insertAdjacentHTML('beforeEnd', upfile);
		G('_upfile' + i).click();
	}

	function G(id) {
		return document.getElementById(id);
	}
	
	function findAttachment(fn) {
		var o = G('preview').getElementsByTagName('div');
		for (var i = 0; i < o.length; i++){
			if (o[i].name == fn)
				return true;	
		}
			
		return false;
	}
	
	function addAttachmentToList(file) {
		var theEvent = window.event || arguments.callee.caller.arguments[0];
		obj = theEvent.srcElement ? theEvent.srcElement : theEvent.target;
		var _filename = extractFileName(obj.value)
		
		if (findAttachment(_filename)){
			alert("已经在上传队列，请勿重复上传！！");
			return; //如果此文档已在附件列表中则不再添加
		}
		
		var div = $('#preview').show();
		var _titleSpanHtml = '附件'+i
		+ '&nbsp;<a href="javascript:delAttachment(' +i+ ')"><font color="blue">删除</font></a><br/>';
		
		div.append("<div  style='float:left;padding-left:10px;' align='center' name='"+_filename+"'>"
				+  " <div id='div"+i+"' style='margin-bottom:5px;border:2px solid #8A9BA8;width:80px;height:80px;>"
				+  "	<img id='imghead"+i+"' style='display:none'></img>"
				+  " </div>"
				+  " <span>" +_titleSpanHtml+ "</span>"
				+  "</div>");
		i=i+1;
		var a = i-1;
		$.miaxisTools.imagePreview(file, 'div'+a, 'imghead'+a, 80, 80);
	}

	function extractFileName(fn) {
		return fn.substr(fn.lastIndexOf('\\') + 1);
	}
	
	function delAttachment(id) {
		var div = $('#preview');
		var p = G('div'+id).parentNode;
		G('preview').removeChild(p);
		document.body.removeChild(G('_upfile' + id));
	}
	
	function clearAttachment() {
		for (var a = 0; a <= i; a++){
			delAttachment(a);
		}
	}

	</script>
</body>
