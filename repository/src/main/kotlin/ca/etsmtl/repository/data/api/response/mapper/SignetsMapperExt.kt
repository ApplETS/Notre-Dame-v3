package ca.etsmtl.repository.data.api.response.mapper

import ca.etsmtl.repository.data.api.response.signets.ApiActivite
import ca.etsmtl.repository.data.api.response.signets.ApiCours
import ca.etsmtl.repository.data.api.response.signets.ApiEnseignant
import ca.etsmtl.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.repository.data.api.response.signets.ApiEvaluation
import ca.etsmtl.repository.data.api.response.signets.ApiHoraireExamenFinal
import ca.etsmtl.repository.data.api.response.signets.ApiJourRemplace
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesElementsEvaluation
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesSeances
import ca.etsmtl.repository.data.api.response.signets.ApiListeHoraireExamensFinaux
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
import ca.etsmtl.repository.data.db.entity.signets.SommaireElementsEvaluationEntity
import ca.etsmtl.repository.data.model.Cours
import ca.etsmtl.repository.data.model.Session

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

fun ApiEvaluation.toEvaluationEntity(cours: Cours) = EvaluationEntity(
        cours.sigle,
        cours.groupe,
        cours.session,
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
        this.publie == "Oui",
        this.messageDuProf,
        this.ignoreDuCalcul == "Oui"
)

fun ApiListeDesElementsEvaluation.toEvaluationEntities(cours: Cours) = ArrayList<EvaluationEntity>().apply {
    this@toEvaluationEntities.liste.forEach {
        add(it.toEvaluationEntity(cours))
    }
}

fun ApiListeDesElementsEvaluation.toSommaireEvaluationEntity(cours: Cours) = SommaireElementsEvaluationEntity(
        cours.sigle,
        cours.session,
        this.noteACeJour,
        this.scoreFinalSur100,
        this.moyenneClasse,
        this.ecartTypeClasse,
        this.medianeClasse,
        this.rangCentileClasse,
        this.noteACeJourElementsIndividuels,
        this.noteSur100PourElementsIndividuels
)

fun ApiHoraireExamenFinal.toHoraireExemanFinalEntity(session: Session) = HoraireExamenFinalEntity(
        this.sigle,
        this.groupe,
        this.dateExamen,
        this.heureDebut,
        this.heureFin,
        this.local,
        session.abrege
)

fun ApiListeHoraireExamensFinaux.toHoraireExamensFinauxEntities(session: Session) = ArrayList<HoraireExamenFinalEntity>().apply {
    this@toHoraireExamensFinauxEntities.listeHoraire?.forEach {
        add(it.toHoraireExemanFinalEntity(session))
    }
}

fun ApiJourRemplace.toJourRemplaceEntity(session: Session) = JourRemplaceEntity(
        this.dateOrigine,
        this.dateRemplacement,
        this.description,
        session.abrege
)

fun ApiListeJoursRemplaces.toJourRemplaceEntities(session: Session): List<JourRemplaceEntity>? {
    this.listeJours?.let {
        return ArrayList<JourRemplaceEntity>().apply {
            it.forEach {
                this.add(it.toJourRemplaceEntity(session))
            }
        }
    }

    return null
}

fun ApiSeance.toSeanceEntity(cours: Cours) = SeanceEntity(
        this.dateDebut,
        this.dateFin,
        CoursEntity(
                cours.sigle,
                cours.session,
                cours.programmeEtudes,
                cours.programmeEtudes,
                cours.cote,
                cours.nbCredits,
                cours.titreCours
        ),
        this.nomActivite,
        this.local,
        this.descriptionActivite
)

fun ApiListeDesSeances.toSeancesEntities(cours: Cours): List<SeanceEntity> = ArrayList<SeanceEntity>().apply {
    this@toSeancesEntities.liste.forEach {
        add(it.toSeanceEntity(cours))
    }
}