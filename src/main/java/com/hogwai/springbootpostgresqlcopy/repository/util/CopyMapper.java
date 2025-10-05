package com.hogwai.springbootpostgresqlcopy.repository.util;

@FunctionalInterface
public interface CopyMapper<T> {
    String[] toCopyRow(T entity);
}

