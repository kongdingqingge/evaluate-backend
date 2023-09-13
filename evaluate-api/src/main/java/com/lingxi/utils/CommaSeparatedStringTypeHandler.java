package com.lingxi.utils;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommaSeparatedStringTypeHandler implements TypeHandler<List<Integer>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<Integer> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            String commaSeparatedString = parameter.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            ps.setString(i, commaSeparatedString);
        } else {
            ps.setNull(i, Types.VARCHAR);
        }
    }

    @Override
    public List<Integer> getResult(ResultSet rs, String columnName) throws SQLException {
        return convertToIntegerList(rs.getString(columnName));
    }

    @Override
    public List<Integer> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertToIntegerList(rs.getString(columnIndex));
    }

    @Override
    public List<Integer> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertToIntegerList(cs.getString(columnIndex));
    }

    private List<Integer> convertToIntegerList(String commaSeparatedString) {
        if (commaSeparatedString == null || commaSeparatedString.isEmpty()) {
            return null;
        }
        String[] values = commaSeparatedString.split(",");
        return Arrays.stream(values)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}

