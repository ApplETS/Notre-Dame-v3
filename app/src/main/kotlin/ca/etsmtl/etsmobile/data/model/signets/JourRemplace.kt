package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity(primaryKeys = ["dateOrigine", "dateRemplacement"])
@JsonSerializable
data class JourRemplace(
    @Json(name = "dateOrigine") var dateOrigine: String,
    @Json(name = "dateRemplacement") var dateRemplacement: String,
    @Json(name = "description") var untrimmedDescription: String?
) {
    val description: String?
        get() = untrimmedDescription?.trim()
}