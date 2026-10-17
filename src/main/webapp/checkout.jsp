<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Cart, com.tap.model.CartItem"%>
<%
// Prevent access if the cart is empty or session expired
Cart cart = (Cart) session.getAttribute("cart");
if (cart == null || cart.getItems().isEmpty()) {
	response.sendRedirect("cart.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Checkout</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=27">

</head>
<body>

	<!-- Unified Dark Navbar Include -->
	<jsp:include page="includes/navbar.jsp" />

	<div class="container checkout-wrapper">
		<a href="cart.jsp" class="back-link">← Back to Cart</a>

		<!-- The entire layout wrapped in a single form -->
		<form action="checkout" method="post" class="checkout-layout">

			<!-- Left Column: Delivery Details -->
			<div class="neu-card checkout-card">
				<h2>Delivery Details</h2>

				<div class="form-group">
					<label for="fullName">Full Name</label> <input type="text"
						id="fullName" name="fullName" class="neu-input" required
						placeholder="Enter your full name">
				</div>

				<div class="form-group">
					<label for="phone">Phone Number</label> <input type="tel"
						id="phone" name="phone" class="neu-input" required
						placeholder="10-digit mobile number" pattern="[0-9]{10}">
				</div>

				<div class="form-group">
					<label for="deliveryAddress">Complete Delivery Address</label>
					<textarea id="deliveryAddress" name="deliveryAddress"
						class="neu-input" required
						placeholder="House/Flat No., Building Name, Street, Area"></textarea>
				</div>

				<div class="form-group">
					<label for="paymentMode">Payment Method</label>

					<!-- CLEANED: External CSS classes applied here -->
					<select id="paymentMode" name="paymentMode"
						class="neu-input checkout-select" required>
						<option value="Cash on Delivery" class="checkout-option">Cash
							on Delivery (COD)</option>
						<option value="Credit/Debit Card" class="checkout-option">Credit
							/ Debit Card</option>
						<option value="UPI" class="checkout-option">UPI (GPay,
							PhonePe)</option>
					</select>
				</div>
			</div>

			<!-- Right Column: Order Review & Summary -->
			<div class="neu-card checkout-card">
				<h2>Review Your Order</h2>

				<div class="order-summary-list">
					<%
					// Loop through all items in the cart to create the receipt
					for (CartItem item : cart.getItems().values()) {
					%>
					<div class="review-item">
						<div class="item-details">
							<span class="item-name"><%=item.getName()%></span> <span
								class="item-qty">Quantity: <%=item.getQuantity()%></span>
						</div>
						<span class="item-price">₹<%=item.getSubTotal()%></span>
					</div>
					<%
					}
					%>
				</div>

				<div class="review-total">
					<span>Total Amount</span> <span>₹<%=cart.getTotalAmount()%></span>
				</div>

				<!-- Final Confirmation Button using Neon Glow -->
				<button type="submit" class="neu-btn-solid checkout-btn-submit">Confirm
					Order 🚀</button>
			</div>

		</form>
	</div>

</body>
</html>