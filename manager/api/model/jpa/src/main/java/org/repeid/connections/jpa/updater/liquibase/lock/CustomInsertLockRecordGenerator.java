/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.repeid.connections.jpa.updater.liquibase.lock;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.InitializeDatabaseChangeLogLockTableStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * We need to remove DELETE SQL command, which liquibase adds by default when inserting record to table lock. This is causing buggy behaviour
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class CustomInsertLockRecordGenerator extends AbstractSqlGenerator<InitializeDatabaseChangeLogLockTableStatement> {

    @Override
    public int getPriority() {
        return super.getPriority() + 1; // Ensure bigger priority than InitializeDatabaseChangeLogLockTableGenerator
    }

    @Override
    public ValidationErrors validate(InitializeDatabaseChangeLogLockTableStatement initializeDatabaseChangeLogLockTableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }

    @Override
    public Sql[] generateSql(InitializeDatabaseChangeLogLockTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        // Generated by InitializeDatabaseChangeLogLockTableGenerator
        Sql[] sqls = sqlGeneratorChain.generateSql(statement, database);

        // Removing delete statement
        List<Sql> result = new ArrayList<>();
        for (Sql sql : sqls) {
            String sqlCommand = sql.toSql();
            if (!sqlCommand.toUpperCase().contains("DELETE")) {
                result.add(sql);
            }
        }

        return result.toArray(new Sql[result.size()]);
    }
}
