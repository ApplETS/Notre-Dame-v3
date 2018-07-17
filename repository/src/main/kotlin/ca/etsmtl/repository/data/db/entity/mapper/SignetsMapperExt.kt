package ca.etsmtl.repository.data.db.entity.mapper

import ca.etsmtl.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.repository.data.db.entity.signets.HoraireExamenFinalEntity
import ca.etsmtl.repository.data.db.entity.signets.JourRemplaceEntity
import ca.etsmtl.repository.data.db.entity.signets.SeanceEntity
import ca.etsmtl.repository.data.model.Etudiant
import ca.etsmtl.repository.data.model.HoraireExamenFinal
import ca.etsmtl.repository.data.model.JourRemplace
import ca.etsmtl.repository.data.model.Seance

/**
 * Created by Sonphil on 09-07-18.
 */

fun EtudiantEntity.toEtudiant() = Etudiant(
        this.type,
        this.nom,
        this.prenom,
        this.codePerm,
        this.soldeTotal,
        this.masculin
)

fun HoraireExamenFinalEntity.toHoraireExamenFinal() = HoraireExamenFinal(
        this.sigle,
        this.groupe,
        this.dateExamen,
        this.heureDebut,
        this.heureFin,
        this.local
)

fun List<HoraireExamenFinalEntity>.toHorairesExamensFinaux() = ArrayList<HoraireExamenFinal>().apply {
    this@toHorairesExamensFinaux.forEach { add(it.toHoraireExamenFinal()) }
}

fun JourRemplaceEntity.toJourRemplace() = JourRemplace(
        this.dateOrigine,
        this.dateRemplacement,
        this.description
)

fun List<JourRemplaceEntity>.toJoursRemplaces(): List<JourRemplace> = ArrayList<JourRemplace>().apply {
    this@toJoursRemplaces.forEach { add(it.toJourRemplace()) }
}

fun SeanceEntity.toSeance() = Seance(
        this.dateDebut,
        this.dateFin,
        this.cours.sigle,
        this.cours.groupe,
        this.nomActivite,
        this.local,
        this.descriptionActivite,
        this.cours.titreCours
)

fun List<SeanceEntity>.toSeances(): List<Seance> = ArrayList<Seance>().apply {
    this@toSeances.forEach {
        add(it.toSeance())
    }
}