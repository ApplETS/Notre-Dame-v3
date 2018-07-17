package ca.etsmtl.repository.data.api.response.mapper

import ca.etsmtl.repository.data.api.response.signets.ApiActivite
import ca.etsmtl.repository.data.api.response.signets.ApiCours
import ca.etsmtl.repository.data.api.response.signets.ApiEnseignant
import ca.etsmtl.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.repository.data.api.response.signets.ApiEvaluation
import ca.etsmtl.repository.data.db.entity.signets.ActiviteEntity
import ca.etsmtl.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.repository.data.db.entity.signets.EnseignantEntity
import ca.etsmtl.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.repository.data.db.entity.signets.EvaluationEntity

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

fun ApiEvaluation.toEvaluationEntity() = EvaluationEntity(
        this.coursGroupe,
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