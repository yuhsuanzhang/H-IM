package com.yuhsuanzhang.him.imserver.mapper;

import com.yuhsuanzhang.him.imcommon.entity.GroupMessage;
import com.yuhsuanzhang.him.imcommon.entity.GroupMessageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMessageMapper {
    long countByExample(GroupMessageExample example);

    int deleteByExample(GroupMessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GroupMessage record);

    int insertSelective(GroupMessage record);

    List<GroupMessage> selectByExample(GroupMessageExample example);

    GroupMessage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GroupMessage record, @Param("example") GroupMessageExample example);

    int updateByExample(@Param("record") GroupMessage record, @Param("example") GroupMessageExample example);

    int updateByPrimaryKeySelective(GroupMessage record);

    int updateByPrimaryKey(GroupMessage record);
}