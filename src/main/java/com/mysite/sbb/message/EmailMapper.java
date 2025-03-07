package com.mysite.sbb.message;

import com.mysite.sbb.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EmailMapper {
    @Mapping(target = "mail_sn", ignore = true)
    @Mapping(target = "reg_id", source = "reg_id") // UserMapper의 변환 명시
    EmailDto toDto(Email email);

    @Mapping(target = "mail_sn", ignore = true)
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "recipient", target = "recipient")
    @Mapping(source = "subject", target = "subject")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "send_dt", target = "send_dt")
    @Mapping(source = "reg_id", target = "reg_id")
    Email toEntity(EmailDto emailDto);

    List<Email> toEntityList(List<EmailDto> emailDtoList);


}
