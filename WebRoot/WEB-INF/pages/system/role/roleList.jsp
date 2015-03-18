<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div data-options="region:'center'"  border="false">
			<table id="roleDg" title="角色信息" toolbar="#roleToolBar" pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="roleName" width="30" align="left" sortable="true">
							角色名
						</th>
						<th field="disable" width="20" align="center" sortable="true" formatter="formatDisable">
							是化固化
						</th>
						<th field="memo" width="100" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
			<div id="roleToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#roleDg').datagrid({
			url : 'role.do?list'
		});
	});
	
	function detailrole() {
		var row = $('#roleDg').datagrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'role.do?detail&id=' + row.id,
				width : 500,
				height : 150,
				title : '查看角色',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的角色记录!');
		}
	}

	function editrole() {
		var row = $('#roleDg').datagrid('getSelected');
		if (row) {
			var index = $('#roleDg').datagrid('getRowIndex',row);
			if(row.disable=='1'){
				$.miaxisTools.alert('固化角色,不能进行修改!');
				return false;
			}else{
				$.miaxisTools.openPopupWin({
					url   : 'role.do?insertOrUpdate&operationType=edit&id=' + row.id,
					width : 500,
					height: 150,
					title : '修改角色',
					okBtnId : 'btn_sub'
				});
			}
		} else {
			$.miaxisTools.alert('请选择需要修改的角色记录!');
		}
	}

	function newrole() {
		$.miaxisTools.openPopupWin({
			url    : 'role.do?insertOrUpdate&operationType=insert',
			width  : 500,
			height : 150,
			title  : '新增角色',
			okBtnId : 'btn_sub'
		});
	}
	
	function removerole() {
		var row = $('#roleDg').datagrid('getSelected');
			if (row){
				var index = $('#roleDg').datagrid('getRowIndex',row);
				if(row.disable=='1'){
					$.miaxisTools.alert('固化角色,不能进行删除!');
					return false;
				}else{
					$.miaxisTools.confirm('确定要删除当前角色吗?',function(r){
						if (r){
								$.post('role.do?del',{id:row.id}, function (data){
									if (data.result=='0'){
					                	$('#roleDg').datagrid('deleteRow',index);
					              	}
					               $.miaxisTools.alert(data.message);
					            },'json');
						}
					});
				}
			}else{
				$.miaxisTools.alert('请选择需要删除的角色记录!');
			}
	}
	
	function roleAuthority(){
		var row = $('#roleDg').datagrid('getSelected');
		if (row) {
			var index = $('#roleDg').datagrid('getRowIndex',row);
			$.miaxisTools.openPopupWin({
				url   : 'role.do?authority&roleId=' + row.id,
				width : 600,
				height: 420,
				title : '【'+row.roleName+'】权限设置'
			});
		} else {
			$.miaxisTools.alert('请选择需要设置权限的角色记录!');
		}
	}
	
	function formatDisable(val,row){  
		if(val=='0'){
	 		return "非固化";
		}else if(val=='1'){
	 		return "固化";
	 	}
     } 
</script>
</body>
