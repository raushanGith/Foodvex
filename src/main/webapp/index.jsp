<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Premium Food Delivery</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css?v=26">

</head>
<body>

	<!-- Include your dynamic navbar -->
	<jsp:include page="includes/navbar.jsp" />

	<%
	// Check if the user is already logged in to personalize the greeting
	User user = (User) session.getAttribute("loggedInUser");
	%>

	<div class="container hero-wrapper">
		<div class="neu-card hero-card">

			<%
			if (user != null) {
			%>
			<h1 class="hero-title">
				Welcome back,
				<%=user.getUsername()%>!
			</h1>
			<%
			} else {
			%>
			<h1 class="hero-title">Welcome to Foodvex</h1>
			<%
			}
			%>

			<p class="hero-subtitle">Experience the finest local cuisines
				delivered straight to your door. Fast, fresh, and meticulously
				crafted. What are you craving today?</p>

			<div class="hero-actions">
				<!-- Primary Call to Action -> Routes to RestaurantController -->
				<a href="restaurant" class="neu-btn-solid hero-btn-primary">Explore
					Restaurants 🍔</a>

				<%
				if (user == null) {
				%>
				<!-- Show login/register options if they aren't logged in -->
				<a href="login.jsp" class="neu-btn hero-btn-primary">Login to
					Order</a>
				<%
				}
				%>
			</div>

		</div>
	</div>

</body>
</html>