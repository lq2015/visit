<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height: 50px;" class="query_panel" border="false">
			<form id="category_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							父类:
						</td>
						<td>
							<input class="easyui-combobox" id="qScParent" name="qScParent" url="serveCategory.do?getParent"
							 data-options="valueField:'id',textField:'scCategory',panelHeight:'auto',editable:true"
						</td>
						<td class="tdlabel">
							类别层级:
						</td>
						<td>
							<select class="easyui-combobox"  name="qScLevel"  id="qScLevel" 
							 data-options="panelHeight:'auto'"  style="width:120px;">
							 	<option value="">--全部--</option>
								<option value="1">1</option>
								<option value="2">2</option>
							</select>
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryCategory();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="categoryDg" title="类别信息" toolbar="#categoryToolBar" pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="scCategory" width="60" align="left" sortable="true">
							类别名称
						</th>
						<th field="scLevel" width="30" align="center" sortable="true">
							类别层级
						</th>
						<th field="scMemo" width="100" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
			<div id="categoryToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#categoryDg').datagrid({
			url : 'serveCategory.do?list'
		});
	});
	
	function queryCategory() {
		$('#categoryDg').datagrid('load',$.miaxisTools.serializeObject($('#category_query_form')));
	}
	
	function detailCategory() {
		var row = $('#categoryDg').datagrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'serveCategory.do?detail&id=' + row.id,
				width : 500,
				height : 200,
				title : '查看类别',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的类别记录!');
		}
	}

	function editCategory() {
		var row = $('#categoryDg').datagrid('getSelected');
		if (row) {
			var index = $('#categoryDg').datagrid('getRowIndex',row);
			$.miaxisTools.openPopupWin({
				url   : 'serveCategory.do?insertOrUpdate&operationType=edit&id=' + row.id,
				width : 500,
				height: 200,
				title : '修改类别',
				okBtnId : 'btn_sub'
			});
		} else {
			$.miaxisTools.alert('请选择需要修改的类别记录!');
		}
	}

	function newCategory() {
		$.miaxisTools.openPopupWin({
			url    : 'serveCategory.do?insertOrUpdate&operationType=insert',
			width  : 500,
			height : 200,
			title  : '新增类别',
			okBtnId : 'btn_sub'
		});
			  
	}
	
	function removeCategory() {
		var row = $('#categoryDg').datagrid('getSelected');
			if (row){
				var index = $('#categoryDg').datagrid('getRowIndex',row);
				$.miaxisTools.confirm('确定要删除当前类别吗?',function(r){
					if (r){
							$.post('serveCategory.do?del',{id:row.id}, function (data){
								if (data.result=='0'){
				                	$('#categoryDg').datagrid('deleteRow',index);
				              	}
				               $.miaxisTools.alert(data.message);
				            },'json');
					}
				});
			}else{
				$.miaxisTools.alert('请选择需要删除的类别记录!');
			}
	}
	 
</script>
</body>
