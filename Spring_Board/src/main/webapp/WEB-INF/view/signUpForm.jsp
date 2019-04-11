<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 화면</title>
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css" href="./css/SignUp.css">

<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>

</head>
<script type="text/javascript">
	function check(){
// 		alert("작동");
		//pw 확인, id중복 
		var pw = document.getElementById("pw").value;
		var passOK = document.getElementById("passOK").value;
// 		alert(pw+":"+passOK);

		var frm = document.forms[0]; // documetn.frm // <-name form의 name써놨을떄 탐색 방법
		
		var chk = document.getElementById("chaVal").value;
// 		alert(chk);
		
		if(pw!=passOK){
			swal("회원가입 오류 : ","비밀번호를 확인해주세요");
			return false;
		}else if(chk=="0"){
			swal("회원가입 오류 : ","아이디중복체크를 해주세요");
			return false;			
		}else{
			return true;
		}
	}
	
	//id중복 + 유효값 ajax
	$(document).ready(function(){
		$("#id").keyup(function(){
			var inputLength	 = $(this).val().length;// length는 메소드가 아니고 예약어 //$(this) 현재객체
// 			alert(inputLength);
			var id=$(this).val();
			// 공백 유효값 검사
			if(id.indexOf(" ")!=-1){ // -1은 ""으로 쓰면 안된다. 
// 				alert("공백 있음");
				$("#result").css("color", "red");
				$("#result").html("공백이 포함된 아이디는 사용이 불가능 합니다.");
				$("#chaVal").val("0"); // document.getElementById("chaVal").value= "0";
				
			}else if(inputLength> 5){
// 				alert("아작스 작동");
				jQuery.ajax({
					url : "./idCheck.do",
					type : "post",
					data: "id="+$(this).val(),
					async : true, //동기식
					success: function(msg){
// 						alert(msg); // 유효값 F12로 바꿀수 없다.
						$("#result").html(msg);
						var temp = msg;
						temp = temp.substring(0, temp.indexOf("디")+1);
						if(temp == "사용가능한 아이디"){
							$("#chaVal").val(1);
							$("#result").css("color", "blue");
						}else{
							$("#chaVal").val(1);
							$("#result").css("color", "red");
						}

					}
				});
			}else{
				$("#result").css("color", "red");
				$("#result").html("6자리 이상만 사용가능합니다. ");
				$("#chaVal").val("0"); // document.getElementById("chaVal").value= "0";
			}
		});//눌러서 나갈떄
		// 스트립트에서는 keypress
	});
	
</script>
<body>
<div id="container">
		<div id="title">
			<img id="TitleImage" src="./image/signuptitle.png">
		</div>
	
	
	<input type="hidden" value="0" id="chaVal">
	
	<form action="./signUp.do" method="post" name="frm" id="frm" onsubmit="return check()">
		<div id="info">
			<div id="LeftInfo">정보입력</div>
			<div id="CenterInfo">
				<input type="text" id="name" name="name" placeholder="이름" required="required">
				<br>
				<input type="text" id="id" name="id" placeholder="아이디" required="required">
				<br>
				<span id="result"></span>
				<br>
				<input type="password" id="pw" name="pw" placeholder="비밀번호" required="required">
				<br>
				<input type="password" id="passOK" required="required" placeholder="비밀번호 확인">
			</div>
			<div id="RightInfo">
					
			</div>
		</div>
		<div id="line"></div>
		<div id="bottom">
				<br>
				*만 14세 미망의 법정대리인 동의 후 회원 서비스를 이용가능합니다.
				<br>
				*선택 약관에 동의하지않아도 회원가입은 가능합니다. 
				<br>
				<strong id="bottomStrong">
				약관과 개인정보 수집 및 이용을 확인하였으며 이에 동의합니까?
				</strong>
				<br>
				
				<div id="button">
					<input class="btn btn-success" type="submit" value="동의하고 회원가입">
					<input class="btn btn-sm btn-primary" type="button" value="돌아가기" onclick="javascript:history.back(-1)">
					<!-- 뒤로가기 , javascript 써주는 것이 기본문법-->
					<!-- onclick="javascript:location.href='./test.do'"이런식으로 ""안에는 ''으로 써주어야한다.  -->
				</div>
				
		</div>
	</form>
</div>
</body>
</html>