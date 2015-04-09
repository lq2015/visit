<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<%@ include file="/WEB-INF/pages/batchUploadImg.jsp"%>
	<div>
		<div class="ftitle">
			<span>已上传工作单附件</span>
		</div>
		<div id="billPic">
			<c:forEach var="billPic" items="${billPicList}">
				<div style='float:left;padding-left:10px;' align='center'
					id="pic${billPic.id}">
					<div
						style='margin-bottom:5px;border:2px solid #8A9BA8;width:80px;height:80px;'>
						<img id='imghead' src="${billPic.jbPicUrl}"
							style="width:80px;height:80px;" />
					</div>
					<span><a href="#" onClick="javscript:delpic('${billPic.id}');">删除</a></span>
	
				</div>
			</c:forEach>
		</div>
		
		<div style="clear: both;"></div>
		<div class="ftitle">
			<span>未上传工作单附件</span>
		</div>
		<form id="jobUploadDetail_form" method="post" novalidate
			enctype="multipart/form-data">
			<div id="preview"></div>
		</form>
		<input type="hidden" id="btn_sub">
		<input type="hidden" id="jobId" value="${jobId}">
	</div>

	<script type="text/javascript">
		<%--//初始化图片上传控件--%>
		addAttachment(0);
		
		$("#btn_sub").click(function() {
			var params={jobId:$("#jobId").val()};
			$.miaxisTools.ajaxSubmitForm('jobDispatch.do?uploadFiles','jobUploadDetail_form',params);
		});
		
		function delpic(id){
			var _id = id;
			$.miaxisTools.ajaxSubmit('jobDispatch.do?delJobPic',{id:id},function(id){
				$('#pic'+_id).remove();
			});
		}
	</script>
</body>

