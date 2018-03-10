package ca.etsmtl.etsmobile3.data.model

import com.squareup.moshi.Json

class EtudiantWrapper: SignetsModel() {
    @Json(name = "d")
    var data: Etudiant? = null

    override fun getSignetsData(): SignetsData? {
        return data
    }
}
