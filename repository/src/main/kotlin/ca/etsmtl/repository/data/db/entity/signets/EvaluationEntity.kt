package ca.etsmtl.repository.data.db.entity.signets

import android.arch.persistence.room.Entity

@Entity(primaryKeys = ["coursGroupe", "nom"])
data class EvaluationEntity(
    var coursGroupe: String,
    var nom: String,
    var equipe: String?,
    var dateCible: String?,
    var note: String?,
    var corrigeSur: String?,
    var ponderation: String?,
    var moyenne: String?,
    var ecartType: String?,
    var mediane: String?,
    var rangCentile: String?,
    var publie: String?,
    var messageDuProf: String?,
    var ignoreDuCalcul: String?
)