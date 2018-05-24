package ca.etsmtl.etsmobile.data.model.signets

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@Entity
@JsonSerializable
data class JourRemplace(
    @Json(name = "dateOrigine") var dateOrigine: String,
    @PrimaryKey
    @Json(name = "dateRemplacement") var dateRemplacement: String,
    @Json(name = "description") var untrimmedDescription: String?
) {
    val description: String?
        get() = untrimmedDescription?.trim()
}