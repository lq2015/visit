<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height:50px;" class="query_panel"  border="false">
			<form id="user_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							姓名:
						</td>
						<td>
							<input class="easyui-validatebox" type="text" 
								name="qrealName" id="qrealName" />
						</td>
						<td class="tdlabel">
							登陆名:
						</td>
						<td>
							<input class="easyui-validatebox" type="text" 
								name="quserName" />
						</td>
						<td class="tdlabel">
							状态:
						</td>
						<td>
							<select class="easyui-combobox"  name="qUserStatus"  
								style="width: 100px;" data-options="panelHeight:'auto'">
								<option value="">--全部--</option>
								<option value="0">录入</option>
								<option value="1">启用</option>
								<option value="2">注销</option>
							</select>
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryUser();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="userDg" title="用户信息" toolbar="#userToolBar" pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="persontype" width="40" align="center" sortable="true" formatter="formatPersonType">
							人员类型
						</th>
						<th field="userName" width="50" align="center" sortable="true">
							系统用户名
						</th>
						<th field="realName" width="60" align="center" sortable="true">
							真实姓名
						</th>
						<th field="email" width="60" align="center" sortable="true">
							邮箱
						</th>
						<th field="officePhone" width="40" align="center" sortable="true">
							办公电话
						</th>
						<th field="mobilePhone" width="40" align="center" sortable="true">
							手机
						</th>
						<th field="fix" width="40" align="center" sortable="true" formatter="formatFix">
							是否固化
						</th>
						<th field="status" width="40" align="center" sortable="true" formatter="formatUserStatus">
							状态
						</th>
					</tr>
				</thead>
			</table>
			<div id="userToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#userDg').datagrid({
			url : 'user.do?list'
		});
	});
	
	function formatPersonType(val,row){  
		if(val=='1'){
	 		return "总部部门";
		}else if(val=='2'){
	 		return "支行网点";
	 	}else if(val=='3'){
	 		return "运维机构";
	 	}
     } 
	
	function formatFix(val,row){  
		if(val=='0'){
	 		return "否";
		}else if(val=='1'){
	 		return "是";
	 	}
     } 
	
	function formatUserStatus(val,row){  
		if(val=='0'){
	 		return "录入";
		}else if(val=='1'){
	 		return "启用";
	 	}else if(val=='2'){
	 		return "注销";
	 	}
     } 
	
	function printUser() {
		$('#mainPanle').jqprint();
	}

	function queryUser() {
		$('#userDg').datagrid('load',
				$.miaxisTools.serializeObject($('#user_query_form')));
	}
	
	function detailUser() {
		var row = $('#userDg').datagrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				id  : 'w_user',
				url : 'user.do?detail&id=' + row.id,
				width : 540,
				height : 255,
				title : '查看用户',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的用户记录!');
		}
	}

	function editUser() {
		var row = $('#userDg').datagrid('getSelected');
		if (row) {
			if(row.fix=='1'){
				$.miaxisTools.alert('固化用户不能修改!');
				return false;
			}
			
			$.miaxisTools.openPopupWin({
				id  : 'w_user',
				url   : 'user.do?insertOrUpdate&operationType=edit&id=' + row.id,
				width : 540,
				height: 255,
				title : '修改用户',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要修改的用户记录!');
		}
	}

	function newUser() {
		$.miaxisTools.openPopupWin({
			id  : 'w_user',
			url    : 'user.do?insertOrUpdate&operationType=insert',
			width  : 540,
			height : 255,
			title  : '新增用户',
			okBtnId : 'btn_sub'
		});
	 }
	
	function removeUser() {
		var row = $('#userDg').datagrid('getSelected');
			if (row){
				if(row.fix=='1'){
					$.miaxisTools.alert('固化用户不能删除!');
					return false;
				}
				
				$.miaxisTools.confirm('确定要删除当前用户吗?',function(r){
					if (r){
						var index = $('#userDg').datagrid('getRowIndex',row);
						if(row.status!='0'){
							$.miaxisTools.alert('此用户已经被启用过,不能进行删除!');
							return false;
						}else{
							$.post('user.do?del',{id:row.id}, function (data){
								if (data.result=='0'){
				                	$('#userDg').datagrid('deleteRow',index);
				              	}
				               $.miaxisTools.alert(data.message);
				            },'json');
						}
					}
				});
			}else{
				$.miaxisTools.alert('请选择需要删除的用户记录!');
			}
	}
	
	 function updateUserStatus(status){
    	var row = $('#userDg').datagrid('getSelected');
		if (row){
			var index = $('#userDg').datagrid('getRowIndex',row);
			
			if(status=='1'){
				if(row.status=='1'){
					$.miaxisTools.alert('此用户已经启用,无须重复提交!');
					return false;
				}
			}
			
			if(status=='2'){
				if(row.fix=='1'){
					$.miaxisTools.alert('固化用户不能注销!');
					return false;
				}
				
				if(row.status!='1'){
					$.miaxisTools.alert('此用户未启用,不能注销!');
					return false;
				}
			}
			
			$.post('user.do?updateStatus',{id:row.id,status:status}, function (data){
				   row.status=status;
				   $('#userDg').datagrid('refreshRow', index);
	               $.miaxisTools.alert(data.message);
	         },'json');
		}else{
			var keymsg=status=='1'?'启用':'注销';
			$.miaxisTools.alert('请选择需要的'+keymsg+'记录!');
		}
    }
	 
	 function authMachine() {
		var row = $('#userDg').datagrid('getSelected');
		if (row) {
			if(row.userName=='admin'){
				$.miaxisTools.alert('admin用户无须授权!');
				return false;
			}
			
			$.miaxisTools.openPopupWin({
				id  : 'w_authMachine',
				url   : 'userAuthMachine.do?main&userId=' + row.id +'&userName=' + row.userName,
				width : 540,
				height: 255,
				lock  : true,
				title : '使用机器授权',
				cancel: false
			});

		} else {
			$.miaxisTools.alert('请选择需要进行授权操作的用户记录!');
		}
	 }
</script>
</body>
