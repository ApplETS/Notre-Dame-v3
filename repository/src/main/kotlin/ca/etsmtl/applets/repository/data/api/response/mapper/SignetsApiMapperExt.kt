package ca.etsmtl.applets.repository.data.api.response.mapper

import ca.etsmtl.applets.repository.data.api.response.signets.ApiActivite
import ca.etsmtl.applets.repository.data.api.response.signets.ApiCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiEnseignant
import ca.etsmtl.applets.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.applets.repository.data.api.response.signets.ApiEvaluation
import ca.etsmtl.applets.repository.data.api.response.signets.ApiHoraireExamenFinal
import ca.etsmtl.applets.repository.data.api.response.signets.ApiJourRemplace
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDeCours
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDeSessions
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDesElementsEvaluation
import ca.etsmtl.applets.repository.data.api.response.signets.ApiListeDesSeances
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
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.HoraireExamenFinalEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.JourRemplaceEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.ProgrammeEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SeanceEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SessionEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SommaireElementsEvaluationEntity
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Session
import ca.etsmtl.applets.repository.util.dateToUnixms
import ca.etsmtl.applets.repository.util.msDateToUnix
import ca.etsmtl.applets.repository.util.replaceCommaAndParseToDouble
import ca.etsmtl.applets.repository.util.replaceCommaAndParseToFloat
import ca.etsmtl.applets.repository.util.toLocaleDate
import java.text.NumberFormat
import java.util.Locale

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

fun formatter(): NumberFormat = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
    maximumFractionDigits = 1
}

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

    val formatter = formatter()

    return EvaluationEntity(
            cours.sigle,
            cours.groupe,
            cours.session,
            this.nom,
            this.equipe,
            dateCible.toLocaleDate(),
            formatter.format(note),
            formatter.format(corrigeSur.replaceCommaAndParseToFloat()),
            formatter.format(notePourcentage),
            formatter.format(ponderation.replaceCommaAndParseToFloat()),
            formatter.format(moyenne),
            formatter.format(moyennePourcentage),
            formatter.format(ecartType.replaceCommaAndParseToFloat()),
            formatter.format(mediane.replaceCommaAndParseToFloat()),
            formatter.format(rangCentile.replaceCommaAndParseToFloat()),
            this.publie == "Oui",
            this.messageDuProf,
            this.ignoreDuCalcul == "Oui"
    )
}

fun ApiListeDesElementsEvaluation.toEvaluationEntities(cours: Cours) = liste.map { it.toEvaluationEntity(cours) }

fun ApiListeDesElementsEvaluation.toSommaireEvaluationEntity(cours: Cours): SommaireElementsEvaluationEntity {
    val formatter = formatter()

    val noteSur = liste.asSequence()
            .filter { it.note.isNotBlank() && it.ignoreDuCalcul == "Non" }
            .map { it.ponderation.replaceCommaAndParseToFloat() }
            .sum()
            .coerceAtMost(100f)

    val moyenneClassePourcentage = this.moyenneClasse.replaceCommaAndParseToFloat() / noteSur * 100

    return SommaireElementsEvaluationEntity(
            cours.sigle,
            cours.session,
            formatter.format(scoreFinalSur100.replaceCommaAndParseToFloat()),
            formatter.format(noteSur),
            formatter.format(noteACeJour.replaceCommaAndParseToFloat()),
            formatter.format(moyenneClasse.replaceCommaAndParseToFloat()),
            formatter.format(moyenneClassePourcentage),
            formatter.format(ecartTypeClasse.replaceCommaAndParseToFloat()),
            formatter.format(medianeClasse.replaceCommaAndParseToFloat()),
            formatter.format(rangCentileClasse.replaceCommaAndParseToFloat()),
            formatter.format(noteACeJourElementsIndividuels.replaceCommaAndParseToFloat()),
            formatter.format(noteSur100PourElementsIndividuels.replaceCommaAndParseToFloat())
    )
}

fun ApiHoraireExamenFinal.toHoraireExemanFinalEntity(session: Session) = HoraireExamenFinalEntity(
        this.sigle,
        this.groupe,
        this.dateExamen,
        this.heureDebut,
        this.heureFin,
        this.local,
        session.abrege
)

fun ApiListeHoraireExamensFinaux.toHoraireExamensFinauxEntities(session: Session) = listeHoraire?.map {
    it.toHoraireExemanFinalEntity(session)
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
        dateDebut.dateToUnixms("yyyy-MM-dd") ,
        dateFin.dateToUnixms("yyyy-MM-dd") ,
        dateFinCours.dateToUnixms("yyyy-MM-dd"),
        dateDebutChemiNot.dateToUnixms("yyyy-MM-dd"),
        dateFinChemiNot.dateToUnixms("yyyy-MM-dd"),
        dateDebutAnnulationAvecRemboursement.dateToUnixms("yyyy-MM-dd"),
        dateFinAnnulationAvecRemboursement.dateToUnixms("yyyy-MM-dd"),
        dateFinAnnulationAvecRemboursementNouveauxEtudiants.dateToUnixms("yyyy-MM-dd"),
        dateDebutAnnulationSansRemboursementNouveauxEtudiants.dateToUnixms("yyyy-MM-dd"),
        dateFinAnnulationSansRemboursementNouveauxEtudiants.dateToUnixms("yyyy-MM-dd"),
        dateLimitePourAnnulerASEQ.dateToUnixms("yyyy-MM-dd")
)

fun ApiListeDeSessions.toSessionEntities(): List<SessionEntity> = liste.map { it.toSessionEntity() }