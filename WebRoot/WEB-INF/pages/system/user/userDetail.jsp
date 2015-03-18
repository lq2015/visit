<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="userDetail">
		<form id="userDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<input id="id" name="id" type="hidden" value="${user.id}" />
			<div class="ftitle" >
				<span>用户基本信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th>
						人员类型:					</th>
					<td colspan="3">	
						<select class="easyui-combobox"  name="persontype"  id="persontype" style="width:422px;"
								 data-options="panelHeight:'auto',required:true">
								<option value="1">培训机构</option>
								<option value="2">管理部门</option>
								<option value="3">运维机构</option>
							</select>
					</td>
				</tr>
				<tr>
					<th>
						所属部门:					</th>
					<td colspan="3">
						<input class="easyui-combotree" id="departmant" name="departmant" 
						data-options="method:'get',panelHeight:150,editable:false" style="width:422px;">					</td>
				</tr>
				<tr>
					<th>
						登陆名:					</th>
					<td>
						<input id="userName" class="easyui-validatebox" name="userName"
							 value="${user.userName}"  data-options="required:true,validType:'length[2,10]'"/>					</td>
					<th>
						真实姓名:					</th>
					<td>
						<input id="realName" name="realName" class="easyui-validatebox"
							value="${user.realName}" />					</td>
				</tr>
				<tr>
					
					<th>
						联系手机:					</th>
					<td>
						<input id="mobilePhone" name="mobilePhone" class="easyui-numberbox" sizeWidth="11" 
							 value="${user.mobilePhone}" />					</td>
					<th>
						电子邮箱:					</th>
					<td>
						<input id="email" name="email" class="easyui-validatebox" data-options="validType:'email'"
							value="${user.email}" />					</td>
				</tr>
				<tr>
					<th>
						办公电话:					</th>
					<td colspan="3" >
						<input id="officePhone" name="officePhone"  type="text"
							class="easyui-numberbox" sizeWidth="12" 
							value="${user.officePhone}" />										</td>
				</tr>
				<tr>
					<th>
						角色:					</th>
					<td colspan="3" >
						<input type="hidden" id="roleIds" value="${roleIds}" >
						<input id="roleNames" name="roleNames"  type="text""   style="width:250px;"
							value="${roleNames}"  readonly="readonly""/> 
						<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'"
								onclick="selectRole();">选择</a>
						<a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'"
								onclick="clearRole();">清空</a>					</td>
				</tr>
			</table>
		</form>
		<input id="operationType" name="operationType" type="hidden" value="${operationType}" />
	</div>
	
	<script type="text/javascript">
		$("#btn_sub").click(function() {
			var persontype = $('#persontype').combo('getValue');
			if(persontype!="3"){
				var departmant = $('#departmant').combotree('getValue');
				if(departmant==''){
					$.miaxisTools.alert('请选择所属部门!');
					return false;
				}
			}
			
			var params={operationType:$("#operationType").val(),roleIds:$("#roleIds").val()};
			$.miaxisTools.ajaxSubmitForm('user.do?save','userDetail_form',params);
		});
		
		$().ready(function() {
			if('${user.persontype}'!=''){
	        	$('#persontype').combobox('setValue', '${user.persontype}');
	        }else{
				$('#persontype').combobox('setValue', '3');
			}
			
			if('${user.departmant}'!=''){
	        	$('#departmant').combotree('setValue', '${user.departmant}');
	        }
			var persontype = $('#persontype').combo('getValue');
			if(persontype!='3'){
				loadDepartmant(persontype);
			}
		})
		
		function selectRole() {
			var api = frameElement.api, W = api.opener;
			W.$.dialog({title:'选择角色',content:'url:user.do?selectRole',lock:true,parent:api});
		}
		
		function clearRole() {
			$("#roleIds").val('');
			$("#roleNames").val('');
		}
		
		$('#departmant').combotree({
			onSelect : function(record) {
				var persontype = $('#persontype').combo('getValue');
				if(persontype=='1'){
					if (record.id.length < 8) {
						$('#departmant').combotree('clear');
						$.miaxisTools.alert("请选择培训机构!");
						return false;
					}
				}
			}
		});
		
		$('#persontype').combobox({
			onChange: function(newValue, oldValue){
				if(newValue=='3' ){
					$('#departmant').combo('disable');	
					$('#departmant').combotree('clear');	
				}else{
					$('#departmant').combo('enable');
					$('#departmant').combotree('clear');
					var persontype = $('#persontype').combo('getValue');
					if(persontype!=''){
						loadDepartmant(persontype);
					}
				}
			}
		});
		
		function loadDepartmant(persontype){
			var url ='public.do?departmantTree&personType='+persontype;
			$('#departmant').combotree('reload', url);
		}
	</script>
</body>
