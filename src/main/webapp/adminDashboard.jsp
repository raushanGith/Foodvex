<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User"%>
<%
// Security Check: Ensure the user is logged in AND is an Admin
User user = (User) session.getAttribute("loggedInUser");
if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
	response.sendRedirect("login.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Admin Dashboard</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css?v=22">

</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container dashboard-wrapper">

		<div class="dashboard-header">
			<div>
				<h2 class="page-title" style="margin-bottom: 5px; text-align: left;">Restaurant
					Dashboard</h2>
				<div style="color: var(--text-muted); font-size: 1.1rem;">
					Welcome back,
					<%=user.getUsername()%></div>
			</div>
			<a href="profile.jsp" class="neu-btn">View My Profile</a>
		</div>

		<!-- Dashboard Statistics -->
		<div class="stats-grid">
			<div class="neu-card stat-card">
				<div class="stat-number">124</div>
				<div class="stat-label">Total Orders</div>
			</div>
			<div class="neu-card stat-card">
				<div class="stat-number">45</div>
				<div class="stat-label">Menu Items</div>
			</div>
			<div class="neu-card stat-card">
				<div class="stat-number">₹8.5k</div>
				<div class="stat-label">Revenue Today</div>
			</div>
			<div class="neu-card stat-card">
				<div class="stat-number"
					style="color: #48c479; text-shadow: 0 0 15px rgba(72, 196, 121, 0.3);">12</div>
				<div class="stat-label">Active Deliveries</div>
			</div>
		</div>

		<!-- Admin Action Panels -->
		<div class="admin-actions-grid">

			<!-- Manage Menu Items -->
			<div class="neu-card admin-action-card">
				<div class="admin-action-icon">📋</div>
				<div class="admin-action-title">Manage Menu</div>
				<div class="admin-action-desc">Add new dishes, update prices,
					or remove unavailable items from your restaurant.</div>
				<a href="manageMenu" class="neu-btn-solid" style="width: 100%;">Edit
					Menu Items</a>
			</div>

			<!-- Manage Orders -->
			<div class="neu-card admin-action-card">
				<div class="admin-action-icon">🛵</div>
				<div class="admin-action-title">Manage Orders</div>
				<div class="admin-action-desc">View incoming customer orders,
					update delivery statuses, and track history.</div>
				<a href="manageOrders" class="neu-btn-solid"
					style="width: 100%;">View All Orders</a>
			</div>

		</div>

	</div>

</body>
</html>