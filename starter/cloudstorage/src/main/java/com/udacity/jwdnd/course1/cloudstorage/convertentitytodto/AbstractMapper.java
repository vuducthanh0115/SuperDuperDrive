package com.udacity.jwdnd.course1.cloudstorage.convertentitytodto;

import org.modelmapper.ModelMapper;

public abstract class AbstractMapper<E, DTO> implements Mapper<E, DTO> {

    private ModelMapper modelMapper;

    /**
     * @param e
     * @return
     */
    @Override
    public DTO toDto(E e) {
        return modelMapper.map(e, getDtoClass());
    }
}
