<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="jobDispatchDetail">
		<form id="jobDispatchDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<input id="id" name="id" type="hidden" value="${jobDispatch.id}" />
			<div class="ftitle" >
				<span>派工基本信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th width="100px">
						服务项目:
					</th>
					<td>
						<input id="jdServeItem" name="jdServeItem" type="hidden" value="${jobDispatch.jdServeItem}" />
						<input class="easyui-combotree" id="jdServeItemId" name="jdServeItemId" 
							data-options="url:'unitPact.do?getServeItem',method:'get',panelHeight:120,panelWidth:200,editable:false,required:true" style="width:305px;"/>		
					</td>
				</tr>
				<tr>
					<th>
						服务单位:
					</th>
					<td>
						<input class="easyui-combobox" id="jdUnit" name="jdUnit"  style="width: 305px;"
								 data-options="valueField:'id',textField:'uiName',panelHeight:'auto',editable:false,required:true"/>	
					</td>
				</tr>
				<tr>
					<th >
						服务人员:						</th>
					<td>
						<input id="jdPersonIds" name="jdPersonIds" type="hidden" value="${jobDispatch.jdPersonIds}" />
						<input id="jdPersonNames" name="jdPersonNames" class="easyui-validatebox" value="${jobDispatch.jdPersonNames}" readonly="readonly" style="width: 300px;"/>	
						<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'"
								onclick="selectPerson();">选择</a>
						<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'"
								onclick="clearPerson();">清空</a>	
					</td>
				</tr>
				<tr>
					<th  >
						需服务网点:
					</th>
					<td>
						<input class="easyui-combobox" id="jdJobBank" name="jdJobBank"  style="width: 305px;" url="public.do?userDept&persontype=2"
								 data-options="valueField:'id',textField:'name',panelHeight:'auto',editable:false,required:true"/>	
					</td>
				</tr>
				<tr>
					<th >
						工作内容简述:
					</th>
					<td>
						<textarea id="jdJobContent" name="jdJobContent"
							class="easyui-validatebox" style="width: 300px;height:50px" >${jobDispatch.jdJobContent}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						服务有效开始日期:					</th>
					<td>
						<input id="jdServeStart" name="jdServeStart"  onclick="WdatePicker();" data-options="required:true"
								class="easyui-validatebox my97" value="${jobDispatch.jdServeStart}" style="width: 300px;"/>
					</td>
				</tr>
				<tr>
					<th>
						服务有效结束日期:
					</th>
					<td>
						<input id="jdServeEnd" name="jdServeEnd"  onclick="WdatePicker();" data-options="required:true"
								class="easyui-validatebox my97" value="${jobDispatch.jdServeEnd}" style="width: 300px;"/>
					</td>
				</tr>
				<tr>
					<th>
						备注:	
                    </th>
					<td>
						<textarea id="jdMemo" name="jdMemo"
							class="easyui-validatebox"  style="width: 300px;height:30px">${jobDispatch.jdMemo}</textarea>
					</td>		
				</tr>
			</table>
		</form>
		<input id="operationType" name="operationType" type="hidden" value="${operationType}" />
	</div>
	
	<script type="text/javascript">
	$().ready(function() {
		if($("#operationType").val()=='edit'){
			
			if ('${jobDispatch.jdServeItemId}' != '') {
				$('#jdServeItemId').combotree('setValue','${jobDispatch.jdServeItemId}');
				loadWorkUnit('${jobDispatch.jdServeItemId}');
			}
			
			if ('${jobDispatch.jdJobBank}' != '') {
				$('#jdJobBank').combobox('setValue','${jobDispatch.jdJobBank}');
			}
			
			setTimeout(function() {
				if ('${jobDispatch.jdUnit}' != '') {
					$('#jdUnit').combobox('setValue','${jobDispatch.jdUnit}');
				}
			}, 1000)
			
			$("#id").attr('readonly','true');
			$("#id").attr("style","border:0;border-bottom:1 solid black;background:white;");
		}
	});
	
	$("#btn_sub").click(function() {
		var _jdServeStart = $("#jdServeStart").val();
		var _jdServeEnd = $("#jdServeEnd").val();
		if(_jdServeStart>_jdServeEnd){
			$.miaxisTools.alert('服务有效开始日期不能大于结束日期!');
			return false;
		}
		
		var params={operationType:$("#operationType").val()};
		var opts={};
		
		if($("#operationType").val()=='insert'){
			opts.resetForm=true;
		}
		
		$.miaxisTools.ajaxSubmitForm('jobDispatch.do?save','jobDispatchDetail_form',params,opts);
	});
	
	$('#jdServeItemId').combotree({
		onSelect : function(node) {
			loadWorkUnit(node.id);
			$('#jdServeItem').val(node.text);
		}
	});
	
	$('#jdUnit').combobox({
		onSelect : function(node) {
			clearPerson();
		}
	});
	
	function selectPerson() {
		var _jdUnit =$('#jdUnit').combobox('getValue')
		if(_jdUnit){
			var api = frameElement.api, W = api.opener;
			W.$.dialog({title:'选择人员',content:'url:jobDispatch.do?selectPerson&unitId='+_jdUnit,lock:true,parent:api});
		}else{
			$.miaxisTools.alert('请选择服务单位!');
		}
	}
	
	function clearPerson() {
		$("#jdPersonIds").val('');
		$("#jdPersonNames").val('');
	}
	
	function loadWorkUnit(_serveItem) {
		var url = 'jobDispatch.do?getServeUnit&serveItem='+_serveItem;
		$('#jdUnit').combobox('clear');
		$('#jdUnit').combobox('reload', url);
	}
	</script>
</body>
