package ca.etsmtl.repository.data.db.entity.mapper

import ca.etsmtl.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.repository.data.db.entity.signets.JourRemplaceEntity
import ca.etsmtl.repository.data.db.entity.signets.SeanceEntity
import ca.etsmtl.repository.data.model.Etudiant
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

fun JourRemplaceEntity.toJourRemplace() = JourRemplace(
        this.dateOrigine,
        this.dateRemplacement,
        this.description
)

fun List<JourRemplaceEntity>.toJoursRemplaces(): List<JourRemplace> = ArrayList<JourRemplace>().apply {
    this@toJoursRemplaces.forEach {
        add(it.toJourRemplace())
    }
}

fun SeanceEntity.toSeance() = Seance(
        this.dateDebut,
        this.dateFin,
        this.coursGroupe,
        this.nomActivite,
        this.local,
        this.descriptionActivite,
        this.libelleCours
)

fun List<SeanceEntity>.toSeances(): List<Seance> = ArrayList<Seance>().apply {
    this@toSeances.forEach {
        add(it.toSeance())
    }
}