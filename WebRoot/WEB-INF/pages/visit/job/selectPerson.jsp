<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div data-options="region:'center'"  border="false">
			<table id="selPersonDg" title="人员信息"  fitColumns="true" singleSelect="false" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="ck" checkbox="true" />
						<th field="id" hidden="true" />
						<th field="piName" width="20" align="center" sortable="true">
							姓名
						</th>
						<th field="piIdnum" width="30" align="left" sortable="true">
							身份证号
						</th>
						<th field="piMobile" width="30" align="left" sortable="true">
							联系手机
						</th>
					</tr>
				</thead>
			</table>
		</div>
		<input id="unitId" name="unitId" type="hidden" value="${unitId}" />
	</div>
	
	<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	
	$().ready(function() {
		var _unitId =$('#unitId').val();
		$('#selPersonDg').datagrid({
			url : 'person.do?list&qPiWorkUnit='+_unitId+'&qPiStatus=1'
		});
		
		setTimeout(function(){
			var rows = $('#selPersonDg').datagrid('getRows');
			var ids =api.get('w_job',1).iframe.contentWindow.document.getElementById('jdPersonIds').value;
			if(ids){
				var ids = ids.split(',');
				for(var i=0;i<ids.length;i++ ){
					for(var x=0;x<rows.length;x++ ){
						var row = rows[x];
						if(row.id==ids[i]){
							$('#selPersonDg').datagrid('selectRow',x);
						}
					}
				}
				
			}
	    },500);
	});
	
	api.button({
	    id:'valueOk',
	    name:'确定',
	    callback:function(){
	    	var rows = $('#selPersonDg').datagrid('getSelections');
			if (rows){
				var names='',ids='';
				for(var i=0;i<rows.length;i++ ){
					if(i==0){
						names = rows[i].piName; 
						ids =   rows[i].id;
					}else{
						names = names +','+ rows[i].piName; 
						ids =   ids +','+ rows[i].id;
					}
				}
				api.get('w_job',1).iframe.contentWindow.document.getElementById('jdPersonIds').value = ids;
				api.get('w_job',1).iframe.contentWindow.document.getElementById('jdPersonNames').value = names;
			}
	    }
	});
	
</script>
</body>
