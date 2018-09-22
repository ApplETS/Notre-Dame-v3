package ca.etsmtl.repository.data.db.entity.mapper

import ca.etsmtl.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.repository.data.db.entity.signets.EvaluationEntity
import ca.etsmtl.repository.data.db.entity.signets.HoraireExamenFinalEntity
import ca.etsmtl.repository.data.db.entity.signets.JourRemplaceEntity
import ca.etsmtl.repository.data.db.entity.signets.SeanceEntity
import ca.etsmtl.repository.data.db.entity.signets.SommaireElementsEvaluationEntity
import ca.etsmtl.repository.data.db.entity.signets.SessionEntity
import ca.etsmtl.repository.data.model.Cours
import ca.etsmtl.repository.data.model.Etudiant
import ca.etsmtl.repository.data.model.Evaluation
import ca.etsmtl.repository.data.model.HoraireExamenFinal
import ca.etsmtl.repository.data.model.JourRemplace
import ca.etsmtl.repository.data.model.Seance
import ca.etsmtl.repository.data.model.SommaireElementsEvaluation
import ca.etsmtl.repository.data.model.Session

/**
 * Created by Sonphil on 09-07-18.
 */

fun CoursEntity.toCours() = Cours(
        this.sigle,
        this.groupe,
        this.session,
        this.programmeEtudes,
        this.cote,
        this.noteSur100,
        this.nbCredits,
        this.titreCours
)

fun List<CoursEntity>.toCours() = ArrayList<Cours>().apply {
    this@toCours.forEach { add(it.toCours()) }
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
        this.dateCible,
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

fun List<EvaluationEntity>.toEvaluations() = ArrayList<Evaluation>().apply {
    this@toEvaluations.forEach { add(it.toEvaluation()) }
}

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

fun List<JourRemplaceEntity>.toJoursRemplaces() = ArrayList<JourRemplace>().apply {
    this@toJoursRemplaces.forEach { add(it.toJourRemplace()) }
}

fun SeanceEntity.toSeance() = Seance(
        this.dateDebut,
        this.dateFin,
        this.nomActivite,
        this.local,
        this.descriptionActivite,
        this.libelleCours,
        this.sigleCours,
        this.session
)

fun List<SeanceEntity>.toSeances(): List<Seance> = ArrayList<Seance>().apply {
    this@toSeances.forEach { add(it.toSeance()) }
}

fun SommaireElementsEvaluationEntity.toSommaireEvaluation() = SommaireElementsEvaluation(
        this.sigleCours,
        this.session,
        this.note,
        this.noteSur,
        this.noteSur100,
        this.moyenneClasse,
        this.moyenneClassePourcentage,
        this.ecartTypeClasse,
        this.medianeClasse,
        this.rangCentileClasse,
        this.noteACeJourElementsIndividuels,
        this.noteSur100PourElementsIndividuels
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