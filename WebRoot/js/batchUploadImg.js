
$.BatchUploadImg = {
     i:1,
     fileNums:0,
     
	/**
	 * 创建INPUT
	 */
	selectAttachment : function() {
		// 动态创建上传控件并与span对应
		var upfile = '<div  style="display:none" ><input type="file" onchange="$.BatchUploadImg.addAttachmentToList(this);" name="files" id="_upfile' + this.i + '"></div>';
		this.G('preview').insertAdjacentHTML('beforeEnd', upfile);
		this.G('_upfile' + this.i).click();
	},
	
	/**
	 * 
	 */
	G : function(id) {
		return document.getElementById(id);
	},
	
	/**
	 * 查找已创建的元素
	 * @param fn
	 * @returns {Boolean}
	 */
	findAttachment :function(fn) {
		var o =this.G('preview').getElementsByTagName('div');
		for (var i = 0; i < o.length; i++){
			if (o[i].name == fn)
				return true;	
		}
			
		return false;
	},
	
	/**
	 * 创建IMG
	 * @param file
	 */
	addAttachmentToList: function(file) {
		var theEvent = window.event || arguments.callee.caller.arguments[0];
		obj = theEvent.srcElement ? theEvent.srcElement : theEvent.target;
		var _filename = this.extractFileName(obj.value)
		if (this.findAttachment(_filename)){
			alert("已经在上传队列，请勿重复上传！！");
			return; //如果此文档已在附件列表中则不再添加
		}
		var div = this.G('preview');
		div.style.display="block";
		var _titleSpanHtml = /*'附件'+this.i*/
		 '&nbsp;<a href="javascript:$.BatchUploadImg.delAttachment(' +this.i+ ')"><font color="blue">删除</font></a><br/>';
		
		div.insertAdjacentHTML('beforeEnd', "<div  style='float:left;padding-left:10px;' align='center' name='"+_filename+"'>"
				+  " <div id='div"+this.i+"' style='margin-bottom:5px;border:2px solid #8A9BA8;width:80px;height:80px;>"
				+  "	<img id='imghead"+this.i+"' style='display:online'></img>"
				+  " </div>"
				+  " <span>" +_titleSpanHtml+ "</span>"
				+  "</div>");
		this.i=this.i+1;
		var a = this.i-1;
		this.fileNums++;
		this.imagePreview(file, 'div'+a, 'imghead'+a, 80, 80);
	},
	
	/**
	 * 获取文件名
	 * @param fn
	 * @returns
	 */
	extractFileName : function(fn) {
		return fn.substr(fn.lastIndexOf('\\') + 1);
	},
	
	/**
	 * 删除元素
	 * @param id
	 */
	delAttachment : function(id){
		var p = this.G('div'+id).parentNode;
		this.G('preview').removeChild(p);
		this.G('preview').removeChild(this.G('_upfile' + id).parentNode);
		this.fileNums--;
	},
	
	/**
	 * 清除所有创建的元素
	 */
	clearAttachment: function() {
		var o = this.G('preview').childNodes;
		for (var i = o.length - 1; i >= 0; i--)
			this.G('preview').removeChild(o[i]);
		
		o = document.body.getElementsByTagName('input');
		for (var i = o.length - 1; i >= 0; i--)
			if (o[i].type == 'file' && o[i].id.indexOf('_upfile') == 0) {
				this.G('preview').removeChild( this.G('_upfile' + id).prentNode);
			}
		
	},
	
	/**
	 * 图片上传预览
	 * @param imagefile  file控件
	 * @param previewDiv 预览DIV
	 * @param imghead    预览IMG
	 * @param width      预览图片宽度
	 * @param height     预览图片高度
	 */
	imagePreview : function(imagefile, previewDiv, imghead,width,height) {
		var imgPath = imagefile.value;
		var point = imgPath.lastIndexOf(".");
		var imgName = imgPath.substr(point + 1).toLowerCase();
		if ("jpg,jpeg,png,gif,bmp".indexOf(imgName) < 0) {
			$.miaxisTools.alert("只支持格式为jpg,jpeg,png,gif,bmp的图片");
			return ;
		}

		var userAgent = window.navigator.userAgent.toLowerCase();
		if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
			//参数 是否是IE，div的id，img的id，高,宽
			if (window.navigator.userAgent.indexOf("MSIE 10") >= 1) {
				previewImage(false, imagefile, previewDiv, imghead, width, height);
			} else if (window.navigator.userAgent.indexOf("MSIE 11") >= 1) {
				previewImage(false, imagefile, previewDiv, imghead, width, height);
			} else {
				previewImage(true, imagefile, previewDiv, imghead, width, height);
			}
		} else {
			previewImage(false, imagefile, previewDiv, imghead, width, height);
		}
	}
};
