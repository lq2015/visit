<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div id="menuDetail">
		<form id="menuDetail_form" method="post" novalidate>
			<input type="hidden" id="btn_sub" >
			<input id="id" name="id" type="hidden" value="${menu.id}" />
			<div class="ftitle" >
				<span>菜单基本信息</span>
			</div>
			<table class="doc-table">
				<tr>
					<th> 菜单名称:</th>
					<td>
						<input id="menuName" class="easyui-validatebox" name="menuName"
							 value="${menu.menuName}"  data-options="required:true"/>					</td>
					<th> 父級菜单:</th>
					<td>
						<input class="easyui-combotree" id="parentMenu" name="parentMenu" 
						data-options="url:'system.do?menuComBoTree&tag=1',method:'get',panelHeight:150,editable:true" style="width:154px;">					</td>
				</tr>
				<tr>
					<th> 链接页面:</th>
					<td colspan="3">
						<input id="linkPage" class="easyui-validatebox" name="linkPage" style="width:270px;"
							 value="${menu.linkPage}"/>					</td>
				</tr>
				<tr>
					<th> 图标:</th>
					<td>
					   <input  id="icoIndex" name="icoIndex"  value="${menu.icoIndex}"  style="width:154px;">		
					</td>
					<th> 选中时图标:</th>
					<td>
						<input  id="selIcoIndex" name="selIcoIndex"  value="${menu.selIcoIndex}"  style="width:154px;">
					</td>
				</tr>
				<tr>
					<th> 是否有效:</th>
					<td>
					    <select class="easyui-combobox"  name="isValid"  id="isValid" 
								style="width: 155px;" data-options="panelHeight:'auto'" value='1'>
							<option value="1" <c:if test='${menu.isValid == 1}'>  selected  </c:if> >有效</option>
							<option value="0"  <c:if test='${menu.isValid == 0}'>  selected  </c:if>>无效</option>
						</select>					</td>
					<th> 排序号:</th>
					<td>
						<input id="orderNum" class="easyui-numberspinner" name="orderNum"
							 value="${menu.orderNum}" style="width:154px;"/>					</td>
				</tr>
			</table>
		</form>
		<input id="operationType" name="operationType" type="hidden" value="${operationType}" />
	</div>
	
	
	
	<script type="text/javascript">
		var api = frameElement.api, W = api.opener;
		
		$("#btn_sub").click(function() {
			var params={operationType:$("#operationType").val()};
			$.miaxisTools.ajaxSubmitForm('menu.do?save','menuDetail_form',params);
			setTimeout("W.reloadMenu()", 500);
		});
		
		$().ready(function() {
	    	$('#parentMenu').combotree('setValue', '${menu.parentMenu}');
	    	if('${menu.isValid}'!=''){
	    		$('#isValid').combobox('setValue', '${menu.isValid}');
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
		
	</script>
</body>
