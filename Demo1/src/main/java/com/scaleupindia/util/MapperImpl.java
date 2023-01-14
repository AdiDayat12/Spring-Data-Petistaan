package com.scaleupindia.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.scaleupindia.dto.OwnerDTO;
import com.scaleupindia.dto.PetDTO;
import com.scaleupindia.enums.Gender;
import com.scaleupindia.enums.PetType;

/**
 * @author abhishekvermaa10
 *
 */
public class MapperImpl {
	public static OwnerDTO convertOwnerResultSetToDto(ResultSet resultSet) throws SQLException {
		OwnerDTO ownerDTO = new OwnerDTO();
		ownerDTO.setId(resultSet.getInt("o.id"));
		ownerDTO.setFirstName(resultSet.getString("o.first_name"));
		ownerDTO.setLastName(resultSet.getString("o.last_name"));
		ownerDTO.setGender(Gender.valueOf(resultSet.getString("o.gender")));
		ownerDTO.setCity(resultSet.getString("o.city"));
		ownerDTO.setState(resultSet.getString("o.state"));
		ownerDTO.setMobileNumber(resultSet.getString("o.mobile_number"));
		ownerDTO.setEmailId(resultSet.getString("o.email_id"));
		PetDTO petDTO = convertPetResultSetToDto(resultSet);
		ownerDTO.setPetDTO(petDTO);
		return ownerDTO;
	}

	public static PetDTO convertPetResultSetToDto(ResultSet resultSet) throws SQLException {
		PetDTO petDTO = new PetDTO();
		petDTO.setType(PetType.valueOf(resultSet.getString("p.type")));
		petDTO.setId(resultSet.getInt("p.id"));
		petDTO.setName(resultSet.getString("p.name"));
		petDTO.setBirthDate(resultSet.getDate("p.date_of_birth").toLocalDate());
		petDTO.setGender(Gender.valueOf(resultSet.getString("p.gender")));
		return petDTO;
	}
}
