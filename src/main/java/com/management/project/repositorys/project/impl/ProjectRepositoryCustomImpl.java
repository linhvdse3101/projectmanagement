package com.management.project.repositorys.project.impl;

import com.management.project.commons.PagingProvider;
import com.management.project.commons.database.CommonMapper;
import com.management.project.repositorys.Task.TaskRepositoryCustom;
import com.management.project.repositorys.project.ProjectRepositoryCustom;
import com.management.project.requests.FindProjectRequest;
import com.management.project.requests.FindTaskRequest;
import com.management.project.responses.ProjectResponse;
import com.management.project.responses.TaskResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<ProjectResponse> searchProject(Long userId, FindProjectRequest request) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> paramMap = new HashMap<>();
        sql.append("SELECT p.project_id, p.name ,acc.user_name, p.project_description  ");
        extractedSql(userId, request, sql, paramMap);
        // Sorting
        if (StringUtils.hasText(request.getSortBy()) && StringUtils.hasText(request.getSortDirection())) {
            sql.append("ORDER BY ").append(request.getSortBy()).append(" ").append(request.getSortDirection()).append(" ");
        } else {
            sql.append("ORDER BY p.project_id ASC "); // Default sorting
        }

        // Pagination
        sql.append("LIMIT :limit OFFSET :offset ");
        paramMap.put("limit", request.getPageSize());
        paramMap.put("offset", PagingProvider.calculateOffset(request.getPage(), request.getPageSize()));
        // Execute query
        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        paramMap.forEach(nativeQuery::setParameter);

        @SuppressWarnings("unchecked")
        List<Object[]> result = nativeQuery.getResultList();
        CommonMapper<ProjectResponse> mapper = new CommonMapper<>(ProjectResponse::new);
        return mapper.mapToObjects(result);
    }

    @Override
    public Integer countProject(Long userId, FindProjectRequest request) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> paramMap = new HashMap<>();
        sql.append("SELECT count(p.project_id)" );
        extractedSql(userId, request, sql, paramMap);
        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        paramMap.forEach(nativeQuery::setParameter);
        Object result = nativeQuery.getSingleResult();
        return result != null ? ((Number) result).intValue() : 0;
    }

    private void extractedSql(Long userId, FindProjectRequest request, StringBuilder sql, HashMap<String, Object> paramMap) {
        sql.append(" FROM st_project p LEFT JOIN user_account acc  ");
        sql.append(" on p.user_id = acc.user_id  ");
        sql.append(" WHERE acc.user_id = :userId " );
        paramMap.put("userId", userId);
        if (StringUtils.hasText(request.getQuery())){
            sql.append(" AND ( ");
            sql.append("p.project_description LIKE :query OR acc.user_name LIKE :query ");
            sql.append(" OR p.name LIKE :query  ");
            sql.append(" ) ");
            paramMap.put("query", "%" + request.getQuery() + "%");
        }
        if (StringUtils.hasText(request.getProjectName())){
            sql.append("AND p.name  = :projectName ");
            paramMap.put("projectName", request.getProjectName());
        }
    }

}
