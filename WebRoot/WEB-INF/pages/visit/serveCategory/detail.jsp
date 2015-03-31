<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="categoryDetail">
		<form id="categoryDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<input id="id" name="id" type="hidden" value="${serveCategory.id}" />
			<div class="ftitle" >
				<span>类别基本信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th>
						类别名称:
					</th>
					<td colspan="3">
						<input id="scCategory" name="scCategory"  data-options="required:true" style="width:300px;"
							class="easyui-validatebox" value="${serveCategory.scCategory}" />
					</td>
				</tr>
				<tr>
					<th>
						类别层级:					</th>
					<td>
						<select class="easyui-combobox"  name="scLevel"  id="scLevel"  style="width:305px;"
							 data-options="panelHeight:'auto',required:true">
							<option value="1">1</option>
							<option value="2">2</option>
						</select>
					</td>
				</tr>
				<tr>
					<th >
						父类:
					</th>
					<td>
						<input class="easyui-combobox" id="scParent" name="scParent" 
							 data-options="valueField:'id',textField:'scCategory',panelHeight:'auto',editable:false"
						     style="width:305px;">
					</td>
				</tr>
				<tr>
					<th>
						备注:	
                    </th>
					<td colspan="3">
						<input id="scMemo" name="scMemo"  style="width:300px;"
							class="easyui-validatebox" value="${serveCategory.scMemo}" />
					</td>		
				</tr>
			</table>
		</form>
		<input id="operationType" name="operationType" type="hidden" value="${operationType}" />
	</div>
	
	<script type="text/javascript">
	$().ready(function() {
		loadParent();
		
		setTimeout(function(){
			if ('${serveCategory.scLevel}' != '') {
				$('#scLevel').combobox('setValue', '${serveCategory.scLevel}');
			}
			if ('${serveCategory.scParent}' != '') {
				$('#scParent').combobox('setValue', '${serveCategory.scParent}');
			}
			
			var level = $('#scLevel').combo('getValue');
			if(level=="1"){
				$('#scParent').combobox('disable');	
			}else{
				$('#scParent').combobox('enable');
			}
			
		},500)
	});
	
	$('#scLevel').combobox({
		onSelect : function() {
			var level = $('#scLevel').combo('getValue');
			if(level=="1"){
				$('#scParent').combobox('disable');	
				$('#scParent').combobox('clear');
			}else{
				$('#scParent').combobox('enable');
				$('#scParent').combobox('clear');
				loadParent();
			}
		}
	});
	
	$("#btn_sub").click(function() {
		var level = $('#scLevel').combo('getValue');
		if(level!="1"){
			var parent = $('#scParent').combo('getValue');
			if(parent==''){
				$.miaxisTools.alert('请选择所属父类!');
				return false;
			}
		}
		
		var params={operationType:$("#operationType").val()};
		$.miaxisTools.ajaxSubmitForm('serveCategory.do?save','categoryDetail_form',params);
	});
	
	function loadParent(){
		var url ='serveCategory.do?getParent';
		$('#scParent').combobox('clear');
		$('#scParent').combobox('reload', url); 
	}
	</script>
</body>
