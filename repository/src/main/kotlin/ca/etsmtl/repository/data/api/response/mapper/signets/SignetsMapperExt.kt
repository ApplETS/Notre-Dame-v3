package ca.etsmtl.repository.data.api.response.mapper.signets

import ca.etsmtl.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.repository.data.db.entity.signets.EtudiantEntity

/**
 * Created by Sonphil on 08-07-18.
 */

fun ApiEtudiant.toEtudiantEntity() = EtudiantEntity(
        this.type,
        untrimmedNom.trim(),
        untrimmedPrenom.trim(),
        this.codePerm,
        this.soldeTotal,
        this.masculin
)