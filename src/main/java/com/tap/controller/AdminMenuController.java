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
import com.tap.model.Menu;
import com.tap.model.Restaurant;
import com.tap.daoimpl.MenuDaoImpl;
import com.tap.daoimpl.RestaurantDaoImpl;

// Maps this Servlet to the URL we used in adminDashboard.jsp
@WebServlet("/manageMenu")
public class AdminMenuController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. Security Check: Verify user is logged in and is an Admin
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");

		if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
			response.sendRedirect("login.jsp");
			return;
		}

		// 2. DYNAMIC LOGIC: Find this admin's specific restaurant using their user ID
		RestaurantDaoImpl restaurantDao = new RestaurantDaoImpl();
		Restaurant adminRestaurant = restaurantDao.getRestaurantByAdminId(user.getUserId());

		// 3. Fetch ONLY the menus belonging to their specific restaurant
		if (adminRestaurant != null) {
			MenuDaoImpl menuDao = new MenuDaoImpl();

			// EXACT MATCH: Uses the getMenusByRestaurantId() method from your MenuDaoImpl
			List<Menu> menuList = menuDao.getMenusByRestaurantId(adminRestaurant.getRestaurantId());
			request.setAttribute("menuList", menuList);
		} else {
			// If the admin doesn't have a restaurant assigned in the database yet, send an
			// empty list
			request.setAttribute("menuList", new ArrayList<Menu>());
		}

		// 4. Forward to the frontend page
		request.getRequestDispatcher("manageMenu.jsp").forward(request, response);
	}
}