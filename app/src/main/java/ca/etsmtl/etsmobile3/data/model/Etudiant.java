package ca.etsmtl.etsmobile3.data.model;

import com.squareup.moshi.Json;

public class Etudiant {

    @Json(name = "d")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
