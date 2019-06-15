package ca.etsmtl.applets.repository.data.db.entity.mapper

import ca.etsmtl.applets.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.CoursEntityAndNoteSur100
import ca.etsmtl.applets.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationCoursEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.HoraireExamenFinalEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.JourRemplaceEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.ProgrammeEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SeanceEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SessionEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SommaireElementsEvaluationEntity
import model.Cours
import model.Etudiant
import model.Evaluation
import model.EvaluationCours
import model.HoraireExamenFinal
import model.JourRemplace
import model.Programme
import model.Seance
import model.Session
import model.SommaireElementsEvaluation
import utils.date.ETSMobileDate

/**
 * Created by Sonphil on 09-07-18.
 */

fun CoursEntity.toCours(noteSur100: String?) = Cours(
        this.sigle,
        this.groupe,
        this.session,
        this.programmeEtudes,
        this.cote,
        noteSur100,
        this.nbCredits,
        this.titreCours
)

fun List<CoursEntityAndNoteSur100>.toCours() = map {
    val coursEntity = it.cours

    coursEntity.toCours(it.noteSur100)
}

fun EtudiantEntity.toEtudiant() = Etudiant(
        this.type,
        this.nom,
        this.prenom,
        this.codePerm,
        this.soldeTotal,
        this.masculin
)

fun EvaluationEntity.toEvaluation() = Evaluation(
        this.cours,
        this.groupe,
        this.session,
        this.nom,
        this.equipe,
        dateCible?.let { ETSMobileDate(it * 1000) },
        this.note,
        this.corrigeSur,
        this.notePourcentage,
        this.ponderation,
        this.moyenne,
        this.moyennePourcentage,
        this.ecartType,
        this.mediane,
        this.rangCentile,
        this.publie,
        this.messageDuProf,
        this.ignoreDuCalcul
)

fun List<EvaluationEntity>.toEvaluations() = map { it.toEvaluation() }

fun EvaluationCoursEntity.toEvaluationCours() = EvaluationCours(
    session,
    ETSMobileDate(dateDebutEvaluation * 1000),
    ETSMobileDate(dateFinEvaluation * 1000),
    enseignant,
    estComplete,
    groupe,
    sigle,
    typeEvaluation
)

fun List<EvaluationCoursEntity>.toEvaluationCours() = map { it.toEvaluationCours() }

fun HoraireExamenFinalEntity.toHoraireExamenFinal() = HoraireExamenFinal(
        this.sigle,
        this.groupe,
        this.dateExamen,
        this.heureDebut,
        this.heureFin,
        this.local
)

fun List<HoraireExamenFinalEntity>.toHorairesExamensFinaux() = map { it.toHoraireExamenFinal() }

fun JourRemplaceEntity.toJourRemplace() = JourRemplace(
        this.dateOrigine,
        this.dateRemplacement,
        this.description
)

fun List<JourRemplaceEntity>.toJoursRemplaces() = map { it.toJourRemplace() }

fun ProgrammeEntity.toProgramme() = Programme(
        code,
        libelle,
        profil,
        statut,
        sessionDebut,
        sessionFin,
        moyenne,
        nbEquivalences,
        nbCrsReussis,
        nbCrsEchoues,
        nbCreditsInscrits,
        nbCreditsCompletes,
        nbCreditsPotentiels,
        nbCreditsRecherche
)

fun List<ProgrammeEntity>.toProgrammes(): List<Programme> = map { it.toProgramme() }

fun SeanceEntity.toSeance() = Seance(
        ETSMobileDate(dateDebut * 1000),
        ETSMobileDate(dateFin * 1000),
        this.nomActivite,
        this.local,
        this.descriptionActivite,
        this.libelleCours,
        this.sigleCours,
        this.groupe,
        this.session
)

fun List<SeanceEntity>.toSeances(): List<Seance> = map { it.toSeance() }

fun SommaireElementsEvaluationEntity.toSommaireEvaluation() = SommaireElementsEvaluation(
        sigleCours,
        session,
        note,
        noteSur,
        noteSur100,
        moyenneClasse,
        moyenneClassePourcentage,
        ecartTypeClasse,
        medianeClasse,
        rangCentileClasse,
        noteACeJourElementsIndividuels,
        noteSur100PourElementsIndividuels
)

fun SessionEntity.toSession() = Session(
        abrege,
        auLong,
        dateDebut,
        dateFin,
        dateFinCours,
        dateDebutChemiNot,
        dateFinChemiNot,
        dateDebutAnnulationAvecRemboursement,
        dateFinAnnulationAvecRemboursement,
        dateFinAnnulationAvecRemboursementNouveauxEtudiants,
        dateDebutAnnulationSansRemboursementNouveauxEtudiants,
        dateFinAnnulationSansRemboursementNouveauxEtudiants,
        dateLimitePourAnnulerASEQ
)

fun List<SessionEntity>.toSessions(): List<Session> = map { it.toSession() }