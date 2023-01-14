package com.scaleupindia.repository.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.scaleupindia.dto.OwnerDTO;
import com.scaleupindia.repository.OwnerRepository;
import com.scaleupindia.util.MapperImpl;

/**
 * @author abhishekvermaa10
 *
 */
@Repository
public class OwnerRepositoryImpl implements OwnerRepository {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		int petId = 0;
		String petSql = "INSERT INTO pet_table (name, date_of_birth, gender, type) VALUES (:name, :birthDate, :gender, :type)";
		String ownerSql = "INSERT INTO owner_table (first_name, last_name, gender, city, state, mobile_number, email_id, pet_id) VALUES (:firstName, :lastName, :gender, :city, :state, :mobileNumber, :emailId, :petId)";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("name", ownerDTO.getPetDTO().getName());
		mapSqlParameterSource.addValue("birthDate", Date.valueOf(ownerDTO.getPetDTO().getBirthDate()));
		mapSqlParameterSource.addValue("gender", ownerDTO.getPetDTO().getGender().toString());
		mapSqlParameterSource.addValue("type", ownerDTO.getPetDTO().getType().toString());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int petsSaved = namedParameterJdbcTemplate.update(petSql, mapSqlParameterSource, keyHolder);
		if (petsSaved == 1) {
			Number generatedPetId = keyHolder.getKey();
			if (Objects.nonNull(generatedPetId)) {
				petId = generatedPetId.intValue();
			}
			mapSqlParameterSource.addValue("firstName", ownerDTO.getFirstName());
			mapSqlParameterSource.addValue("lastName", ownerDTO.getLastName());
			mapSqlParameterSource.addValue("gender", ownerDTO.getGender().toString());
			mapSqlParameterSource.addValue("city", ownerDTO.getCity());
			mapSqlParameterSource.addValue("state", ownerDTO.getState());
			mapSqlParameterSource.addValue("mobileNumber", ownerDTO.getMobileNumber());
			mapSqlParameterSource.addValue("emailId", ownerDTO.getEmailId());
			mapSqlParameterSource.addValue("petId", petId);
			namedParameterJdbcTemplate.update(ownerSql, mapSqlParameterSource);
		}
	}

	@Override
	public OwnerDTO findOwner(int ownerId) {
		String sql = "SELECT o.id, o.first_name, o.last_name, o.gender, o.city, o.state, o.mobile_number, o.email_id, p.id, p.name, p.date_of_birth, p.gender, p.type FROM owner_table o LEFT JOIN pet_table p ON o.pet_id = p.id WHERE o.id = :ownerId";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ownerId", ownerId);
		return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new ResultSetExtractor<OwnerDTO>() {
			@Override
			public OwnerDTO extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				OwnerDTO ownerDTO = null;
				while (resultSet.next()) {
					ownerDTO = MapperImpl.convertOwnerResultSetToDto(resultSet);
				}
				return ownerDTO;
			}
		});
	}

	@Override
	public void updatePetDetails(int petId, String petName) {
		String sql = "UPDATE pet_table p SET p.name = :name WHERE p.id = :petId";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("name", petName);
		mapSqlParameterSource.addValue("petId", petId);
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}

	@Override
	public void deleteOwner(int ownerId, int petId) {
		int ownersDeleted = 0;
		String ownerSql = "DELETE FROM owner_table WHERE id = :ownerId";
		String petSql = "DELETE FROM pet_table WHERE id = :petId";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("ownerId", ownerId);
		ownersDeleted = namedParameterJdbcTemplate.update(ownerSql, mapSqlParameterSource);
		if (ownersDeleted == 1) {
			mapSqlParameterSource.addValue("petId", petId);
			namedParameterJdbcTemplate.update(petSql, mapSqlParameterSource);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		String sql = "SELECT o.id, o.first_name, o.last_name, o.gender, o.city, o.state, o.mobile_number, o.email_id, p.id, p.name, p.date_of_birth, p.gender, p.type FROM owner_table o LEFT JOIN pet_table p ON o.pet_id = p.id";
		return namedParameterJdbcTemplate.query(sql, new ResultSetExtractor<List<OwnerDTO>>() {
			@Override
			public List<OwnerDTO> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				List<OwnerDTO> ownerDTOList = new ArrayList<>();
				while (resultSet.next()) {
					OwnerDTO ownerDTO = MapperImpl.convertOwnerResultSetToDto(resultSet);
					ownerDTOList.add(ownerDTO);
				}
				return ownerDTOList;
			}
		});
	}
}
