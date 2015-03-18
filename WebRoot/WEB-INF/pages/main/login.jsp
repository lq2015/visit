<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<STYLE TYPE="text/css">
BODY {
	background-color: #ffffff;
}

TD span {
	FONT-SIZE: 13px;
	COLOR: #ffffff;
}

#main {
	position: absolute;
	width: 900px;
	left: 50%;
	top: 50%;
	margin-left: -450px;
	margin-top: -280px;
}
</STYLE>
<body>
	<div id="main">
		<form id="loginform" method="post" action="main.do?loginSubmit">
			<TABLE cellSpacing=0 cellPadding=0 width=900 align=center border=0>
				<TBODY>
					<TR>
						<TD style="HEIGHT: 105px">
							<IMG src="images/login_1.gif" border=0>
						</TD>
					</TR>
					<TR>
						<TD background=images/login_2.jpg height=300>
							<TABLE height=300 cellPadding=0 width=900 border=0>
								<TBODY>
									<TR>
										<TD colSpan=2 height=35></TD>
									</TR>
									<TR>
										<TD width=360></TD>
										<TD>
											<TABLE cellSpacing=0 cellPadding=2 border=0>
												<TBODY>
													<TR>
														<TD style="HEIGHT: 30px" width=70>
															<span>登&nbsp;&nbsp;录&nbsp;名：</span>
														</TD>
														<TD>
															<input class="easyui-validatebox" id="loginName"
																name="loginName" value="${loginName }"
																style="height: 18px; width: 150px"
																data-options="required:true" />
														</TD>
													</TR>
													<TR>
														<TD style="HEIGHT: 30px">
															<span>登录密码：</span>
														</TD>
														<TD>
															<input class="easyui-validatebox" id="loginPassword"
																type="password" name="loginPassword"
																value="${loginPassword }"
																style="height: 18px; width: 150px"
																data-options="required:true" />
														</TD>
													</TR>

													<TR>
														<TD style="HEIGHT: 18px"></TD>
														<TD style="HEIGHT: 18px"></TD>
														<TD style="HEIGHT: 18px"></TD>
													</TR>
													<TR>
														<TD></TD>
														<TD>
															<INPUT style="background:url('images/login_button.gif') no-repeat ;BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px;width:152px;height:36px;cursor:hand"
																id="submitBtn" type=button  >
														</TD>
													</TR>
												</TBODY>
											</TABLE>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<IMG src="images/login_3.jpg" border=0>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			<input type="hidden" id="machineCode" name="machineCode">
			<input type="hidden" id="hostname" name="hostname">
		</form>
	</div>
	<script type="text/javascript">
	
	$(function() {
		if ("${error}" != '') {
			$.dialog({ 
				icon: 'alert.gif', 
				title: '系统提示',
			    time: 5, 
			    content: "${error}" ,
			    cancelVal: '确定', 
			    cancel: true 
			});
		}
		var machine = LODOP.GET_SYSTEM_INFO('NetworkAdapter.1.PhysicalAddress');
		var hostname = LODOP.GET_SYSTEM_INFO('Printer.SystemName');
		machine = machine.replace(/-/g,"");
		$("#machineCode").val(machine);
		$("#hostname").val(hostname);
	});
	
	$("#submitBtn").click(
		function() {
			if ($("#loginName").val() == "") {
				return false;
			} else {
				$("#loginform").submit();
				return true;
			}
		}
	);
	
	function authApply(machinecode){
		var loginName= $("#loginName").val();
		var hostname= $("#hostname").val();
		if(loginName==''){
			$.miaxisTools.alert('登陆名不能为空!');
			return ;
		}
		
		$.miaxisTools.openPopupWin({
			url : 'userAuthMachine.do?apply&machinecode='+machinecode+'&loginname='+loginName+'&hostname='+hostname,
			width : 500,
			height : 200,
			title : '',
			okBtnId : 'btn_sub'
		});
	}

</script>
</body>
