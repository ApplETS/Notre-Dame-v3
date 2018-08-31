package ca.etsmtl.etsmobile.presentation.grades

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.etsmobile.R
import ca.etsmtl.repository.data.model.Evaluation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_evaluation.textViewEvaluationGrade
import kotlinx.android.synthetic.main.item_evaluation.textViewEvaluationName

/**
 * Created by Sonphil on 29-08-18.
 */

class EvaluationAdapter : RecyclerView.Adapter<EvaluationAdapter.EvaluationViewHolder>() {

    val differ = AsyncListDiffer<Evaluation>(this, object : DiffUtil.ItemCallback<Evaluation>() {
        override fun areItemsTheSame(e0: Evaluation, e1: Evaluation): Boolean {
            return e0.nom == e1.nom
                    && e0.groupe == e1.groupe
                    && e0.cours == e1.cours
                    && e0.session == e1.session
        }

        override fun areContentsTheSame(e0: Evaluation, e1: Evaluation) = e0 == e1
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvaluationViewHolder {
        return EvaluationViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_evaluation, parent, false))
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: EvaluationViewHolder, position: Int) {
        with (differ.currentList[position]) {
            holder.nameTv.text = this.nom
            holder.gradeTv.text = this.note + " / " + this.corrigeSur
        }
    }

    class EvaluationViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val nameTv = textViewEvaluationName
        val gradeTv = textViewEvaluationGrade
    }
}