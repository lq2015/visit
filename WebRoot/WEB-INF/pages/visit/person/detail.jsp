<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<script type="text/javascript" src="<%=basePath%>/js/MiaxisOCX/Third_FrontOCX.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/imagePreview.js"></script>
<object classid="CLSID:A3ECA36F-CD28-422C-918F-5893C6FC9849" id="CommOcx" width="1" height="1" codebase="Third_FrontOCX.ocx"> </object>
<OBJECT height="0" width="0" align="left" classid="clsid:07390B1A-6918-493E-A465-F857F4A8D655" name="xt22UOCX">
	<PARAM NAME="_ExtentX" VALUE="265">
	<PARAM NAME="_ExtentY" VALUE="265">
	<PARAM NAME="_StockProps" VALUE="0">
	<embed width="0" height="0">
	</embed>
</OBJECT>
<body>

	<div class="easyui-tabs" fit=true>
		<div title="基本信息" style="padding: 10px">
			<form id="personDetail_form" method="post" novalidate enctype="multipart/form-data">
				<input type="hidden" id="btn_sub"> <input type="hidden" id="btn_idnum"> <input type="hidden" id="btn_shoot"> <input id="id" name="id" type="hidden"
					value="${personInfo.id}" />
				<div class="ftitle">
					<span>人员信息</span>
				</div>
				<table class="doc-table">
					<tr>
						<th>工作单位:</th>
						<td><input class="easyui-combobox" id="piWorkUnit" name="piWorkUnit" style="width: 201px;"
							data-options="valueField:'id',textField:'uiName',panelHeight:'auto',editable:false,required:true" /></td>
						<th>工号:</th>
						<td><input id="piNumber" name="piNumber" class="easyui-validatebox" value="${personInfo.piNumber}" style="width: 198px;" /></td>
					</tr>
					<tr>
						<th width="74">姓名:</th>
						<td><input id="piName" name="piName" class="easyui-validatebox" data-options="required:true" validType="chinese" value="${personInfo.piName}" style="width: 198px;" /></td>
						<th>身份证号:</th>
						<td><input id="piIdnum" name="piIdnum" class="easyui-validatebox" data-options="required:true" validType="idcard" value="${personInfo.piIdnum}" style="width: 198px;" />
						</td>
					</tr>
					<tr>
						<th>职务:</th>
						<td><input id="piPost" name="piPost" class="easyui-validatebox" data-options="required:true" value="${personInfo.piPost}" style="width: 198px;" /></td>
						<th>性别:</th>
						<td><select class="easyui-combobox" name="piSex" id="piSex" style="width: 200px;" data-options="panelHeight:'auto',editable:false">
								<option value="男">男</option>
								<option value="女">女</option>
						</select></td>
					</tr>
					<tr>
						<th>联系手机:</th>
						<td><input id="piMobile" name="piMobile" class="easyui-validatebox" data-options="required:true" value="${personInfo.piMobile}" style="width: 198px;" validType="mobile" />
						</td>
						<th>联系电话:</th>
						<td><input id="piTelephone" name="piTelephone" class="easyui-numberbox" value="${personInfo.piTelephone}" style="width: 198px;" /></td>
					</tr>
				</table>
				<div class="ftitle">
					<span>人员证照信息</span>
				</div>
				<table class="doc-table">
					<tr>
						<th>人员照片</th>
						<th>工作证</th>
					</tr>
					<tr>
						<td align="center">
							<div id="preview" style="border:solid 1px #999999;width:80px;height:100px">
								<img id="personPhoto" width=81 height=101 border=0 />
							</div>
						</td>
						<td align="center">
							<div id="preview2" style="border:solid 1px #999999;width:80px;height:100px">
								<img id="personCert" width=81 height=101 border=0 />
							</div>
						</td>
					</tr>
					<tr>
						<td align="center" id="selFile"><input type="file" name="files" id="coverPicFile" style="width: 250px; height: 20px;" onChange="selectPhoto(this);" /></td>
						<td align="center"><input type="file" name="files" id="coverCertFile" style="width: 250px; height: 20px;" onChange="selectCert(this);" /></td>
					</tr>
				</table>
			</form>
			<input id="operationType" name="operationType" type="hidden" value="${operationType}" /> <input id="photoData" name="photoData" type="hidden" value="${photoData}" />
		</div>

		<div title="指纹信息" style="padding: 5px;">
			<div class="ftitle">
				<span>指纹采集区</span>
			</div>
			<table width="100%" cellspacing="0" height="80" border="0">
				<tr>
					<td align="center" style="text-align: center; " valign="center"><%@ include file="/WEB-INF/pages/visit/finger.jsp"%></td>
				</tr>
			</table>
			<div class="ftitle">
				<span>采集注意事项</span>
			</div>
			<table width="100%" cellspacing="0">
				<tr>
					<td align="left" style="text-align:left;padding-left: 30px" height="70" valign="top">
						<div style="height: 5px">1.采集时请放平手指，指纹纹路在中心区范围内，没有倾斜。</div>
						<br />
						<div style="height: 5px">2.指纹存在脱皮，伤疤等影响纹理清晰情况，会导致采集失败。</div>
						<br />
						<div style="height: 5px">3.采集前请确保手指干净，无油污、灰尘等，湿手指采集前，请擦干手指。</div>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<script type="text/javascript">
		var CommOcx;
	
		$().ready(function() {
			CommOcx = document.getElementById("CommOcx");
			loadWorkUnit();
			
			setTimeout(function() {
				if ('${personInfo.piPhotoUrl}' != '') {
					$("#personPhoto").attr("src", '${personInfo.piPhotoUrl}');
				}
				
				if ('${personInfo.piCertUrl}' != '') {
					$("#personCert").attr("src", '${personInfo.piCertUrl}');
				}
				
				if ('${personInfo.piWorkUnit}' != '') {
					$('#piWorkUnit').combobox('setValue','${personInfo.piWorkUnit}');
				}
				
				if ('${personInfo.piSex}' != '') {
					$('#piSex').combobox('setValue', '${personInfo.piSex}');
				}
				
				<%//表单提交后或编辑时初始化指纹选项%>
				<c:if test="${fingerInfoList != null}">
					var fingerIndex = new Array();
					<c:forEach var="finger" items="${fingerInfoList}">
						fingerIndex.push("${finger.fiCode}");
					</c:forEach>
					$("input[name='fingerCode']").each(function(){
						this.checked = jQuery.inArray(this.value, fingerIndex) == -1 ? false : true;
					});
				</c:if>
				
			}, 500)
		});
		
		$("#btn_shoot").click(
			function() {
				var api = frameElement.api, W = api.opener;
				W.$.dialog({
					title:'拍照',
					id:Math.round(Math.random()*(1000-100000)+100000),
					content:'url:person.do?shootPhoto',
				    lock:true,
				    max:false,
				    parent:api,
				    width:500,
				    height:350
				});
		});

		 <%//选择本地图片文件"%>
		function selectPhoto(obj){
			$("#photoData").val('');
			$.miaxisTools.imagePreview(obj, 'preview', 'personPhoto', 80, 100);
		}
		
		 <%//选择本地图片文件"%>
		function selectCert(obj){
			$.miaxisTools.imagePreview(obj, 'preview2', 'personCert', 80, 100);
		}
		
		function loadWorkUnit() {
			var url = 'person.do?getWorkUnit';
			$('#piWorkUnit').combobox('clear');
			$('#piWorkUnit').combobox('reload', url);
		}
		
		$("#btn_sub").click(function() {
			var params = {operationType : $("#operationType").val()};
			
			var photourl = '${personInfo.piPhotoUrl}'; 
			var coverPicFile = $('#coverPicFile').val(); 
			var photoData = $('#photoData').val(); 
			
			var certurl = '${personInfo.piCertUrl}'; 
			var coverCertFile = $('#coverCertFile').val(); 
			
			if(photourl=='' ){
				if(coverPicFile=='' & photoData==''){
					$.miaxisTools.alert('请上传人员照片!'); 
					return;
				}				
			}
			
			if(certurl=='' & coverCertFile=='' ){
				$.miaxisTools.alert('请上传工作证!'); 
				return;
			}
			
			var params = {};
			if ($("input[type=checkbox]:checked").size() != 2) {
				$.miaxisTools.alert("请采集2枚指纹！");
				return false ;
			}else{
				$("input[type=checkbox]:checked").each(function(index,element){
					var num = $(this).val();
					var fingerData = $("input[name=finger"+num+"]").val();
					if(num==0){
						params.finger0= fingerData;
					}
					if(num==1){
						params.finger1= fingerData;
					}
					if(num==2){
						params.finger2= fingerData;
					}
					if(num==3){
						params.finger3= fingerData;
					}
					if(num==4){
						params.finger4= fingerData;
					}
					if(num==5){
						params.finger5= fingerData;
					}
					if(num==6){
						params.finger6= fingerData;
					}
					if(num==7){
						params.finger7= fingerData;
					}
					if(num==8){
						params.finger8= fingerData;
					}
					if(num==9){
						params.finger9= fingerData;
					}
				})
			}
		
			params.operationType= $("#operationType").val()
			params.photoData=$("#photoData").val();
			
			$.miaxisTools.ajaxSubmitForm('person.do?save','personDetail_form', params);
	    });
		
		$("#btn_idnum").click(
			function() {
				var readStudentIdCard = new IDCard();
				var result = readStudentIdCard.read();
				if (result) {
					//填充信息
					$('#piName').val(result.SFZ_Name);
					$('#piIdnum').val(result.SFZ_IDCode);
					
					var _data = $.miaxisTools.sfzCastAgeSex(result.SFZ_IDCode);
					$('#piSex').combobox('setValue',_data.sex);
				}
		});
	</script>
</body>
