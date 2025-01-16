package kr.hhplus.be.server.common;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.Type;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataCleaner {
    private final EntityManager em;
    private final List<String> tableNames;

    public DataCleaner(final EntityManager em) {
        this.em = em;
        this.tableNames = em.getMetamodel()
            .getEntities()
            .stream()
            .map(Type::getJavaType)
            .map(javaType -> {
                Entity entityAnnotation = javaType.getAnnotation(Entity.class);
                Table tableAnnotation = javaType.getAnnotation(Table.class);

                // @Table이 있으면 해당 이름 사용, 없으면 클래스 이름을 테이블 이름으로 사용
                if (tableAnnotation != null) {
                    return tableAnnotation.name();
                } else if (entityAnnotation != null) {
                    return convertToSnakeCase(javaType.getSimpleName());
                } else {
                    throw new IllegalStateException("DataCleanError:Entity annotation이 존재하지 않습니다.");
                }
            })
            .toList();
    }

    private String convertToSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    @Transactional
    public void clean() {
        em.flush();
        tableNames.forEach(this::truncateTable);
    }

    private int truncateTable(final String tableName) {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate(); // FK 비활성화
        return em.createNativeQuery("truncate table " + tableName)
            .executeUpdate();
    }
}
