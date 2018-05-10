package com.zsx.dao;

import com.zsx.entity.FunImages;

public interface FunImagesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FunImages record);

    int insertSelective(FunImages record);

    FunImages selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FunImages record);

    int updateByPrimaryKey(FunImages record);
}