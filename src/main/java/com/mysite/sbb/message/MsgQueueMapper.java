package com.mysite.sbb.message;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MsgQueueMapper {

    @Mapping(target = "mseq", ignore = true)
    MsgQueueDto toDto(MsgQueue msgQueue);

    @Mapping(target = "mseq", ignore = true)
    @Mapping(source = "dstaddr", target = "dstaddr")
    @Mapping(source = "callback", target = "callback")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "custid", target = "custid")
    @Mapping(source = "request_time", target = "request_time")
    MsgQueue toEntity(MsgQueueDto msgQueueDto);

    List<MsgQueue> toEntityList(List<MsgQueueDto> msgQueueDtoList);


}
