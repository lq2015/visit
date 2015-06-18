<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<script type="text/javascript" src="<%=basePath%>/js/MiaxisOCX/Third_FrontOCX.js" charset="UTF-8"></script>
<style>
.button {
	width: 60px;
	height: 25px
}
</style>

<body>
	<object classid="CLSID:A3ECA36F-CD28-422C-918F-5893C6FC9849" id="CommOcx" width="1" height="1" codebase="Third_FrontOCX.ocx"> </object>
	<OBJECT height="0" width="0" align="left" classid="clsid:07390B1A-6918-493E-A465-F857F4A8D655" name="xt22UOCX">
		<PARAM NAME="_ExtentX" VALUE="265">
		<PARAM NAME="_ExtentY" VALUE="265">
		<PARAM NAME="_StockProps" VALUE="0">
		<embed width="0" height="0">
		</embed>
	</OBJECT>

	<div class="ftitle">
		<span>服务人员信息</span>
	</div>
	<div style="margin-left: 20px">
		<c:forEach var="person" items="${personList}">
			<div style="float:left;margin-left:10px;margin-bottom: 20px;">
				<div id="preview_${person.id}" style="border:solid 1px #999999;width:120px;height:140px">
					<img name="photo_${person.id}" width=120 height=140 border=0 src="${person.piPhotoUrl}"  />
				</div>
				<div style="height:35px;line-height:35px;padding-left:10px;padding-right:10px;background-color:#EBEBEB;border:1px solid #CCCCCC">
					<div style="float:left;"><a href="#" onClick="detailPerson(${person.id})">${person.piName}</a></div>
					<div style="float:right;margin-top: 5px">
						<input id="check_${person.id}" type="button" class="button" value="未验证" onClick="checkFinger(${person.id})"/>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<input type="hidden" id="btn_sub">

	<script>
		var finger = new Finger("${fingerVerdor}");
		var passNumber=0;
		var personNumber="${personNumber}";
	
		<c:forEach var="person" items="${personList}">
			$("#preview_${person.id}").poshytip({
				alignY : 'center',
				className : 'tip-twitter',
				content : function() {
					var _html = [];
					_html.push('<div style="background-color:#D6DCF1;padding:10px;border:1px solid #7E92D3">');
					_html.push('<b>姓 名:</b>：${person.piName}');
					_html.push('<br>');
					_html.push('<b>身份证号:</b>${person.piIdnum}');
					_html.push('<br>');
					_html.push('<b>性 别:</b>${person.piSex}');
					_html.push('<br>');
					_html.push('<b>联系手机:</b>${person.piMobile}');
					_html.push('<br>');
					_html.push('<b>职 务:</b>${person.piPost}');
					_html.push('<div>');
					return _html.join("");
				}
			});
		</c:forEach>
		
		function checkFinger(personId){
			if(personId){
				var fingerData = finger.getImg();
				var verify=0;
				if(fingerData){
					getFinger(personId,function(datajson){
						for(var i=0;i<datajson.length;i++){
							var mb = datajson[i].fiTemplate;
							var result = finger.match(mb,fingerData); <%//中正的传值顺序不能调，模板前，指纹图像后%> 
							if(result){
								passNumber++;
								$("#check_"+personId).val('验证通过');
								$("#check_"+personId).attr({"disabled":"disabled"});
							}else{
								verify++;
								if(verify==2){
									$.miaxisTools.alert('指纹验证没有通过，签到失败!');
								}
							}
						}
					});
				}
			}
		}
		
		<%//提取指纹%> 
		function getFinger(personId,callback){
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : "person.do?getFinger&personId="+personId,
				error : function() {
					$.miaxisTools.alert("获取人员指纹信息失败!"); 
				},
				success : function(datajson) {
					callback(datajson);
				}
			});	
		}
		
		function detailPerson(personId){
			var api = frameElement.api, W = api.opener;
			W.$.dialog({
				title:'查看人员信息',
				id:Math.round(Math.random()*(1000-100000)+100000),
				content:'url:<%=basePath%>/person.do?detail&id=' + personId,
			    lock:true,
			    max:false,
			    parent:api,
			    width:600,
			    height:380,
			    cancel : true,
			    cancelVal:'关闭'
			});
		}
		
		$("#btn_sub").click(function() {
			if(personNumber==""){
				personNumber="0";
			}
			if(passNumber<personNumber){
				$.miaxisTools.confirm('还有未验证通过的人员,是否允许签到?',function(r){
					if(r){
						var params = {id : ${id}};
						$.miaxisTools.ajaxSubmit('jobDispatch.do?signSubmit', params);
					}
				})
			}else{
				var params = {id : ${id}};
				$.miaxisTools.ajaxSubmit('jobDispatch.do?signSubmit', params);
			}
		});
		
	</script>
</body>