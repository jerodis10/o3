package com.o3.openfeign.circuit;

import feign.FeignException;
import feign.RetryableException;

import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

public class DefaultExceptionRecordFailurePredicate implements Predicate<Throwable> {

    @Override
    public boolean test(Throwable t) {
        if (t instanceof TimeoutException) {
            return true;
        }

        if (t instanceof RetryableException) {
            return true;
        }

        return t instanceof FeignException.FeignServerException;
    }
}
