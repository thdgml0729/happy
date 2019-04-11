<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<!-- link는 이렇게쓰는것이 표준  -->
<!-- 유일하게 link만 href로 붙인다.  -->
<!-- jquery는 제일 위에 올리는 것이 좋다.  -->
<link rel="stylesheet" type="text/css" href="./css/Login.css"><!--  type은 번역되는 글자, rel은 내가가지고 있는 타입  -->
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<!-- 로드가되면 uri다음에 jsp,화면은 containerㅔ 있다. , uri까지가 현재위치  -->
<!-- web-inf(servlet을 타서 로딩을 해주는 화면)와 webapp는 아예 다른 영역이라서 폴더로 인식하지 않는다.  -->
<!-- (URI)/WEB-INF/view/intro.jsp일떄 ....으로 상위폴더로 올라갈수없다.  -->
</head>
<script type="text/javascript">
	function loginCheck(){
		var id = document.getElementById("inputId").value;
		var pw = document.getElementById("inputPw").value;
// 		alert(id+":"+pw);
		var frm = document.forms[0]; // ==document.getElementById("frm");
		frm.action = "./login.do";
		var result = "";
		if(id==null || id==""){
			swal("로그인", "아이디를 확인해 주세요");
		}else if(pw==null || pw==""){
			swal("로그인", "비밀번호를 확인해 주세요");
		}else{
			jQuery.ajax({
				url : "./loginCheck.do",
				type : "post", // Controller의 method와 같게 해주어야 한다.
				data : "id="+id+"&pw="+pw,
				success: function(msg){
// 					alert(msg);
					//어디에 담겨있는 것이 아니라 msg자체가 값입니다
					var temp1="";
					var temp2="";
					
					var temp1 = msg;
					var temp2 = msg;
					
					temp1 = temp1.slice(0, 2);
					if(temp1 == "성공"){
// 						alert("if문 들어옴"); // 성공이라는 글자만 뜬다. 
						temp2 = temp2.split("/")[1]; // "/"의 토큰을 기준으로 자르고 0, 1번의 문자열중 1번쨰의 문자열을 가지고 온다. = auth
						document.getElementById("loginChk").value = temp2;
						frm.submit();
						
					}else {
						swal("로그인 실패", "아이디나 비밀번호를 확인해 주세요");
					}
				
				}
			}); // $.ajax();
		}
	}
	function signUp(){
		location.href="./signUpForm.do";
	}
	function idSearch(){
		swal("아이디 찾기", "문이슬이 준비중입니다.");
	}
	function pwSearch(){
		swal("비밀번호 찾기", "송희 송희 준비중 입니다.");
	}
</script>
<body>
	<div id="container">
		<div id="title">Board Login</div><!-- 영역을 잡을떄는 묶고해야한다.  -->
		<div id="id">아이디</div>
		<form method="post" id="frm">
			<input type="hidden" id="loginChk" name="auth" value="0">
			<input type="text" name="id" id="inputId" placeholder="숫자- 대소문자영문" required="required">
			
			<div id="pw">비밀번호</div>
			<input type="password" name="pw" id="inputPw" placeholder="비밀번호 입력" required="required">
			<br>
			
			<input type="button" id="login" name="login" value="로그인" onclick="loginCheck()">
			
			<div id="bottom">
				<a href="#" onclick="signUp()">
					<input  id="SignUp" type="button" name="signup" value="회원가입">
				</a>
				<input id="SearchId" type="button" value="아이디찾기" onclick="idSearch()">
				<input id="SearchPw" type="button" value="비밀번호찾기" onclick="pwSearch()">
			</div>
			
		</form>
	</div>
</body>
</html>