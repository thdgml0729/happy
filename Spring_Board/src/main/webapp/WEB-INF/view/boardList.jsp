<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/BoardList.css">
<script type="text/javascript" src="./js/boardList.js">

</script>
</head>
<body>
<%@ include file="/WEB-INF/view/boardTopMenu.jsp" %><!-- 파일은 물리적인 위치를 가지고와야 해서 ./로 쓰는 것이 아니다 -->
<%-- ${lists}<br> --%>
<%-- ${row} --%>

<div id="container">
	<!-- 게시판 폼 -->
	<div id="select">
		<span>
			<select class="btn btn-primary" id="list" name="list" onchange="pageList()">
				<option value="5">5</option>
				<option value="10">10</option>
				<option value="15">15</option>
				<option value="20">20</option>
			</select>
		</span>
	</div>
	
	<form action="#" method="post" id="frm" onsubmit="return chkBox()">
		<div>
			<input class="btn btn-danger" type="submit" value="다중삭제">
		</div>
		<div class="panel-group" id="accordion">
			<table id="table" class="table table-borderer">
				<tr>	
					<th><input type="checkbox" onclick="checkAllDel(this.checked)"></th>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>조회수</th>
					<c:if test="${fn:trim(mem.auth) eq 'A'}">
						<th>삭제여부</th>
					</c:if>
					<th>작성일</th>
				</tr>
				<jsp:useBean id="format" class="com.happy.prj.beans.InputList" scope="page"/><!-- 해당 페이지에서만쓰겠다. session, requset, page등 -->
				<jsp:setProperty property="lists" name="format" value="${lists}"/>
				<jsp:setProperty property="mem" name="format" value="${mem}"/>
				<jsp:getProperty property="listformat" name="format"/>
<!-- 				<tr> -->				
<%-- 					<td><input type="checkbox" name="chkVal" value="${lists[0].seq}"></td> --%>
<%-- 					<td>${lists[0].seq}</td> --%>
<%-- 					<td>${lists[0].title}</td> --%>
<%-- 					<td>${lists[0].id}</td> --%>
<%-- 					<td>${lists[0].readcount}</td> --%>
<!-- 				</tr> -->
			</table> 
		</div>
		
		<!-- 현재 페이지, 인덱스, 출력갯수 -->
		${row.index}
		${row.pageNum}
		${row.listNum}
		${row.count}
		<input type="hidden" name="index" id="index" value="${row.index}">
		<input type="hidden" name="pageNum" id="pageNum" value="${row.pageNum}">
		<input type="hidden" name="listNum" id="listNum" value="${row.listNum}">
		
		<div class="center">
			<ul class="pagination">
				<li><a href="#" onclick="pageFrist(${row.pageList},${row.pageList})">&laquo;</a></li>
				<li><a href="#" onclick="pagePre(${row.pageNum},${row.pageList})">&lsaquo;</a></li>
				<c:forEach var="i" begin="${row.pageNum}" end="${row.count}" step="1">
					<li><a href="#" onclick="pageIndex(${i})">${i}</a></li>
				</c:forEach>
				<li><a href="#" onclick="pageNext(${row.pageNum},${row.total},${row.listNum} ,${row.pageList})">&rsaquo;</a></li>
				<li><a href="#" onclick="pageLast(${row.pageNum},${row.total},${row.listNum} ,${row.pageList})">&raquo;</a></li>
			</ul>
		</div>
		
	</form>
	<!-- 수정폼 -->
  <!-- Modal -->
  <div class="modal fade" id="modify" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">게시글 수정</h4>
        </div>
        <div class="modal-body">
          <form action="#" role="form" method="post" id="frmModify"></form>
        </div>
        
      </div>
      
    </div>
  </div><!-- 수정 modal -->
  
  <!-- 답글쓰기 form -->
  <div id="reply" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">답글쓰기</h4>
      </div>
      <div class="modal-body">
        <form role="form" action="#" method="post" id="frmReply"></form>
      </div>
      
    </div>

  </div>
</div>

	<div>
<!-- 		<input type="hidden" name="seq" value="값값"> -->
<!-- 		<div class="form-group">form-group안에 form-controller있어야한다.  -->
<!-- 			<label>답글할 글번호</label><br> -->
<!-- 			<p class="form-control"></p> -->
<!-- 		</div> -->
<!-- 		<div class="form-group"> -->
<!-- 			<label>답글할 글 조회수</label> -->
<!-- 			<p class="form-control">값값</p> -->
<!-- 		</div> -->
<!-- 		<div class="form-group"> -->
<!-- 			<label>답글할 글 작성일</label> -->
<!-- 			<p class="form-control">값값</p> -->
<!-- 		</div> -->
<!-- 		<div class="form-group"> -->
<!-- 			<label>답글할 글 작성자</label> -->
<!-- 			<p class="form-control">값값</p> -->
<!-- 		</div> -->
<!-- 		<div class="form-group"> -->
<!-- 			<label id="titl">제목(원본)</label>탐색시에 label은 너무많아서 id로 준다. -->
<!-- 			<input type="text" class="form-control" id="title" name="title" value="값" required="required" onclick="chk"> -->
<!-- 		</div> -->
<!-- 		<div class="form-group"> -->
<!-- 			<label id="con">내용(원본)</label> -->
<!-- 			<input type="hidden" id="hiddenContent" value="값값"> -->
<!-- 			<textarea id="txtArea" rows="7" class="form-control" name="content">값값</textarea> -->
<!-- 		</div> -->
<!-- 		<div class="modal-footer"> -->
<!-- 			<input type="button" class="btn btn-info" value="답글작성" onclick="replied()"> -->
<!-- 			<input type="reset" class="btn btn-info"> -->
<!-- 			<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button> -->
		</div>	
		
		
	</div>

</div>
<script type="text/javascript">
	$(document).ready(function(){

		var listNum=$("#listNum").val();
// 		alert(listsNum);
		$("#list option").each(function(){ // for문
			if(listNum==$(this).val()){
				$(this).prop("selected",true);
			}else{
				$(this).prop("selected",false);
			}
		});
	});
</script>
</body>
</html>