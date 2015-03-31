<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height: 50px;" class="query_panel" border="false">
			<form id="bank_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							支行网点名:
						</td>
						<td>
							<input class="easyui-validatebox" type="text" 
								name="qBiName" id="qBiName" />
						</td>
						<td class="tdlabel">
							状态:
						</td>
						<td>
							<select class="easyui-combobox"  name="qBiStatus"  
								style="width: 100px;" data-options="panelHeight:'auto'">
								<option value="">--全部--</option>
								<option value="0">录入</option>
								<option value="1">正常</option>
								<option value="9">注销</option>
							</select>	
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryBank();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="bankDg" title="支行网点信息" toolbar="#bankToolBar" pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" width="50" align="center" sortable="true">
							网点编码
						</th>
						<th field="biName" width="80" align="left" sortable="true">
							网点名称
						</th>
						<th field="biShortenName" width="50" align="center" sortable="true">
							网点简称
						</th>
						<th field="biTelephone" width="40" align="center" sortable="true">
							 联系电话
						</th>
						<th field="biAddress" width="100" align="center" sortable="true">
							联系地址
						</th>
						<th field="biLinkman" width="30" align="center" sortable="true">
							联系人
						</th>
						<th field="biMobile" width="50" align="center" sortable="true">
							联系人手机
						</th>
						<th field="biStatus" width="20" align="center" sortable="true" formatter="formatBankStatus">
							状态
						</th>
						<th field="biMemo" width="100" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
			<div id="bankToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#bankDg').datagrid({
			url : 'bank.do?list'
		});
	});
	
	function queryBank() {
		$('#bankDg').datagrid('load',$.miaxisTools.serializeObject($('#bank_query_form')));
	}
	
	function detailBank() {
		var row = $('#bankDg').datagrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'bank.do?detail&id=' + row.id,
				width : 550,
				height : 180,
				title : '查看支行网点',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的支行网点记录!');
		}
	}

	function editBank() {
		var row = $('#bankDg').datagrid('getSelected');
		if (row) {
			var index = $('#bankDg').datagrid('getRowIndex',row);
			if(row.biStatus=='9'){
				$.miaxisTools.alert('支行网点已经注销,不能进行修改!');
				return false;
			}else{
				$.miaxisTools.openPopupWin({
					url   : 'bank.do?insertOrUpdate&operationType=edit&id=' + row.id,
					width : 550,
					height: 180,
					title : '修改支行网点',
					okBtnId : 'btn_sub'
				});
			}
		} else {
			$.miaxisTools.alert('请选择需要修改的支行网点记录!');
		}
	}

	function newBank() {
		$.miaxisTools.openPopupWin({
			url    : 'bank.do?insertOrUpdate&operationType=insert',
			width  : 550,
			height : 180,
			title  : '新增支行网点',
			okBtnId : 'btn_sub'
		});
	}
	
	function removeBank() {
		var row = $('#bankDg').datagrid('getSelected');
			if (row){
				var index = $('#bankDg').datagrid('getRowIndex',row);
				if(row.biStatus!='0'){
					$.miaxisTools.alert('支行网点已经被启用过,不能进行删除!');
					return false;
				}else{
					$.miaxisTools.confirm('确定要删除当前支行网点吗?',function(r){
						if (r){
								$.post('bank.do?del',{id:row.id}, function (data){
									if (data.result=='0'){
					                	$('#bankDg').datagrid('deleteRow',index);
					              	}
					               $.miaxisTools.alert(data.message);
					            },'json');
						}
					});
				}
			}else{
				$.miaxisTools.alert('请选择需要删除的支行网点记录!');
			}
	}
	
	 function updateBankStatus(status){
	    	var row = $('#bankDg').datagrid('getSelected');
			if (row){
				var index = $('#bankDg').datagrid('getRowIndex',row);
				
				if(status=='1'){
					if(row.biStatus=='1'){
						$.miaxisTools.alert('此支行网点已经启用,无须重复启用!');
						return false;
					}
				}
				
				if(status=='9'){
					if(row.biStatus!='1'){
						$.miaxisTools.alert('此支行网点未启用,不能注销!');
						return false;
					}
				}
				
				$.post('bank.do?updateStatus',{id:row.id,status:status}, function (data){
					   row.biStatus=status;
					   $('#bankDg').datagrid('refreshRow', index);
		               $.miaxisTools.alert(data.message);
		         },'json');
			}else{
				var keymsg=status=='1'?'启用':'注销';
				$.miaxisTools.alert('请选择需要的'+keymsg+'记录!');
			}
	    }
	 
	 function formatBankStatus(val, row) {
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
