<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height:50px;" class="query_panel"  border="false">
			<form id="AuthApprove_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							登陆名:
						</td>
						<td>
							<input class="easyui-validatebox" type="text" 
								name="qloginname" />
						</td>
						<td class="tdlabel">
							状态:
						</td>
						<td>
							<select class="easyui-combobox"  name="qStatus"  
								style="width: 100px;" data-options="panelHeight:'auto'">
								<option value="">--全部--</option>
								<option value="0">待审核</option>
								<option value="1">审核通过</option>
								<option value="2">审核不通过</option>
							</select>
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryAuthApprove();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="authMachineapproveDg" title="用户信息" toolbar="#authmrToolBar" pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="loginname" width="70" align="left" sortable="true">
							登陆用户名
						</th>
						<th field="machinecode" width="70" align="left" sortable="true">
							机器特征码
						</th>
						<th field="machinename" width="20" align="center" sortable="true">
							机器名称
						</th>
						<th field="authenddate" width="25" align="center" sortable="true">
							授权结束日期
						</th>
						<th field="status" width="30" align="left" sortable="true" formatter="formatStatus">
							状态
						</th>
						<th field="memo" width="30" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
			<div id="authmrToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#authMachineapproveDg').datagrid({
			url : 'userAuthMachine.do?approvelist'
		});
	});
	
	function queryAuthApprove() {
		$('#authMachineapproveDg').datagrid('load',
				$.miaxisTools.serializeObject($('#AuthApprove_query_form')));
	}

	function formatStatus(val,row){  
		if(val=='0'){
	 		return "待审核";
		}else if(val=='1'){
	 		return "审批通过";
	 	}else if(val=='2'){
	 		return "审批不通过";
	 	}
     } 
	
	 function approveSubmit(status){
    	var row = $('#authMachineapproveDg').datagrid('getSelected');
		if (row){
			var index = $('#authMachineapproveDg').datagrid('getRowIndex',row);
			
			if(status=='1'){
				if(row.status=='1'){
					$.miaxisTools.alert('此记录已经审批过,无须重复审核!');
					return false;
				}
			}
			
			if(status=='2'){
				$.miaxisTools.alert('此记录已经审批过,无须重复审核!');
				return false;
			}
			
			$.post('userAuthMachine.do?approveSubmit',{id:row.id,result:status}, function (data){
				if(data.result=='0'){
					row.status=status;
					$('#authMachineapproveDg').datagrid('refreshRow', index);
				}
				  
	            $.miaxisTools.alert(data.message);
	         },'json');
		}else{
			$.miaxisTools.alert('请选择需要的审核的记录!');
		}
    }
	
</script>
</body>
