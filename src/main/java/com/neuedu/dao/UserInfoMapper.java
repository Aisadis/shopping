package com.neuedu.dao;

import com.neuedu.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int insert(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    UserInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    List<UserInfo> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserInfo record);

    int checkUsername(@Param("username") String username);
    int checkEmail(String email);

    UserInfo selectUserByUsernameAndPassword(@Param("username") String username, @Param("password")String password);
    /**
     * 根据username查询密保问题
     *
     */
    String selectQuestionByUsername(String username);

    /**
     * 校验答案
     */

    int checkAnswerByUsernameAndQuestion(@Param("username")String username,
                                         @Param("question")String question,
                                         @Param("answer")String answer);
    /**
     * 根据用户名更新密码
     */
    int updatePasswordByUsername(@Param("username")String username,//map里边的key值
                                 @Param("passwordNew")String passwordNew);

}