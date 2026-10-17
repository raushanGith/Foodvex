package com.tap.controller;

import java.io.IOException;
import java.util.List;

import com.tap.daoimpl.OrderDaoImpl;
import com.tap.model.Order;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/orderHistory")
public class OrderHistoryController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. Get the session and check if the user is logged in
		HttpSession session = req.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			// If nobody is logged in, redirect to login page
			resp.sendRedirect("login.jsp");
			return;
		}

		// 2. Fetch the user's past orders from the database
		OrderDaoImpl orderDao = new OrderDaoImpl();

		// IMPORTANT: Check your OrderDaoImpl.java to ensure this method name matches
		// exactly.
		// It might be called "getAllOrdersByUserId" or "getOrdersByUser"
		List<Order> orderList = orderDao.getOrdersByUserId(loggedInUser.getUserId());

		// 3. Attach the list to the request so orders.jsp can read it
		req.setAttribute("orderList", orderList);

		// 4. Forward the request to the orders.jsp page to display the data
		req.getRequestDispatcher("orders.jsp").forward(req, resp);
	}
}