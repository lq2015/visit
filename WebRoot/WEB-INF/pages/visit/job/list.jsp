<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height: 50px;" class="query_panel" border="false">
			<form id="job_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							服务单位:
						</td>
						<td>
							<input class="easyui-combobox" id="qPiWorkUnit" name="qPiWorkUnit" 
							 data-options="valueField:'id',textField:'uiName',panelHeight:'auto',panelWidth:200,url:'person.do?getWorkUnit',editable:true"
						     >
						</td>
						<td class="tdlabel">
							网点:
						</td>
						<td>
							<input class="easyui-combobox" id="qPiWorkUnit" name="qPiWorkUnit" 
							 data-options="valueField:'id',textField:'name',panelHeight:'auto',panelWidth:200,url:'public.do?userDept&persontype=2',editable:true" url="public.do?userDept&persontype=2"
						     >
						</td>
						<td class="tdlabel">
							状态:
						</td>
						<td>
							<select class="easyui-combobox"  name="qJdStatus"  
								style="width: 100px;" data-options="panelHeight:'auto',editable:false">
								<option value="">--全部--</option>
								<option value="0">录入</option>
								<option value="1">派工</option>
								<option value="2">签到</option>
								<option value="3">签离</option>
								<option value="9">注销</option>
							</select>	
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryjob();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="jobDg" title="派工信息" toolbar="#jobToolBar" pagination="true" 
				fitColumns="false" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="bankInfo" width="100" align="center" sortable="true" formatter="formatBankInfo">
							网点
						</th>
						<th field="unitInfo" width="160" align="center" sortable="true" formatter="formatUnitInfo">
							服务单位
						</th>
						<th field="jdPersonNames" width="100" align="center" sortable="true">
							服务人员
						</th>
						<th field="jdServeItem" width="150" align="center" sortable="true">
							服务项目
						</th>
						<th field="jdJobContent" width="200" align="center" sortable="true">
							工作内容简述
						</th>
						<th field="jdServeStart" width="80" align="center" sortable="true">
							有效开始日期
						</th>
						<th field="jdServeEnd" width="80" align="center" sortable="true">
							有效结束日期
						</th>
						<th field="jdStatus" width="40" align="center" sortable="true" formatter="formatJobStatus">
							状态
						</th>
						<th field="user" width="80" align="center" sortable="true"  formatter="formatUser">
							操作人
						</th>
						<th field="jdOperateTime" width="80" align="center" sortable="true">
							操作日期
						</th>
						<th field="jdSignTime" width="80" align="center" sortable="true">
							签到时间
						</th>
						<th field="jdOutTime" width="80" align="center" sortable="true">
							签离时间
						</th>
						<th field="jdMemo" width="100" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
			<div id="jobToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#jobDg').datagrid({
			url : 'jobDispatch.do?list'
		});
	});
	
	function queryJob() {
		$('#jobDg').datagrid('load',$.miaxisTools.serializeObject($('#job_query_form')));
	}
	
	function detailJob() {
		var row = $('#jobDg').datagrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'jobDispatch.do?detail&id=' + row.id,
				id  : 'w_job',
				width : 590,
				height : 340,
				title : '查看派工单',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的派工单记录!');
		}
	}

	function editJob() {
		var row = $('#jobDg').datagrid('getSelected');
		if (row) {
			if(row.jdStatus=='9'){
				$.miaxisTools.alert('此派工单已经取消,不能修改!');
				return false;
			}
			if (',1,2,3,'.indexOf(row.jdStatus)>0) {
				$.miaxisTools.alert('此派工单已经被派过工,不能修改!');
				return false;
			}
			
			$.miaxisTools.openPopupWin({
				id     : 'w_job',
				url   : 'jobDispatch.do?insertOrUpdate&operationType=edit&id=' + row.id,
				width : 590,
				height: 340,
				title : '修改派工单',
				okBtnId : 'btn_sub'
			});
		} else {
			$.miaxisTools.alert('请选择需要修改的派工单记录!');
		}
	}

	function newJob() {
		$.miaxisTools.openPopupWin({
			id     : 'w_job',
			url    : 'jobDispatch.do?insertOrUpdate&operationType=insert',
			width  : 590,
			height : 340,
			title  : '新增派工单',
			okBtnId : 'btn_sub'
		});
	}
	
	function removeJob() {
		var row = $('#jobDg').datagrid('getSelected');
			if (row){
				var index = $('#jobDg').datagrid('getRowIndex',row);
				$.miaxisTools.confirm('确定要删除当前派工单吗?',function(r){
					if (r){
							$.post('jobDispatch.do?del',{id:row.id}, function (data){
								if (data.result=='0'){
				                	$('#jobDg').datagrid('deleteRow',index);
				              	}
				               $.miaxisTools.alert(data.message);
				            },'json');
					}
				});
			}else{
				$.miaxisTools.alert('请选择需要删除的派工单记录!');
			}
	}
	
	
	function updateJobStatus(status){
    	var row = $('#jobDg').datagrid('getSelected');
		if (row){
			var index = $('#jobDg').datagrid('getRowIndex',row);
			if(status=='9'){
				if(row.jdStatus=='2'){
					$.miaxisTools.alert('此派工单已经签到,不能取消!');
					return false;
				}
				if(row.jdStatus=='3'){
					$.miaxisTools.alert('此派工单已经签离,不能取消!');
					return false;
				}
				if(row.jdStatus=='9'){
					$.miaxisTools.alert('此派工单已经取消,无须重复提交!');
					return false;
				}
			}
			$.post('jobDispatch.do?updateStatus',{id:row.id,status:status}, function (data){
				if(data.result==0){
					row.jdStatus=status;
					$('#jobDg').datagrid('refreshRow', index);
				}
	            $.miaxisTools.alert(data.message);
	         },'json');
		}else{
			$.miaxisTools.alert('请选择需要操作的记录!');
		}
	}
	
	function dispatchJob(){
		var row = $('#jobDg').datagrid('getSelected');
		if (row){
			var index = $('#jobDg').datagrid('getRowIndex',row);
			if(row.jdStatus=='9'){
				$.miaxisTools.alert('此派工单已经取消,不能派工!');
				return false;
			}
			if (',1,2,3,'.indexOf(row.jdStatus)>0) {
				$.miaxisTools.alert('此派工单已经被派过工,不能重复派工!');
				return false;
			}
			
			$.post('jobDispatch.do?dispatchJob',{id:row.id}, function (data){
				if(data.result==0){
					row.jdStatus='1';
					$('#jobDg').datagrid('refreshRow', index);
				}
	            $.miaxisTools.alert(data.message);
	         },'json');
		}else{
			$.miaxisTools.alert('请选择需要操作的记录!');
		}
	}
	
	function sign(){
		var row = $('#jobDg').datagrid('getSelected');
		if (row){
			var index = $('#jobDg').datagrid('getRowIndex',row);
			if (row.jdStatus=='0') {
				$.miaxisTools.alert('此派工单还没有派过工,不能签到!');
				return false;
			}
			if (row.jdStatus=='9') {
				$.miaxisTools.alert('此派工单已经取消,不能签到!');
				return false;
			}
			if (',2,3,'.indexOf(row.jdStatus)>0) {
				$.miaxisTools.alert('此派工单还已经签到过,不能重复签到!');
				return false;
			}
			
			$.miaxisTools.openPopupWin({
				id     : 'w_out',
				url    : 'jobDispatch.do?sign&id='+row.id,
				width  : 590,
				height : 340,
				title  : '签到',
				okBtnId : 'btn_sub'
			});
		}else{
			$.miaxisTools.alert('请选择需要签到操作的记录!');
		}
	}
	
	function out(){
		var row = $('#jobDg').datagrid('getSelected');
		if (row){
			var index = $('#jobDg').datagrid('getRowIndex',row);
			if (row.jdStatus=='3') {
				$.miaxisTools.alert('此派工单已经签离,不能重复操作!');
				return false;
			}
			if (row.jdStatus=='9') {
				$.miaxisTools.alert('此派工单已经取消,不能签离!');
				return false;
			}
			if (',0,1,'.indexOf(row.jdStatus)>0) {
				$.miaxisTools.alert('此派工单还还没签到过,不能签离!');
				return false;
			}
			
			$.post('jobDispatch.do?out',{id:row.id}, function (data){
				if(data.result==0){
					row.jdStatus='3';
					$('#jobDg').datagrid('refreshRow', index);
				}
	            $.miaxisTools.alert(data.message);
	         },'json');
		}else{
			$.miaxisTools.alert('请选择需要签离操作的记录!');
		}
	}
	
	function grade(){
		var row = $('#jobDg').datagrid('getSelected');
		if (row){
			var index = $('#jobDg').datagrid('getRowIndex',row);
			if (row.jdStatus=='9') {
				$.miaxisTools.alert('此派工单已经取消,不能评价!');
				return false;
			}
			if (',0,1,2,'.indexOf(row.jdStatus)>0) {
				$.miaxisTools.alert('此派工单还还没签离过,不能评价!');
				return false;
			}
			
			$.miaxisTools.openPopupWin({
				id     : 'w_grade',
				url    : 'jobDispatch.do?grade&id='+row.id,
				width  : 590,
				height : 340,
				title  : '服务评价',
				okBtnId : 'btn_sub'
			});
		}else{
			$.miaxisTools.alert('请选择需要评价操作的记录!');
		}
	}
	
	function upload(){
		var row = $('#jobDg').datagrid('getSelected');
		if (row){
			var index = $('#jobDg').datagrid('getRowIndex',row);
			if (row.jdStatus=='9') {
				$.miaxisTools.alert('此派工单已经取消,不能上传维修单!');
				return false;
			}
			if (',0,1,2,'.indexOf(row.jdStatus)>0) {
				$.miaxisTools.alert('此派工单还还没签离过,不能上传维修单!');
				return false;
			}
			
			$.miaxisTools.openPopupWin({
				id     : 'w_upload',
				url    : 'jobDispatch.do?upload&id='+row.id,
				width  : 590,
				height : 340,
				title  : '上传维修单',
				okBtnId : 'btn_sub'
			});
		}else{
			$.miaxisTools.alert('请选择需要上传维修单的记录!');
		}
	}
	
	function formatJobStatus(val, row) {
		if (val == '0') {
			return "录入";
		} else if (val == '1') {
			return "派工";
		} else if (val == '2') {
			return "签到";
		} else if (val == '3') {
			return "签离";
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
		 
	function formatUnitInfo(val,row){
		if(val){
			return val.uiName;
		}else{
			return '';
		}
	}
	
	function formatUser(val,row){
		if(val){
			return val.realName;
		}else{
			return '';
		}
	}
	 
</script>
</body>
