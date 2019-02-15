package com.yishenxiao.commons.dao;

import com.yishenxiao.commons.beans.FileUpload;

public interface FileUploadMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileUpload record);

    int insertSelective(FileUpload record);

    FileUpload selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileUpload record);

    int updateByPrimaryKey(FileUpload record);
}