package com.neuedu.dao;

import com.neuedu.pojo.Address;
import java.util.List;

public interface AddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated
     */
    int insert(Address record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated
     */
    Address selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated
     */
    List<Address> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table address
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Address record);
}