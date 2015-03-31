<%@ page pageEncoding="UTF-8"%>
<style>
<!--
.leftHand{
	width:160px;
	padding-left:0px;
	float:left;
}
.rightHand{
	float:right;
	padding-right:0px;

}
.handchx{
	float:left; width:31px;
}.handchx1{
	float:right; width:31px;
}
.handimg{
	border:none;
	clear: both;
	width:364px;
}
.handimg img{ margin:0 0 0 0 ;
}
.hand{
	
}
-->
</style>
	<input type="hidden" name="finger0" value="${finger0}">
	<input type="hidden" name="finger1" value="${finger1}">
	<input type="hidden" name="finger2" value="${finger2}">
	<input type="hidden" name="finger3" value="${finger3}">
	<input type="hidden" name="finger4" value="${finger4}">
	<input type="hidden" name="finger5" value="${finger5}">
	<input type="hidden" name="finger6" value="${finger6}">
	<input type="hidden" name="finger7" value="${finger7}">
	<input type="hidden" name="finger8" value="${finger8}">
	<input type="hidden" name="finger9" value="${finger9}">
	
<div class="clear"></div>
<div  class="hand" style="text-align:center; border:0px solid #dcdcdc; height:230px; width:380px; margin:0 auto;">
  <span class="leftHand">
  	<c:forEach var="i" begin="0" end="4">
	  <span class="handchx">
	  	<c:if test="${i=='0' || i=='1' || i == '2' || i == '3'  }">
	  		<input type="checkbox" name="fingerCode" value="${i}" disabled/>
	  	</c:if>
		<c:if test="${i!='0' && i!='1' && i != '2' && i != '3' }">
	  		<input type="checkbox" name="fingerCode" value="${i}"/>
	  	</c:if>
	  </span>
	</c:forEach>
  </span>
  <span class="rightHand">
  		<div>
  		 <c:forEach var="i" begin="5" end="9">
		 <span class="handchx1">
		 	<c:if test="${(14-i)=='7' || (14-i)=='8' || (14-i) == '9' || (14-i) == '6' }">
				<input type="checkbox" name="fingerCode" value="${14-i}" disabled/>
			</c:if>
			<c:if test="${(14-i) !='7' && (14-i) !='8' && (14-i) != '9' && (14-i) != '6'}">
				<input type="checkbox" name="fingerCode" value="${14-i}"/>
			</c:if>
		 </span>
		</c:forEach>
		</div>
  </span>
  <div class="handimg">
	  <img
		src="${pageContext.request.contextPath}/images/hand.gif"  width="364" height="197"></img>
 </div>
</div>
<script>
	
</script>