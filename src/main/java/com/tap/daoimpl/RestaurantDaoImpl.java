package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.RestaurantDao;
import com.tap.model.Restaurant;
import com.tap.util.DBConnection;

public class RestaurantDaoImpl implements RestaurantDao {

	private static final String INSERT_RESTAURANT_QUERY = "INSERT INTO restaurant (name, cuisineType, deliveryTime, address, adminUserId, rating, isActive, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SELECT_RESTAURANT_BY_ID = "SELECT * FROM restaurant WHERE restaurantId = ?";
	private static final String SELECT_ALL_RESTAURANTS = "SELECT * FROM restaurant";
	private static final String UPDATE_RESTAURANT_QUERY = "UPDATE restaurant SET name = ?, cuisineType = ?, deliveryTime = ?, address = ?, adminUserId = ?, rating = ?, isActive = ?, imagePath = ? WHERE restaurantId = ?";
	private static final String DELETE_RESTAURANT_QUERY = "DELETE FROM restaurant WHERE restaurantId = ?";
	private static final String SEARCH_RESTAURANTS_QUERY = "SELECT * FROM restaurant WHERE name LIKE ? OR cuisineType LIKE ?";

	// NEW CONSTANT: Fetch restaurant by the admin's user ID
	private static final String SELECT_RESTAURANT_BY_ADMIN = "SELECT * FROM restaurant WHERE adminUserId = ?";

	@Override
	public void addRestaurant(Restaurant restaurant) {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESTAURANT_QUERY)) {

			preparedStatement.setString(1, restaurant.getName());
			preparedStatement.setString(2, restaurant.getCuisineType());
			preparedStatement.setInt(3, restaurant.getDeliveryTime());
			preparedStatement.setString(4, restaurant.getAddress());
			preparedStatement.setInt(5, restaurant.getAdminUserId());
			preparedStatement.setDouble(6, restaurant.getRating());
			preparedStatement.setBoolean(7, restaurant.isActive());
			preparedStatement.setString(8, restaurant.getImagePath());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Restaurant getRestaurant(int restaurantId) {
		Restaurant restaurant = null;
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RESTAURANT_BY_ID)) {

			preparedStatement.setInt(1, restaurantId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					restaurant = extractRestaurantFromResultSet(resultSet);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurant;
	}

	// NEW METHOD: Finds the specific restaurant managed by this logged-in admin
	public Restaurant getRestaurantByAdminId(int adminUserId) {
		Restaurant restaurant = null;
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RESTAURANT_BY_ADMIN)) {

			preparedStatement.setInt(1, adminUserId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					restaurant = extractRestaurantFromResultSet(resultSet);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurant;
	}

	@Override
	public List<Restaurant> getAllRestaurants() {
		List<Restaurant> restaurantList = new ArrayList<>();
		try (Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_ALL_RESTAURANTS)) {

			while (resultSet.next()) {
				restaurantList.add(extractRestaurantFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurantList;
	}

	public List<Restaurant> getRestaurantsBySearch(String searchQuery) {
		List<Restaurant> searchList = new ArrayList<>();
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_RESTAURANTS_QUERY)) {

			String searchParam = "%" + searchQuery + "%";
			preparedStatement.setString(1, searchParam);
			preparedStatement.setString(2, searchParam);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					searchList.add(extractRestaurantFromResultSet(resultSet));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return searchList;
	}

	@Override
	public void updateRestaurant(Restaurant restaurant) {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESTAURANT_QUERY)) {

			preparedStatement.setString(1, restaurant.getName());
			preparedStatement.setString(2, restaurant.getCuisineType());
			preparedStatement.setInt(3, restaurant.getDeliveryTime());
			preparedStatement.setString(4, restaurant.getAddress());
			preparedStatement.setInt(5, restaurant.getAdminUserId());
			preparedStatement.setDouble(6, restaurant.getRating());
			preparedStatement.setBoolean(7, restaurant.isActive());
			preparedStatement.setString(8, restaurant.getImagePath());
			preparedStatement.setInt(9, restaurant.getRestaurantId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRestaurant(int restaurantId) {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESTAURANT_QUERY)) {

			preparedStatement.setInt(1, restaurantId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Restaurant extractRestaurantFromResultSet(ResultSet resultSet) throws SQLException {
		Restaurant restaurant = new Restaurant();
		restaurant.setRestaurantId(resultSet.getInt("restaurantId"));
		restaurant.setName(resultSet.getString("name"));
		restaurant.setCuisineType(resultSet.getString("cuisineType"));
		restaurant.setDeliveryTime(resultSet.getInt("deliveryTime"));
		restaurant.setAddress(resultSet.getString("address"));
		restaurant.setAdminUserId(resultSet.getInt("adminUserId"));
		restaurant.setRating(resultSet.getDouble("rating"));
		restaurant.setActive(resultSet.getBoolean("isActive"));
		restaurant.setImagePath(resultSet.getString("imagePath"));
		return restaurant;
	}
}