package trevisanvinicius.store.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserDto request);
    void updateDto(UpdateUserDto request, @MappingTarget User user);
}
