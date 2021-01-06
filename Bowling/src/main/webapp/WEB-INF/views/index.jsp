<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<title>Bowling Score</title>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
</head>
<body>
	<div id="wrap">
		<h1>Welcome to BowlingGame</h1>
		<form action="#">
			<%
			for (int i = 0; i <= 10; i++) {
			%>
			<button class='btn btn-info' id="btn<%=i%>" value="<%=i%>"
				onclick="writeScore(<%=i%>);"><%=i%></button>
			<%
			}
			%>
			<table border="1">
				<thead>
					<tr>
						<%
						for (int i = 1; i <= 9; i++) {
						%>
						<th colspan="2"><%=i%></th>
						<%
						}
						%>
						<th colspan="3">10</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<%
						for (int i = 1; i <= 20; i++) {
						%>
						<td id="scoreBoard<%=i%>" width="150" height="50"></td>
						<%
						}
						%>
						<td id="scoreBoard21" width="150" height="50"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	var board = 1;
	var limit
	
	function writeScore(btnNumber) {
		if(board < 20){
			if((board % 2) == 0){
				limit = Number(document.getElementById("scoreBoard" + (board-1)).innerHTML);
				if((limit + btnNumber) > 10)
				{
					alert("잘못입력하셨습니다.");
					return 0;
				}
			}
			
			document.getElementById("scoreBoard"+board).innerHTML = btnNumber;
			if(btnNumber == 10 && board != 19)
				board++;
			board++;
 		}else if(board == 20){
				limit = Number(document.getElementById("scoreBoard19").innerHTML);
				if(limit != 10 && (limit + btnNumber) > 10){
					alert("잘못입력하셨습니다.");
					return 0;
				}else{
					document.getElementById("scoreBoard"+board).innerHTML = btnNumber;
					if((limit + btnNumber) < 10)
						board++;	
					board++;
				}
 		}else if(board == 21){
 			limit = Number(document.getElementById("scoreBoard20"));
			if(limit != 10 && (limit + btnNumber) > 10){
				alert("경기끝났습니다.");
				board++;
				return 0;	
			}else{
				document.getElementById("scoreBoard"+board).innerHTML = btnNumber;
				board++;
			}
		}else
			alert("경기끝났습니다.");
	}
</script>
<script type="text/javascript" src="/resources/js/bootstrap.js"></script>
</html>
