package ca.etsmtl.repository.data.db.entity.mapper

import ca.etsmtl.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.repository.data.model.Etudiant

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