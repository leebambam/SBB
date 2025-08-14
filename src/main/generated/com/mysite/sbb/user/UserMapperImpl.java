package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-14T10:41:05+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.24 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(SiteUser siteUser) {
        if ( siteUser == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.password( siteUser.getPassword() );
        userDto.tempPassword( siteUser.isTempPassword() );
        userDto.id( siteUser.getId() );
        userDto.username( siteUser.getUsername() );
        userDto.email( siteUser.getEmail() );
        userDto.phoneNumber( siteUser.getPhoneNumber() );

        return userDto.build();
    }

    @Override
    public SiteUser toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        SiteUser.SiteUserBuilder siteUser = SiteUser.builder();

        siteUser.id( userDto.getId() );
        siteUser.username( userDto.getUsername() );
        siteUser.password( userDto.getPassword() );
        siteUser.email( userDto.getEmail() );
        siteUser.tempPassword( userDto.isTempPassword() );
        siteUser.phoneNumber( userDto.getPhoneNumber() );

        return siteUser.build();
    }

    @Override
    public UserDto formToDto(UserCreateForm userCreateForm) {
        if ( userCreateForm == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.password( userCreateForm.getPassword1() );
        userDto.username( userCreateForm.getUsername() );
        userDto.email( userCreateForm.getEmail() );
        userDto.phoneNumber( userCreateForm.getPhoneNumber() );

        return userDto.build();
    }

    @Override
    public List<UserDto> toDtoList(List<SiteUser> siteUsers) {
        if ( siteUsers == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( siteUsers.size() );
        for ( SiteUser siteUser : siteUsers ) {
            list.add( toDto( siteUser ) );
        }

        return list;
    }
}
