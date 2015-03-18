<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height: 50px;" class="query_panel" border="false">
			<form id="menu_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							菜单层级:
						</td>
						<td>
							<select class="easyui-combobox" name="qMenuLevel" id="qMenuLevel" 
								style="width: 100px;" data-options="panelHeight:'auto'">
								<option value="1">1</option>
								<option value="2">2</option>
							</select>
						</td>
						<td class="tdlabel">
							是否有效:
						</td>
						<td>
						 <select class="easyui-combobox"  name="qIsValid"  id="qIsValid" 
						  data-options="panelHeight:'auto'" style="width: 100px;" >
							<option value="1" >有效</option>
							<option value="0" >无效</option>
						</select>		
							
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryMenu();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'" border="false">
			<table id="menuDg" title="菜单信息" toolbar="#menuToolBar"
				pagination="false" fitColumns="true" singleSelect="true" fit="true"
				striped="true" rownumbers="true" class="easyui-treegrid"
				data-options=" idField:'id',treeField: 'menuName',onClickRow:showFun">
				<thead>
					<tr>
						<th field="id" hidden="true" />

						<th field="menuName" width="30" align="left" sortable="true">
							菜单名
						</th>
						<th field="linkPage" width="60" align="left" sortable="true">
							菜单地址
						</th>
						<th field="menuLevel" width="15" align="center" sortable="true">
							菜单层级
						</th>
						<th field="orderNum" width="10" align="center" sortable="true">
							排序号
						</th>
						<th field="icoIndex" width="10" align="center" sortable="true" formatter="formatIco">
							图标
						</th>
						<th field="isValid" width="15" align="center" sortable="true"
							formatter="formatIsValid">
							是化有效
						</th>
					</tr>
				</thead>
			</table>
			<div id="menuToolBar">
				<miaxis:userMenuFunAuth menuId="${menuId}" />
			</div>
		</div>
		<div data-options="region:'east',split:true,border:false"
			style="width: 480px; overflow: hidden;">
			<table class="easyui-treegrid" title="菜单功能点" id="menuBtnDg"
				toolbar="#menuBtnToolBar" fitColumns="true" singleSelect="true" data-options=" idField:'id',treeField: 'funNameCn'"
				fit="true" striped="true" rownumbers="true" pagination="true" >
				<thead>
					<tr>
						<th field="funNameCn" width="30" align="left" sortable="true">
							按钮名称
						</th>
						<th field="funUrl" width="40" align="left" sortable="true">
							按钮事件
						</th>
						<th field="icoIndex" width="10" align="center" sortable="true" formatter="formatIco">
							图标
						</th>
						<th field="orderNum" width="10" align="center" sortable="true" >
							排序号
						</th>
						<th field="isValid" width="15" align="center" sortable="true"
							formatter="formatIsValid">
							是化有效
						</th>
					</tr>
				</thead>
			</table>
			<div id="menuBtnToolBar">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true" onclick="newFun()">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-edit',plain:true" onclick="editFun()">修改</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-no',plain:true" onclick="removeFun()">删除</a>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#menuDg').treegrid({
			url : 'menu.do?list'
		});
	});

	function queryMenu() {
		$('#menuDg').treegrid('reload',$.miaxisTools.serializeObject($('#menu_query_form')));
	}
	
	function reloadMenu(){
		$('#menuDg').treegrid('reload');
	}
	
	function detailMenu() {
		var row = $('#menuDg').treegrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'menu.do?detail&id=' + row.id,
				width : 550,
				height : 260,
				title : '查看菜单',
				okBtnId : 'btn_sub'
			});

		} else {
			$.miaxisTools.alert('请选择需要查看的菜单记录!');
		}
	}

	function editMenu() {
		var row = $('#menuDg').treegrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'menu.do?insertOrUpdate&operationType=edit&id=' + row.id,
				width : 550,
				height : 260,
				title : '修改菜单',
				okBtnId : 'btn_sub'
			});
		} else {
			$.miaxisTools.alert('请选择需要修改的菜单记录!');
		}
	}

	function newMenu() {
		$.miaxisTools.openPopupWin({
			url : 'menu.do?insertOrUpdate&operationType=insert',
			width : 550,
			height : 260,
			title : '新增菜单',
			okBtnId : 'btn_sub'
		});
	}

	function removeMenu() {
		var row = $('#menuDg').treegrid('getSelected');
		if (row) {
			$.miaxisTools.confirm('确定要删除当前菜单吗?', function(r) {
				if (r) {
					$.post('menu.do?del', {
						id : row.id
					}, function(data) {
						if (data.result == '0') {
							$('#menuDg').treegrid('remove', row.id);
						}
						$.miaxisTools.alert(data.message);
					},'json');
				}
			});
		} else {
			$.miaxisTools.alert('请选择需要删除的菜单记录!');
		}
	}

	function showFun(){
		 var row = $('#menuDg').treegrid('getSelected');  
         if (row){
        	 var _parentMenu = row.id;
        	 $('#menuBtnDg').treegrid({
     			url : 'function.do?list&parentMenu='+_parentMenu
     		});
        	 var _title = "<span style='color:blue'>【"+row.menuName+"】</span>菜单功能点";
        	 $('#menuBtnDg').treegrid({title:_title});
         }
	}
	
	function editFun() {
		var row = $('#menuBtnDg').treegrid('getSelected');
		if (row) {
			$.miaxisTools.openPopupWin({
				url : 'function.do?insertOrUpdate&operationType=edit&id=' + row.id,
				width : 580,
				height : 280,
				title : '修改菜单功能点',
				okBtnId : 'btn_sub'
			});
		} else {
			$.miaxisTools.alert('请选择需要修改的菜单功能点记录!');
		}
	}

	function newFun() {
		$.miaxisTools.openPopupWin({
			url : 'function.do?insertOrUpdate&operationType=insert',
			width : 580,
			height : 280,
			title : '新增菜单功能点',
			okBtnId : 'btn_sub'
		});
	}

	function removeFun() {
		var row = $('#menuBtnDg').treegrid('getSelected');
		if (row) {
			$.miaxisTools.confirm('确定要删除当前菜单功能点吗?', function(r) {
				if (r) {
					$.post('function.do?del', {
						id : row.id
					}, function(data) {
						if (data.result == '0') {
							$('#menuBtnDg').treegrid('remove', row.id);
						}
						$.miaxisTools.alert(data.message);
					},'json');
				}
			});
		} else {
			$.miaxisTools.alert('请选择需要删除的菜单功能点记录!');
		}
	}
	
	function reloadFun(){
		$('#menuBtnDg').treegrid('reload');
	}
	
	function formatIsValid(val, row) {
		if (val == '0') {
			return "无效";
		} else if (val == '1') {
			return "有效";
		}
	}
	
	function formatIco(val,row) {
		return '<span class="'+val+'" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>';
	}
	
</script>
</body>
