<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/jquery/imagePreview.js"></script>
<body>
	<div class="easyui-tabs" fit=true>
		
		<div title="基本信息" style="padding: 10px">
			<div class="ftitle">
				<span>人员信息</span>
			</div>
			<table class="doc-table">
				<tr  height=28px>
					<th>
						工作单位:
                    </th>
					<td >
							
						${personInfo.unitInfo.uiName}
					</td>
					<th>
						工号:
				    </th>
					<td>
						${personInfo.piNumber}
					</td>
				</tr>
				<tr  height=28px>
					<th width="74">
						姓名:					</th>
					<td>
						${personInfo.piName}
					</td>
					<th>
						身份证号:
					</th>
					<td>
						${personInfo.piIdnum}
					</td>
				</tr>
				<tr  height=28px>
					<th>
						职务:
					</th>
					<td>
						${personInfo.piPost}
					</td>
					<th>
						性别:
					</th>
					<td>
						${personInfo.piSex}
                   </td>
			    </tr>
				<tr  height=28px>
					<th>
						联系手机:					</th>
					<td>
						${personInfo.piMobile}
					</td>
					<th>
						联系电话:
					</th>
					<td>
						${personInfo.piTelephone}
				    </td>
			    </tr>
			</table>
			<div class="ftitle">
				<span>人员证照信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th>
						人员照片
                    </th>
                    <th>
						工作证	
                    </th>
				</tr>
				<tr>
					 <td align="center">
						<div id="preview" style="border:solid 1px #999999;width:80px;height:100px">
							<img id="personPhoto" width=81 height=101 border=0 src="${personInfo.piPhotoUrl}" />
						</div>
					</td>
					<td align="center" >
						<div id="preview2" style="border:solid 1px #999999;width:80px;height:100px">
							<img id="personCert" width=81 height=101 border=0  src="${personInfo.piCertUrl}" />
						</div>
                    </td>
				</tr>
				
			</table>
	</div>
		
		
	</div>

	
</body>
