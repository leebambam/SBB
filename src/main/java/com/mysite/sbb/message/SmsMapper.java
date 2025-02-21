package com.mysite.sbb.message;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SmsMapper {

    SmsDto toDto(Sms sms);

    Sms toEntity(SmsDto smsDto);

/*    List<Sms> toEntityList(List<SmsDto> smsDtoList);

    List<SmsDto> toDtoList(List<Sms> sms);*/

}
