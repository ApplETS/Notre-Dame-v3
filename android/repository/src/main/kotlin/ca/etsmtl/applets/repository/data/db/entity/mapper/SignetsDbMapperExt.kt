package ca.etsmtl.applets.repository.data.db.entity.mapper

import ca.etsmtl.applets.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationCoursEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.HoraireExamenFinalEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.JourRemplaceEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.ProgrammeEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SeanceEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SessionEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SommaireElementsEvaluationEntity
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Etudiant
import ca.etsmtl.applets.repository.data.model.Evaluation
import ca.etsmtl.applets.repository.data.model.EvaluationCours
import ca.etsmtl.applets.repository.data.model.HoraireExamenFinal
import ca.etsmtl.applets.repository.data.model.JourRemplace
import ca.etsmtl.applets.repository.data.model.Programme
import ca.etsmtl.applets.repository.data.model.Seance
import ca.etsmtl.applets.repository.data.model.Session
import ca.etsmtl.applets.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.applets.repository.util.zeroIfNullOrBlank
import java.util.Date

/**
 * Created by Sonphil on 09-07-18.
 */

fun CoursEntity.toCours() = Cours(
        this.sigle,
        this.groupe,
        this.session,
        this.programmeEtudes,
        this.cote,
        null,
        this.nbCredits,
        this.titreCours
)

fun List<CoursEntity>.toCours() = map { it.toCours() }

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
        this.dateCible,
        this.note.zeroIfNullOrBlank(),
        this.corrigeSur.zeroIfNullOrBlank(),
        this.notePourcentage.zeroIfNullOrBlank(),
        this.ponderation,
        this.moyenne.zeroIfNullOrBlank(),
        this.moyennePourcentage.zeroIfNullOrBlank(),
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
    Date(dateDebutEvaluation),
    Date(dateFinEvaluation),
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
        Date(dateDebut),
        Date(dateFin),
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
        note.zeroIfNullOrBlank(),
        noteSur.zeroIfNullOrBlank(),
        noteSur100.zeroIfNullOrBlank(),
        moyenneClasse.zeroIfNullOrBlank(),
        moyenneClassePourcentage.zeroIfNullOrBlank(),
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