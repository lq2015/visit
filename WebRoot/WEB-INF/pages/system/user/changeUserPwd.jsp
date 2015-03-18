<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<form id="changepwd_form" method="post">
		<input type="hidden" id="btn_sub" >
		<input type="hidden" name="id" id="id" value="${userSession.id}" >
		<div class="ftitle" >
				<span>用户密码修改</span>
			</div>
		<table class="doc-table">
			<tr>
				<th>
					原用户密码:
				</th>
				<td>
					<input id="oldPassword" name="oldPassword" class="easyui-validatebox" type="password" 
						data-options="required:true"/>
				</td>
			</tr>
			<tr>
				<th>
					新用户密码:
				</th>
				<td>
					<input id="newPassword" name="newPassword" class="easyui-validatebox" type="password" 
						data-options="required:true,validType:'length[2,10]'" />
				</td>
			</tr>
			<tr>
				<th>
					新密码确认:
				</th>
				<td>
					<input id="newPassword2" name="newPassword2" class="easyui-validatebox" type="password" 
						data-options="required:true,validType:'length[2,10]'"/>
				</td>
			</tr>
		</table>
	</form>
</body>

<script type="text/javascript">
   	$("#btn_sub").click(function() {
   		var newPassword1 = $('#newPassword').val();
		var newPassword2 = $('#newPassword2').val();
		if(newPassword1!=newPassword2){
			$.miaxisTools.alert('两次输入的新密码不一致,请重新输入!');
			return;
		}
   		var params={};
		$.miaxisTools.ajaxSubmitForm('user.do?changeUserPwdSubmit','changepwd_form',params);
	});
</script>