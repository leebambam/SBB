package com.mysite.sbb.message;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-14T10:41:04+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.24 (Oracle Corporation)"
)
@Component
public class SmsMapperImpl implements SmsMapper {

    @Override
    public SmsDto toDto(Sms sms) {
        if ( sms == null ) {
            return null;
        }

        SmsDto.SmsDtoBuilder smsDto = SmsDto.builder();

        smsDto.id( sms.getId() );
        smsDto.user( siteUserToUserDto( sms.getUser() ) );
        smsDto.messageContent( sms.getMessageContent() );
        smsDto.senderPhoneNumber( sms.getSenderPhoneNumber() );
        smsDto.sendTime( sms.getSendTime() );

        return smsDto.build();
    }

    @Override
    public Sms toEntity(SmsDto smsDto) {
        if ( smsDto == null ) {
            return null;
        }

        Sms sms = new Sms();

        return sms;
    }

    protected UserDto siteUserToUserDto(SiteUser siteUser) {
        if ( siteUser == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( siteUser.getId() );
        userDto.username( siteUser.getUsername() );
        userDto.password( siteUser.getPassword() );
        userDto.email( siteUser.getEmail() );
        userDto.tempPassword( siteUser.isTempPassword() );
        userDto.phoneNumber( siteUser.getPhoneNumber() );

        return userDto.build();
    }
}
