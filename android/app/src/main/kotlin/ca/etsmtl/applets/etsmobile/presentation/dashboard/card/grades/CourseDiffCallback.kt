package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.grades

import androidx.recyclerview.widget.DiffUtil
import model.Cours

/**
 * Created by Sonphil on 13-11-19.
 */

class CourseDiffCallback : DiffUtil.ItemCallback<Cours>() {
    override fun areItemsTheSame(oldItem: Cours, newItem: Cours): Boolean {
        return oldItem.sigle == newItem.sigle &&
            oldItem.groupe == newItem.groupe &&
            oldItem.session == newItem.session
    }

    override fun areContentsTheSame(oldItem: Cours, newItem: Cours): Boolean {
        return oldItem == newItem
    }
}