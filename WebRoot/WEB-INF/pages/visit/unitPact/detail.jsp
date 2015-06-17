<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<%@ include file="/WEB-INF/pages/batchUploadImg.jsp"%>
	<div class="easyui-layout" style="width: 100%;" fit="true">
	    <form id="unitPactDetail_form" method="post" novalidate enctype="multipart/form-data" >
			<input type="hidden" id="btn_sub" >
			<input type="hidden" id="btn_upload" >
			<input type="hidden" id="btn_clear" >
			<input id="id" name="id" type="hidden" value="${unitPact.id}" />
			<div data-options="region:'center'" border="false" style="overflow: hidden;">
				<div class="ftitle" >
					<span>服务合同基本信息</span>
				</div>
				<table class="doc-table">
					<tr>
						<th width="100px">
							服务单位:
						</th>
						<td colspan="3">
							<input class="easyui-combobox" id="upUnitId" name="upUnitId"  style="width: 280px;"
								 data-options="valueField:'id',textField:'uiName',panelHeight:'auto',editable:false,required:true"/>	
						</td>
					</tr>
					<tr>
						<th >
							合同服务项目:
						</th>
						<td >
							 <input id="upServeItem" name="upServeItem" type="hidden" value="${unitPact.upServeItem}" />
							 <input class="easyui-combotree" id="upServeItemId" name="upServeItemId" 
							data-options="url:'unitPact.do?getServeItem',method:'get',panelHeight:120,panelWidth:200,editable:true,required:true" style="width:280px;">				
						</td>
					</tr>
					<tr>
						<th >
							合同编号:
						</th>
						<td >
							<input id="upNumber" name="upNumber"  style="width:280px;" data-options="required:true"
								class="easyui-validatebox" value="${unitPact.upNumber}" />
						</td>
					</tr>
					
					<tr>
						<th >
							合同开始日期:
						</th>
						<td>
							<input id="upServeStart" name="upServeStart"  onclick="WdatePicker();" data-options="required:true"
								class="easyui-validatebox my97" value="${unitPact.upServeStart}" style="width: 280px;"/>
						</td>
					</tr>
					<tr>
						<th>
							合同结束日期:
						</th>
						<td>
							<input id="upServeEnd" name="upServeEnd"  onclick="WdatePicker();" data-options="required:true"
								class="easyui-validatebox my97" value="${unitPact.upServeEnd}" style="width: 280px;"/>
						</td>
					</tr>
					<tr>
						<th>
							备注:	
	                    </th>
						<td colspan="3">
							<textarea id="upMemo" name="upMemo"
							class="easyui-validatebox" value="${unitPact.upMemo}" style="width: 283px;height:160px"></textarea>
						</td>		
					</tr>
				</table>
			</div>
			<div data-options="region:'east'" border="true"
				style="width: 300px; overflow: hidden;">
				<div class="ftitle" >
					<span>已上传服务合同附件</span>
				</div>
				<div id="pactPic">
					<c:forEach var="pactPic" items="${pactPicList}">
					<div  style='float:left;padding-left:10px;' align='center' id="pic${pactPic.id}">
						<div style='margin-bottom:5px;border:2px solid #8A9BA8;width:80px;height:80px;'>
						<img id='imghead' src="${pactPic.upPicUrl}" style="width:80px;height:80px;"/> </div>
						<span><a href="#" onClick="javscript:delpic('${pactPic.id}');">删除</a></span>
						
						</div>
					</c:forEach>
				</div>
				<div style="clear: both;"></div>
				<div class="ftitle" >
					<span>未上传服务合同附件</span>
				</div>
				<div id="preview"  >
					<!-- 图片预览区域 -->
				</div>
			</div>
		</form>
		<input id="operationType" name="operationType" type="hidden" value="${operationType}" />
	</div>
	
	<script type="text/javascript">
	<%--//初始化图片上传控件--%>
	addAttachment(0);
	
	$().ready(function() {
		
		loadWorkUnit();
		setTimeout(function() {

			if ('${unitPact.upUnitId}' != '') {
				$('#upUnitId').combobox('setValue','${unitPact.upUnitId}');
			}
			if ('${unitPact.upServeItemId}' != '') {
				$('#upServeItemId').combotree('setValue','${unitPact.upServeItemId}');
			}
		}, 500)
		
		if($("#operationType").val()=='edit'){
			$("#id").attr('readonly','true');
			$("#id").attr("style","border:0;border-bottom:1 solid black;background:white;");
		}
	});
	
	$('#upServeItemId').combotree({
		onSelect : function(node) {
			$('#upServeItem').val(node.text);
		}
	});
	
	function loadWorkUnit() {
		var url = 'person.do?getWorkUnit';
		$('#upUnitId').combobox('clear');
		$('#upUnitId').combobox('reload', url);
	}
	
	$("#btn_sub").click(function() {
		var start = $('#upServeStart').val(); 
		var end = $('#upServeEnd').val(); 
		if(start>end){
			$.miaxisTools.alert('合同开始日期不能大于合同结束日期！');
			return ;
		}
		
		
		var params={operationType:$("#operationType").val()};
		$.miaxisTools.ajaxSubmitForm('unitPact.do?save','unitPactDetail_form',params);
	});
	
	function delpic(id){
		var _id = id;
		$.miaxisTools.ajaxSubmit('unitPact.do?delPactPic',{id:id},function(id){
			$('#pic'+_id).remove();
		});
	}
	
	$("#btn_upload").click(function() {
		if($.BatchUploadImg.fileNums>4){
			$.miaxisTools.alert('合同附件最大上传数不能超过2张');
			return false;
		}else{
			$.BatchUploadImg.selectAttachment();
		}
	});
	
	$("#btn_clear").click(function() {
		$.BatchUploadImg.clearAttachment();
	});
	</script>
</body>
