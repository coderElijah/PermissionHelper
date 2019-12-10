package com.elijah.permissionhelper;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.SelectUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Properties;

/**
 * Description:
 *
 * @author elijahliu
 * @Note Talk is cheap,just show me ur code.- -!
 * ProjectName:permissionhelper
 * PackageName: com.elijah.permissionhelper
 * Date: 2019/12/9 12:21
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PermissionInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        PermissionObject permissionObject = PermissionHelper.getLocalPermission();
        if (!permissionObject.getPermission()) {
            logger.info("current thread no need to check permission thread:{} permission:{}", Thread.currentThread().getName(), permissionObject);
            return invocation.proceed();
        } else {
            PermissionHelper.reset();
        }
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        String sql = (String) metaObject.getValue("delegate.boundSql.sql");
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect selectBody = (PlainSelect) select.getSelectBody();
        Expression whereExpression = selectBody.getWhere();
        StringBuilder tableName;
        Boolean hasTableAlias = Boolean.FALSE;
        if (selectBody.getFromItem().getAlias() != null) {
            hasTableAlias = Boolean.TRUE;
            tableName = new StringBuilder(selectBody.getFromItem().getAlias().getName());
        } else {
            tableName = new StringBuilder(selectBody.getFromItem().toString());
        }
        EqualsTo equalsTo = new EqualsTo();
        if (hasTableAlias) {
            equalsTo.setLeftExpression(new Column(tableName.append(".").append(permissionObject.getPermissionKey()).toString()));
        } else {
            equalsTo.setLeftExpression(new Column(permissionObject.getPermissionKey().toString()));
        }
        equalsTo.setRightExpression(new StringValue(permissionObject.getPermissionValue().toString()));
        if (whereExpression != null) {
            AndExpression andExpression = new AndExpression(whereExpression, equalsTo);
            whereExpression = andExpression;
        } else {
            whereExpression = equalsTo;
        }
        selectBody.setWhere(whereExpression);
        metaObject.setValue("delegate.boundSql.sql", select.toString());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
