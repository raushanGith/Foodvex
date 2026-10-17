<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User, com.tap.model.Menu"%>
<%
User user = (User) session.getAttribute("loggedInUser");
if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
	response.sendRedirect("login.jsp");
	return;
}
Menu menu = (Menu) request.getAttribute("menu");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Edit Menu Item</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=25">
</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container admin-form-wrapper">
		<div class="neu-card admin-form-card">

			<div style="margin-bottom: 30px;">
				<a href="manageMenu"
					style="color: var(--primary-yellow); text-decoration: none; font-weight: bold; margin-bottom: 15px; display: inline-block;">←
					Back to Menu List</a>
				<h2 class="page-title" style="margin-bottom: 0; text-align: left;">
					Edit Item #<%=menu != null ? menu.getMenuId() : ""%></h2>
			</div>

			<form action="updateMenu" method="POST">
				<!-- Hidden ID so the server knows which item to update -->
				<input type="hidden" name="menuId"
					value="<%=menu != null ? menu.getMenuId() : ""%>">

				<div class="form-group">
					<label for="itemName">Item Name</label> <input type="text"
						id="itemName" name="itemName" class="neu-input" required
						value="<%=menu != null ? menu.getItemName() : ""%>">
				</div>

				<div class="form-group">
					<label for="price">Price (₹)</label> <input type="number"
						id="price" name="price" class="neu-input" step="0.01" min="0"
						required value="<%=menu != null ? menu.getPrice() : ""%>">
				</div>

				<div class="form-group">
					<label for="description">Description</label>
					<textarea id="description" name="description" class="neu-input"
						required><%=menu != null ? menu.getDescription() : ""%></textarea>
				</div>

				<div class="form-group">
					<label for="status">Availability Status</label> <select id="status"
						name="status" class="neu-input" style="cursor: pointer;">
						<option value="Yes"
							<%=(menu != null && menu.isAvailable()) ? "selected" : ""%>
							style="background: #1e1f26; color: white;">Available</option>
						<option value="No"
							<%=(menu != null && !menu.isAvailable()) ? "selected" : ""%>
							style="background: #1e1f26; color: white;">Out of Stock</option>
					</select>
				</div>

				<div style="display: flex; gap: 15px; margin-top: 35px;">
					<a href="manageMenu" class="neu-btn"
						style="flex: 1; text-align: center;">Cancel</a>
					<button type="submit" class="neu-btn-solid" style="flex: 2;">Update
						Item 💾</button>
				</div>
			</form>

		</div>
	</div>

</body>
</html>