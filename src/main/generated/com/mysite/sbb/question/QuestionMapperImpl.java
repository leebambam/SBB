package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-14T10:41:05+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.24 (Oracle Corporation)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public QuestionDto toDto(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionDto.QuestionDtoBuilder questionDto = QuestionDto.builder();

        List<Answer> list = question.getAnswerList();
        if ( list != null ) {
            questionDto.answerList( new ArrayList<Answer>( list ) );
        }
        questionDto.id( question.getId() );
        questionDto.subject( question.getSubject() );
        questionDto.content( question.getContent() );
        questionDto.createDate( question.getCreateDate() );
        questionDto.author( siteUserToUserDto( question.getAuthor() ) );
        questionDto.modifyDate( question.getModifyDate() );
        questionDto.voter( siteUserSetToUserDtoSet( question.getVoter() ) );

        return questionDto.build();
    }

    @Override
    public Question toEntity(QuestionDto questionDto) {
        if ( questionDto == null ) {
            return null;
        }

        Question.QuestionBuilder question = Question.builder();

        List<Answer> list = questionDto.getAnswerList();
        if ( list != null ) {
            question.answerList( new ArrayList<Answer>( list ) );
        }
        question.id( questionDto.getId() );
        question.subject( questionDto.getSubject() );
        question.content( questionDto.getContent() );
        question.createDate( questionDto.getCreateDate() );
        question.author( userDtoToSiteUser( questionDto.getAuthor() ) );
        question.modifyDate( questionDto.getModifyDate() );
        question.voter( userDtoSetToSiteUserSet( questionDto.getVoter() ) );

        return question.build();
    }

    @Override
    public List<QuestionDto> toDtoList(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<QuestionDto> list = new ArrayList<QuestionDto>( questions.size() );
        for ( Question question : questions ) {
            list.add( toDto( question ) );
        }

        return list;
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

    protected Set<UserDto> siteUserSetToUserDtoSet(Set<SiteUser> set) {
        if ( set == null ) {
            return null;
        }

        Set<UserDto> set1 = new LinkedHashSet<UserDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SiteUser siteUser : set ) {
            set1.add( siteUserToUserDto( siteUser ) );
        }

        return set1;
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

    protected Set<SiteUser> userDtoSetToSiteUserSet(Set<UserDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<SiteUser> set1 = new LinkedHashSet<SiteUser>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UserDto userDto : set ) {
            set1.add( userDtoToSiteUser( userDto ) );
        }

        return set1;
    }
}
