<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="unitDetail">
		<form id="unitDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<input id="id" name="id" type="hidden" value="${unitInfo.id}" />
			<div class="ftitle" >
				<span>单位基本信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th>
						单位名称:
					</th>
					<td colspan="3">
						<input id="uiName" name="uiName"  data-options="required:true" style="width:420px;"
							class="easyui-validatebox" value="${unitInfo.uiName}" />
					</td>
				</tr>
				<tr>
					<th >
						单位地址:
					</th>
					<td colspan="3">
						<input id="uiAddress" name="uiAddress" style="width:420px;"
							class="easyui-validatebox" value="${unitInfo.uiAddress}" />
					</td>
				</tr>
				<tr>
					<th >
						联系人:
					</th>
					<td>
						<input id="uiLinkman" name="uiLinkman"
							class="easyui-validatebox" value="${unitInfo.uiLinkman}" />
					</td>
					<th>
						联系电话:					</th>
					<td>
						<input id="uiTelephone" name="uiTelephone" 
							class="easyui-numberbox" value="${unitInfo.uiTelephone}" />
					</td>
				</tr>
				<tr>
					<th>
						联系手机:
					</th>
					<td>
						<input id="uiMobile" name="uiMobile" data-options="required:true"
							class="easyui-numberbox" value="${unitInfo.uiMobile}" />
					</td>
					<th>
						备注:	
                    </th>
					<td>
						<input id="uiMemo" name="uiMemo"
							class="easyui-validatebox" value="${unitInfo.uiMemo}" />
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
		$.miaxisTools.ajaxSubmitForm('unit.do?save','unitDetail_form',params);
	});
	</script>
</body>
