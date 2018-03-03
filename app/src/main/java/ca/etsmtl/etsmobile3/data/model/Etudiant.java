package ca.etsmtl.etsmobile3.data.model;

import com.squareup.moshi.Json;

public class Etudiant {

    @Json(name = "d")
    private EtudiantData etudiantData;

    public EtudiantData getEtudiantData() {
        return etudiantData;
    }

    public void setEtudiantData(EtudiantData etudiantData) {
        this.etudiantData = etudiantData;
    }
}
