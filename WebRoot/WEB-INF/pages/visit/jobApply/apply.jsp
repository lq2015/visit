<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="jobApplyDetail">
		<form id="jobApplyDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<input id="id" name="id" type="hidden" value="${jobApply.id}" />
			<div class="ftitle" >
				<span>服务申请信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th  >
						需服务网点:
					</th>
					<td>
						<input class="easyui-combobox" id="jaJobBank" name="jaJobBank"  style="width: 305px;" url="public.do?userDept&persontype=2"
								 data-options="valueField:'id',textField:'name',panelHeight:'auto',editable:false,required:true"/>	
					</td>
				</tr>
				<tr>
					<th width="100px">
						服务项目:
					</th>
					<td>
						<input id="jaServeItem" name="jaServeItem" type="hidden" value="${jobApply.jaServeItem}" />
						<input class="easyui-combotree" id="jaServeItemId" name="jaServeItemId" 
							data-options="url:'unitPact.do?getServeItem',method:'get',panelHeight:120,panelWidth:200,editable:false,required:true" style="width:305px;"/>		
					</td>
				</tr>
				<tr>
					<th>
						服务时间:					</th>
					<td>
						<input id="jaJobDate" name="jaJobDate"  onclick="WdatePicker();" data-options="required:true"
								class="easyui-validatebox my97" value="${jobApply.jaJobDate}" style="width: 300px;"/>
					</td>
				</tr>
				<tr>
					<th >
						工作内容简述:
					</th>
					<td>
						<textarea id="jaJobContent" name="jaJobContent"
							class="easyui-validatebox" style="width: 300px;height:50px" >${jobApply.jaJobContent}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						备注:	
                    </th>
					<td>
						<textarea id="jaMemo" name="jaMemo"
							class="easyui-validatebox"  style="width: 300px;height:40px">${jobApply.jaMemo}</textarea>
					</td>		
				</tr>
			</table>
		</form>
		<input id="operationType" name="operationType" type="hidden" value="${operationType}" />
	</div>
	
	<script type="text/javascript">
	$().ready(function() {
			
		if ('${jobApply.jaServeItemId}' != '') {
			$('#jaServeItemId').combotree('setValue','${jobApply.jaServeItemId}');
		}
		
		if ('${jobApply.jaJobBank}' != '') {
			$('#jaJobBank').combobox('setValue','${jobApply.jaJobBank}');
		}
	});
	
	$('#jaServeItemId').combotree({
		onSelect : function(node) {
			$('#jaServeItem').val(node.text);
		}
	});
	
	$("#btn_sub").click(function() {
		var params={operationType:$("#operationType").val()};
		var opts={resetForm:true};
		$.miaxisTools.ajaxSubmitForm('jobApply.do?save','jobApplyDetail_form',params,opts);
	});
	</script>
</body>
