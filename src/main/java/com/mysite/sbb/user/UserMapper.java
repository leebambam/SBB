package com.mysite.sbb.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // 엔티티 → DTO 변환
    @Mapping(target = "password", source = "password") //  // 암호화된 비밀번호도 처리할 수 있다.
    @Mapping(source = "tempPassword", target = "tempPassword")
    UserDto toDto(SiteUser siteUser);

    // DTO → 엔티티 변환
    SiteUser toEntity(UserDto userDto);

    // Form → DTO 변환
    // Form의 password1 → DTO의 password
    @Mapping(target = "id", ignore = true) // Form에는 ID가 없으므로 무시
    @Mapping(target = "password", source = "password1")
    @Mapping(target = "tempPassword", ignore = true)  // 매핑 제외
    UserDto formToDto(UserCreateForm userCreateForm);
}
