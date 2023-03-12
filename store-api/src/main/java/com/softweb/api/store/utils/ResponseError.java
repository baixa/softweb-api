package com.softweb.api.store.utils;

import java.io.Serializable;

public record ResponseError(String error) implements Serializable {
}
