package ca.etsmtl.repository.data.api.response.mapper.signets

import ca.etsmtl.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.repository.data.model.signets.Etudiant

/**
 * Created by Sonphil on 08-07-18.
 */

fun ApiEtudiant.toEtudiantEntity() = Etudiant(
        this.type,
        untrimmedNom.trim(),
        untrimmedPrenom.trim(),
        this.codePerm,
        this.soldeTotal,
        this.masculin,
        this.erreur
)