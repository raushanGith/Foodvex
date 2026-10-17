<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User, com.tap.model.Menu, java.util.List"%>
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
<title>Foodvex - Manage Menu</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=23">

</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container dashboard-wrapper">

		<div class="admin-page-header">
			<div>
				<a href="adminDashboard.jsp"
					style="color: var(--primary-yellow); text-decoration: none; font-weight: bold; margin-bottom: 10px; display: inline-block;">←
					Back to Dashboard</a>
				<h2 class="page-title" style="margin-bottom: 0; text-align: left;">Manage
					Menu Items</h2>
			</div>
			<!-- Button to trigger an Add Menu Item page or modal -->
			<a href="addMenu.jsp" class="neu-btn-solid">➕ Add New Item</a>
		</div>

		<div class="neu-card">
			<div class="admin-table-wrapper">
				<table class="admin-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>Item Name</th>
							<th>Description</th>
							<th>Price</th>
							<th>Status</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<%
						// Retrieve the menu list sent from your AdminMenuController
						List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");

						if (menuList != null && !menuList.isEmpty()) {
							for (Menu menu : menuList) {
						%>
						<tr>
							<td>#<%=menu.getMenuId()%></td>
							<td style="font-weight: bold;"><%=menu.getItemName()%></td>
							<td
								style="color: var(--text-muted); max-width: 250px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
								<%=menu.getDescription() != null ? menu.getDescription() : "No description"%>
							</td>
							<td style="color: var(--primary-yellow); font-weight: bold;">₹<%=menu.getPrice()%></td>
							<td>
								<!-- Example toggle based on an 'isAvailable' boolean from your model -->
								<span class="status-badge status-active">Available</span>
							</td>
							<td>
								<div class="action-icons">
									<a href="editMenu?id=<%=menu.getMenuId()%>"
										class="action-btn-icon" title="Edit">✏️</a> <a
										href="deleteMenu?id=<%=menu.getMenuId()%>"
										class="action-btn-icon delete" title="Delete"
										onclick="return confirm('Are you sure you want to delete this item?');">🗑️</a>
								</div>
							</td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="6"
								style="text-align: center; padding: 40px; color: var(--text-muted);">
								No menu items found. Click "Add New Item" to start building your
								menu!</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>

	</div>

</body>
</html>