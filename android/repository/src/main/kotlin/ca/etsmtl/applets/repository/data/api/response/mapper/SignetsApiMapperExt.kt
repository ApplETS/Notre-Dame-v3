package ca.etsmtl.applets.repository.data.api.response.mapper

import ca.etsmtl.applets.repository.data.api.response.signets.ApiActivite
import ca.etsmtl.applets.repository.data.api.response.signets.ApiCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiEnseignant
import ca.etsmtl.applets.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.applets.repository.data.api.response.signets.ApiEvaluation
import ca.etsmtl.applets.repository.data.api.response.signets.ApiEvaluationCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiHoraireExamenFinal
import ca.etsmtl.applets.repository.data.api.response.signets.ApiJourRemplace
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDeCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDeSessions
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDesElementsEvaluation
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDesSeances
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeEvaluationCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeHoraireExamensFinaux
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeJoursRemplaces
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeProgrammes
import ca.etsmtl.applets.repository.data.api.response.signets.ApiProgramme
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSeance
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSession
import ca.etsmtl.applets.repository.data.db.entity.signets.ActiviteEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EnseignantEntity
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
import ca.etsmtl.applets.repository.data.model.Session
import ca.etsmtl.applets.repository.util.*
import java.text.NumberFormat
import java.util.Locale

/**
 * Created by Sonphil on 08-07-18.
 */

val numberFormatter by lazy {
    NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 1
    }
}

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

fun ApiListeDeCours.toCoursEntities() = liste.map { it.toCoursEntity() }

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

fun ApiEvaluation.toEvaluationEntity(cours: Cours): EvaluationEntity {
    val note = this.note.replaceCommaAndParseToDouble()
    val moyenne = this.moyenne.replaceCommaAndParseToDouble()
    var notePourcentage = 0.0
    var moyennePourcentage = 0.0

    this.corrigeSur.substringBefore("+").replace(",", ".").toDoubleOrNull()?.let {
        if (it == 0.0) {
            notePourcentage = 0.0
            moyennePourcentage = 0.0
        } else {
            notePourcentage = note / it * 100
            moyennePourcentage = moyenne / it * 100
        }
    }

    return EvaluationEntity(
            cours.sigle,
            cours.groupe,
            cours.session,
            this.nom,
            this.equipe,
            dateCible.apply { if (isNotBlank()) { toLocaleDate() } },
            numberFormatter.format(note),
            numberFormatter.format(corrigeSur.replaceCommaAndParseToFloat()),
            numberFormatter.format(notePourcentage),
            numberFormatter.format(ponderation.replaceCommaAndParseToFloat()),
            numberFormatter.format(moyenne),
            numberFormatter.format(moyennePourcentage),
            numberFormatter.format(ecartType.replaceCommaAndParseToFloat()),
            numberFormatter.format(mediane.replaceCommaAndParseToFloat()),
            numberFormatter.format(rangCentile.replaceCommaAndParseToFloat()),
            this.publie == "Oui",
            this.messageDuProf,
            this.ignoreDuCalcul == "Oui"
    )
}

fun ApiListeDesElementsEvaluation.toEvaluationEntities(cours: Cours) = liste.map { it.toEvaluationEntity(cours) }

fun ApiListeDesElementsEvaluation.toSommaireEvaluationEntity(cours: Cours): SommaireElementsEvaluationEntity {
    val noteSur = liste.asSequence()
            .filter { it.note.isNotBlank() && it.ignoreDuCalcul == "Non" }
            .map { it.ponderation.replaceCommaAndParseToFloat() }
            .sum()
            .coerceAtMost(100f)

    val moyenneClassePourcentage = this.moyenneClasse.replaceCommaAndParseToFloat() / noteSur * 100

    return SommaireElementsEvaluationEntity(
            cours.sigle,
            cours.session,
            numberFormatter.format(scoreFinalSur100.replaceCommaAndParseToFloat()),
            numberFormatter.format(noteSur),
            numberFormatter.format(noteACeJour.replaceCommaAndParseToFloat()),
            numberFormatter.format(moyenneClasse.replaceCommaAndParseToFloat()),
            numberFormatter.format(moyenneClassePourcentage),
            numberFormatter.format(ecartTypeClasse.replaceCommaAndParseToFloat()),
            numberFormatter.format(medianeClasse.replaceCommaAndParseToFloat()),
            numberFormatter.format(rangCentileClasse.replaceCommaAndParseToFloat()),
            numberFormatter.format(noteACeJourElementsIndividuels.replaceCommaAndParseToFloat()),
            numberFormatter.format(noteSur100PourElementsIndividuels.replaceCommaAndParseToFloat())
    )
}

fun ApiEvaluationCours.toEvaluationCoursEntity(session: Session) = EvaluationCoursEntity(
    session.abrege,
    dateDebutEvaluation.msDateToUnix(),
    dateFinEvaluation.msDateToUnix(),
    enseignant,
    estComplete,
    groupe,
    sigle,
    typeEvaluation
)

fun ApiListeEvaluationCours.toEvaluationCoursEntities(session: Session) = listeApiEvaluations.map {
    it.toEvaluationCoursEntity(session)
}

fun ApiHoraireExamenFinal.toHoraireExamenFinalEntity(session: Session) = HoraireExamenFinalEntity(
        sigle,
        groupe,
        dateExamen,
        heureDebut,
        heureFin,
        local,
        session.abrege
)

fun ApiListeHoraireExamensFinaux.toHoraireExamensFinauxEntities(session: Session) = listeHoraire?.map {
    it.toHoraireExamenFinalEntity(session)
} ?: emptyList()

fun ApiJourRemplace.toJourRemplaceEntity(session: Session) = JourRemplaceEntity(
        this.dateOrigine,
        this.dateRemplacement,
        this.description,
        session.abrege
)

fun ApiListeJoursRemplaces.toJourRemplaceEntities(session: Session): List<JourRemplaceEntity>? {
    this.listeJours?.let { return it.map { it.toJourRemplaceEntity(session) } }

    return null
}

fun ApiProgramme.toProgrammeEntity() = ProgrammeEntity(
        code,
        libelle,
        profil,
        statut.capitalize(),
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

fun ApiListeProgrammes.toProgrammesEntities(): List<ProgrammeEntity> = liste.map { it.toProgrammeEntity() }

fun ApiSeance.toSeanceEntity(session: String) = SeanceEntity(
        dateDebut.msDateToUnix(),
        dateFin.msDateToUnix(),
        nomActivite,
        local,
        descriptionActivite,
        libelleCours,
        coursGroupe.substringBefore("-"),
    coursGroupe.substringAfter("-"),
        session
)

fun ApiListeDesSeances.toSeancesEntities(session: String): List<SeanceEntity> = liste.map { it.toSeanceEntity(session) }

fun ApiSession.toSessionEntity() = SessionEntity(
        abrege,
        auLong,
        dateDebut.signetsDefaultDateToUnix(),
        dateFin.signetsDefaultDateToUnix(),
        dateFinCours.signetsDefaultDateToUnix(),
        dateDebutChemiNot.signetsDefaultDateToUnix(),
        dateFinChemiNot.signetsDefaultDateToUnix(),
        dateDebutAnnulationAvecRemboursement.signetsDefaultDateToUnix(),
        dateFinAnnulationAvecRemboursement.signetsDefaultDateToUnix(),
        dateFinAnnulationAvecRemboursementNouveauxEtudiants.signetsDefaultDateToUnix(),
        dateDebutAnnulationSansRemboursementNouveauxEtudiants.signetsDefaultDateToUnix(),
        dateFinAnnulationSansRemboursementNouveauxEtudiants.signetsDefaultDateToUnix(),
        dateLimitePourAnnulerASEQ.signetsDefaultDateToUnix()
)

fun ApiListeDeSessions.toSessionEntities(): List<SessionEntity> = liste.map { it.toSessionEntity() }