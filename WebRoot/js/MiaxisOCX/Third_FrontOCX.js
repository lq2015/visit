var returnStr = false;

var Finger = function(vendores){
	this.vendores = vendores ;
}
 Finger.prototype = {
		getMB:function(){ // 获取指纹模板
			var fingInfo="";
			if(this.vendores=='miaxis'){
				var finger = new ZZFinger();
				fingInfo = finger.getMB();
			}
			if(this.vendores=='zt'){
				var finger = new ZTFinger();
				fingInfo = finger.getMB();
			}
			return fingInfo;
		},
		match:function(mb1,mb2){ 
			var result=false;
			if(this.vendores=='miaxis'){
				var finger = new ZZFinger();
				result = finger.match(mb1, mb2);
			}
			if(this.vendores=='zt'){
				var finger = new ZTFinger();
				result = finger.match(mb1, mb2);
			}
			return result;
		},
		getVersion:function(){
			var version="";
			if(this.vendores=='miaxis'){
				var finger = new ZZFinger();
				version = finger.getVersion();
			}
			if(this.vendores=='zt'){
				var finger = new ZTFinger();
				version = finger.getVersion();
			}
			return version;
		}
}

var ZZFinger = function(){
	this.manuCode = "00001";
}
ZZFinger.prototype = {
		getImg:function(){ // *采集指纹图像
		var resultObj = "";
			try{
				resultObj = $.parseJSON(CommOcx.ThirdGetImage(this.manuCode,''));
			}catch(e){
				exceptions(e);
			}
			if(resultObj.result==0){
				return resultObj.content;
			}
			$.miaxisTools.alert(resultObj.text);
			return false;
		},
		getMB:function(lpImageStr1,lpImageStr2,lpImageStr3){ // 获取指纹模板
			var lpImageStr1 = this.getImg();// 三个指纹图像合成指纹模板.
			if(lpImageStr1)
				var lpImageStr2 = this.getImg();
			if(lpImageStr2)
				var lpImageStr3 = this.getImg();
			if(lpImageStr3){
				var value = CommOcx.ThirdGetMB(this.manuCode,lpImageStr1,lpImageStr2,lpImageStr3);
				var resultObj = $.parseJSON(value);
				if(resultObj.result=="0"){
					setTimeout(function(){$.miaxisTools.alert("指纹采集成功！");},200);
					return resultObj.content;
				}
				return false;
			}
			return false;
		},
		match:function(fingerFeature,mb){ // 指纹模板与图像匹配(指纹比对) 参数 指纹模板 和 指纹图像
			var resultObj = $.parseJSON(CommOcx.ThirdFingerMatch(this.manuCode,mb,fingerFeature));
			if(resultObj.result=="0"){
				return true;
			}else{
				return false;
			}
		},
		getVersion:function(){ // 获取指纹仪硬件版本
			var resultObj = $.parseJSON(CommOcx.ThirdGetVerFinger(this.manuCode,''));
			return resultObj;
		}
}

var ZTFinger = function(){}
ZTFinger.prototype = {
		getTemplate:function(){ // *采集指纹图像
		var resultObj = "";
			try{
				result = xt22UOCX.FPIGetTemplate (0, 10000);
			}catch(e){
				exceptions(e);
			}
			if(result!=0){
				$.miaxisTools.alert('采集指纹模版失败!');
			}
			return result;
		},
		getMB:function(){ // 获取指纹模板
			var result = this.getTemplate();
			if(result==0){
				result = xt22UOCX.FPIGetFingerInfo();
				return result;
			}
			return "";
		},
		match:function(mb2){ // 指纹模板与图像匹配(指纹比对) 参数 指纹模板 和 指纹图像
			var result = xt22UOCX.FPIGetFeature(0,10000 );
		    if(result != 0 ){
		    	$.miaxisTools.alert('采集指纹特征失败!');
		    }else{
		    	var mb1 = xt22UOCX.FPIGetFingerInfo();
				var result = xt22UOCX.xtVerify(mb1, mb2, 3);
				if(result==0){
					return true;
				}else{
					return false;
				}
		    }
		},
		getVersion:function(){ // 获取指纹仪硬件版本
			var result = xt22UOCX.XTGetVersion();
			return result;
		}
}

var IDCard = function(){
	this.manuCode = "00001";
}
IDCard.prototype = {
		read:function(){// 读二代证信息
			var resultObj = "";
			try{
				resultObj = $.parseJSON(CommOcx.ThirdReadIDCard(this.manuCode,''));
			}catch(e){
				exceptions(e);
			}
			if(resultObj.result=="0"){
				return resultObj.content;
			}
			$.miaxisTools.alert(resultObj.text);
			return false;
		},
		readID:function(){// 读取二代证物理编号
			var str;
			try{
				str = CommOcx.ThirdReadCardID(this.manuCode,'');
			}catch(e){
				exceptions(e);
			}
			var resultObj = $.parseJSON(str);
			if(resultObj.result=="0"){
				if(resultObj.content.CardType!="20"){
					$.miaxisTools.alert("卡类型错误，请刷身份证！");
					return false;
				}
				return resultObj.content.CardID;
			}
			$.miaxisTools.alert(resultObj.text);
			return false;
		}
}

var Photo = function(){
	this.manuCode = "00001";
}
Photo.prototype = {
		getPhoto:function(){// 摄像头拍照
			var resultObj = $.parseJSON(CommOcx.ThirdGetPhoto(this.manuCode,''));
			if(resultObj.result=="0"){
				return resultObj.content;
			}
			$.miaxisTools.alert(resultObj.text);
			return false;
		}
}

// 获取工程目录
var getRootPath = function(){
	var strFullPath = window.document.location.href;
	var strPath = window.document.location.pathname;
	var pos = strFullPath.indexOf(strPath);
	var prePath = strFullPath.substring(0, pos);
	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	return postPath;
}

function isMiaxisOcxReg(callback){
	if(typeof(CommOcx)=="undefined"){
		$.miaxisTools.alert("中正设备OCX未注册");
		setTimeout(isMiaxisOcxReg, 1000);
		return false;
	}else
		return true;
}

function isZtOcxReg(callback){
	if(typeof(xt22UOCX)=="undefined"){
		$.miaxisTools.alert("浙泰设备OCX未注册");
		setTimeout(isZtOcxReg, 1000);
		return false;
	}else
		return true;
}

