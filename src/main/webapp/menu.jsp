<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map"%>
<%@ page
	import="com.tap.model.Menu, com.tap.model.Cart, com.tap.model.CartItem"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Menu</title>

<!-- Pulling 100% of styles externally -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=12">
</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<%
	// Read the cart from the session purely in Java
	Cart sessionCart = (Cart) session.getAttribute("cart");
	Map<Integer, CartItem> cartItems = null;
	int totalItemsInCart = 0;

	if (sessionCart != null) {
		cartItems = sessionCart.getItems();
		if (cartItems != null) {
			totalItemsInCart = cartItems.size();
		}
	}
	%>

	<div class="container">
		<h2
			style="margin-top: 0; margin-bottom: 30px; padding-bottom: 15px; border-bottom: 1px dashed rgba(255, 255, 255, 0.15); background: linear-gradient(135deg, var(--primary-yellow), var(--primary-orange)); -webkit-background-clip: text; -webkit-text-fill-color: transparent;">
			Menu Items</h2>

		<div class="menu-grid">
			<%
			List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
			if (menuList != null && !menuList.isEmpty()) {
				for (Menu menu : menuList) {

					// Check if this specific item is already in the cart
					int currentQty = 0;
					if (cartItems != null && cartItems.containsKey(menu.getMenuId())) {
				currentQty = cartItems.get(menu.getMenuId()).getQuantity();
					}
			%>

			<div class="neu-card menu-card">
				<img src="<%=menu.getImagePath()%>"
					alt="<%=menu.getItemName()%>" class="menu-image">

				<div class="menu-details">
					<h3 class="menu-name"><%=menu.getItemName()%></h3>
					<div class="menu-desc"><%=menu.getDescription()%></div>

					<div class="menu-price-row">
						<span>Price:</span> <span class="price-tag">₹<%=menu.getPrice()%></span>
					</div>

					<div class="swiggy-action-box">
						<%
						if (currentQty == 0) {
						%>
						<!-- Form 1: Item is NOT in cart, show ADD -->
						<form action="cart" method="post" style="margin: 0; width: 100%;">
							<input type="hidden" name="action" value="add"> <input
								type="hidden" name="menuId" value="<%=menu.getMenuId()%>">
							<input type="hidden" name="quantity" value="1"> <input
								type="hidden" name="restaurantId"
								value="<%=menu.getRestaurantId()%>"> <input
								type="hidden" name="source" value="menu">
							<button type="submit" class="add-btn-solid">ADD</button>
						</form>
						<%
						} else {
						%>
						<!-- Form 2: Item IS in cart, show [ - ] QTY [ + ] -->
						<form action="cart" method="post" style="margin: 0;">
							<input type="hidden" name="action"
								value="<%=(currentQty == 1) ? "remove" : "update"%>">
							<input type="hidden" name="menuId"
								value="<%=menu.getMenuId()%>"> <input type="hidden"
								name="quantity" value="<%=currentQty - 1%>"> <input
								type="hidden" name="restaurantId"
								value="<%=menu.getRestaurantId()%>"> <input
								type="hidden" name="source" value="menu">
							<button type="submit" class="qty-btn">-</button>
						</form>

						<span class="qty-number"><%=currentQty%></span>

						<form action="cart" method="post" style="margin: 0;">
							<input type="hidden" name="action" value="update"> <input
								type="hidden" name="menuId" value="<%=menu.getMenuId()%>">
							<input type="hidden" name="quantity"
								value="<%=currentQty + 1%>"> <input type="hidden"
								name="restaurantId" value="<%=menu.getRestaurantId()%>">
							<input type="hidden" name="source" value="menu">
							<button type="submit" class="qty-btn">+</button>
						</form>
						<%
						}
						%>
					</div>

				</div>
			</div>

			<%
			}
			} else {
			%>
			<div class="no-data">No menu items available for this
				restaurant.</div>
			<%
			}
			%>
		</div>
	</div>

	<!-- Floating View Cart Banner (Only shows if items exist in cart) -->
	<%
	if (totalItemsInCart > 0) {
	%>
	<div class="floating-cart-banner">
		<div class="banner-text">
			<%=totalItemsInCart%>
			Item(s) in Cart
		</div>
		<a href="cart.jsp" class="banner-link">VIEW CART 🛍️</a>
	</div>
	<% } %>

</body>
</html>