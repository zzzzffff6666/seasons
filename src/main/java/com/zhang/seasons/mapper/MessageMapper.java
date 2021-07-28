package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Message;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface MessageMapper {
    @Insert("insert into message(receiver, sender, msg, url, read, created) " +
            "values(#{receiver}, #{sender}, #{msg}, #{url}, 0, #{created})")
    int insert();

    @Insert("<script> " +
            "insert into message(receiver, sender, msg, url, read, created) " +
            "values " +
            "<foreach collection='list' item='item' index='index' separator=','> " +
            "(#{item}, #{sender}, #{msg}, #{url}, 0, #{created})" +
            "</foreach> " +
            "</script>")
    int insertByCondition(@Param("list") List<Integer> list, int sender, String msg, String url, Timestamp now);

    @Delete("delete from message " +
            "where receiver = #{receiver} " +
            "and sender = #{sender} " +
            "and created = #{created}")
    int delete(int receiver, int sender, Timestamp created);

    @Select("select * " +
            "from message " +
            "where receiver = #{receiver} " +
            "order by created desc")
    @ResultType(Message.class)
    List<Message> selectByReceiver(int receiver);

    @Select("select * " +
            "from message " +
            "where sender = #{sender} " +
            "order by created desc")
    @ResultType(Message.class)
    List<Message> selectBySender(int sender);

    @Select("select * " +
            "from message " +
            "order by created desc")
    @ResultType(Message.class)
    List<Message> selectAll();
}