package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.UserDto;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;

public interface UserService {

    int createUser (UserDto userDto) throws InternalException;
}
