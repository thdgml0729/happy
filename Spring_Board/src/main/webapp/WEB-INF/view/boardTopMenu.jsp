<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- boardTopMenu를 include하기 때문에 지워준다 -->
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="./css/bootstrap-theme.min.css">

<script type="text/javascript" src="./js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="./js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/sweetalert.min.js"></script>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
				data-target="#navbar--1">
				<span class="sr-only"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"><img width="30" height="25" src="./image/darly.png"></a>
		</div><!-- class="navbar-header" -->
		<div class="collapse navbar-collapse" id="navbar--1">
			<ul class="nav navbar-nav navbar-left">
				<c:if test="${fn:trim(mem.auth) eq 'U'}">
					<li id="CompanyInfo">
						<a href="./boardWriteForm.do">글작성
							<span class="sr-only"></span>
						</a>
					</li>
				</c:if>
				<li id="boardlist"><a href="./boardList.do">게시판</a></li>
				<c:if test="${fn:trim(mem.auth) eq 'A'}">
					<li id="MemberList"><a href="./memberList.do">회원게시판</a></li>
				</c:if>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li id="navSignUp"><a href="#">${mem.id}님 환영합니다.</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li id="navSignLogOut"><a href="./logOut.do">logout</a></li>
			</ul>
		</div><!-- class="collapse navbar-collapse" -->
	</div><!-- class="container-fluid" -->
</nav>