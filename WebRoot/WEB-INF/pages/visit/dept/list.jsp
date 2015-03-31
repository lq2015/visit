<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height: 50px;" class="query_panel" border="false">
			<form id="dept_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							部门名:
						</td>
						<td>
							<input class="easyui-validatebox" type="text" 
								name="qDiName" id="qDiName" />
						</td>
						<td class="tdlabel">
							状态:
						</td>
						<td>
							<select class="easyui-combobox"  name="qDiStatus"  
								style="width: 100px;" data-options="panelHeight:'auto'">
								<option value="">--全部--</option>
								<option value="0">录入</option>
								<option value="1">正常</option>
								<option value="9">注销</option>
							</select>	
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryDept();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="deptDg" title="部门信息" toolbar="#deptToolBar" pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" width="50" align="center" sortable="true">
							部门编码
						</th>
						<th field="diName" width="80" align="left" sortable="true">
							部门名称
						</th>
						<th field="diLinkman" width="30" align="center" sortable="true">
							联系人
						</th>
						<th field="diTelephone" width="40" align="center" sortable="true">
							 联系电话
						</th>
						<th field="diStatus" width="20" align="center" sortable="true" formatter="formatDeptStatus">
							状态
						</th>
						<th field="diMemo" width="100" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
			<div id="deptToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#deptDg').datagrid({
			url : 'department.do?list'
		});
	});
	
	function queryDept() {
		$('#deptDg').datagrid('load',$.miaxisTools.serializeObject($('#dept_query_form')));
	}
	
	function detailDept() {
		var row = $('#deptDg').datagrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'department.do?detail&id=' + row.id,
				width : 550,
				height : 180,
				title : '查看部门',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的部门记录!');
		}
	}

	function editDept() {
		var row = $('#deptDg').datagrid('getSelected');
		if (row) {
			var index = $('#deptDg').datagrid('getRowIndex',row);
			if(row.diStatus=='9'){
				$.miaxisTools.alert('部门已经注销,不能进行修改!');
				return false;
			}else{
				$.miaxisTools.openPopupWin({
					url   : 'department.do?insertOrUpdate&operationType=edit&id=' + row.id,
					width : 550,
					height: 180,
					title : '修改部门',
					okBtnId : 'btn_sub'
				});
			}
		} else {
			$.miaxisTools.alert('请选择需要修改的部门记录!');
		}
	}

	function newDept() {
		$.miaxisTools.openPopupWin({
			url    : 'department.do?insertOrUpdate&operationType=insert',
			width  : 550,
			height : 180,
			title  : '新增部门',
			okBtnId : 'btn_sub'
		});
			  
	}
	
	function removeDept() {
		var row = $('#deptDg').datagrid('getSelected');
			if (row){
				var index = $('#deptDg').datagrid('getRowIndex',row);
				if(row.diStatus!='0'){
					$.miaxisTools.alert('部门已经被启用过,不能进行删除!');
					return false;
				}else{
					$.miaxisTools.confirm('确定要删除当前部门吗?',function(r){
						if (r){
								$.post('department.do?del',{id:row.id}, function (data){
									if (data.result=='0'){
					                	$('#deptDg').datagrid('deleteRow',index);
					              	}
					               $.miaxisTools.alert(data.message);
					            },'json');
						}
					});
				}
			}else{
				$.miaxisTools.alert('请选择需要删除的部门记录!');
			}
	}
	
	 function updateDeptStatus(status){
	    	var row = $('#deptDg').datagrid('getSelected');
			if (row){
				var index = $('#deptDg').datagrid('getRowIndex',row);
				
				if(status=='1'){
					if(row.diStatus=='1'){
						$.miaxisTools.alert('此部门已经启用,无须重复启用!');
						return false;
					}
				}
				
				if(status=='9'){
					if(row.diStatus!='1'){
						$.miaxisTools.alert('此部门未启用,不能注销!');
						return false;
					}
				}
				
				$.post('department.do?updateStatus',{id:row.id,status:status}, function (data){
					   row.diStatus=status;
					   $('#deptDg').datagrid('refreshRow', index);
		               $.miaxisTools.alert(data.message);
		         },'json');
			}else{
				var keymsg=status=='1'?'启用':'注销';
				$.miaxisTools.alert('请选择需要的'+keymsg+'记录!');
			}
	    }
	 
	 function formatDeptStatus(val, row) {
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
