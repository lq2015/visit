var returnStr = false;
var ICCard = function(manuCode){
	this.manuCode = manuCode;   // 厂商编码
}
ICCard.prototype = {
		init:function(lpField){// ----------卡初始化----------//0x01：学员卡；0x02：教练员卡；0x04：维护员卡；0x08：监督员卡；0x10:管理员卡；0x20：电子标签
			$.miaxisTools.alert(resultObj.text);
		},
		isValid:function(logicType,cityCode){// 卡合法性
			cityCode = '6502';
			var lpField = '{"Field":["CardID","CardType"]}';
			var icData = this.read(lpField);
			if(icData){
				decodeData(icData,logicType,cityCode,function(result){
					if(result){
						if(parseInt(result.CardID)==0){
							$.miaxisTools.alert("卡未初始化！");
							return false;
						}else if(result.CardID.substring(2,6)!=cityCode){
							$.miaxisTools.alert("卡所属地市错误！");
							return false;
						}else if(result.CardType != logicType){
								if(logicType=="1"){
									$.miaxisTools.alert("卡类型不对，请放入学员IC卡！");
								}
								if(logicType=="2"){
									$.miaxisTools.alert("卡类型不对，请放入教练员IC卡！");
								}
								return false;
//						}else if(!result.Used){
//							$.miaxisTools.alert("非法的IC卡！");
//							return false;
						}else{
							returnStr = result.CardID;
						}
					}
				});
			}else{
				//$.miaxisTools.alert("IC卡读取失败！");
				return false;
			}
			return returnStr;
		},
		read:function(lpField){// ----------读取卡信息-------//
			
			try{
				var resultObj = $.parseJSON(CommOcx.ThirdReadICCardField(this.manuCode,lpField));
			}catch(e){
				exceptions(e);
			}
			if(resultObj.result=="0"){
				return resultObj.content;
			}
			$.miaxisTools.alert(resultObj.text);
			return false; 
		},
		write:function(lpICData){// ----------写入IC卡--------//
			var results = "";
			try{
				results = CommOcx.ThirdWriteICCardField(this.manuCode,lpICData);
			}catch(e){
				exceptions(e);
			}
			var resultObj = $.parseJSON(results);
			if(resultObj.result==0)
				return true;
			return false;
		},
		readLog:function(InfoType){// --------读取日志信息(日志类型
								// LogType为1表示设备日志信息，为2表示学员签到签退日志，为3表示教练签到签退日志)-------//
			var resultObj = "";
			try{
				resultObj= $.parseJSON(CommOcx.ThirdGatherICCardLog(this.manuCode,InfoType,''));
			}catch(e){
				exceptions(e);
			}
			if(resultObj.result=="0"){
				return resultObj.content;
			}
			$.miaxisTools.alert(resultObj.text);
			return false;
		},
		readTrainInfo:function(){        // 采集IC卡中学员训练明细InfoType为0表示采集IC卡中所有的训练明细，为1表示采集未上传过得训练明细
			var resultObj="";
			try{
				resultObj = $.parseJSON(CommOcx.ThirdGatherICCardTrainInfo(this.manuCode,InfoType,''));
			}catch(e){
				exceptions(e);
			}
			
			if(resultObj.result=="0"){
				return resultObj.content;
			}
			$.miaxisTools.alert(resultObj.text);
			return false;
		}
}
var Finger = function(manuCode){
	this.manuCode = manuCode;
}
Finger.prototype = {
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
			
		}
}
var Terminal = function(manuCode){
	this.manuCode = manuCode;
}
Terminal.prototype = { // 发送终端通信信息 参数 发送的数据包 和 超时
		sendRecv:function(lpSend,nTimeout){
			var resultObj = $.parseJSON(CommOcx.ThirdSendRecvTerminalInfo(this.manuCode,lpSend,nTimeout));
			$.miaxisTools.alert(resultObj.text);
		},
		receive:function(){// 终端接收信息并发送后台
			var resultObj = $.parseJSON(CommOcx.ThirdReceiveTerminalInfo(this.manuCode,''));
			$.miaxisTools.alert(resultObj.text);
		},
		send:function(lpSend){// 往终端设备发送信息
			var resultObj = $.parseJSON(CommOcx.ThirdSendTerminalInfo(this.manuCode,lpSend));
			$.miaxisTools.alert(resultObj.text);
		}
}
var IDCard = function(manuCode){
	this.manuCode = manuCode;
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

var Photo = function(manuCode){
	this.manuCode = manuCode;
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
// 打印学员证
var PrintStudentCard = function(manuCode){
	this.manuCode = manuCode;
}

PrintStudentCard.prototype = {
		printStudentCard : function(lpData){
			var resultObj = $.parseJSON(CommOcx.ThirdPrintXYZ(this.manuCode,lpData));
			if(0 == resultObj.result){
				return resultObj.text;
			}
			return false;
		}
}
// 下载文件
var downloadFile = function(url) 
{   
	try{ 
		var elemIF = document.createElement("iframe");   
		elemIF.src = getRootPath()+"/"+url;   
		elemIF.style.display = "none";   
		document.body.appendChild(elemIF);   
	}catch(e){ 
		$.miaxisTools.alert(e);
	} 
}
// 通过ajax进行后台数据解密
var decodeData = function(datas,cardType,cityCode,callback){
	params={data:JSON.stringify(datas)};
	$.ajax({
		type:"get",
		dataType:"json",
		url:getRootPath()+"/miaxisOcx.do?decode",
		data:params,
		async:false,
		success : function(msg) {
			callback(msg);
			},
		error : function() {
			$.miaxisTools.alert("数据解密失败!!!");
		}
	});
}
var addLoading = function(text){
	if($("#windown-box").html() ==null){
		newWindow("系统提示");
	};
	$("div .windown-text").html("<img src='"+getRootPath()+"/images/tipswindown/loading.gif' style='margin:5px auto'/>"+text);
}// 获取工程目录
var getRootPath = function(){
	var strFullPath = window.document.location.href;
	var strPath = window.document.location.pathname;
	var pos = strFullPath.indexOf(strPath);
	var prePath = strFullPath.substring(0, pos);
	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	return postPath;
}
function isOcxReg(){
	if(typeof(mxDevObj)=="undefined"){
		setTimeout(isOcxReg, 1000);
		$.miaxisTools.alert("OCX未注册");
		return false;
	}else
		return true;
}
function exceptions(e){
	if(e.toString().indexOf("属性或方法")!=-1){
		if(confirm("控件未注册,现在安装控件？")){
			downloadFile('OCX-XM.EXE');
		}
	}
	if(e.toString().indexOf("CommOcx")!=-1){
		$.miaxisTools.alert("未找到OCX控件！");
	}
	return false;
}
var MR300 = function(){
}
MR300.prototype = { // 读取MR300条形码
	readSerialNumber:function(){
		var resultObj = $.parseJSON(CommOcx.ThirdMR300ReadHardSn());
		if(resultObj.result=="0"){
			return resultObj.content;
		}else{
			if(resultObj.result=="-19"){
				$.miaxisTools.alert("设备读取失败！");
			}else{
				$.miaxisTools.alert(resultObj.text);
			}
			return false;
		}
	}
}

