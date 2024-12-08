package com.github.howwrite.treasure.core.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class BaseEnumMybatisHandler extends BaseTypeHandler<BaseEnum> {
    private final Class<BaseEnum> type;

    public BaseEnumMybatisHandler(Class<BaseEnum> type) {
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.code(), JdbcType.INTEGER.TYPE_CODE);
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? null : parseBaseEnum(rs.getInt(columnName));
    }

    @Override
    public BaseEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : parseBaseEnum(rs.getInt(columnIndex));
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : parseBaseEnum(cs.getInt(columnIndex));
    }

    private BaseEnum parseBaseEnum(Integer code) {
        BaseEnum[] enumConstants = this.type.getEnumConstants();
        for (BaseEnum enumConstant : enumConstants) {
            if (Objects.equals(enumConstant.code(), code)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException(String.format("未知的枚举code: %s, 类名: %s", code, type.getName()));
    }
}
