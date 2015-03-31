<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="bankDetail">
		<form id="bankDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<div class="ftitle" >
				<span>支行网点基本信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th >
						网点代码:
					</th>
					<td>
						<input id="id" name="id"  data-options="required:true"
							class="easyui-validatebox" value="${bankInfo.id}" />
					</td>
					<th>
						网点名称:
					</th>
					<td>
						<input id="biName" name="biName"  data-options="required:true"
							class="easyui-validatebox" value="${bankInfo.biName}" />
					</td>
				</tr>
				<tr>
					<th >
						网点简称:						</th>
					<td>
						<input id="biShortenName" name="biShortenName"
							class="easyui-validatebox" value="${bankInfo.biShortenName}" />	
					</td>
					<th >
						网点地址:
					</th>
					<td>
						<input id="biAddress" name="biAddress"
							class="easyui-validatebox" value="${bankInfo.biAddress}" />
					</td>
				</tr>
				<tr>
					<th >
						联系人:
					</th>
					<td>
						<input id="biLinkman" name="biLinkman"
							class="easyui-validatebox" value="${bankInfo.biLinkman}" />
					</td>
					<th>
						联系电话:					</th>
					<td>
						<input id="biTelephone" name="biTelephone" 
							class="easyui-numberbox" value="${bankInfo.biTelephone}" />
					</td>
				</tr>
				<tr>
					<th>
						联系人手机:
					</th>
					<td>
						<input id="biMobile" name="biMobile" data-options="required:true"
							class="easyui-numberbox" value="${bankInfo.biMobile}" />
					</td>
					<th>
						备注:	
                    </th>
					<td>
						<input id="biMemo" name="biMemo"
							class="easyui-validatebox" value="${bankInfo.biMemo}" />
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
		$.miaxisTools.ajaxSubmitForm('bank.do?save','bankDetail_form',params);
	});
	</script>
</body>
