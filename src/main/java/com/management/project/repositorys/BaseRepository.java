package com.management.project.repositorys;

public interface BaseRepository <D,T>{
    D FindWithNativeQuery(T entity, Long entityId);
}
