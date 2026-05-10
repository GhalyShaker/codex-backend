package com.codex.backend.system.business.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T value);

}
