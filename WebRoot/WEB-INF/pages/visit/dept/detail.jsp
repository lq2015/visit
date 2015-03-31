<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="deptDetail">
		<form id="deptDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<div class="ftitle" >
				<span>部门基本信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th >
						部门代码:
					</th>
					<td>
						<input id="id" name="id"  data-options="required:true"
							class="easyui-validatebox" value="${departmentInfo.id}" />
					</td>
					<th>
						部门名称:
					</th>
					<td>
						<input id="diName" name="diName"  data-options="required:true"
							class="easyui-validatebox" value="${departmentInfo.diName}" />
					</td>
				</tr>
				<tr>
					<th >
						联系人:
					</th>
					<td>
						<input id="diLinkman" name="diLinkman"
							class="easyui-validatebox" value="${departmentInfo.diLinkman}" />
					</td>
					<th>
						联系电话:					</th>
					<td>
						<input id="diTelephone" name="diTelephone" 
							class="easyui-numberbox" value="${departmentInfo.diTelephone}" />
					</td>
				</tr>
				<tr>
					<th>
						备注:	
                    </th>
					<td colspan="3">
						<input id="diMemo" name="diMemo"
							class="easyui-validatebox" value="${departmentInfo.diMemo}" />
					</td>		
				</tr>
			</table>
		</form>
		<input id="operationType" name="operationType" type="hidden" value="${operationType}" />
	</div>
	
	<script type="text/javascript">
	$().ready(function() {
		if($("#operationType").val()=='edit'){
			$("#id").attr('readonly','true');
			$("#id").attr("style","border:0;border-bottom:1 solid black;background:white;");
		}
	});
	
	$("#btn_sub").click(function() {
		var params={operationType:$("#operationType").val()};
		$.miaxisTools.ajaxSubmitForm('department.do?save','deptDetail_form',params);
	});
	</script>
</body>
