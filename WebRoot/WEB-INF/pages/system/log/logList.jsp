<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height:50px;" class="query_panel"  border="false">
			<form id="log_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							日志类型:
						</td>
						<td>
							<select class="easyui-combobox"  name="qLogOperateType"  
								style="width: 100px;" data-options="panelHeight:'auto'">
								<option value="1">登陆</option>
								<option value="2">登出</option>
							</select>
						</td>
						<td class="tdlabel">
							操作员:
						</td>
						<td>
							<input type="text" 
								name="qName" />
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryLog();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center'"  border="false">
			<table id="logDg" title="系统日志信息"  pagination="true" 
				fitColumns="true" singleSelect="true" fit="true" striped="true"
				rownumbers="true">
				<thead>
					<tr>
						<th field="ck" checkbox="true" />
						<th field="id" hidden="true" />
						<th field="operatetype" width="20" align="center" sortable="true" formatter="formatOperateType">
							日志类型
						</th>
						<th field="user" width="20" align="center" sortable="true" formatter="formatOperator">
							操作人
						</th>
						<th field="broswer" width="20" align="center" sortable="true">
							用户浏览器
						</th>
						<th field="note" width="30" align="center" sortable="true">
							用户IP
						</th>
						<th field="logcontent" width="120" align="left" sortable="true">
							内容
						</th>
						<th field="operatetime" width="30" align="center" sortable="true">
							操作时间
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#logDg').datagrid({
			url : 'system.do?logList'
		});
	});
	
	function queryLog() {
		$('#logDg').datagrid('reload',
				$.miaxisTools.serializeObject($('#log_query_form')));
	}
	
	function formatOperateType(val,row){  
		if(val=='1'){
	 		return "登陆";
		}else if(val=='2'){
	 		return "登出";
	 	}else if(val=='3'){
	 		return "修改";
	 	}else if(val=='4'){
	 		return "删除";
	 	}
     } 
	
	function formatOperator(val,row){
		return val.realName;
	}
	
</script>
</body>
