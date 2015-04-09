<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height: 50px;" class="query_panel" border="false">
			<form id="jobApply_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							网点:
						</td>
						<td>
							<input class="easyui-combobox" id="qJdJobBank" name="qJaJobBank" 
							 data-options="valueField:'id',textField:'name',panelHeight:'auto',panelWidth:200,url:'public.do?userDept&persontype=2',editable:true" url="public.do?userDept&persontype=2"
						     >
						</td>
						<td class="tdlabel">
							状态:
						</td>
						<td>
							<select class="easyui-combobox"  name="qJaStatus"  
								style="width: 100px;" data-options="panelHeight:'auto',editable:false">
								<option value="">--全部--</option>
								<option value="1">申请</option>
								<option value="2">派工</option>
								<option value="3">驳回</option>
								<option value="9">取消</option>
							</select>	
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryApply();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="jobApplyDg" title="申请信息" toolbar="#jobApplyToolBar" pagination="true" 
				fitColumns="false" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="bankInfo" width="150" align="center" sortable="true" formatter="formatBankInfo">
							网点
						</th>
						<th field="jaServeItem" width="150" align="center" sortable="true">
							服务项目
						</th>
						<th field="jaJobContent" width="250" align="center" sortable="true">
							工作内容简述
						</th>
						<th field="jaJobDate" width="100" align="center" sortable="true">
							服务时间
						</th>
						<th field="applyUser" width="80" align="center" sortable="true"  formatter="formatapplyUser">
							申请人
						</th>
						<th field="jaApplyTime" width="100" align="center" sortable="true">
							申请日期
						</th>
						<th field="approveUser" width="80" align="center" sortable="true"  formatter="formatapproveUser">
							审批人
						</th>
						<th field="jaApproveTime" width="100" align="center" sortable="true">
							审批日期
						</th>
						<th field="jaStatus" width="60" align="center" sortable="true" formatter="formatJobApplyStatus">
							状态
						</th>
						<th field="jaMemo" width="200" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
			<div id="jobApplyToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#jobApplyDg').datagrid({
			url : 'jobApply.do?list'
		});
	});
	
	function queryApply() {
		$('#jobApplyDg').datagrid('load',$.miaxisTools.serializeObject($('#jobApply_query_form')));
	}
	
	function apply(){
		$.miaxisTools.openPopupWin({
			id     : 'w_jobApply',
			url    : 'jobApply.do?apply',
			width  : 500,
			height : 240,
			title  : '服务申请',
			okBtnId : 'btn_sub'
		});
	}
	
	function applyDispatch(){
		var row = $('#jobApplyDg').datagrid('getSelected');
		if (row){
			var index = $('#jobApplyDg').datagrid('getRowIndex',row);
			if (row.jaStatus=='0') {
				$.miaxisTools.alert('此申请单还没有提交,不能审核!');
				return false;
			}
			if (row.jaStatus=='9') {
				$.miaxisTools.alert('此申请单已经取消,不能审核!');
				return false;
			}
			if (',2,3,'.indexOf(row.jaStatus)>0) {
				$.miaxisTools.alert('此申请单还已经审核过,不能重复审核!');
				return false;
			}
			
			$.miaxisTools.openPopupWin({
				id     : 'w_job',
				url    : 'jobApply.do?applyDispatch&id='+row.id,
				width  : 590,
				height : 340,
				title  : '服务申请派工',
				okBtnId : 'btn_sub'
			});
		}else{
			$.miaxisTools.alert('请选择需要审核操作的记录!');
		}
	}
	
	function reject(){
    	var row = $('#jobApplyDg').datagrid('getSelected');
		if (row){
			var index = $('#jobApplyDg').datagrid('getRowIndex',row);
			alert(0)
			return ;
			if (row.jaStatus=='0') {
				$.miaxisTools.alert('此申请单还没有提交,不能审核!');
				return false;
			}
			if (row.jaStatus=='2') {
				$.miaxisTools.alert('此申请单已经派过工,不能驳回!');
				return false;
			}
			if(row.jaStatus=='3'){
				$.miaxisTools.alert('此申请单已经驳回,无须重复操作!');
				return false;
			}
			if(row.jaStatus=='9'){
				$.miaxisTools.alert('此申请单已经取消,不能驳回!');
				return false;
			}
			$.post('jobApply.do?updateStatus',{id:row.id,status:3}, function (data){
				if(data.result==0){
					row.jaStatus=status;
					$('#jobApplyDg').datagrid('refreshRow', index);
				}
	            $.miaxisTools.alert(data.message);
	         },'json');
		}else{
			$.miaxisTools.alert('请选择需要操作的记录!');
		}
	}
	
	function updateApplyStatus(status){
    	var row = $('#jobApplyDg').datagrid('getSelected');
		if (row){
			var index = $('#jobApplyDg').datagrid('getRowIndex',row);
			if(status=='9'){
				if(row.jaStatus=='3'){
					$.miaxisTools.alert('此申请单已经驳回,不能取消!');
					return false;
				}
				if(row.jaStatus=='9'){
					$.miaxisTools.alert('此申请单已经取消,无须重复提交!');
					return false;
				}
			}
			$.post('jobApply.do?updateStatus',{id:row.id,status:status}, function (data){
				if(data.result==0){
					row.jaStatus=status;
					$('#jobApplyDg').datagrid('refreshRow', index);
				}
	            $.miaxisTools.alert(data.message);
	         },'json');
		}else{
			$.miaxisTools.alert('请选择需要操作的记录!');
		}
	}
	
	function formatJobApplyStatus(val, row) {
		if (val == '0') {
			return "录入";
		} else if (val == '1') {
			return "申请";
		} else if (val == '2') {
			return "派工";
		} else if (val == '3') {
			return "驳回";
		} else if (val == '9') {
			return "取消";
		}
	}
	
	function formatBankInfo(val,row){
		if(val){
			return val.biName;
		}else{
			return '';
		}
	}
	
	function formatapplyUser(val,row){
		if(val){
			return val.realName;
		}else{
			return '';
		}
	}
	
	function formatapproveUser(val,row){
		if(val){
			return val.realName;
		}else{
			return '';
		}
	}
	 
</script>
</body>
