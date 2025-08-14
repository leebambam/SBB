package com.mysite.sbb.message;

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
public class MsgQueueMapperImpl implements MsgQueueMapper {

    @Override
    public MsgQueueDto toDto(MsgQueue msgQueue) {
        if ( msgQueue == null ) {
            return null;
        }

        MsgQueueDto.MsgQueueDtoBuilder msgQueueDto = MsgQueueDto.builder();

        msgQueueDto.dstaddr( msgQueue.getDstaddr() );
        msgQueueDto.callback( msgQueue.getCallback() );
        msgQueueDto.text( msgQueue.getText() );
        msgQueueDto.custid( msgQueue.getCustid() );
        msgQueueDto.request_time( msgQueue.getRequest_time() );

        return msgQueueDto.build();
    }

    @Override
    public MsgQueue toEntity(MsgQueueDto msgQueueDto) {
        if ( msgQueueDto == null ) {
            return null;
        }

        MsgQueue.MsgQueueBuilder msgQueue = MsgQueue.builder();

        msgQueue.dstaddr( msgQueueDto.getDstaddr() );
        msgQueue.callback( msgQueueDto.getCallback() );
        msgQueue.text( msgQueueDto.getText() );
        msgQueue.custid( msgQueueDto.getCustid() );
        msgQueue.request_time( msgQueueDto.getRequest_time() );

        return msgQueue.build();
    }

    @Override
    public List<MsgQueue> toEntityList(List<MsgQueueDto> msgQueueDtoList) {
        if ( msgQueueDtoList == null ) {
            return null;
        }

        List<MsgQueue> list = new ArrayList<MsgQueue>( msgQueueDtoList.size() );
        for ( MsgQueueDto msgQueueDto : msgQueueDtoList ) {
            list.add( toEntity( msgQueueDto ) );
        }

        return list;
    }
}
