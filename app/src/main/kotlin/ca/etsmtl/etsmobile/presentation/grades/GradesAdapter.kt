package ca.etsmtl.etsmobile.presentation.grades

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

    private var courses: List<Cours> = emptyList()

    fun setCourses(courses: List<Cours>) {
        this.courses = courses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView: CardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_grade_course, parent, false) as CardView

        return ViewHolder(cardView)
    }

    override fun getItemCount() = courses.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(courses[position]) {
            when {
                !this.cote.isNullOrEmpty() -> holder.gradeTextView.text = this.cote
                else -> holder.gradeTextView.text = this.noteSur100
            }

            holder.sigleTextView.text = this.sigle
        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val gradeTextView: TextView = tvCourseGrade
        val sigleTextView: TextView = tvCourseSigle
    }
}