package com.yuhsuanzhang.him.imserver.mapper;

import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;
import com.yuhsuanzhang.him.imcommon.entity.example.IMMessageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMMessageMapper {
    long countByExample(IMMessageExample example);

    int deleteByExample(IMMessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IMMessageProto.IMMessage record);

    int insertSelective(IMMessageProto.IMMessage record);

    List<IMMessageProto.IMMessage> selectByExample(IMMessageExample example);

    IMMessageProto.IMMessage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IMMessageProto.IMMessage record, @Param("example") IMMessageExample example);

    int updateByExample(@Param("record") IMMessageProto.IMMessage record, @Param("example") IMMessageExample example);

    int updateByPrimaryKeySelective(IMMessageProto.IMMessage record);

    int updateByPrimaryKey(IMMessageProto.IMMessage record);
}