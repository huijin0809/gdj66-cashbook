<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<!-- 부트스트랩5 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<style>
	a {
		text-decoration: none;
	}
</style>
</head>
<body>
	<c:set var="m" value="${resultMap}"></c:set> <!-- model값 받기 -->
	<div class="container">
		<div class="text-center">
			<h3>
				<a href="/cash/calendar?targetYear=${m.targetYear}&targetMonth=${m.targetMonth-1}" class="btn btn-outline-dark btn-sm">
					이전달
				</a>
				${m.memberId}님의 ${m.targetYear}년 ${m.targetMonth + 1}월 달력
				<a href="/cash/calendar?targetYear=${m.targetYear}&targetMonth=${m.targetMonth + 1}" class="btn btn-outline-dark btn-sm">
					다음달
				</a>
			</h3>
		</div>
		<table class="table">
			<thead class="table-dark">
				<tr>
					<th>일</th>
					<th>월</th>
					<th>화</th>
					<th>수</th>
					<th>목</th>
					<th>금</th>
					<th>토</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<!-- end는 해당 값까지 포함해서 반복하기 때문에 -1 해주어야함! -->
					<c:forEach var="i" begin="0" end="${m.totalTd - 1}" step="1">
						<c:if test="${i != 0 && i % 7 == 0}">
							</tr><tr>
						</c:if>
						<c:set var="day" value="${i - m.beginBlank + 1}"></c:set> <!-- 변수 선언 -->
						<!-- choose, when, otherwise를 사용하면 if-else문처럼 작성 가능 -->
						<c:choose>
							<c:when test="${day > 0 && day <= m.lastDate}">
								<td>
									<c:choose>
										<c:when test="${i % 7 == 0}">
											<div style="color:red;">${day}</div>
										</c:when>
										<c:when test="${i % 7 == 6}">
											<div style="color:blue;">${day}</div>
										</c:when>
										<c:otherwise>
											<div>${day}</div>
										</c:otherwise>
									</c:choose>
										
									<c:forEach var="c" items="${m.cashbookList}">
										<a href="/cashbookListByDay?cashbookDate=${c.cashbookDate}"> <!-- 클릭시 해당 날짜 상세보기 예정.. -->
											<c:if test="${day == fn:substring(c.cashbookDate,8,10)}">
												<div>
													<c:if test="${c.category == '수입'}">
														<span style="color:green">+${c.price}</span>
													</c:if>
													<c:if test="${c.category == '지출'}">
														<span style="color:orange">-${c.price}</span>
													</c:if>
												</div>
											</c:if>
										</a>
									</c:forEach>
								</td>
							</c:when>
							
							<c:when test="${day < 1}">
								<td style="color:gray">${m.preEndDate + day}</td>
							</c:when>
							
							<c:otherwise>
								<td style="color:gray">${day - m.lastDate}</td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>