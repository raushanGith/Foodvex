package com.tap.controller;

import java.io.IOException;

import com.tap.daoimpl.MenuDaoImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = req.getParameter("action");

        if ("add".equals(action)) {
            int menuId = Integer.parseInt(req.getParameter("menuId"));
            int restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            MenuDaoImpl menuDao = new MenuDaoImpl();
            Menu menuItem = menuDao.getMenu(menuId);

            if (menuItem != null) {
                CartItem item = new CartItem(
                    menuItem.getMenuId(),
                    menuItem.getRestaurantId(),
                    menuItem.getItemName(),
                    quantity,
                    menuItem.getPrice()
                );
                cart.addItem(item);
            }
        } 
        else if ("update".equals(action)) {
            int menuId = Integer.parseInt(req.getParameter("menuId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            cart.updateItem(menuId, quantity);
        } 
        else if ("remove".equals(action)) {
            int menuId = Integer.parseInt(req.getParameter("menuId"));
            cart.removeItem(menuId);
        }
        else if ("clear".equals(action)) {
            cart.clear();
        }

        // --- NEW REDIRECT LOGIC ---
        // Check if the request came from the menu page
        String source = req.getParameter("source");
        if ("menu".equals(source)) {
            String restaurantId = req.getParameter("restaurantId");
            resp.sendRedirect("menu?restaurantId=" + restaurantId);
        } else {
            // Otherwise, it came from the cart page, so redirect back to cart
            resp.sendRedirect("cart.jsp");
        }
    }
}