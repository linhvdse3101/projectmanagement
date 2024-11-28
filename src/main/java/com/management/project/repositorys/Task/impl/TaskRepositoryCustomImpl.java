package com.management.project.repositorys.Task.impl;

import com.management.project.commons.database.CommonMapper;
import com.management.project.repositorys.Task.TaskRepositoryCustom;
import com.management.project.requests.FindTaskRequest;
import com.management.project.responses.TaskResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<TaskResponse> searchTasks(FindTaskRequest request) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> paramMap = new HashMap<>();
        sql.append("SELECT t.task_id, t.task_name, t.task_status,acc.user_name, t.task_description, t.project_id ");
        extractedSql(request, sql, paramMap);
        // Sorting
        if (StringUtils.hasText(request.getSortBy()) && StringUtils.hasText(request.getSortDirection())) {
            sql.append("ORDER BY ").append(request.getSortBy()).append(" ").append(request.getSortDirection()).append(" ");
        } else {
            sql.append("ORDER BY t.task_id ASC "); // Default sorting
        }

        // Pagination
        sql.append("LIMIT :limit OFFSET :offset ");
        paramMap.put("limit", request.getPageSize());
        paramMap.put("offset", request.getPage() * request.getPageSize());
        // Execute query
        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        paramMap.forEach(nativeQuery::setParameter);

        @SuppressWarnings("unchecked")
        List<Object[]> result = nativeQuery.getResultList();
        CommonMapper<TaskResponse> mapper = new CommonMapper<>(TaskResponse::new);
        return mapper.mapToObjects(result);
    }

    @Override
    public Integer countTasks(FindTaskRequest request) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> paramMap = new HashMap<>();
        sql.append("SELECT count(t.task_id)" );
        extractedSql(request, sql, paramMap);
        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        paramMap.forEach(nativeQuery::setParameter);
        Object result = nativeQuery.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }

    private void extractedSql(FindTaskRequest request, StringBuilder sql, HashMap<String, Object> paramMap) {
        sql.append(" FROM st_task t LEFT JOIN st_project p  ");
        sql.append(" on t.project_id = p.project_id ");
        sql.append(" JOIN user_account acc on t.user_id = acc.user_id  ");
        sql.append(" WHERE t.project_id = :projectId AND t.delete_flag = false " );
        paramMap.put("projectId",request.getProjectId());

        if (StringUtils.hasText(request.getQuery())){
            sql.append(" AND ( ");
            sql.append("t.task_name LIKE :query OR acc.user_name LIKE :query ");
            sql.append(" ) ");
            paramMap.put("query", "%" + request.getQuery() + "%");
        }
    }

}
