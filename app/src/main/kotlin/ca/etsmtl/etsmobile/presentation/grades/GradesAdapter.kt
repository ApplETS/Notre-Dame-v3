package ca.etsmtl.etsmobile.presentation.grades

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.etsmtl.etsmobile.R
import ca.etsmtl.repository.data.model.Cours
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_grade_course.tvCourseGrade
import kotlinx.android.synthetic.main.item_grade_course.tvCourseSigle

/**
 * Created by Sonphil on 12-08-18.
 */

class GradesAdapter : RecyclerView.Adapter<GradesAdapter.ViewHolder>() {

    private val differ = AsyncListDiffer<Cours>(this, object: DiffUtil.ItemCallback<Cours>() {
        override fun areItemsTheSame(oldItem: Cours, newItem: Cours): Boolean {
            return oldItem.sigle == newItem.sigle && oldItem.groupe == newItem.groupe && oldItem.session == newItem.session
        }

        override fun areContentsTheSame(oldItem: Cours, newItem: Cours): Boolean = oldItem == newItem

    })
    var courses: List<Cours> = emptyList()
        set(value) {
            field = value
            differ.submitList(value)
        }

    init {
        differ.submitList(courses)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView: CardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_grade_course, parent, false) as CardView

        return ViewHolder(cardView)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(courses[position]) {
            holder.gradeTextView.text = when {
                !this.cote.isNullOrEmpty() -> this.cote
                !this.noteSur100.isNullOrEmpty() -> this.noteSur100
                else -> "--"
            }

            holder.sigleTextView.text = this.sigle
        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val gradeTextView: TextView = tvCourseGrade
        val sigleTextView: TextView = tvCourseSigle
    }
}