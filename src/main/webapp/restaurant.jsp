<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.tap.model.Restaurant"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Restaurants</title>

<!-- Forcing browser to load the new CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=4">
</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container">

		<!-- Title moved to the left -->
		<h2
			style="margin-top: 0; margin-bottom: 35px; font-weight: 800; letter-spacing: 0.5px; text-align: left;">
			Explore Restaurants</h2>

		<div class="restaurant-grid">
			<%
			List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
			if (restaurants != null && !restaurants.isEmpty()) {
				for (Restaurant restaurant : restaurants) {
			%>

			<div class="neu-card restaurant-card">
				<img src="<%=restaurant.getImagePath()%>"
					alt="<%=restaurant.getName()%>" class="restaurant-image">

				<div class="restaurant-details">
					<div class="restaurant-header">
						<h2 class="restaurant-name" title="<%=restaurant.getName()%>"><%=restaurant.getName()%></h2>
						<span class="restaurant-rating">★ <%=restaurant.getRating()%></span>
					</div>

					<div class="restaurant-cuisine"
						title="<%=restaurant.getCuisineType()%>"><%=restaurant.getCuisineType()%></div>

					<div class="restaurant-meta">
						<span class="meta-time">🕒 <%=restaurant.getDeliveryTime()%>
							mins
						</span> <span class="meta-address" title="<%=restaurant.getAddress()%>">📍
							<%=restaurant.getAddress()%></span>
					</div>

					<a href="menu?restaurantId=<%=restaurant.getRestaurantId()%>"
						class="neu-btn-solid view-menu-btn">View Menu</a>
				</div>
			</div>

			<%
			}
			} else {
			%>
			<div class="no-data">
				<h2>No restaurants found.</h2>
				<p>Try clearing your search and looking for something else!</p>
			</div>
			<%
			}
			%>
		</div>
	</div>

</body>
</html>