package com.example.scheduler.api.config.db;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class CustomMySQLDialect extends MySQL57Dialect {

    public CustomMySQLDialect() {
        super();
        registerFunction("timestampdiff", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "sum(timestampdiff(?1, ?2, ?3))"));
        registerFunction("str_to_date", new SQLFunctionTemplate(StandardBasicTypes.DATE, "str_to_date(?1, ?2)"));
        registerFunction("date_add", new SQLFunctionTemplate(StandardBasicTypes.DATE, "date_add(?1, INTERVAL ?2 ?3)"));

    }
}
