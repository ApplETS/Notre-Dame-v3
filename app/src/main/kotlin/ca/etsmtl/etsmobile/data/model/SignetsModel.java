package ca.etsmtl.etsmobile.data.model;

import com.squareup.moshi.Json;

/**
 * Created by Sonphil on 09-03-18.
 */

public class SignetsModel<T> {
    @Json(name = "d")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
