package com.tap.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tap.model.User;
import com.tap.model.Order;
import com.tap.model.Restaurant;
import com.tap.daoimpl.OrderDaoImpl;
import com.tap.daoimpl.RestaurantDaoImpl;

@WebServlet("/manageOrders")
public class AdminOrderController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. Security Check
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
			response.sendRedirect("login.jsp");
			return;
		}

		// 2. DYNAMIC LOGIC: Find this admin's specific restaurant
		RestaurantDaoImpl restaurantDao = new RestaurantDaoImpl();
		Restaurant adminRestaurant = restaurantDao.getRestaurantByAdminId(user.getUserId());

		// 3. Fetch ONLY the orders placed at their restaurant
		if (adminRestaurant != null) {
			OrderDaoImpl orderDao = new OrderDaoImpl();

			// EXACT MATCH: Uses the new getOrdersByRestaurantId() method
			List<Order> allOrders = orderDao.getOrdersByRestaurantId(adminRestaurant.getRestaurantId());
			request.setAttribute("allOrders", allOrders);
		} else {
			// If they don't have a restaurant assigned yet, pass an empty list
			request.setAttribute("allOrders", new ArrayList<Order>());
		}

		// 4. Forward to the JSP
		request.getRequestDispatcher("manageOrders.jsp").forward(request, response);
	}
}