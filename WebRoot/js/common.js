$.miaxisTools = {
	/**
	 * 弹lhgdialog窗
	 * 
	 * @param options
	 */
	openPopupWin : function(options) {
		var optionsObj={};
		
		optionsObj.id =options.id ? options.id : Math.round(Math.random()*(1000-100000)+100000);
		optionsObj.content = 'url:' + options.url;
		optionsObj.width  = options.width ? options.width : 700;
		optionsObj.height = options.height ? options.height : 500;
		optionsObj.title  = options.title;
		optionsObj.opacity = options.opacity ? options.opacity : 0.3;
		optionsObj.max = options.max ? options.max : true,
		optionsObj.min = options.min ? options.min : true
		optionsObj.cache  = false;
		optionsObj.lock   = true;
		
		optionsObj.cancelVal = '关闭';
		optionsObj.okVal =  options.okVal ? options.okVal : '确定';
		optionsObj.cancel = options.cancel ? options.cancel :true;
		
		if(options.okBtnId){
			optionsObj.ok = function() {
				iframe = this.iframe.contentWindow;
				$('#' + options.okBtnId, iframe.document).click();
				return false;
			};
		}
		
		if(options.button){
			/**
			 * 按钮为数组串, focus:是否选中, disabled:是否可用
			 * [{name: '同意',callback: function(){},focus: true,disabled:false},{...}]
			 */
			optionsObj.button = options.button;
		}
		
		$.dialog(optionsObj);
	},

	/**
	 * 提示alert
	 * 
	 * @param msg
	 */
	alert : function(msg) {
		$.messager.alert('系统提示', '<div style="margin-top:10px">' + msg + '</div>', 'info');
	},
	
	/**
	 * 提示confirm
	 * 
	 * @param msg
	 */
	confirm : function(msg,callback) {
		$.messager.confirm('系统提示', '<div style="margin-top:10px">' + msg + '</div>',function(r){
			callback(r);
		});
	},

	/**
	 * 序列化from
	 * 
	 * @param form
	 * @returns {___anonymous675_676}
	 */
	serializeObject : function(form) {
		var o = {};
		$.each(form.serializeArray(), function(index) {
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		});
		return o;
	},
	/**
	 * ajax提交
	 * 
	 * @param url
	 * @param formname
	 */
	ajaxSubmit : function(url,data,callback) {
		MaskUtil.mask();
		$.ajax({
			type : 'POST',
			dataType : 'json',
			data: data,
			url : url,
			error : function() {
				MaskUtil.unmask();
				$.miaxisTools.alert('操作失败!'); 
			},
			success : function(data) {
				MaskUtil.unmask();
				if(data.result==0){
					if(data.message==''){
						callback();
						$.miaxisTools.alert('操作成功!');
					}else{
						callback();
						$.miaxisTools.alert(data.message);
					}
				}else{
					$.miaxisTools.alert(data.message);
				}
			}
		});
	},
	/**
	 * ajax提交FORM
	 * 
	 * @param url
	 * @param formname
	 */
	ajaxSubmitForm : function(url, formName,data) {
		var isValid = $('#' + formName).form('validate');
		if (isValid) {
			MaskUtil.mask(); 
			var options = {
				type : 'POST',
				dataType : 'json',
				data: data,
				url : url,
				error : function() {
					MaskUtil.unmask();
					$.miaxisTools.alert('操作失败!'); 
				},
				success : function(data) {
					MaskUtil.unmask();
					if(data.result==0){
						if(data.message==''){
							$.miaxisTools.alert('操作成功!');
						}else{
							$.miaxisTools.alert(data.message);
						}
					}else{
						$.miaxisTools.alert(data.message);
					}
				}
			};
			$('#' + formName).ajaxSubmit(options);
		}
	},
	/**
	 * ajax提交FORM  含有文件上传
	 * 
	 * @param url
	 * @param formname
	 */
	ajaxSubmitFormInFile : function(url, formName,data) {
		var isValid = $('#' + formName).form('validate');
		if (isValid) {
			MaskUtil.mask(); 
			var options = {
				type : 'POST',
				dataType : 'text',
				data: data,
				url : url,
				error : function() {
					MaskUtil.unmask();
					$.miaxisTools.alert('操作失败!'); 
				},
				success : function(data) {
					MaskUtil.unmask();
					if(data=="0"){
						$.miaxisTools.alert('操作成功!');
					}else{
						$.miaxisTools.alert("操作失败！");
					}
				}
			};
			$('#' + formName).ajaxSubmit(options);
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

$(function() {
	if (location.href.indexOf("detail") != -1) {
		 $(":input").attr("disabled","true");
		 $(":input").attr("style","border:0;border-bottom:1 solid black;background:white;");
	}
});

function showStudentAbout(id){
	$.miaxisTools.openPopupWin({
		id  : 'w_student',
		url : 'student.do?detail&id=' + id,
		width  : 680,
		height : 480,
		max    : false,
		title  : '查看学员'
	});
}

function showStudentAbout2(id){
	var api = frameElement.api, W = api.opener;
	W.$.dialog({
		title:'查看学员',
		id:Math.round(Math.random()*(1000-100000)+100000),
		content:'url:student.do?detail&id=' + id,
	    lock:true,
	    max:false,
	    parent:api,
	    width:700,
	    height:480,
	    cancel : true,
	    cancelVal:'关闭'
	});
}

/**
 * 使用方法:
 * 开启:MaskUtil.mask();
 * 关闭:MaskUtil.unmask();
 * 
 * MaskUtil.mask('其它提示文字...');
 */
var MaskUtil = (function(){
	
	var $mask,$maskMsg;
	
	var defMsg = '正在处理，请稍待。。。';
	
	function init(){
		if(!$mask){
			$mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo("body");
		}
		if(!$maskMsg){
			$maskMsg = $("<div class=\"datagrid-mask-msg mymask\">"+defMsg+"</div>")
				.appendTo("body").css({'font-size':'12px'});
		}
		
		$mask.css({width:"100%",height:$(document).height()});
		
		$maskMsg.css({
			left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2
		}); 
				
	}
	
	return {
		mask:function(msg){
			init();
			$mask.show();
			$maskMsg.html(msg||defMsg).show();
		}
		,unmask:function(){
			$mask.hide();
			$maskMsg.hide();
		}
	}
	
}());