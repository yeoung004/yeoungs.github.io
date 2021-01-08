<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<title>Bowling Score</title>
<link rel="stylesheet" href="/resources/css/bootstrap.css">
<link rel="shortcut icon" type="image/x-icon" href="https://img.icons8.com/fluent-systems-filled/2x/bowling.png">
</head>
<body>
	<div id="wrap">
		<h1>Welcome to BowlingGame</h1>
			<%
			for (int i = 0; i <= 10; i++) {
			%>
			<button class='btn btn-info' id="btn<%=i%>" value="<%=i%>"
				onclick="writeScore(<%=i%>);"><%=i%></button>
			<%
			}
			%>
			<button class="btn btn-danger" onclick="resetGame()">Reset</button>
			<button class="btn btn-warning" onclick="cancelframe()">Cancel</button>
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
					<tr>
						<%
						for (int i = 1; i <= 9; i++) {
						%>
						<td colspan = "2" id="resultBoard<%=i%>" width="150" height="50"></td>
						<%
						}
						%>
						<td colspan = "3" id="resultBoard10" width="150" height="50"></td>
					</tr>
				</tbody>
			</table>
	</div>
</body>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	var board = 1;
	var limit = 0;
	var score = 0;
	
	function cancelframe(){
		var preBoard;
		
		if(board == 1)
		{
			document.getElementById("scoreBoard1").innerHTML = "";
			return 0;
		}
		
		preBoard = Number(document.getElementById("scoreBoard" + (board-1)).innerHTML);
		if(preBoard == "")
			board--;
		board--;
		document.getElementById("scoreBoard" + board).innerHTML = "";
			
	}
	
	function resetGame(){
		board = 1;
		limit = 0;
		
		for (var i = 1; i <= 21; i++) {
			document.getElementById("scoreBoard" + i).innerHTML = "";
		}
	}
	
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
			score += btnNumber;
			bowlingScore(score);
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
					score += btnNumber;
					bowlingScore(score);
					if((limit + btnNumber) < 10)
						board++;	
					board++;
				}
 		}else if(board == 21){
 			frame_20 = Number(document.getElementById("scoreBoard20").innerHTML);
 			frame_19 = Number(document.getElementById("scoreBoard19").innerHTML);
 			
			if((frame_20 + frame_19) < 10){
				alert("경기끝났습니다.");
				board++;
				return 0;	
 			}else if((frame_20 + frame_19) == 20 || (frame_20 + frame_19) == 10 || (frame_20 + btnNumber) <= 10){
 				score += btnNumber;
 				bowlingScore(score);
 				document.getElementById("scoreBoard"+board).innerHTML = btnNumber;
				board++;
			}else{
				alert("잘못입력하셨습니다.");
				return 0;
			}
		}else
			alert("경기끝났습니다.");
	}
	
	function bowlingScore(score){
		 $.ajax({
	         url:'/score',
	         data:{ "socre" : score},
	         methoed:"get",
	         dataType: "text",
			 
	         success: function(data){
	        	 
	         }
	     })
	}
</script>
<script type="text/javascript" src="/resources/js/bootstrap.js"></script>
</html>
