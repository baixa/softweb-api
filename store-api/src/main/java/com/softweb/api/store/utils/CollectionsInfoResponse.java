package com.softweb.api.store.utils;

import java.io.Serializable;

public class CollectionsInfoResponse implements Serializable {
    public final Long total;
    public final Integer pageSize;
    public final Long pages;

    public CollectionsInfoResponse(Long total, Integer pageSize) {
        this.total = total;
        this.pageSize = pageSize;
        this.pages = (long) Math.ceil((double) total / pageSize);
    }
}
