<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height: 50px;" class="query_panel" border="false">
			<form id="person_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							所属单位:
						</td>
						<td>
							<input class="easyui-combobox" id="qPiWorkUnit" name="qPiWorkUnit"  url="person.do?getWorkUnit"
							 data-options="valueField:'id',textField:'uiName',panelHeight:'auto',panelWidth:200,editable:true"
						     >
						</td>
						<td class="tdlabel">
							人员姓名:
						</td>
						<td>
							<input class="easyui-validatebox" type="text" 
								name="qPiName" id="qPiName" />
						</td>
						<td class="tdlabel">
							状态:
						</td>
						<td>
							<select class="easyui-combobox"  name="qPiStatus"  
								style="width: 100px;" data-options="panelHeight:'auto',editable:false">
								<option value="">--全部--</option>
								<option value="0">正常</option>
								<option value="9">注销</option>
							</select>	
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryperson();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="personDg" title="人员信息" toolbar="#personToolBar" pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="piNumber" width="30" align="center" sortable="true">
							工号
						</th>
						<th field="piName" width="30" align="center" sortable="true">
							人员姓名
						</th>
						<th field="piIdnum" width="50" align="center" sortable="true">
							身份证号
						</th>
						<th field="piSex" width="30" align="center" sortable="true">
							性别
						</th>
						<th field="piTelephone" width="30" align="center" sortable="true">
							联系电话
						</th>
						<th field="piMobile" width="30" align="center" sortable="true">
							联系手机
						</th>
						<th field="piPost" width="30" align="center" sortable="true">
							职务
						</th>
						<th field="unitInfo" width="80" align="center" sortable="true" formatter="formatUnitInfo">
							工作单位
						</th>
						<th field="piCrdate" width="30" align="center" sortable="true">
							建档日期
						</th>
						<th field="piStatus" width="30" align="center" sortable="true" formatter="formatPersonStatus">
							状态
						</th>
					</tr>
				</thead>
			</table>
			<div id="personToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#personDg').datagrid({
			url : 'person.do?list'
		});
	});
	
	function queryperson() {
		$('#personDg').datagrid('load',$.miaxisTools.serializeObject($('#person_query_form')));
	}
	
	function detailPerson() {
		var row = $('#personDg').datagrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'person.do?detail&id=' + row.id,
				width : 700,
				height : 420,
				title : '查看人员',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的人员记录!');
		}
	}

	function editPerson() {
		var row = $('#personDg').datagrid('getSelected');
		if (row) {
			var index = $('#personDg').datagrid('getRowIndex',row);
			$.miaxisTools.openPopupWin({
				url   : 'person.do?insertOrUpdate&operationType=edit&id=' + row.id,
				width : 700,
				height: 420,
				title : '修改人员',
				okBtnId : 'btn_sub'
			});
		} else {
			$.miaxisTools.alert('请选择需要修改的人员记录!');
		}
	}

	function newPerson() {
		$.miaxisTools.openPopupWin({
			url    : 'person.do?insertOrUpdate&operationType=insert',
			width  : 700,
			height : 420,
			title  : '新增人员',
			okBtnId : 'btn_sub'
		});
			  
	}
	
	function removePerson() {
		var row = $('#personDg').datagrid('getSelected');
			if (row){
				var index = $('#personDg').datagrid('getRowIndex',row);
				$.miaxisTools.confirm('确定要删除当前人员吗?',function(r){
					if (r){
							$.post('person.do?del',{id:row.id}, function (data){
								if (data.result=='0'){
				                	$('#personDg').datagrid('deleteRow',index);
				              	}
				               $.miaxisTools.alert(data.message);
				            },'json');
					}
				});
			}else{
				$.miaxisTools.alert('请选择需要删除的人员记录!');
			}
	}
	
	
	function updatePersonStatus(status){
    	var row = $('#personDg').datagrid('getSelected');
		if (row){
			var index = $('#personDg').datagrid('getRowIndex',row);
			if(status=='9'){
				if(row.piStatus=='9'){
					$.miaxisTools.alert('此人员已经注销,无须重复提交!');
					return false;
				}
			}
			$.post('person.do?updateStatus',{id:row.id,status:status}, function (data){
				if(data.result==0){
					row.piStatus=status;
					$('#personDg').datagrid('refreshRow', index);
				}
	            $.miaxisTools.alert(data.message);
	         },'json');
		}else{
			$.miaxisTools.alert('请选择需要注销的记录!');
		}
	}
	
	 function formatPersonStatus(val, row) {
		if (val == '1') {
			return "正常";
		} else if (val == '9') {
			return "注销";
		}
	}
	 
	function formatUnitInfo(val,row){
		if(val){
			return val.uiName;
		}else{
			return '';
		}
	}
	 
</script>
</body>
