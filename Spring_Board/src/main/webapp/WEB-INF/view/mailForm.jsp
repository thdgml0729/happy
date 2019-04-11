<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일 보내기 화면 </title>
</head>
<body>
	<%@ include file="/WEB-INF/view/boardTopMenu.jsp" %>
	<div id="container">
		<form action="./mailSending.do" method="post">
			<h2><img alt="메일" src="./image/mail.png">메일 보내기<img alt="메일" src="./image/mail.png"></h2>
			<div class="form-group">
				<label>받는사람</label>
				<input type="text" class="form-control" name="toMail" width="100%" placeholder="상대방 메일 주소 입력">
			</div>
			
			<div class="form-group">
				<label>메일제목</label>
				<input type="text" class="form-control" name="title"  width="100%" placeholder="제목을 입력">
			</div>
			<p>
			
			<div class="form-group">
				<label>메일 내용</label>
				<textarea name="content" class="form-control" rows="12" cols="120" width= 100% placeholder="메일 내용"></textarea>
			</div>
			<div>
				<input type="submit" value="메일 보내기">
			</div>
		</form>
	</div>
	
</body>
</html>