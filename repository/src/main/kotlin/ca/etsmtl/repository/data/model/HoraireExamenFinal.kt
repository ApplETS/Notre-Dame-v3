package ca.etsmtl.repository.data.model

data class HoraireExamenFinal(
    var sigle: String,
    var groupe: String?,
    var dateExamen: String?,
    var heureDebut: String?,
    var heureFin: String?,
    var local: String?
)