package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.OrderDao;
import com.tap.model.Order;
import com.tap.util.DBConnection;

public class OrderDaoImpl implements OrderDao {

	private static final String INSERT_ORDER_QUERY = "INSERT INTO `order` (userId, restaurantId, orderdate, totalamount, status, paymentMode) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_ORDER_BY_ID = "SELECT * FROM `order` WHERE orderId = ?";
	private static final String SELECT_ALL_ORDERS = "SELECT * FROM `order`";
	private static final String SELECT_ORDERS_BY_USER = "SELECT * FROM `order` WHERE userId = ?";
	private static final String UPDATE_ORDER_QUERY = "UPDATE `order` SET userId = ?, restaurantId = ?, orderdate = ?, totalamount = ?, status = ?, paymentMode = ? WHERE orderId = ?";
	private static final String DELETE_ORDER_QUERY = "DELETE FROM `order` WHERE orderId = ?";

	// NEW CONSTANT: Fetch orders specific to one restaurant
	private static final String SELECT_ORDERS_BY_RESTAURANT = "SELECT * FROM `order` WHERE restaurantId = ?";

	@Override
	public int addOrder(Order order) {
		int generatedOrderId = 0;

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_QUERY,
						Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setInt(1, order.getUserId());
			preparedStatement.setInt(2, order.getRestaurantId());
			preparedStatement.setTimestamp(3, order.getOrderdate());
			preparedStatement.setDouble(4, order.getTotalamount());
			preparedStatement.setString(5, order.getStatus());
			preparedStatement.setString(6, order.getPaymentMode());

			preparedStatement.executeUpdate();

			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
				if (resultSet.next()) {
					generatedOrderId = resultSet.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generatedOrderId;
	}

	@Override
	public Order getOrder(int orderId) {
		Order order = null;
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {

			preparedStatement.setInt(1, orderId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					order = extractOrderFromResultSet(resultSet);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orderList = new ArrayList<>();
		try (Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDERS)) {

			while (resultSet.next()) {
				orderList.add(extractOrderFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	@Override
	public List<Order> getOrdersByUserId(int userId) {
		List<Order> orderList = new ArrayList<>();
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USER)) {

			preparedStatement.setInt(1, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					orderList.add(extractOrderFromResultSet(resultSet));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	// NEW METHOD: Finds all orders placed at a specific admin's restaurant
	public List<Order> getOrdersByRestaurantId(int restaurantId) {
		List<Order> orderList = new ArrayList<>();
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_RESTAURANT)) {

			preparedStatement.setInt(1, restaurantId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					orderList.add(extractOrderFromResultSet(resultSet));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	@Override
	public void updateOrder(Order order) {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_QUERY)) {

			preparedStatement.setInt(1, order.getUserId());
			preparedStatement.setInt(2, order.getRestaurantId());
			preparedStatement.setTimestamp(3, order.getOrderdate());
			preparedStatement.setDouble(4, order.getTotalamount());
			preparedStatement.setString(5, order.getStatus());
			preparedStatement.setString(6, order.getPaymentMode());
			preparedStatement.setInt(7, order.getOrderId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrder(int orderId) {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_QUERY)) {

			preparedStatement.setInt(1, orderId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setOrderId(resultSet.getInt("orderId"));
		order.setUserId(resultSet.getInt("userId"));
		order.setRestaurantId(resultSet.getInt("restaurantId"));
		order.setOrderdate(resultSet.getTimestamp("orderdate"));
		order.setTotalamount(resultSet.getDouble("totalamount"));
		order.setStatus(resultSet.getString("status"));
		order.setPaymentMode(resultSet.getString("paymentMode"));
		return order;
	}
}