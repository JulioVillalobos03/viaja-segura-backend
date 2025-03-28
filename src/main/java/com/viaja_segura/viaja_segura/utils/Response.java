package com.viaja_segura.viaja_segura.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Response<T> {
    public T data;
    public boolean error;
    public int status;
    public String message;

    public Response(T data, boolean error, int status, String message) {
        this.data = data;
        this.error = error;
        this.status = status;
        this.message = message;
    }
}
