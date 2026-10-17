<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.tap.model.User, com.tap.model.Order, java.util.List"%>
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
<title>Foodvex - Manage Orders</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=25">

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
					Customer Orders</h2>
			</div>
		</div>

		<div class="neu-card">
			<div class="admin-table-wrapper">
				<table class="admin-table">
					<thead>
						<tr>
							<th>Order ID</th>
							<th>Date</th>
							<th>Total Amount</th>
							<th>Current Status</th>
							<th>Update Status</th>
						</tr>
					</thead>
					<tbody>
						<%
						// Retrieve the order list sent from your AdminOrderController
						List<Order> allOrders = (List<Order>) request.getAttribute("allOrders");

						if (allOrders != null && !allOrders.isEmpty()) {
							for (Order ord : allOrders) {
								// Determine badge color based on status
								String badgeClass = "status-active"; // default green
								if ("Pending".equalsIgnoreCase(ord.getStatus()))
							badgeClass = "status-inactive"; // red/orange
						%>
						<tr>
							<td style="font-weight: bold;">#<%=ord.getOrderId()%></td>
							<td style="color: var(--text-muted);"><%=ord.getOrderdate()%></td>
							<td style="color: var(--primary-yellow); font-weight: bold;">₹<%=ord.getTotalamount()%></td>
							<td><span class="status-badge <%=badgeClass%>"><%=ord.getStatus()%></span>
							</td>
							<td>
								<!-- Form to update this specific order's status -->
								<form action="updateOrderStatus" method="POST"
									class="status-update-form">
									<input type="hidden" name="orderId"
										value="<%=ord.getOrderId()%>"> <select
										name="newStatus" class="status-select">
										<option value="Pending"
											<%="Pending".equals(ord.getStatus()) ? "selected" : ""%>>Pending</option>
										<option value="Preparing"
											<%="Preparing".equals(ord.getStatus()) ? "selected" : ""%>>Preparing</option>
										<option value="Out for Delivery"
											<%="Out for Delivery".equals(ord.getStatus()) ? "selected" : ""%>>Out
											for Delivery</option>
										<option value="Delivered"
											<%="Delivered".equals(ord.getStatus()) ? "selected" : ""%>>Delivered</option>
										<option value="Cancelled"
											<%="Cancelled".equals(ord.getStatus()) ? "selected" : ""%>>Cancelled</option>
									</select>
									<button type="submit" class="neu-btn-solid btn-update-status">Update</button>
								</form>
							</td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="5"
								style="text-align: center; padding: 40px; color: var(--text-muted);">
								No orders found in the system.</td>
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