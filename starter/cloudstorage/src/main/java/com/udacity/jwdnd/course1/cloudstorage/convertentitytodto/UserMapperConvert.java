package com.udacity.jwdnd.course1.cloudstorage.convertentitytodto;

import com.udacity.jwdnd.course1.cloudstorage.dto.UserDto;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapperConvert extends AbstractMapper<User, UserDto>{
    @Override
    public Class<UserDto> getDtoClass() {
        return UserDto.class;
    }
}
