package com.demo.skiapp.util;

import com.demo.skiapp.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class ServiceUtils {

    public <T, V> T shouldFindById(V searchCriteria, Function<V, Optional<T>> findByOptionalFunction) {
        return findByOptionalFunction.apply(searchCriteria).orElseThrow(() ->
                new ServiceException(String.format("Unable to find mandatory resource with parameter: %s", searchCriteria), HttpStatus.NOT_FOUND));
    }
}
