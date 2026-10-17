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

@WebServlet("/updateMenu")
public class UpdateMenuController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Security Check
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
			response.sendRedirect("login.jsp");
			return;
		}

		// Gather form data
		int menuId = Integer.parseInt(request.getParameter("menuId"));
		String itemName = request.getParameter("itemName");
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		boolean isAvailable = "Yes".equalsIgnoreCase(request.getParameter("status"));

		MenuDaoImpl menuDao = new MenuDaoImpl();

		// Fetch the existing menu to keep the original imagePath and restaurantId safe
		Menu existingMenu = menuDao.getMenu(menuId);

		if (existingMenu != null) {
			existingMenu.setItemName(itemName);
			existingMenu.setDescription(description);
			existingMenu.setPrice(price);
			existingMenu.setAvailable(isAvailable);

			// Update in DB
			menuDao.updateMenu(existingMenu);
		}

		// Refresh the page
		response.sendRedirect("manageMenu");
	}
}