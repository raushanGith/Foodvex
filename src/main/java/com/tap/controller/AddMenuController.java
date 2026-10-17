package com.tap.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tap.model.User;
import com.tap.model.Menu;
import com.tap.daoimpl.MenuDaoImpl;

// Maps this Servlet to the form action in addMenu.jsp
@WebServlet("/addMenu")
public class AddMenuController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. Security Check: Verify user is logged in and is an Admin
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");

		if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
			response.sendRedirect("login.jsp");
			return;
		}

		// 2. Retrieve form data
		String itemName = request.getParameter("itemName");
		String description = request.getParameter("description");
		String priceStr = request.getParameter("price");
		String status = request.getParameter("status");

		// 3. Convert data types
		double price = 0.0;
		if (priceStr != null && !priceStr.isEmpty()) {
			price = Double.parseDouble(priceStr);
		}
		boolean isAvailable = "Yes".equalsIgnoreCase(status);

		// 4. Create and populate the Menu model
		Menu newMenu = new Menu();
		newMenu.setItemName(itemName);
		newMenu.setDescription(description);
		newMenu.setPrice(price);
		newMenu.setAvailable(isAvailable);

		// Note: Defaulting restaurantId to 1 and imagePath to a placeholder.
		// You can make these dynamic later!
		newMenu.setRestaurantId(1);
		newMenu.setImagePath("default_food.png");

		// 5. Save to the database using your DAO
		MenuDaoImpl menuDao = new MenuDaoImpl();
		menuDao.addMenu(newMenu);

		// 6. Redirect back to the Menu List.
		// Notice we redirect to the Servlet ("/manageMenu"), NOT the JSP, so it
		// refreshes the data!
		response.sendRedirect("manageMenu");
	}
}