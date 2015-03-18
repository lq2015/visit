<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="userDetail">
		<form id="authMachineApply_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<input id="loginname" name="loginname" type="hidden" value="${loginname}" />
			<input id="machinecode" name="machinecode" type="hidden" value="${machinecode}" />
			<input id="machinename" name="machinename" type="hidden" value="${hostname}" />
			<div class="ftitle" >
				<span>申请信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th width="60">
						登陆名:					</th>
					<td width="100">${loginname} </td>
					<th  width="80">	
						机器特征信息:					</th>
					<td  width="100">
							${machinecode}	</td>
				</tr>
				<tr>
					<th>
						使用期止:					</th>
					<td colspan="3">
						<input id="authenddate" class="easyui-validatebox" name="authenddate" onclick="WdatePicker();" style="width: 300px;"   class="easyui-validatebox my97" />				</td>
				</tr>
				<tr>
					<th>
						备注:					</th>
					<td colspan="3">
						<textarea id="memo"  name="memo" style="width: 300px;height:50px;"  ></textarea>
                    </td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		$("#btn_sub").click(function() {
			var authenddate = $('#authenddate').val();
			var memo = $('#memo').val();
			if(authenddate==''){
				$.miaxisTools.alert('授权结束时期不能为空，请选择!');
				return ;
			}
			if(memo==''){
				$.miaxisTools.alert('备注不能为空，请填写!');
				return ;
			}
			
			var params={};
			$.miaxisTools.ajaxSubmitForm('userAuthMachine.do?applySubmit','authMachineApply_form',params);
		});
		
		$().ready(function() {
		
		})
		
		
	</script>
</body>
