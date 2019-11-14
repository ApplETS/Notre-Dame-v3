package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.grades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.extension.getDisplayableCote
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_grade_course.tvCourseGrade
import kotlinx.android.synthetic.main.item_grade_course.tvCourseSigle
import model.Cours

/**
 * Created by Sonphil on 03-03-19.
 */

class GradesCardAdapter : ListAdapter<Cours, GradesCardAdapter.GradeViewHolder>(CourseDiffCallback()) {
    var onCourseClickListener: OnCourseClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GradeViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_grade_course,
            parent,
            false
        )
    ).apply {
        itemView.setOnClickListener {
            onCourseClickListener?.onCourseClick(getItem(adapterPosition), this)
        }
    }

    override fun onBindViewHolder(holder: GradeViewHolder, position: Int) {
        val course = getItem(position)

        holder.tvCourseGrade.text = course.getDisplayableCote(holder.containerView.context)
        holder.tvCourseSigle.text = course.sigle
        ViewCompat.setTransitionName(holder.tvCourseGrade, course.sigle)
    }

    class GradeViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

    /**
     * Interface definition for a callback to be invoked when a course of the recycler view is clicked
     */
    interface OnCourseClickListener {
        /**
         * Callback method to be invoked when an item of the recycler view is clicked
         *
         * @param cours The [Cours] clicked
         * @param holder The holder of clicked course
         */
        fun onCourseClick(cours: Cours, holder: GradeViewHolder)
    }
}
