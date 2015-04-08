<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<style>
.radio {
	width: 100px;
	height: 18px;
	border:solid 0px; 
}
</style>
<body>
	<div class="easyui-layout" style="width: 100%; height: 450px;"
		fit="true">
		<div data-options="region:'center'" border="false">
			<form id="jobgrade_form" method="post" novalidate>
				<table class="doc-table">
					<tr>
						<th width="54" align="center" height="24">序号</th>
						<th width="132" align="center">调查内容</th>
						<th width="104" align="center">满意(10分)</th>
						<th width="113" align="center">较满意(8分)</th>
						<th width="118" align="center">一般(6分)</th>
						<th width="104" align="center">不满意(4分)</th>
					</tr>
					<tr>
						<td width="54" align="center" height="22">1</td>
						<td width="132">服务及时性</td>
						<td width="60" align="center">
						   <input name="s1" type="radio" value="10" class="radio"
							<c:if test="${s1 ==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s1" value="8" class="radio"
							<c:if test="${s1 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s1" value="6" class="radio"
							<c:if test="${s1 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s1" value="4" class="radio"
							<c:if test="${s1 ==4 }">
								checked
							</c:if>/>
						</td>
					</tr>
					<tr>
						<td align="center" height="22">2</td>
						<td>服务态度</td>
						<td width="60" align="center">
						   <input name="s2" type="radio" value="10" class="radio"
							<c:if test="${s2 ==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s2" value="8" class="radio"
							<c:if test="${s2 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s2" value="6" class="radio"
							<c:if test="${s2 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s2" value="4" class="radio"
							<c:if test="${s2 ==4 }">
								checked
							</c:if>/>
						</td>

					</tr>
					<tr>
						<td align="center" height="22">3</td>
						<td>专业素质</td>
						<td width="60" align="center">
						   <input name="s3" type="radio" value="10" class="radio"
							<c:if test="${s3 ==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s3" value="8" class="radio"
							<c:if test="${s3 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s3" value="6" class="radio"
							<c:if test="${s3 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s3" value="4" class="radio"
							<c:if test="${s3 ==4 }">
								checked
							</c:if>/>
						</td>
					</tr>
					<tr>
						<td align="center" height="22">4</td>
						<td>故障修复情况</td>
						<td width="60" align="center">
						   <input name="s4" type="radio" value="10" class="radio"
							<c:if test="${s4 ==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s4" value="8" class="radio"
							<c:if test="${s4 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s4" value="6" class="radio"
							<c:if test="${s4 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s4" value="4" class="radio"
							<c:if test="${s4 ==4 }">
								checked
							</c:if>/>
						</td>

					</tr>
					<tr>
						<td align="center" height="22">5</td>
						<td>应急能力</td>
						<td width="60" align="center">
						   <input name="s5" type="radio" value="10" class="radio"
							<c:if test="${s5 ==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s5" value="8" class="radio"
							<c:if test="${s5 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s5" value="6" class="radio"
							<c:if test="${s5 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s5" value="4" class="radio"
							<c:if test="${s5 ==4 }">
								checked
							</c:if>/>
						</td>

					</tr>
					<tr>
						<td align="center" height="22">6</td>
						<td>技术水平</td>
						<td width="60" align="center">
						   <input name="s6" type="radio" value="10" class="radio"
							<c:if test="${s6 ==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s6" value="8" class="radio"
							<c:if test="${s6 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s6" value="6" class="radio"
							<c:if test="${s6 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s6" value="4" class="radio"
							<c:if test="${s6 ==4 }">
								checked
							</c:if>/>
						</td>

					</tr>
					<tr>
						<td align="center" height="22">7</td>
						<td>巡检情况</td>
						<td width="60" align="center">
						   <input name="s7" type="radio" value="10" class="radio"
							<c:if test="${s7 ==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s7" value="8" class="radio"
							<c:if test="${s7 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s7" value="6" class="radio"
							<c:if test="${s7 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s7" value="4" class="radio"
							<c:if test="${s7 ==4 }">
								checked
							</c:if>/>
						</td>
					</tr>
					<tr>
						<td align="center" height="22">8</td>
						<td>服务沟通情况</td>
						<td width="60" align="center">
						   <input name="s8" type="radio" value="10" class="radio"
							<c:if test="${s8 ==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s8" value="8" class="radio"
							<c:if test="${s8 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s8" value="6" class="radio"
							<c:if test="${s8 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s8" value="4" class="radio"
							<c:if test="${s8 ==4 }">
								checked
							</c:if>/>
						</td>

					</tr>
					<tr>
						<td align="center" height="22">9</td>
						<td>培训辅导情况</td>
						<td width="60" align="center">
						   <input name="s9" type="radio" value="10" class="radio"
							<c:if test="${s9 ==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s9" value="8" class="radio"
							<c:if test="${s9 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s9" value="6" class="radio"
							<c:if test="${s9 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s9" value="4" class="radio"
							<c:if test="${s9 ==4 }">
								checked
							</c:if>/>
						</td>

					</tr>
					<tr>
						<td align="center" height="22">10</td>
						<td>整体服务质量</td>
						<td width="60" align="center">
						   <input name="s10" type="radio" value="10" class="radio"
							<c:if test="${s10==10 }">
								checked
							</c:if>/>
						</td>

						<td width="60" align="center">
							<input type="radio" name="s10" value="8" class="radio"
							<c:if test="${s10 ==8 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s10" value="6" class="radio"
							<c:if test="${s10 ==6 }">
								checked
							</c:if>/>
						</td>
						<td width="60" align="center">
							<input type="radio" name="s10" value="4" class="radio"
							<c:if test="${s10 ==4 }">
								checked
							</c:if>/>
						</td>

					</tr>
					<tr>
						<td width="187" colspan="2" height="22">综合得分</td>
						<td width="439" colspan="4" align="center"><b><span
								id="score">${score}</span></b></td>
					</tr>
					<tr>
						<td width="187" colspan="2" height="22">综合评价</td>
						<td width="439" colspan="4"><textarea id="describe"
								name="describe" class="easyui-validatebox"
								style="width: 400px;height:35px">${describe}</textarea></td>
					</tr>
					<tr>
						<td width="187" colspan="2" height="22">意见与建议</td>
						<td width="439" colspan="4"><textarea id="suggest"
								name="suggest" class="easyui-validatebox"
								style="width: 400px;height:35px">${suggest}</textarea></td>
					</tr>
				</table>
				<input id="jobId" name="jobId" type="hidden" value="${jobId}" />
			</form>
		</div>
		<input id="masterId" name="masterId" type="hidden" value="${masterId}" />
		<input type="hidden" id="btn_sub">
	</div>

	<script type="text/javascript">
		var api = frameElement.api, W = api.opener;

		$().ready(function() {
			if ($("#masterId").val() != '') {
				$(".radio").attr('disabled','false');
				$("#describe").attr('disabled','false');
				$("#suggest").attr('disabled','false');
				$("#describe").attr("style","border:0;border-bottom:1 solid black;background:white;");
				$("#suggest").attr("style","border:0;border-bottom:1 solid black;background:white;");
			}
			
		});
		
		var _score;
		$('.radio').on('click',function(){
			_score=0;
			
			$(".radio").each(function(){
				 if($(this).attr("checked")=='checked'){
					 var tmp = $(this).val();
					 _score = (_score*1)+(tmp*1) ; 
				 }
			});
			$("#score").html(_score);
		});

		$("#btn_sub").click(function() {
			var _jobId =$("#jobId").val();
			if ($("#masterId").val() == '') {
				var params = {
					jobId : _jobId
				};
				$.miaxisTools.ajaxSubmitForm('jobDispatch.do?gradeSubmit', 'jobgrade_form',params);
			} else {
				$.miaxisTools.alert("该派工单已经评价过，不能重复评价!");
				return;
			}
		});
	</script>
</body>
