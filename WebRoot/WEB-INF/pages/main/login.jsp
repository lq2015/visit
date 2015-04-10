<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<STYLE TYPE="text/css">
BODY {
	background-color: #ffffff;
}

#main {
	position: absolute;;
	width: 100%;
	top: 50%;
	margin-top: -280px;
}

form input {
	width: 161px;
	border: solid 1px #083272;
	padding-left: 5px;
	line-height: 22px;
	height: 22px;
	font-size:14px;
	color: #000000;
}
</STYLE>
<body>
	<div id="main">
		<form id="loginform" method="post" action="main.do?loginSubmit">
			<table width="100%" height="550" border="0" cellspacing="0">
				<tr>
					<td style="background:url('images/login_1.jpg')">&nbsp;</td>
					<td style="background:url('images/login.jpg') no-repeat ;"
						width="1003">
						<div style="padding-left:380px;padding-top:80px;">
							<table width="280" border="0" style="">
								<tr height="40">
									<td><img src="images/user.png"></td>
									<td width="40px">用户:</td>
									<td><input class="easyui-textbox" id="loginName"
										name="loginName" value="admin" 
										data-options="required:true,iconCls:'icon-node'" missingMessage="用户名不能为空"/></td>
								</tr>
								<tr height="40">
									<td><img src="images/pwd.png"></td>
									<td>密码:</td>
									<td><input class="easyui-validatebox" id="loginPassword"
										type="password" name="loginPassword" value="123456"
										data-options="required:true" missingMessage="密码不能为空"/></td>
								</tr>
								<tr height="60">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td><INPUT
										style="background:url('images/login_button.png') no-repeat ;BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px;width:161px;height:36px;cursor:hand"
										id="submitBtn" type=button></td>
								</tr>
							</table>
						</div>
					</td>
					<td style="background:url('images/login_1.jpg')">&nbsp;</td>
				</tr>
			</table>
			<input type="hidden" id="machineCode" name="machineCode"> <input
				type="hidden" id="hostname" name="hostname">
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			if ("${error}" != '') {
				$.dialog({
					icon : 'alert.gif',
					title : '系统提示',
					time : 5,
					content : "${error}",
					cancelVal : '确定',
					cancel : true
				});
			}
			/*var machine = LODOP
					.GET_SYSTEM_INFO('NetworkAdapter.1.PhysicalAddress');
			var hostname = LODOP.GET_SYSTEM_INFO('Printer.SystemName');
			machine = machine.replace(/-/g, "");
			$("#machineCode").val(machine);
			$("#hostname").val(hostname);*/
		});

		$("#submitBtn").click(function() {
			if ($("#loginName").val() == "") {
				return false;
			} else {
				$("#loginform").submit();
				return true;
			}
		});

		function authApply(machinecode) {
			var loginName = $("#loginName").val();
			var hostname = $("#hostname").val();
			if (loginName == '') {
				$.miaxisTools.alert('登陆名不能为空!');
				return;
			}

			$.miaxisTools.openPopupWin({
				url : 'userAuthMachine.do?apply&machinecode=' + machinecode
						+ '&loginname=' + loginName + '&hostname=' + hostname,
				width : 500,
				height : 200,
				title : '',
				okBtnId : 'btn_sub'
			});
		}
	</script>
</body>
