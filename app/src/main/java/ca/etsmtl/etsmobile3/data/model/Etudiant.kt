package ca.etsmtl.etsmobile3.data.model

import com.squareup.moshi.Json

class Etudiant: SignetsModel() {
    @Json(name = "d")
    var data: EtudiantData? = null

    override fun getSignetsData(): SignetsData? {
        return data
    }
}
