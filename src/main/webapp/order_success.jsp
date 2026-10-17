<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Order Success</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=28">

</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container success-wrapper">
		<div class="neu-card success-card">

			<div class="success-icon">✓</div>

			<div class="success-title">Order Placed Successfully!</div>

			<div class="success-message">Thank you for choosing Foodvex.
				Your delicious food is currently being prepared by the restaurant
				and will be out for delivery shortly.</div>

			<div class="success-action-buttons">
				<!-- Primary glowing button to check orders -->
				<a href="orders.jsp" class="neu-btn-solid">Track My Orders 🚀</a>

				<!-- CLEANED: External CSS class applied here -->
				<a href="restaurant" class="neu-btn text-warning">Return to Home</a>
			</div>

		</div>
	</div>

</body>
</html>