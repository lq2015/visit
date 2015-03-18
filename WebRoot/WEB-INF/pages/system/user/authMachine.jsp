<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" fit="true">
		<div data-options="region:'center'"  border="false">
			<table id="authMachineDg" title="用户【${userName}】使用机器授权信息"  pagination="true" fitColumns="true" singleSelect="false" fit="true" striped="true"
				rownumbers="true" >
				<thead>
					<tr>
						<th field="id" hidden="true" />
						<th field="machinecode" width="70" align="left" sortable="true">
							机器特征码
						</th>
						<th field="machinename" width="20" align="center" sortable="true">
							机器名称
						</th>
						<th field="authenddate" width="25" align="center" sortable="true">
							授权结束日期
						</th>
						<th field="memo" width="30" align="left" sortable="true">
							备注
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<script type="text/javascript">
		var api = frameElement.api, W = api.opener;
		
		$().ready(function() {
			$('#authMachineDg').datagrid({
				url : 'userAuthMachine.do?list&userId=${userId}'
			});
		});
		
		api.button({
			id:'authinsert',
			focus: true,
		    name:'授权新增',
		    callback:function(){	
		    	W.$.dialog({title:'新增授权机器',content:'url:userAuthMachine.do?insert&userId=${userId}&userName=${userName}',width:500,height:180,max:false,min:false,lock:true,parent:api});
		    	return false;
		    }
		},{
			id:'authdel',
		    name:'授权删除',
		    callback:function(){
		    	var row = $('#authMachineDg').datagrid('getSelected');
		    	if (row){
					$.miaxisTools.confirm('确定要删除当前记录吗?',function(r){
						if (r){
							var index = $('#authMachineDg').datagrid('getRowIndex',row);
							$.post('userAuthMachine.do?del',{id:row.id}, function (data){
								if (data.result=='0'){
				                	$('#authMachineDg').datagrid('deleteRow',index);
				              	}
				               $.miaxisTools.alert(data.message);
				            },'json');
						}
					});
				}else{
					$.miaxisTools.alert('请选择需要删除的记录!');
				}
		    	return false;
			}
		});
		
	</script>
</body>
