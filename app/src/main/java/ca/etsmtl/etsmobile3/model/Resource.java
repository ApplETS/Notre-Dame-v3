package ca.etsmtl.etsmobile3.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Sonphil on 31-08-17.
 */

public class Resource<T> {
    public static final int SUCCESS = 200;
    public static final int LOADING = 350;
    public static final int ERROR = 500;

    @NonNull
    public final int status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    private Resource(@NonNull int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}
