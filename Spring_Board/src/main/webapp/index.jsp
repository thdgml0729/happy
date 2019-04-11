<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>
	<a href="./loginForm.do">로그인 폼이동</a>
</h1>
	<a href="./signUp.do?id=happy1&name=happy&pw=happy">회원가입</a>
	<a href="./idCheck.do?id=happy">아이디 확인</a>
	<!-- id에 특수문자가 들어가면 false가 뜨고, 들어가지 않으면 true가 뜹니다.  -->
	<a href="./login.do?id=TEST001&pw=TEST001">로그인</a>
	<a href="./memberList.do">회원리스트</a>
	
	<hr>
	
	<a href="./writeBoard.do?id=gold99&title=새글&content=new글">root글 작성하기</a>
	<a href="./reply.do?seq=23&id=happy&title=행복의 답글&content=영재오빠는 군대..">답글작성</a>
	<a href="./getOneBoard.do?seq=19">상세글</a>
	<a href="./readcountBoard.do?seq=19">조회수증가</a>
	<a href="./modifyBoard.do?seq=19&title=군대&content=영재는 군 복무중">수정</a>
	<a href="./delflagBoard.do?seq=21">delflag</a>
	<a href="./deleteBoardSel.do?seq=21">삭제선택</a>
	<a href="./userboardlisttotal.do">사용자 갯수조회</a>
	<a href="./adminboardlisttotal.do">관리자 갯수 조회</a>
	<a href="./userBoardListRow.do">사용자 글 갯수</a>
	<a href="./adminboardListRow.do">관리자 글 갯수</a>
</body>
</html>