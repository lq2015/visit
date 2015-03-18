<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/jquery/imagePreview.js"></script>
<body>
	<div id="paramDetail">
		<form id="paramDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub">
			<input id="id" name="id" type="hidden" value="${sysParam.id}" />
			<div class="ftitle">
				<span>参数信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th>
					参数名称：
					</th>
					<td>
						<input id="spName" name="spName" style="width: 150px;" data-options="required:true"
								class="easyui-validatebox" value="${sysParam.spName}"/>
					</td>
                    <th>
                        参数类别:
                    </th>
                    <td>
                            <select class="easyui-combobox"  name="spType"  id="spType" 
                                style="width: 154px;" data-options="panelHeight:'auto'">
                                <option value="">--全部--</option>
                                <option value="0">系统参数</option>
                                <option value="1">业务参数</option>
                            </select>
                    </td>
				</tr>
                <tr>
                    <th>
                        参数ID:
                    </th>
                    <td>
                        <input id="spKey" name="spKey" style="width: 150px;" data-options="required:true"
                            class="easyui-validatebox" value="${sysParam.spKey}" />
                    </td>
                    <th>
                        参数值:
                    </th>
                    <td>
                        <input id="spValue" name="spValue" style="width: 150px;" data-options="required:true"
                            class="easyui-validatebox" value="${sysParam.spValue}" />
                    </td>
                </tr>
                <tr>
                    <th>
                        参数状态:
                    </th>
                    <td>
                        <select class="easyui-combobox"  name="spStatus" id="spStatus" 
                            style="width: 154px;" data-options="panelHeight:'auto'" >
                            <option value="0">无效</option>
                            <option value="1" selected>有效</option>
                        </select>
                    </td>
                    <th>
                    参数说明：
                    </th>
                    <td>
                        <input id="spMemo" name="spMemo" style="width: 150px;" data-options="required:false"
                                value="${sysParam.spMemo}"/>
                    </td>
                </tr>
                
			</table>

		</form>
		<input id="operationType" name="operationType" type="hidden"
			value="${operationType}" />
	</div>

	<script type="text/javascript">
	$().ready(function() {
        if ('${sysParam.spType}' != '') {
            $('#spType').combobox('setValue', '${sysParam.spType}');
        }			
        if ('${sysParam.spStatus}' != '') {
            $('#spStatus').combobox('setValue', '${sysParam.spStatus}');
        }
	});
	


	$("#btn_sub").click(
			function() {
				var params = {
					operationType : $("#operationType").val()
				};
				$.miaxisTools.ajaxSubmitForm('sysparam.do?save',
						'paramDetail_form', params);
			});
			

</script>
</body>
