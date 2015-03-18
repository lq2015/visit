<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div
			data-options="region:'north',split:true,title:'',iconCls:'icon-node'"
			style="height:50px;" class="query_panel"  border="false">
			<form id="sysparam_query_form" method="post">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="tdlabel">
							参数类型:
						</td>
						<td>
							<select class="easyui-combobox"  name="qspType"  
								style="width: 100px;" data-options="panelHeight:'auto'">
                                <option value="">--全部--</option>
                                <option value="0">系统参数</option>
                                <option value="1">业务参数</option>
							</select>
						</td>
						<td class="tdlabel">
							参数名称:
						</td>
						<td>
                            <input class="easyui-validatebox" type="text" 
                                name="qspName" id="qspName" style="width: 130px;"/>
						</td>
						<td class="tdbutton">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								onclick="queryParam();">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
        <div data-options="region:'center'"  border="false">
            <table id="sysparamDg" title="考试信息" toolbar="#sysparamToolBar" pagination="true" 
                fitColumns="true" singleSelect="true" fit="true" striped="true"
                rownumbers="true">
                <thead>
                    <tr>
                        <th field="id" hidden="true" />
                        <th field="spName" width="15" align="center" sortable="true">
                            参数名称
                        </th>
                        <th field="spKey" width="15" align="center" sortable="true">
                            参数ID
                        </th>
                        <th field="spValue" width="20" align="center" sortable="true">
                            参数值
                        </th>
                        <th field="spTypeText" width="10" align="center" sortable="true">
                            参数类别
                        </th>
                        <th field="spStatusText" width="10" align="center" sortable="true">
                            参数状态
                        </th>
                        <th field="spMemo" width="25" align="center" sortable="true">
                            参数说明
                        </th>
                    </tr>
                </thead>
            </table>
            <div id="sysparamToolBar">
                <miaxis:userMenuFunAuth menuId="${menuId}" />
            </div>
		</div>
	</div>

	<script type="text/javascript">
	$().ready(function() {
		$('#sysparamDg').datagrid({
			url : 'sysparam.do?list'
		});
	});
	
	function queryParam() {
		$('#sysparamDg').datagrid('reload',
				$.miaxisTools.serializeObject($('#sysparam_query_form')));
	}
	

    function editParam() {
        var row = $('#sysparamDg').datagrid('getSelected');
        if (row) {
            $.miaxisTools.openPopupWin({
                id  : 'w_sysparam',
                url   : 'sysparam.do?insertOrUpdate&operationType=edit&id=' + row.id,
                width : 600,
                height: 200,
                title : '修改参数',
                okBtnId : 'btn_sub'
            });

        } else {
            $.miaxisTools.alert('请选择需要修改的参数记录!');
        }
    }

    function newParam() {
        $.miaxisTools.openPopupWin({
            id  : 'w_sysparam',
            url    : 'sysparam.do?insertOrUpdate&operationType=insert',
            width  : 600,
            height : 200,
            title  : '新增参数',
            okBtnId : 'btn_sub'
        });
    }
    function delParam() {
        var row = $('#sysparamDg').datagrid('getSelected');
            if (row){
                $.miaxisTools.confirm('确定要删除当前参数吗?',function(r){
                    if (r){
                        var index = $('#sysparamDg').datagrid('getRowIndex',row);
                      
                        $.post('sysparam.do?del',{id:row.id}, function (data){
                            if (data.result=='0'){
                                $('#sysparamDg').datagrid('deleteRow',index);
                            }
                           $.miaxisTools.alert(data.message);
                        },'json');
                        
                    }
                });
            }else{
                $.miaxisTools.alert('请选择需要删除的参数记录!');
            }
    }
</script>
</body>
