<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="authMachine">
		<form id="authMachine_form" method="post" novalidate>
			<input id="userid" name="userid" type="hidden" value="${userId}" />
			<div class="ftitle" >
				<span>用户【${userName}】使用机器授权信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th>
						机器特征码:
					</th>
					<td>
						<input id="machinecode" class="easyui-validatebox" name="machinecode" style="width: 300px;"  data-options="required:true"/>	
					</td>
				</tr>
				<tr>
					<th>
						机器名称:					</th>
					<td>
						<input id="machinename" class="easyui-validatebox" name="machinename" style="width:300px;" />				</td>
				</tr>
				<tr>
					<th>
						授权结束日期:					</th>
					<td>
						<input id="authenddate" class="easyui-validatebox" name="authenddate" onclick="WdatePicker();" 
							style="width: 300px;" data-options="required:true"
							 class="easyui-validatebox my97" />		
							 			</td>
				</tr>
				<tr>
					<th>
						备注:					</th>
					<td>
						<input id="memo" class="easyui-validatebox" name="memo" style="width: 300px;"/>
                    </td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		$().ready(function() {
			$('#authenddate').val('2099-12-30');
		});
	
	    var api = frameElement.api, W = api.opener;
		api.button({
			id:'valueOk',
	        focus:true,
		    name:'确定',
		    callback:function(){
		    	var params={};
				$.miaxisTools.ajaxSubmitForm('userAuthMachine.do?save','authMachine_form',params);
		    	return false;
		    }
		},{
			id:'valueCancel',
		    name:'取消',
		    callback:function(){	    	
		    }
		});
		
	</script>
</body>
