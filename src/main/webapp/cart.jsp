<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.Map, com.tap.model.Cart, com.tap.model.CartItem"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Your Cart</title>

<!-- Using the global Dark Neo-Tactile CSS engine safely -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=26">

</head>
<body>

	<!-- Unified Dark Navbar Include -->
	<jsp:include page="includes/navbar.jsp" />

	<div class="container cart-wrapper">
		<div class="neu-card cart-card">

			<h2 class="cart-header-title">Your Shopping Cart</h2>

			<%
			Cart cart = (Cart) session.getAttribute("cart");

			if (cart == null || cart.getItems().isEmpty()) {
			%>

			<!-- PERFECTLY CENTERED EMPTY CART STATE -->
			<div class="empty-cart-wrapper">
				<h2 class="empty-cart-title">Your cart is currently empty.</h2>
				<p class="empty-cart-text">Looks like you haven't added any
					delicious food yet.</p>
				<a href="restaurant" class="neu-btn-solid cart-browse-btn">Browse
					Restaurants</a>
			</div>

			<%
			} else {
			%>
			<table class="cart-table">
				<thead>
					<tr>
						<th>Food Item</th>
						<th>Price</th>
						<th>Quantity</th>
						<th>Subtotal</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (CartItem item : cart.getItems().values()) {
					%>
					<tr>
						<td class="cart-item-name"><%=item.getName()%></td>
						<td class="cart-price-text">₹<%=item.getPrice()%></td>
						<td>
							<div class="cart-qty-controls">
								<form action="cart" method="post" class="inline-form">
									<input type="hidden" name="menuId"
										value="<%=item.getItemId()%>"> <input type="hidden"
										name="action" value="update"> <input type="hidden"
										name="quantity" value="<%=item.getQuantity() - 1%>">
									<button type="submit" class="cart-qty-btn">−</button>
								</form>

								<span class="cart-qty-text"><%=item.getQuantity()%></span>

								<form action="cart" method="post" class="inline-form">
									<input type="hidden" name="menuId"
										value="<%=item.getItemId()%>"> <input type="hidden"
										name="action" value="update"> <input type="hidden"
										name="quantity" value="<%=item.getQuantity() + 1%>">
									<button type="submit" class="cart-qty-btn">+</button>
								</form>
							</div>

							<form action="cart" method="post" class="inline-form">
								<input type="hidden" name="menuId" value="<%=item.getItemId()%>">
								<input type="hidden" name="action" value="remove">
								<button type="submit" class="remove-link">Remove</button>
							</form>
						</td>
						<td class="cart-price-text">₹<%=item.getSubTotal()%></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>

			<div class="cart-summary">
				<div class="cart-grand-total">
					Total Amount: <span>₹<%=cart.getTotalAmount()%></span>
				</div>

				<div class="cart-action-buttons">
					<form action="cart" method="post" class="inline-form">
						<input type="hidden" name="action" value="clear">
						<button type="submit" class="neu-btn text-danger">Clear
							Cart</button>
					</form>

					<%
					int currentRestId = cart.getItems().values().iterator().next().getRestaurantId();
					%>

					<a href="menu?restaurantId=<%=currentRestId%>" class="neu-btn">Add
						More Items</a> <a href="checkout.jsp" class="neu-btn-solid">Proceed
						to Checkout ➔</a>
				</div>
			</div>
			<%
			}
			%>
		</div>
	</div>

</body>
</html>