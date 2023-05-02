package com.yuhsuanzhang.him.imserver.mapper;

import com.yuhsuanzhang.him.imcommon.entity.IMMessage;
import com.yuhsuanzhang.him.imcommon.entity.example.IMMessageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMMessageMapper {
    long countByExample(IMMessageExample example);

    int deleteByExample(IMMessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IMMessage record);

    int insertSelective(IMMessage record);

    List<IMMessage> selectByExample(IMMessageExample example);

    IMMessage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IMMessage record, @Param("example") IMMessageExample example);

    int updateByExample(@Param("record") IMMessage record, @Param("example") IMMessageExample example);

    int updateByPrimaryKeySelective(IMMessage record);

    int updateByVersionKeySelective(IMMessage record);

    int updateByPrimaryKey(IMMessage record);
}