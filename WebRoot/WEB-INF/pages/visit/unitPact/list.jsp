<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width:50%; height: 450px;"fit="true">
		<div data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height: 50px;" class="query_panel" border="false">
			<form id="unitPact_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							服务合同编号:
						</td>
						<td>
							<input class="easyui-validatebox" type="text" 
								name="qUpNumber" id="qUpNumber" />
						</td>
						<td class="tdlabel">
							状态:
						</td>
						<td>
							<select class="easyui-combobox"  name="qUpStatus"  
								style="width: 100px;" data-options="panelHeight:'auto'">
								<option value="">--全部--</option>
								<option value="0">录入</option>
								<option value="1">正常</option>
								<option value="9">注销</option>
							</select>	
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryPact();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="unitPactDg" title="服务合同信息" toolbar="#unitPactToolBar" pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="upNumber" width="40" align="left" sortable="true">
							合同编号
						</th>
						<th field="upServeItem" width="100" align="center" sortable="true">
							 合同服务项目
						</th>
						<th field="upServeStart" width="40" align="center" sortable="true">
							合同开始日期
						</th>
						<th field="upServeEnd" width="40" align="center" sortable="true">
							合同结束日期
						</th>
						<th field="upStatus" width="20" align="center" sortable="true" formatter="formatPactStatus">
							状态
						</th>
						<th field="upMemo" width="80" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
			<div id="unitPactToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#unitPactDg').datagrid({
			url : 'unitPact.do?list'
		});
	});
	
	function queryPact() {
		$('#unitPactDg').datagrid('load',$.miaxisTools.serializeObject($('#unitPact_query_form')));
	}
	
	function detailPact() {
		var row = $('#unitPactDg').datagrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'unitPact.do?detail&id=' + row.id,
				width : 700,
				height : 365,
				title : '查看服务合同',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的服务合同记录!');
		}
	}

	function editPact() {
		var row = $('#unitPactDg').datagrid('getSelected');
		if (row) {
			var index = $('#unitPactDg').datagrid('getRowIndex',row);
			if(row.uiStatus=='9'){
				$.miaxisTools.alert('服务合同已经注销,不能进行修改!');
				return false;
			}else{
				$.miaxisTools.openPopupWin({
					url   : 'unitPact.do?insertOrUpdate&operationType=edit&id=' + row.id,
					width : 700,
					height: 365,
					title : '修改服务合同',
					okBtnId : 'btn_sub'
				});
			}
		} else {
			$.miaxisTools.alert('请选择需要修改的服务合同记录!');
		}
	}

	function newPact() {
		$.miaxisTools.openPopupWin({
			url    : 'unitPact.do?insertOrUpdate&operationType=insert',
			width  : 700,
			height : 365,
			title  : '新增服务合同',
			okBtnId : 'btn_sub'
		});
	}
	
	function removePact() {
		var row = $('#unitPactDg').datagrid('getSelected');
			if (row){
				var index = $('#unitPactDg').datagrid('getRowIndex',row);
				$.miaxisTools.confirm('确定要删除当前服务合同吗?',function(r){
					if (r){
							$.post('unitPact.do?del',{id:row.id}, function (data){
								if (data.result=='0'){
				                	$('#unitPactDg').datagrid('deleteRow',index);
				              	}
				               $.miaxisTools.alert(data.message);
				            },'json');
					}
				});
			}else{
				$.miaxisTools.alert('请选择需要删除的服务合同记录!');
			}
	}
	
	 function updatePactStatus(status){
	    	var row = $('#unitPactDg').datagrid('getSelected');
			if (row){
				var index = $('#unitPactDg').datagrid('getRowIndex',row);
				
				if(status=='1'){
					if(row.upStatus=='1'){
						$.miaxisTools.alert('此服务合同已经启用,无须重复启用!');
						return false;
					}
				}
				if(status=='9'){
					if(row.upStatus!='1'){
						$.miaxisTools.alert('此服务合同未启用,不能注销!');
						return false;
					}
				}
				
				$.post('unitPact.do?updateStatus',{id:row.id,status:status}, function (data){
					   row.upStatus=status;
					   $('#unitPactDg').datagrid('refreshRow', index);
		               $.miaxisTools.alert(data.message);
		         },'json');
			}else{
				var keymsg=status=='1'?'启用':'注销';
				$.miaxisTools.alert('请选择需要的'+keymsg+'记录!');
			}
	    }
	 
	 function formatPactStatus(val, row) {
		if (val == '0') {
			return "录入";
		} else if (val == '1') {
			return "正常";
		} else if (val == '9') {
			return "注销";
		}
	}
	 
</script>
</body>
