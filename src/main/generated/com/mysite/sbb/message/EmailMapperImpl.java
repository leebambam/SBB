package com.mysite.sbb.message;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-14T10:41:04+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.24 (Oracle Corporation)"
)
@Component
public class EmailMapperImpl implements EmailMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public EmailDto toDto(Email email) {
        if ( email == null ) {
            return null;
        }

        EmailDto.EmailDtoBuilder emailDto = EmailDto.builder();

        emailDto.reg_id( userMapper.toDto( email.getReg_id() ) );
        emailDto.sender( email.getSender() );
        emailDto.recipient( email.getRecipient() );
        emailDto.subject( email.getSubject() );
        emailDto.content( email.getContent() );
        emailDto.status( email.getStatus() );
        emailDto.send_dt( email.getSend_dt() );

        return emailDto.build();
    }

    @Override
    public Email toEntity(EmailDto emailDto) {
        if ( emailDto == null ) {
            return null;
        }

        Email email = new Email();

        email.setSender( emailDto.getSender() );
        email.setRecipient( emailDto.getRecipient() );
        email.setSubject( emailDto.getSubject() );
        email.setContent( emailDto.getContent() );
        email.setStatus( emailDto.getStatus() );
        email.setSend_dt( emailDto.getSend_dt() );
        email.setReg_id( userDtoToSiteUser( emailDto.getReg_id() ) );

        return email;
    }

    @Override
    public List<Email> toEntityList(List<EmailDto> emailDtoList) {
        if ( emailDtoList == null ) {
            return null;
        }

        List<Email> list = new ArrayList<Email>( emailDtoList.size() );
        for ( EmailDto emailDto : emailDtoList ) {
            list.add( toEntity( emailDto ) );
        }

        return list;
    }

    protected SiteUser userDtoToSiteUser(UserDto userDto) {
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
}
