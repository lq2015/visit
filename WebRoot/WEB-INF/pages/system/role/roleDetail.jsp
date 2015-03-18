<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="roleDetail">
		<form id="roleDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<input id="id" name="id" type="hidden" value="${role.id}" />
			<input id="disable" name="disable" type="hidden" value="0" />
			<div class="ftitle" >
				<span>角色基本信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th>
						角色名:					</th>
					<td>
						<input id="roleName" class="easyui-validatebox" name="roleName"
							 value="${role.roleName}"  data-options="required:true"/>					</td>
				</tr>
				<tr>
					<th>
						备注:					</th>
					<td>
						<textarea style="width: 95%; height: 50px;" name="memo"
										id="memo" value="${role.memo}">${role.memo}</textarea>
					</td>		
				</tr>
			</table>
		</form>
		<input id="operationType" name="operationType" type="hidden" value="${operationType}" />
	</div>
	
	<script type="text/javascript">
		$("#btn_sub").click(function() {
			var params={operationType:$("#operationType").val()};
			$.miaxisTools.ajaxSubmitForm('role.do?save','roleDetail_form',params);
		});
	</script>
</body>
