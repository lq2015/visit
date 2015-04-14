<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>/js/MiaxisOCX/Third_FrontOCX.js" charset="UTF-8"></script>
<style>
<!--
.leftHand {
	width: 160px;
	padding-left: 0px;
	float: left;
}

.rightHand {
	float: right;
	padding-right: 0px;
}

.handchx {
	float: left;
	width: 31px;
}

.handchx1 {
	float: right;
	width: 31px;
}

.handimg {
	border: none;
	clear: both;
	width: 364px;
}

.handimg img {
	margin: 0 0 0 0;
}

.hand {
	
}
-->
</style>
<body>
  <object classid="CLSID:A3ECA36F-CD28-422C-918F-5893C6FC9849"
	id="CommOcx" width="1" height="1" codebase="Third_FrontOCX.ocx">
	</object>
	<OBJECT height="0" width="0" align="left"
		classid="clsid:07390B1A-6918-493E-A465-F857F4A8D655" name="xt22UOCX">
		<PARAM NAME="_ExtentX" VALUE="265">
		<PARAM NAME="_ExtentY" VALUE="265">
		<PARAM NAME="_StockProps" VALUE="0">
		<embed width="0" height="0">
		</embed>
	</OBJECT>
		
	<input type="hidden" name="finger0" value="${finger0}">
	<input type="hidden" name="finger1" value="${finger1}">
	<input type="hidden" name="finger2" value="${finger2}">
	<input type="hidden" name="finger3" value="${finger3}">
	<input type="hidden" name="finger4" value="${finger4}">
	<input type="hidden" name="finger5" value="${finger5}">
	<input type="hidden" name="finger6" value="${finger6}">
	<input type="hidden" name="finger7" value="${finger7}">
	<input type="hidden" name="finger8" value="${finger8}">
	<input type="hidden" name="finger9" value="${finger9}">
	<div class="ftitle">
		<span>服务人员信息</span>
	</div>
	<table width="100%" cellspacing="0">
		<tr>
			<td align="left" style="text-align:left;padding-left: 30px"
				height="30" valign="top">
				<div style="height: 5px">${personNames }</div> <br />
			</td>
		</tr>
	</table>
	<div class="ftitle">
		<span>【${person}】指纹信息</span>
	</div>
	<div class="clear"></div>
	<div class="hand"
		style="text-align:center; border:0px solid #dcdcdc; height:230px; width:380px; margin:0 auto;">
		<span class="leftHand"> <c:forEach var="i" begin="0" end="4">
				<span class="handchx"> <input type="checkbox"
					name="fingerCode" value="${i}" disabled />
				</span>
			</c:forEach>
		</span> <span class="rightHand">
			<div>
				<c:forEach var="i" begin="5" end="9">
					<span class="handchx1"> <input type="checkbox"
						name="fingerCode" value="${14-i}" disabled />
					</span>
				</c:forEach>
			</div>
		</span>
		<div class="handimg">
			<img src="${pageContext.request.contextPath}/images/hand.gif"
				width="364" height="197"></img>
		</div>
		<input type="hidden" id="btn_sub">
	</div>
	<script>
		var finger = new Finger("${fingerVerdor}");
		
		$().ready(function() {
			<%//表单提交后或编辑时初始化指纹选项%>
			<c:if test="${fingerInfoList != null}">
				var fingerIndex = new Array();
				<c:forEach var="finger" items="${fingerInfoList}">
					fingerIndex.push("${finger.fiCode}");
				</c:forEach>
				$("input[name='fingerCode']").each(function(){
					this.checked = jQuery.inArray(this.value, fingerIndex) == -1 ? false : true;
				});
			</c:if>
		})
		
		$("#btn_sub").click(function() {
			var fingerData = finger.getImg();
			var verify=0;
			if(fingerData){
				$("input[type=checkbox]:checked").each(function(index,element){
					var num = $(this).val();
					var mb = $("input[name=finger"+num+"]").val();
					var result = finger.match(mb,fingerData);  <%//中正的传值顺序不能调，模板前，指纹图像后%>
					if(result){
						var params = {id : ${id}};
						$.miaxisTools.ajaxSubmit('jobDispatch.do?signSubmit', params);
						return false;
					}else{
						verify++;
						if(verify==2){
							$.miaxisTools.alert('指纹验证没有通过，签到失败!');
						}
					}
				})
			}
		});
	</script>
</body>