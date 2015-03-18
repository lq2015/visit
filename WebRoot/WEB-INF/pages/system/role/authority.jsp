<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div data-options="region:'center'" border="false" style=" overflow: hidden;">
			<div class="easyui-panel" title="系统菜单" fit="true"
				data-options="iconCls:'icon-node'">
				<div data-options="region:'east'" style="width: 100%;">
					<div id="mbar" class="datagrid-toolbar">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-check" plain="true" onclick="selectAll()">全选</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-unchecked" plain="true" onclick="cancelAll()">全不选</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-save" plain="true" onclick="saveMenu()">保存菜单</a>
					</div>
				</div>
				<div data-options="region:'center'" style="padding: 10px;">
					<ul id="menuTree" class="easyui-tree"
					data-options="url:'system.do?menuComBoTree',method:'get',animate:true,checkbox:true,lines:true"></ul>
				</div>
			</div>
		</div>
		<div data-options="region:'east'" border="true" style="width:320px;overflow: hidden;" >
			<table class="easyui-treegrid" title="菜单功能点" id="menuFunDg" striped="true" rownumbers="true"  toolbar="#toolbar"
				fitColumns="false" singleSelect="false" pagination="false" fit="true" data-options=" idField:'id',treeField: 'funNameCn'">
				<thead>
					<tr>
						<th field="ck" checkbox="true" />
						<th field="funNameCn" width="120" align="center">
							按钮名称
						</th>
						<th field="icoIndex" width="60" align="center" formatter="formatIco">
							图标
						</th>
					</tr>
				</thead>
			</table>
			<div id="toolbar" class="datagrid-toolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-save" plain="true" onclick="saveFun()">保存功能点</a>
			</div>
		</div>
		<input id="roleId"  type="hidden" value="${roleId}" />
		<input id="menuId" type="hidden" value="" />
		<input id="authIds" type="hidden" value="${authIds}" />
	</div>

	<script type="text/javascript">
	$().ready(function() {
		//设置树选中 
		setTimeout(function(){
			var authIds = $("#authIds").val();
			if(authIds){
				var _authIdsArray = authIds.split(",");
				for(var i=0;i<_authIdsArray.length;i++){
					var node = $('#menuTree').tree('find', _authIdsArray[i]);
					if(node){
						var isLeaf = $('#menuTree').tree('isLeaf',node.target);
						if(isLeaf){
							$('#menuTree').tree('check', node.target);
						}
					}
				}
			}
		},500)
	});
	
	$('#menuTree').tree({
		onSelect: function(node){
			var _parentMenu = node.id;
	       	$('#menuFunDg').treegrid({
    			url : 'function.do?list&parentMenu='+_parentMenu
    		});
	       	var _title = "<span style='color:blue'>【"+node.text+"】</span>菜单功能点";
	       	$('#menuFunDg').treegrid({title:_title});
	        $("#menuId").val(_parentMenu);
	      	//设置选中
	        setTimeout(selectFungrid(),2000);
		}
	});
	
	function selectFungrid(){
		var roleId =$("#roleId").val();
		var menuId =$("#menuId").val();
		$.ajax({
			type : 'POST',
			dataType : 'json',
			data: {roleId:roleId,menuId:menuId},
			url : 'role.do?getRoleFunAuthIds',
			success : function(obj) {
				var data = obj.data;
				if(data){
					var _authIdsArray = data.split(",");
					for(var i=0;i<_authIdsArray.length;i++){
						var node = $('#menuFunDg').treegrid('find', _authIdsArray[i]);
						if(node){
							$('#menuFunDg').treegrid('select', node.id);
						}
					}
				}
			},
			error : function(e) {
				$.miaxisTools.alert('获取菜单权限功能失败!'); 
			}
		});
	}
	
	function selectAll(){
    	var nodes =  $('#menuTree').tree('getChecked', 'unchecked');
    	for(var i=0; i<nodes.length; i++){
    		var node = nodes[i];
    		$('#menuTree').tree('check', node.target);
    	}
    }
    
    function cancelAll(){
    	var nodes =  $('#menuTree').tree('getChecked');
    	for(var i=0; i<nodes.length; i++){
    		var node = nodes[i];
    		$('#menuTree').tree('uncheck', node.target);
    	}
    }
    
    function saveMenu(){
    	var _menuIds = '';
    	var _chcIds = getSelNodes('checked'); //全选节点
    	if(_chcIds){
    		_menuIds = _chcIds;
    	}
    	
    	var _indeIds = getSelNodes('indeterminate');//半选节点
    	if(_indeIds){
    		_menuIds = _menuIds+',' +_indeIds;
    	}
    	if(_menuIds==''){
    		$.miaxisTools.alert('请选择菜单项!');
    		return false;
    	}
    	
    	var _roleId = $("#roleId").val();
    	$.miaxisTools.ajaxSubmit('role.do?saveRoleMenuAuth',{menuIds:_menuIds,roleId:_roleId});
	}
    
    function saveFun(){
    	var rows = $('#menuFunDg').treegrid('getSelections');
		if (rows){
			var _funIds='';
			for(var i=0;i<rows.length;i++ ){
				if(i==0){
					_funIds =   rows[i].id;
				}else{
					_funIds =   _funIds +','+ rows[i].id;
				}
			}
			/*if(_funIds==''){
				$.miaxisTools.alert('请选择功能点记录!');
	    		return false;
			}*/
			
			var _roleId = $("#roleId").val();
			var _menuId = $("#menuId").val();
			$.miaxisTools.ajaxSubmit('role.do?saveRoleFunAuth',{funIds:_funIds,roleId:_roleId,menuId:_menuId});
		}
    }
    
    function getSelNodes(type){
    	var ids = '';
    	var nodes =  $('#menuTree').tree('getChecked', type);
        for(var i=0; i<nodes.length; i++){
        	var id = nodes[i].id;
        	if (ids != '') ids += ',';
			ids += id;
        }
        return ids
    }
	
	function formatIco(val,row) {
		return '<span class="'+val+'" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>';
	}
	</script>
</body>
