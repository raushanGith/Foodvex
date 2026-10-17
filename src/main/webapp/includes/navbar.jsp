<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User"%>

<nav class="premium-navbar">
    
    <!-- LEFT: Logo -->
    <div class="nav-brand">
        <a href="index.jsp">Foodvex</a>
    </div>

    <!-- CENTER: Pill Search Bar -->
    <div class="nav-search">
        <form action="restaurant" method="GET">
            <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="11" cy="11" r="8"></circle>
                <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
            </svg>
            <input type="text" name="search" placeholder="Search food..." value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
        </form>
    </div>

    <!-- RIGHT: Actions & JS Dropdown -->
    <div class="nav-actions">
        <a href="restaurant" class="nav-link">Restaurants</a>
        <a href="orderHistory" class="nav-link">Orders</a>
        
        <a href="cart.jsp" class="nav-cart-btn">
            🛒 Cart
        </a>

        <% 
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser != null) { 
        %>
            <!-- Profile Dropdown (JavaScript Driven) -->
            <div class="profile-menu-container">
                <button class="profile-btn" onclick="toggleProfileDropdown(event)">
                    👤 <%= loggedInUser.getUsername() %> ▾
                </button>
                
                <div class="profile-dropdown" id="userDropdownMenu">
                    <% if ("Admin".equalsIgnoreCase(loggedInUser.getRole())) { %>
                        <a href="adminDashboard.jsp" class="dropdown-item admin">⚙️ Admin Dashboard</a>
                    <% } %>
                    <a href="profile.jsp" class="dropdown-item">👤 Profile Settings</a>
                    
                    <div class="dropdown-divider"></div>
                    
                    <a href="logout" class="dropdown-item logout">🚪 Logout</a>
                </div>
            </div>
        <% } else { %>
            <a href="login.jsp" class="nav-link" style="color: #ffba08;">Login</a>
        <% } %>
    </div>
</nav>

<!-- Minimal JS to handle the React-style dropdown toggle -->
<script>
    function toggleProfileDropdown(event) {
        event.stopPropagation(); // Prevents the window click from immediately firing
        const dropdown = document.getElementById('userDropdownMenu');
        dropdown.classList.toggle('active');
    }

    // Close the dropdown if the user clicks anywhere else on the page
    window.addEventListener('click', function(event) {
        const dropdown = document.getElementById('userDropdownMenu');
        const container = document.querySelector('.profile-menu-container');
        
        if (dropdown && dropdown.classList.contains('active')) {
            if (!container || !container.contains(event.target)) {
                dropdown.classList.remove('active');
            }
        }
    });
</script>