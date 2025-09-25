package com.p2f4.med_office.mapper;

import com.p2f4.med_office.entity.User;
import com.p2f4.med_office.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO toDTO(User user);

    @Mapping(target = "specialties", ignore =true)
    User toEntity(UserDTO userDTO);
}
