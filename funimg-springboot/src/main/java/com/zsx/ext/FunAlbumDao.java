package com.zsx.ext;

import com.zsx.dao.FunAlbumMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * Created by highness on 2018/4/28 0028.
 */
@Mapper
public interface FunAlbumDao extends FunAlbumMapper{


    Long getLastAlbumId(Date publishDate);

    Long getNextAlbumId(Date publishDate);


}
