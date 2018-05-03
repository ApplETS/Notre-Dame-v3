package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class JourRemplace(
    @Json(name = "dateOrigine") val dateOrigine: String? = "",
    @Json(name = "dateRemplacement") val dateRemplacement: String? = "",
    @Json(name = "description") val untrimmedDescription: String? = ""
) {
    val description: String?
        get() = untrimmedDescription?.trim()
}