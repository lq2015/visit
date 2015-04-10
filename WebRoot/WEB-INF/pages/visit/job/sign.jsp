<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>/js/MiaxisOCX/Third_FrontOCX.js" charset="UTF-8"></script>
<body>
	<object classid="CLSID:A3ECA36F-CD28-422C-918F-5893C6FC9849"
		id="CommOcx" width="1" height="1" codebase="Third_FrontOCX.ocx">
	</object>
	<OBJECT height="0.1" width="0.1" align="left"
		classid="clsid:07390B1A-6918-493E-A465-F857F4A8D655" name="xt22UOCX">
		<PARAM NAME="_ExtentX" VALUE="265">
		<PARAM NAME="_ExtentY" VALUE="265">
		<PARAM NAME="_StockProps" VALUE="0">
		<embed width="10" height="10">
		</embed>
	</OBJECT>

	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div data-options="region:'center'" border="false">(${id})sigdddn
		<input id="id" name="id" type="hidden" value="${id}" /> <input
			type="hidden" id="btn_sub">

		<table width="500" border="1">
			<tr>
				<td width="200">
					<div align="center">版本信息</div>
				</td>
				<td width="600" align="center"><input type="text"
					id="version" size="60"></td>
				<td><div align="center">
						<input name="获取版本" type="button" id="XTGetVersion" value="获取版本"
							onClick="getver()">
					</div></td>
			</tr>
			<tr>
				<td width="200">
					<div align="center">指纹采集</div>
				</td>
				<td width="600" align="center"><input type="text"
					id="fingerMb" size="60"></td>
				<td><div align="center">
						<input name="EnrollFinger" type="button" id="EnrollFinger"
							value="采集指纹" onClick="getMb()">
					</div></td>
			</tr>
			<tr>
				<td width="200">
					<div align="center">指纹比对</div>
				</td>
				<td width="600" align="center"><input type="text"
					id="fingerSample" size="60"></td>
				<td><div align="center">
						<input name="SampleFinger" type="button" id="SampleFinger"
							value="指纹比对" onClick="match()">
					</div></td>
			</tr>
			<tr>
				<td width="200">
					<div align="center">比对结果(0 成功；1 失败)</div>
				</td>
				<td width="600" align="center"><input type="text"
					id="xtverify" size="60"></td>
				<td><div align="center"></div></td>
			</tr>
			<td width="200">
				<div align="center">resetdevice</div>
			</td>
			<td width="600" align="center"><input type="text"
				id="resetdevice" size="60"></td>
			<td><div align="center">
					<input name="ResetDevice" type="button" id="ResetDevice"
						value="ResetDevice" onClick="return ResetDevice_onclick()">
				</div></td>
			</tr>
		</table>
		</div>
	</div>


	<script type="text/javascript">
	var CommOcx;
	$().ready(function() {
		CommOcx = document.getElementById("CommOcx");
	});
	
		var api = frameElement.api, W = api.opener;

		$("#btn_sub").click(function() {
			var params = {
				id : ${id}
			};
			$.miaxisTools.ajaxSubmit('jobDispatch.do?signSubmit', params);
		});
		
		var finger = new Finger("miaxis");
		
		function getMb(){
			var mb1 = finger.getMB();	
			$("#fingerMb").val(mb1);
		}
		
		function match(){
			var mb =$("#fingerMb").val();
			var result = finger.match(mb);
			$("#xtverify").val(result);
		}
		
		function getver(){
			var ver = finger.getVersion();
			$("#version").val(ver);
		}
	</script>
</body>
