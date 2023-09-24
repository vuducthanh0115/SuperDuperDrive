package com.udacity.jwdnd.course1.cloudstorage.convertentitytodto;

public interface Mapper<E, DTO> {

    /**
     * Convert entity to dto
     *
     * @param e
     * @return
     */
    DTO toDto(E e);

    /**
     * @return
     */
    Class<DTO> getDtoClass();
}
