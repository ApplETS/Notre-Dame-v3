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
import kotlinx.android.synthetic.main.item_evaluation.tvAverage
import kotlinx.android.synthetic.main.item_evaluation.tvEvaluationGrade
import kotlinx.android.synthetic.main.item_evaluation.tvEvaluationName
import kotlinx.android.synthetic.main.item_evaluation.tvMedian
import kotlinx.android.synthetic.main.item_evaluation.tvPercentileRank
import kotlinx.android.synthetic.main.item_evaluation.tvSD
import kotlinx.android.synthetic.main.item_evaluation.tvTargetDate
import kotlinx.android.synthetic.main.item_evaluation.tvWeight

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
        holder.bindEvaluation(differ.currentList[position])
    }

    class EvaluationViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val nameTv = tvEvaluationName
        val weightTv = tvWeight
        val gradeTv = tvEvaluationGrade
        val averageTv = tvAverage
        val medianTv = tvMedian
        val standardDeviationTv = tvSD
        val percentileRankTv = tvPercentileRank
        val targetDateTv = tvTargetDate

        fun bindEvaluation(evaluation: Evaluation) {
            val context = nameTv.context

            nameTv.text = evaluation.nom
            weightTv.text = String.format(context.getString(R.string.text_weight), evaluation.ponderation)
            gradeTv.text = String.format(
                    context.getString(R.string.text_grade_with_percentage),
                    evaluation.note,
                    evaluation.corrigeSur,
                    evaluation.notePourcentage
            ) // TODO: Replace with graph
            averageTv.text = String.format(
                    context.getString(R.string.text_grade_with_percentage),
                    evaluation.moyenne,
                    evaluation.corrigeSur,
                    evaluation.moyennePourcentage
            )
            medianTv.text = evaluation.mediane
            standardDeviationTv.text = evaluation.ecartType
            percentileRankTv.text = evaluation.rangCentile
            targetDateTv.text = evaluation.dateCible
        }
    }
}