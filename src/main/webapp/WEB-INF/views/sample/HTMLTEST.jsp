<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>HTML5 테스트입니다.</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="/resources/js/user.js"></script>
</head>
<body>
	<h1>HTML 이란?</h1>
	<h2>HTML 의 역사</h2>
	<h3>팀 버너스리에 대해</h3>
	<h4>HTML의 태그</h4>
	<h5>HTML의 태그</h5>
	<a href="/HTMLTEST">페이지 다시 로딩 -불러오기</a>
	<img src="/resources/images/happy.jpg" alt="행복한사진" width="700"
		height="900">
	<p>
		<Strong>이것은</Strong> 문단입니다.<br> <b>이것은</b> 문단입니다.
	</p>
	<p>이것은&nbsp;&nbsp;&nbsp;&nbsp; &amp; &lt; &gt; &copy &nbsp;특수문자입니다.


	
	<hr>
	<p>이것은 문단입니다.</p>
	<ul>
		<li>리스트1</li>
		<li>리스트2</li>
	</ul>
	<ol>
		<li>리스트1</li>
		<li>리스트2</li>
	</ol>
	<div>
		<h2>회원가입폼 입니다.</h2>
		<form method="get" action="/HTMLTEST" class="adminlogin_form">
			성별을 선택해 주세요 <select name="score">
				<option value="M">남자</option>
				<option value="F">여자</option>
			</select> <br> <input type="text" name="userid"
				placeholder="사용자 아이디를 입력해주세요"> <input type="file"
				name="upload_File">
			<textarea name="contents" cols="100" rows="5">여기에 글을 입력</textarea>
			<input type="submit" name="submit" value="회원가입">
		</form>
	</div>
	<br>
	<table summary="oo학교 oo반 성적표" style="border:1px solid black">
	<caption>이 테이블은 학생들의 성적표 입니다.</caption>
	<tr>
	<th>이름</th>
	<th>성적</th>
	</tr>
	<tr>
	<td>한혜성</td>
	<td>15</td>
	</tr>
	<tr>
	<td colspan="2">BBB</td>
	</tr>
	<tr>
	<td>CCC</td>
	<td>15</td>
	</tr>
	</table>
</body>
</html>