package com.zsx.dao;

import com.zsx.entity.RentImages;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RentImagesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RentImages record);

    int insertSelective(RentImages record);

    RentImages selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RentImages record);

    int updateByPrimaryKey(RentImages record);
}