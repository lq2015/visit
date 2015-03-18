<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="menuBtnDetail">
		<form id="menuBtnDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<input id="id" name="id" type="hidden" value="${function.id}" />
			<div class="ftitle" >
				<span>菜单功能点基本信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th> 功能点中文名:</th>
					<td>
						<input id="funNameCn" class="easyui-validatebox" name="funNameCn"
							 value="${function.funNameCn}"  data-options="required:true"/>			
					</td>
					<th> 所属菜单:</th>
					<td>
						<input class="easyui-combotree" id="parentMenu" name="parentMenu" 
						data-options="url:'system.do?menuComBoTree',method:'get',panelHeight:150,editable:false,required:true" style="width:154px;">		
					</td>
				</tr>
				<tr>
					<th> 功能点英文名:</th>
					<td>
						<input id="funNameEn" class="easyui-validatebox" name="funNameEn"
							 value="${function.funNameEn}"  data-options="required:true"/>			
					</td>
					<th> 功能点事件:</th>
					<td>
						<input id="funUrl" class="easyui-validatebox" name="funUrl"
							 value="${function.funUrl}" />
					</td>
				</tr>
				<tr>
					<th> 按钮类型:</th>
					<td>
						 <select class="easyui-combobox"  name="funType"  id="funType" 
								style="width: 155px;" data-options="editable:false,panelHeight:'auto'" value='1'>
							<option value="1" <c:if test='${function.funType == 1}'>  selected  </c:if> >普通按钮</option>
							<option value="2"  <c:if test='${function.funType == 2}'>  selected  </c:if>>下拉式按钮</option>
							<option value="3"  <c:if test='${function.funType == 3}'>  selected  </c:if>>下拉子菜单</option>
						</select>			
					</td>
					<th> 下拉子菜单所属按钮:</th>
					<td>
						<input class="easyui-combotree" id="parentFun" name="parentFun" 
						data-options="method:'get',panelHeight:150,editable:false" style="width:154px;">
					</td>
				</tr>
				<tr>
					<th> 图标:</th>
					<td>
					   <input  id="icoIndex" name="icoIndex"  value="${function.icoIndex}"  style="width:154px;">		
					</td>
					<th> 选中时图标:</th>
					<td>
						<input  id="selIcoIndex" name="selIcoIndex"  value="${function.selIcoIndex}"  style="width:154px;">
					</td>
				</tr>
				<tr>
					<th> 是否有效:</th>
					<td>
					    <select class="easyui-combobox"  name="isValid"  id="isValid" 
								style="width: 155px;" data-options="panelHeight:'auto'" value='1'>
							<option value="1" <c:if test='${function.isValid == 1}'>  selected  </c:if> >有效</option>
							<option value="0"  <c:if test='${function.isValid == 0}'>  selected  </c:if>>无效</option>
						</select>		
					</td>
					<th> 排序号:</th>
					<td>
						<input id="orderNum" class="easyui-numberspinner" name="orderNum"
							 value="${function.orderNum}" style="width:154px;" />			
					</td>
				</tr>
				
			</table>
		</form>
		<input id="operationType" name="operationType" type="hidden" value="${operationType}" />
	</div>
	
	<script type="text/javascript">
		var api = frameElement.api, W = api.opener;
	
		$("#btn_sub").click(function() {
			var parentFun = $('#parentFun').combo('getValue');
			var funType = $('#funType').combo('getValue');
			if(parentFun=='' && funType=="3"){
				$.miaxisTools.alert('请选择下拉子菜单所属按钮!');
				return false;
			}
			
			var params={operationType:$("#operationType").val()};
			$.miaxisTools.ajaxSubmitForm('function.do?save','menuBtnDetail_form',params);
			
			setTimeout("W.reloadFun()", 500);
		});
		
		$().ready(function() {
	    	$('#parentMenu').combotree('setValue', '${function.parentMenu}');
	    	
	    	if('${function.isValid}'!=''){
	    		$('#isValid').combobox('setValue', '${function.isValid}');
	    	}
	        if('${function.funType}'!=''){
	        	$('#funType').combobox('setValue', '${function.funType}');
	        }
	        if('${function.parentFun}'!=''){
	        	$('#parentFun').combotree('setValue', '${function.parentFun}');
	        }
	    	
	    	var funType = $('#funType').combo('getValue');
	    	if(funType=="3" ){
	    		$('#parentFun').combo('enable');
	    	}else{
	    		$('#parentFun').combo('disable');
	    	}
	    	
	    	$('#icoIndex').combobox({
				data : iconData,
				panelHeight:110,
				editable:false,
				formatter : function(v) {
					return '<span class="'+v.value+'" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>'+v.value;
				}
			});
	    	
	    	$('#selIcoIndex').combobox({
				data : iconData,
				panelHeight:110,
				editable:false,
				formatter : function(v) {
					return '<span class="'+v.value+'" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>'+v.value;
				}
			});
		});
		
		$('#funType').combobox({
			onChange: function(newValue, oldValue){
				if(newValue=='1' || newValue=='2'){
					$('#parentFun').combo('disable');	
					$('#parentFun').combotree('clear');	
				}else{
					$('#parentFun').combo('enable');
					var parentMenu = $('#parentMenu').combo('getValue');
					if(parentMenu!=''){
						loadMenuFun(parentMenu);
					}
				}
			}
		});
		
		$('#parentMenu').combotree({
			onChange: function(newValue, oldValue){
				var funType = $('#funType').combo('getValue');
				if(funType=="3"){
					loadMenuFun(newValue);
				}
			}
		});
		
		function loadMenuFun(parentMenu){
			var url ='system.do?funComBoTree&parentMenu='+parentMenu;
			$('#parentFun').combotree('reload', url);
		}
		
	</script>
</body>
