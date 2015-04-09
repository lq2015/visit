<%@ page pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/imagePreview.js"></script>
	<style type="text/css">
    .upfile{
        height:26px;
        margin-left:6px;
        background:url(<%=basePath%>/images/upfile.gif)  no-repeat ; 
        position:relative;
        width: 66px;
        margin-bottom: 10px;
    }
    .delfile{
        height:26px;
        margin-left:6px;
        background:url(<%=basePath%>/images/delfile.gif)  no-repeat ; 
        position:relative;
        width: 66px;
        margin-bottom: 10px;
    }
    
    .fileInput{
        height:20px;
        width:80px;
        overflow: hidden;
        font-size: 10px;
        position:absolute;
        right:0;
        top:0;
        opacity: 0;
        filter:alpha(opacity=0);
        cursor:pointer;
    }
	</style>

	<script type="text/javascript">
		
		var _fileIndex=0;
		function previewImg(file,_i) {
			var theEvent = window.event || arguments.callee.caller.arguments[0];
			obj = theEvent.srcElement ? theEvent.srcElement : theEvent.target;
			
			var fileName = extractFileName(obj.value);
			if (findAttachment(fileName)){
				$.miaxisTools.alert("已经在上传队列，请勿重复上传！！");
				return; //如果此文档已在附件列表中则不再添加
			}
			
			$.miaxisTools.imagePreview(file, 'div'+_i, 'imghead'+_i, 80, 101);
			$("#imghead"+_i).attr("name",fileName);  
			 
			_fileIndex++;
			addAttachment(_fileIndex);
			
			file.width="0px";
			file.parentNode.className ="delfile";
			
			file.parentNode.onclick=function(){
				file.parentNode.parentNode.parentNode.removeChild(file.parentNode.parentNode);
				return false;
			}
		}
		
		function extractFileName(fn) {
			return fn.substr(fn.lastIndexOf('\\') + 1);
		}
		
		function addAttachment(_i){
			var _html=[];
			_html.push('<div style="float:left;margin-left:10px">');
			_html.push('	<div id="div'+_i+'" style="border:solid 1px #999999;width:80px;height:100px;margin-bottom: 10px;">');
			_html.push('		<img id="imghead'+_i+'" width=81 height=101 border=0 />');
			_html.push('	</div>');
			_html.push('	<div class="upfile">');
			_html.push('	 	<input class="fileInput" type="file" name="files" id="" onchange="previewImg(this,'+_i+')"/>');
			_html.push('	</div>');
			_html.push('</div>');
			
			$("#preview").append(_html.join(""));
		}
		
		function findAttachment(fn) {
			var o =this.document.getElementById('preview').getElementsByTagName('img');
			for (var i = 0; i < o.length; i++){
				if (o[i].name == fn)
					return true;	
			}
				
			return false;
		}
		
	</script>
	