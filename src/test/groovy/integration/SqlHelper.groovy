package integration

import groovy.sql.GroovyRowResult
import groovy.sql.Sql

class SqlHelper {

    Sql sql

    SqlHelper() {
        sql = Sql.newInstance(
                'jdbc:h2:mem:testdb',
                'sa',
                'password',
                'org.h2.Driver')
    }

    List<GroovyRowResult> selectAllFromAttendersByGroupId(int groupId) {
        sql.rows("SELECT * FROM ATTENDER WHERE group_id = $groupId" as String)
    }

    List<GroovyRowResult> selectAllFromGroups() {
        sql.rows("SELECT * FROM GROUPS" as String)
    }

    void cleanDatabase() {
        sql.execute("DELETE FROM ATTENDER" as String)
        sql.execute("DELETE FROM GROUPS" as String)
        sql.execute("DELETE FROM EVENTS" as String)
    }

}
