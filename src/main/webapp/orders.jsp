<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Order"%>
<%@ page
	import="com.tap.model.OrderItem, com.tap.daoimpl.OrderItemDaoImpl"%>
<%@ page import="com.tap.model.Menu, com.tap.daoimpl.MenuDaoImpl"%>
<%
// Ensure the user is actually logged in before viewing orders
if (session.getAttribute("loggedInUser") == null) {
	response.sendRedirect("login.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - My Orders</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=32">

</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container orders-wrapper">
		<h2 class="page-title">Your Order History</h2>

		<%
		List<Order> orderList = (List<Order>) request.getAttribute("orderList");

		if (orderList == null || orderList.isEmpty()) {
		%>
		<div class="neu-card no-orders">
			<h2>You haven't placed any orders yet!</h2>

			<!-- CLEANED: Removed inline margin style -->
			<p class="no-orders-text">Looks like you are missing out on some
				delicious meals.</p>

			<a href="restaurant" class="neu-btn-solid">Explore Restaurants 🍔</a>
		</div>
		<%
		} else {
		// Initialize DAOs outside the loop
		OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();
		MenuDaoImpl menuDao = new MenuDaoImpl();

		for (Order order : orderList) {
			String statusClass = "status-pending";
			if ("Delivered".equalsIgnoreCase(order.getStatus())) {
				statusClass = "status-delivered";
			} else if ("Cancelled".equalsIgnoreCase(order.getStatus())) {
				statusClass = "status-cancelled";
			}
		%>
		<div class="neu-card order-card">

			<div class="order-header">
				<div>
					<div class="order-id">
						Order #<%=order.getOrderId()%></div>
					<div class="order-date">
						Placed on:
						<%=order.getOrderdate()%></div>
				</div>
				<div>
					<div class="order-amount">
						₹<%=order.getTotalamount()%></div>
					<div class="order-status <%=statusClass%>"><%=order.getStatus()%></div>
				</div>
			</div>

			<div class="order-items-section">
				<%
				// Using the exact method name from your OrderItemDaoImpl
				List<OrderItem> items = orderItemDao.getOrderItemsByOrderId(order.getOrderId());

				if (items != null && !items.isEmpty()) {
					for (OrderItem item : items) {
						// Assuming your MenuDaoImpl uses getMenu(id)
						Menu menu = menuDao.getMenu(item.getMenuId());
						String itemName = (menu != null) ? menu.getItemName() : "Menu Item #" + item.getMenuId();
				%>
				<div class="order-item-row">
					<span class="order-item-name"><%=item.getQuantity()%>x <%=itemName%></span>
					<span class="order-item-price">₹<%=item.getItemTotal()%></span>
				</div>
				<%
				}
				} else {
				%>

				<!-- CLEANED: Removed inline font-style and color -->
				<div class="order-item-price empty-items-msg">No individual
					items found for this order.</div>
				<%
				}
				%>
			</div>

		</div>
		<%
		}
		}
		%>
	</div>

</body>
</html>