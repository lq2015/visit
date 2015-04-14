<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<script type="text/javascript" src="<%=basePath%>/js/MiaxisOCX/Third_FrontOCX.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/jcrop/jquery.Jcrop.js"></script>
<link rel="stylesheet" href="<%=basePath%>/plug-in/jcrop/jquery.Jcrop.css" />

<object classid="CLSID:A3ECA36F-CD28-422C-918F-5893C6FC9849"
	id="CommOcx" width="1" height="1" codebase="Third_FrontOCX.ocx">
</object>
</head>

<body>
	<br/>
	<div style="margin-left: 150px;margin-top: 20px;border:solid 1px #999999;width:210px"  id="target">
		<img src="<%=basePath%>/images/nophoto.gif" width="210" height="280" id="src_pic" /> 
	</div>
	<form id="shootPhoto_form" method="post" >
		<input type="hidden" id="pic_width" name="pic_width"/>
		<input type="hidden" id="pic_height" name="pic_height"/>
		<input type="hidden" id="pic_x" name="pic_x"/>
		<input type="hidden" id="pic_y" name="pic_y"/>
		<input type="hidden" id="pic_data" name="pic_data"/>
	</form>
	
</body>
</html>
<script type="text/javascript">
var Photo = new Photo();
var jcrop_api = null;
var api = frameElement.api, W = api.opener;

$().ready(function() {
	//初始化Jcrop
	initJcrop();
	
	api.button({
		id:'valueOdk',
	    name:'打开摄像头',
	    callback:function(){	    	
	    	var result = Photo.getPhoto();
	    	if(result){
	    		<% //刷新预览图片%> <% //获取图片数据%>
	    		setTimeout(function(){
	    			
	    			var _width = $("#src_pic").width();
	    			var _height = $("#src_pic").height();
	    			$("#target").html("");
		    		//$("#target").html('<img width="'+_width+'" height="'+_height+'" id="src_pic" border="1" >');
		    		//$("#src_pic").attr("src", "<%=basePath%>/picCreate.jsp?ppath=" + result.SXT_PhotoName);
	    			//$("#src_pic").attr("src", "data:image/png;base64,"+result.SXT_PhotoBase64);
	    			 var newPreview = document.getElementById("target");  
	    			 newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";    
           			 newPreview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = result.SXT_PhotoName;  
           			 newPreview.style.width = _width+"px";    
                     newPreview.style.height = _height+"px";
                     showShootPic(_width,_height,0,0,result.SXT_PhotoBase64);
	    			$("#pic_data").val(result.SXT_PhotoBase64);
	    			//initJcrop();
	    		}, 270);
	    		
	    	}
	    	return false;
		}
	},{
       id:'valueOk',
       focus:true,
	   name:'确定',
	    callback:function(){
	    	var rtn = cutPic();
	    	return rtn;
	    }
	},{
		id:'valueCancel',
	    name:'取消',
	    callback:function(){	    	
	    }
	});
});

function showPreview(coords){
    if(parseInt(coords.w) > 0){
        //计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到
        var rx = 100 / coords.w; 
        var ry = 200 / coords.h;
        $('#pic_width').val(Math.round(coords.w));//裁剪区域的宽  
        $('#pic_height').val(Math.round(coords.h)); //c.h 裁剪区域的高  
        $('#pic_x').val(Math.round(coords.x));  //c.x 裁剪区域左上角顶点相对于图片左上角顶点的x坐标  
        $('#pic_y').val(Math.round(coords.y));  //c.y 裁剪区域顶点的y坐标</span>  
    }
}

function initJcrop(){
  $('#src_pic').Jcrop({
	  onChange:showPreview,
	  onSelect:showPreview,
	  aspectRatio: $("#target").width() / $("#target").height(),
	  bgFade:false
  },function(){
    jcrop_api = this;
  });
};

<% //后台过一下，可以解决IE8.0.6001.18702下js显示base64数据显示一半问题%>
function showShootPic(width,height,x,y,sourcePicData){
	var params={};
	params.pic_width = width;
	params.pic_height = height;
	params.pic_x = x;
	params.pic_y = y;
	params.pic_data = sourcePicData;
	
	$.ajax({
		type : 'POST',
		dataType : 'json',
		data: params,
		url : 'public.do?operateImage',
		error : function() {
			$.miaxisTools.alert('拍摄照片操作失败!'); 
			return false;
		},
		success : function(data) {
			if(data.photoData){
	    		$("#pic_data").val(data.photoData);
	    		 restSetPhotoView(data.photoData);
				return true;
			}
		}
	});
	
	return false;
}

function cutPic(){
	var pic_width = $("#pic_width").val();
	var pic_height = $("#pic_height").val();
	var pic_x = $("#pic_x").val();
	var pic_y = $("#pic_y").val();
	var pic_data = $("#pic_data").val();
	
	if(!pic_data){
		$.miaxisTools.alert('请打开摄像头拍照后操作！');
		return false;
	}
	
	//没有进行图像裁切时，使用原图
	if(pic_width==0){
		pic_width = $("#src_pic").width();
		pic_height = $("#src_pic").height();
		$("#pic_width").val(pic_width);
		$("#pic_height").val(pic_height);
		$("#pic_x").val(0);
		$("#pic_y").val(0);
		restSetPhotoView(pic_data);
		return true; 
	}
	
	//到后台进行图像裁切
	var params={};
	var options = {
		type : 'POST',
		dataType : 'json',
		data: params,
		url : 'public.do?operateImage',
		error : function() {
			$.miaxisTools.alert('拍摄照片操作失败!'); 
			return false;
		},
		success : function(data) {
			if(data.photoData){
				restSetPhotoView(data.photoData);
				api.close(); //关闭窗口
				return true;
			}
		}
	};
	
	$('#shootPhoto_form').ajaxSubmit(options);
	return false;
}

//重置引用窗口图片显示
function restSetPhotoView(photoData){
	var win = '${win}';
	var picwidth = '${picwidth}';
	var picheight = '${picheight}';
	
	win = win==''?'w_person':win;
	picwidth = picwidth==''?80:picwidth;
	picheight = picheight==''?100:picheight;
	
	api.get(win,1).iframe.contentWindow.document.getElementById('preview').innerHTML="";
	api.get(win,1).iframe.contentWindow.document.getElementById('preview').innerHTML ='<img id="personPhoto" width='+picwidth+' height='+picheight+' border=0 />';
	
	api.get(win,1).iframe.contentWindow.document.getElementById('selFile').innerHTML="";
	api.get(win,1).iframe.contentWindow.document.getElementById('selFile').innerHTML ='<input type="file" name="files" id="coverPicFile" style="width: 250px; height: 20px;" onChange="selectPhoto(this);" />';

	api.get(win,1).iframe.contentWindow.document.getElementById('personPhoto').src = "data:image/png;base64,"+photoData;
	api.get(win,1).iframe.contentWindow.document.getElementById('photoData').value = photoData;
}

</script>