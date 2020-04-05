package com.csu.shop.persistence;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    void insertLog(String username, String action);
}
