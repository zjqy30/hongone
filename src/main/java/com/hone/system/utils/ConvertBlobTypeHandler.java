package com.hone.system.utils;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;

/**
 * className:ConvertBlobTypeHandler
 * 
 * 自定义typehandler，解决mybatis存储blob字段后，出现乱码的问题
 * 配置mapper.xml：
 * <result  typeHandler="cn.ffcs.drive.common.util.ConvertBlobTypeHandler"/>
 * 
 * @author lgf
 * @version 1.0.0
 * @date 2016-05-05 11:15:23
 * 
 */

@MappedJdbcTypes(JdbcType.BLOB)
public class ConvertBlobTypeHandler extends BaseTypeHandler<String> {

    private static final String DEFAULT_CHARSET = "UTF-8"; //感觉没屌用

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream(parameter.getBytes());
        ps.setBinaryStream(i, bis, parameter.getBytes().length); //网上都是直接paramter.length() 这样是不对的。

    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Blob blob = rs.getBlob(columnName);
        byte[] returnValue = null;
        String result = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        //将取出的流对象转为utf-8的字符串对象
        if (null != returnValue) {
            result = new String(returnValue);
        }
        return result;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Blob blob = rs.getBlob(columnIndex);
        byte[] returnValue = null;
        String result = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        //将取出的流对象转为utf-8的字符串对象
        if (null != returnValue) {
            result = new String(returnValue);
        }
        return result;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Blob blob = cs.getBlob(columnIndex);
        byte[] returnValue = null;
        String result = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        //将取出的流对象转为utf-8的字符串对象
        if (null != returnValue) {
            result = new String(returnValue);
        }
        return result;
    }
}
