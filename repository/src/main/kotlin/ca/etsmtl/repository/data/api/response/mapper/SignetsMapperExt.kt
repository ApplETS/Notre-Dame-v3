package ca.etsmtl.repository.data.api.response.mapper

import ca.etsmtl.repository.data.api.response.signets.ApiActivite
import ca.etsmtl.repository.data.api.response.signets.ApiCours
import ca.etsmtl.repository.data.api.response.signets.ApiEnseignant
import ca.etsmtl.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.repository.data.api.response.signets.ApiEvaluation
import ca.etsmtl.repository.data.api.response.signets.ApiHoraireExamenFinal
import ca.etsmtl.repository.data.api.response.signets.ApiJourRemplace
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesSeances
import ca.etsmtl.repository.data.api.response.signets.ApiListeJoursRemplaces
import ca.etsmtl.repository.data.api.response.signets.ApiSeance
import ca.etsmtl.repository.data.db.entity.signets.ActiviteEntity
import ca.etsmtl.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.repository.data.db.entity.signets.EnseignantEntity
import ca.etsmtl.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.repository.data.db.entity.signets.EvaluationEntity
import ca.etsmtl.repository.data.db.entity.signets.HoraireExamenFinalEntity
import ca.etsmtl.repository.data.db.entity.signets.JourRemplaceEntity
import ca.etsmtl.repository.data.db.entity.signets.SeanceEntity

/**
 * Created by Sonphil on 08-07-18.
 */

fun ApiActivite.toActiviteEntity() = ActiviteEntity(
        this.sigle,
        this.groupe,
        this.jour,
        this.journee,
        this.codeActivite,
        this.nomActivite,
        this.activitePrincipale,
        this.heureDebut,
        this.heureFin,
        this.local,
        this.titreCours
)

fun ApiCours.toCoursEntity() = CoursEntity(
        this.sigle,
        this.groupe,
        this.session,
        this.programmeEtudes,
        this.cote,
        this.nbCredits,
        this.titreCours
)

fun ApiEnseignant.toEnseignantEntity() = EnseignantEntity(
        this.localBureau,
        this.telephone,
        this.enseignantPrincipal,
        this.nom,
        this.prenom,
        this.courriel
)

fun ApiEtudiant.toEtudiantEntity() = EtudiantEntity(
        this.type,
        untrimmedNom.trim(),
        untrimmedPrenom.trim(),
        this.codePerm,
        this.soldeTotal,
        this.masculin
)

fun ApiEvaluation.toEvaluationEntity() = EvaluationEntity(
        this.coursGroupe,
        this.nom,
        this.equipe,
        this.dateCible,
        this.note,
        this.corrigeSur,
        this.ponderation,
        this.moyenne,
        this.ecartType,
        this.mediane,
        this.rangCentile,
        this.publie,
        this.messageDuProf,
        this.ignoreDuCalcul
)

fun ApiHoraireExamenFinal.toHoraireExemanFinalEntity() = HoraireExamenFinalEntity(
        this.sigle,
        this.groupe,
        this.dateExamen,
        this.heureDebut,
        this.heureFin,
        this.local
)

fun ApiJourRemplace.toJourRemplaceEntity() = JourRemplaceEntity(
        this.dateOrigine,
        this.dateRemplacement,
        this.description
)

fun ApiListeJoursRemplaces.toJourRemplaceEntities(): List<JourRemplaceEntity>? {
    this.listeJours?.let {
        return ArrayList<JourRemplaceEntity>().apply {
            it.forEach {
                this.add(it.toJourRemplaceEntity())
            }
        }
    }

    return null
}

fun ApiSeance.toSeanceEntity() = SeanceEntity(
        this.dateDebut,
        this.dateFin,
        this.coursGroupe,
        this.nomActivite,
        this.local,
        this.descriptionActivite,
        this.libelleCours
)

fun ApiListeDesSeances.toSeancesEntities(): List<SeanceEntity> = ArrayList<SeanceEntity>().apply {
    this@toSeancesEntities.liste.forEach {
        add(it.toSeanceEntity())
    }
}