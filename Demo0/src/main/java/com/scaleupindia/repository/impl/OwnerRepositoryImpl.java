package com.scaleupindia.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.scaleupindia.dto.OwnerDTO;
import com.scaleupindia.repository.OwnerRepository;
import com.scaleupindia.util.MapperUtil;

/**
 * @author abhishekvermaa10
 *
 */
public class OwnerRepositoryImpl implements OwnerRepository {
	private Properties properties;
	private static final String DATABASE_DRIVER = "database.driver";
	private static final String DATABASE_URL = "database.url";
	private static final String DATABASE_USERNAME = "database.username";
	private static final String DATABASE_PASSWORD = "database.password";

	public OwnerRepositoryImpl(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		int petId = 0;
		String petSql = "INSERT INTO pet_table (name, date_of_birth, gender, type) VALUES (?, ?, ?, ?)";
		String ownerSql = "INSERT INTO owner_table (first_name, last_name, gender, city, state, mobile_number, email_id, pet_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(properties.getProperty(DATABASE_URL),
				properties.getProperty(DATABASE_USERNAME), properties.getProperty(DATABASE_PASSWORD));
				PreparedStatement petPreparedStatement = connection.prepareStatement(petSql,
						Statement.RETURN_GENERATED_KEYS);
				PreparedStatement ownerPreparedStatement = connection.prepareStatement(ownerSql);) {
			Class.forName(properties.getProperty(DATABASE_DRIVER));
			petPreparedStatement.setString(1, ownerDTO.getPetDTO().getName());
			petPreparedStatement.setDate(2, Date.valueOf(ownerDTO.getPetDTO().getBirthDate()));
			petPreparedStatement.setString(3, ownerDTO.getPetDTO().getGender().toString());
			petPreparedStatement.setString(4, ownerDTO.getPetDTO().getType().toString());
			int petsSaved = petPreparedStatement.executeUpdate();
			if (petsSaved == 1) {
				ResultSet resultSet = petPreparedStatement.getGeneratedKeys();
				while (resultSet.next()) {
					petId = resultSet.getInt(1);
				}
				ownerPreparedStatement.setString(1, ownerDTO.getFirstName());
				ownerPreparedStatement.setString(2, ownerDTO.getLastName());
				ownerPreparedStatement.setString(3, ownerDTO.getGender().toString());
				ownerPreparedStatement.setString(4, ownerDTO.getCity());
				ownerPreparedStatement.setString(5, ownerDTO.getState());
				ownerPreparedStatement.setString(6, ownerDTO.getMobileNumber());
				ownerPreparedStatement.setString(7, ownerDTO.getEmailId());
				ownerPreparedStatement.setInt(8, petId);
				ownerPreparedStatement.executeUpdate();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OwnerDTO findOwner(int ownerId) {
		OwnerDTO ownerDTO = null;
		String sql = "SELECT o.id, o.first_name, o.last_name, o.gender, o.city, o.state, o.mobile_number, o.email_id, p.id, p.name, p.date_of_birth, p.gender, p.type FROM owner_table o LEFT JOIN pet_table p ON o.pet_id = p.id WHERE o.id = ?";
		try (Connection connection = DriverManager.getConnection(properties.getProperty(DATABASE_URL),
				properties.getProperty(DATABASE_USERNAME), properties.getProperty(DATABASE_PASSWORD));
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			Class.forName(properties.getProperty(DATABASE_DRIVER));
			preparedStatement.setInt(1, ownerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ownerDTO = MapperUtil.convertOwnerResultSetToDto(resultSet);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return ownerDTO;
	}

	@Override
	public void updatePetDetails(int petId, String petName) {
		String sql = "UPDATE pet_table p SET p.name = ? WHERE p.id = ?";
		try (Connection connection = DriverManager.getConnection(properties.getProperty(DATABASE_URL),
				properties.getProperty(DATABASE_USERNAME), properties.getProperty(DATABASE_PASSWORD));
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			Class.forName(properties.getProperty(DATABASE_DRIVER));
			preparedStatement.setString(1, petName);
			preparedStatement.setInt(2, petId);
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOwner(int ownerId, int petId) {
		int ownersDeleted = 0;
		String ownerSql = "DELETE FROM owner_table WHERE id = ?";
		String petSql = "DELETE FROM pet_table WHERE id = ?";
		try (Connection connection = DriverManager.getConnection(properties.getProperty(DATABASE_URL),
				properties.getProperty(DATABASE_USERNAME), properties.getProperty(DATABASE_PASSWORD));
				PreparedStatement ownerPreparedStatement = connection.prepareStatement(ownerSql);
				PreparedStatement petPreparedStatement = connection.prepareStatement(petSql);) {
			Class.forName(properties.getProperty(DATABASE_DRIVER));
			ownerPreparedStatement.setInt(1, ownerId);
			ownersDeleted = ownerPreparedStatement.executeUpdate();
			if (ownersDeleted == 1) {
				petPreparedStatement.setInt(1, petId);
				petPreparedStatement.executeUpdate();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		List<OwnerDTO> ownerDTOList = new ArrayList<>();
		String sql = "SELECT o.id, o.first_name, o.last_name, o.gender, o.city, o.state, o.mobile_number, o.email_id, p.id, p.name, p.date_of_birth, p.gender, p.type FROM owner_table o LEFT JOIN pet_table p ON o.pet_id = p.id";
		try (Connection connection = DriverManager.getConnection(properties.getProperty(DATABASE_URL),
				properties.getProperty(DATABASE_USERNAME), properties.getProperty(DATABASE_PASSWORD));
				Statement statement = connection.createStatement();) {
			Class.forName(properties.getProperty(DATABASE_DRIVER));
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				OwnerDTO ownerDTO = MapperUtil.convertOwnerResultSetToDto(resultSet);
				ownerDTOList.add(ownerDTO);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return ownerDTOList;
	}
}
