package ca.etsmtl.repository.data.db.entity.mapper

import ca.etsmtl.repository.data.db.entity.signets.*
import ca.etsmtl.repository.data.model.*

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

fun List<JourRemplaceEntity>.toJoursRemplaces(): List<JourRemplace> = ArrayList<JourRemplace>().apply {
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
        this.noteACeJour,
        this.scoreFinalSur100,
        this.moyenneClasse,
        this.ecartTypeClasse,
        this.medianeClasse,
        this.rangCentileClasse,
        this.noteACeJourElementsIndividuels,
        this.noteSur100PourElementsIndividuels
)