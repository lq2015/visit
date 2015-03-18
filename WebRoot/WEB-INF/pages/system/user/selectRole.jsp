<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div data-options="region:'center'"  border="false">
			<table id="selRoleDg" title="角色信息"  fitColumns="true" singleSelect="false" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="ck" checkbox="true" />
						<th field="id" hidden="true" />
						<th field="roleName" width="30" align="left" sortable="true">
							角色名
						</th>
						<th field="memo" width="60" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
<input type="hidden" id="qq" value="asfsaf" >
	<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	
	$().ready(function() {
		$('#selRoleDg').datagrid({
			url : 'role.do?listAll'
		});
		
		setTimeout(function(){
			var rows = $('#selRoleDg').datagrid('getRows');
			var roleIds =api.get('w_user',1).iframe.contentWindow.document.getElementById('roleIds').value;
			if(roleIds){
				var ids = roleIds.split(',');
				for(var i=0;i<ids.length;i++ ){
					for(var x=0;x<rows.length;x++ ){
						var row = rows[x];
						if(row.id==ids[i]){
							$('#selRoleDg').datagrid('selectRow',x);
						}
					}
				}
				
			}
	    },100);
	});
	
	
	
	api.button({
	    id:'valueOk',
	    name:'确定',
	    callback:function(){
	    	var rows = $('#selRoleDg').datagrid('getSelections');
			if (rows){
				var roleNames='',roleIds='';
				for(var i=0;i<rows.length;i++ ){
					if(i==0){
						roleNames = rows[i].roleName; 
						roleIds =   rows[i].id;
					}else{
						roleNames = roleNames +','+ rows[i].roleName; 
						roleIds =   roleIds +','+ rows[i].id;
					}
				}
				api.get('w_user',1).iframe.contentWindow.document.getElementById('roleIds').value = roleIds;
				api.get('w_user',1).iframe.contentWindow.document.getElementById('roleNames').value = roleNames;
			}
	    }
	});
	
</script>
</body>
