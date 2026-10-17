package com.tap.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tap.model.User;
import com.tap.model.Order;
import com.tap.daoimpl.OrderDaoImpl;

@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. Security Check
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
			response.sendRedirect("login.jsp");
			return;
		}

		String orderIdStr = request.getParameter("orderId");
		String newStatus = request.getParameter("newStatus");

		if (orderIdStr != null && newStatus != null) {
			int orderId = Integer.parseInt(orderIdStr);
			OrderDaoImpl orderDao = new OrderDaoImpl();

			// 2. Fetch the existing order using your getOrder method
			Order existingOrder = orderDao.getOrder(orderId);

			if (existingOrder != null) {
				// 3. Apply the new status
				existingOrder.setStatus(newStatus);

				// 4. Save the updated order using your updateOrder method
				orderDao.updateOrder(existingOrder);
			}
		}

		// 5. Refresh the page to reflect the new status badge
		response.sendRedirect("manageOrders");
	}
}