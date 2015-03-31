<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height: 50px;" class="query_panel" border="false">
			<form id="unit_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							单位名称:
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
								onclick="queryunit();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="unitDg" title="单位信息" toolbar="#unitToolBar" pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="uiName" width="80" align="left" sortable="true">
							单位名称
						</th>
						<th field="uiTelephone" width="40" align="center" sortable="true">
							 联系电话
						</th>
						<th field="uiAddress" width="100" align="center" sortable="true">
							联系地址
						</th>
						<th field="uiLinkman" width="30" align="center" sortable="true">
							联系人
						</th>
						<th field="uiMobile" width="50" align="center" sortable="true">
							联系人手机
						</th>
						<th field="uiCrdate" width="50" align="center" sortable="true">
							建档日期
						</th>
						<th field="uiStatus" width="20" align="center" sortable="true" formatter="formatUnitStatus">
							状态
						</th>
						<th field="uiMemo" width="50" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
			<div id="unitToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#unitDg').datagrid({
			url : 'unit.do?list'
		});
	});
	
	function queryUnit() {
		$('#unitDg').datagrid('load',$.miaxisTools.serializeObject($('#unit_query_form')));
	}
	
	function detailUnit() {
		var row = $('#unitDg').datagrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'unit.do?detail&id=' + row.id,
				width : 550,
				height : 180,
				title : '查看单位',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的单位记录!');
		}
	}

	function editUnit() {
		var row = $('#unitDg').datagrid('getSelected');
		if (row) {
			var index = $('#unitDg').datagrid('getRowIndex',row);
			if(row.uiStatus=='9'){
				$.miaxisTools.alert('单位已经注销,不能进行修改!');
				return false;
			}else{
				$.miaxisTools.openPopupWin({
					url   : 'unit.do?insertOrUpdate&operationType=edit&id=' + row.id,
					width : 550,
					height: 180,
					title : '修改单位',
					okBtnId : 'btn_sub'
				});
			}
		} else {
			$.miaxisTools.alert('请选择需要修改的单位记录!');
		}
	}

	function newUnit() {
		$.miaxisTools.openPopupWin({
			url    : 'unit.do?insertOrUpdate&operationType=insert',
			width  : 550,
			height : 180,
			title  : '新增单位',
			okBtnId : 'btn_sub'
		});
	}
	
	function removeUnit() {
		var row = $('#unitDg').datagrid('getSelected');
			if (row){
				var index = $('#unitDg').datagrid('getRowIndex',row);
				$.miaxisTools.confirm('确定要删除当前单位吗?',function(r){
					if (r){
							$.post('unit.do?del',{id:row.id}, function (data){
								if (data.result=='0'){
				                	$('#unitDg').datagrid('deleteRow',index);
				              	}
				               $.miaxisTools.alert(data.message);
				            },'json');
					}
				});
			}else{
				$.miaxisTools.alert('请选择需要删除的单位记录!');
			}
	}
	
	 function updateUnitStatus(status){
	    	var row = $('#unitDg').datagrid('getSelected');
			if (row){
				var index = $('#unitDg').datagrid('getRowIndex',row);
				
				if(status=='1'){
					if(row.uiStatus=='1'){
						$.miaxisTools.alert('此单位已经启用,无须重复启用!');
						return false;
					}
				}
				
				if(status=='9'){
					if(row.uiStatus!='1'){
						$.miaxisTools.alert('此单位未启用,不能注销!');
						return false;
					}
				}
				
				$.post('unit.do?updateStatus',{id:row.id,status:status}, function (data){
					   row.uiStatus=status;
					   $('#unitDg').datagrid('refreshRow', index);
		               $.miaxisTools.alert(data.message);
		         },'json');
			}else{
				var keymsg=status=='1'?'启用':'注销';
				$.miaxisTools.alert('请选择需要的'+keymsg+'记录!');
			}
	    }
	 
	 function formatUnitStatus(val, row) {
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
