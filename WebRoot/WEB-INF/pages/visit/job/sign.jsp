<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div data-options="region:'center'"  border="false">
			(${id})sign
		</div>
		<input id="id" name="id" type="hidden" value="${id}" />
		<input type="hidden" id="btn_sub" >
	</div>
	
	<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	
	$("#btn_sub").click(function() {
		var params={id:${id}};
		$.miaxisTools.ajaxSubmit('jobDispatch.do?signSubmit',params);
	});
</script>
</body>
