package eu.venthe.testcontainers;

import liquibase.listener.SqlListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestListener extends SqlListener {
    static List<String> statements;

    @Override
    public void readSqlWillRun(String sql) {
        super.readSqlWillRun(sql);
        statements.add(sql);
    }

    @Override
    public void writeSqlWillRun(String sql) {
        super.writeSqlWillRun(sql);
        statements.add(sql);
    }
}
