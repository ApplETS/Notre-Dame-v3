package ca.etsmtl.applets.repository.data.model

data class Activite(
    var sigle: String,
    var groupe: String?,
    var jour: Int,
    var journee: String?,
    var codeActivite: String?,
    var nomActivite: String?,
    var activitePrincipale: String?,
    var heureDebut: String?,
    var heureFin: String?,
    var local: String?,
    var titreCours: String?
)