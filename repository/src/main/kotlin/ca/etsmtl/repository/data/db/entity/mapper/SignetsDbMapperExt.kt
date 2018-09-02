package ca.etsmtl.repository.data.db.entity.mapper

import ca.etsmtl.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.repository.data.db.entity.signets.EvaluationEntity
import ca.etsmtl.repository.data.db.entity.signets.HoraireExamenFinalEntity
import ca.etsmtl.repository.data.db.entity.signets.JourRemplaceEntity
import ca.etsmtl.repository.data.db.entity.signets.SeanceEntity
import ca.etsmtl.repository.data.db.entity.signets.SommaireElementsEvaluationEntity
import ca.etsmtl.repository.data.model.Cours
import ca.etsmtl.repository.data.model.Etudiant
import ca.etsmtl.repository.data.model.Evaluation
import ca.etsmtl.repository.data.model.HoraireExamenFinal
import ca.etsmtl.repository.data.model.JourRemplace
import ca.etsmtl.repository.data.model.Seance
import ca.etsmtl.repository.data.model.SommaireElementsEvaluation
import java.text.NumberFormat
import java.util.Locale

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

fun EvaluationEntity.toEvaluation(): Evaluation {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
    val note = this.note.replace(",", ".").toDoubleOrNull() ?: 0.0
    val moyenne = this.moyenne.replace(",", ".").toDoubleOrNull() ?: 0.0
    var notePourcentage = 0.0
    var moyennePourcentage = 0.0

    this.corrigeSur.substringBefore("+").toDoubleOrNull()?.let {
        notePourcentage = note / it * 100
        moyennePourcentage = moyenne / it * 100
    }

    return Evaluation(
            this.cours,
            this.groupe,
            this.session,
            this.nom,
            this.equipe,
            this.dateCible,
            formatter.format(note),
            this.corrigeSur,
            formatter.format(notePourcentage),
            this.ponderation,
            formatter.format(moyenne),
            formatter.format(moyennePourcentage),
            this.ecartType,
            this.mediane,
            this.rangCentile,
            this.publie,
            this.messageDuProf,
            this.ignoreDuCalcul
    )
}

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
        this.noteACeJour,
        this.scoreFinalSur100,
        this.moyenneClasse,
        this.ecartTypeClasse,
        this.medianeClasse,
        this.rangCentileClasse,
        this.noteACeJourElementsIndividuels,
        this.noteSur100PourElementsIndividuels
)