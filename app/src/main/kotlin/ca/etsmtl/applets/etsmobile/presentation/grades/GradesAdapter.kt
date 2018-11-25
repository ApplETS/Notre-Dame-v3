package ca.etsmtl.applets.etsmobile.presentation.grades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesAdapter.CourseGradeViewHolder.GradeViewHolder
import ca.etsmtl.applets.etsmobile.presentation.grades.GradesAdapter.CourseGradeViewHolder.HeaderViewHolder
import ca.etsmtl.applets.repository.data.model.Cours
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.header_grade_course.tvSessionGrades
import kotlinx.android.synthetic.main.item_grade_course.tvCourseGrade
import kotlinx.android.synthetic.main.item_grade_course.tvCourseSigle

/**
 * Created by Sonphil on 12-08-18.
 */

class GradesAdapter(private val onCourseClickListener: OnCourseClickListener) : RecyclerView.Adapter<GradesAdapter.CourseGradeViewHolder>() {

    private var itemsList: List<Any> = emptyList()
    var items: Map<String, List<Cours>> = emptyMap()
        set(value) {
            field = value

            val newItemsList = mutableListOf<Any>().apply {
                value.forEach {
                    this.add(it.key)
                    it.value.forEach { cours ->
                        this.add(cours)
                    }
                }
            }
            val diffCallback = object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = itemsList[oldItemPosition]
                    val newItem = newItemsList[newItemPosition]

                    return if (oldItem is Cours && newItem is Cours) {
                        oldItem.sigle == newItem.sigle && oldItem.groupe == newItem.groupe && oldItem.session == newItem.session
                    } else {
                        oldItem == newItem
                    }
                }

                override fun getOldListSize() = itemsList.count()

                override fun getNewListSize() = newItemsList.count()

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem = itemsList[oldItemPosition]
                    val newItem = newItemsList[newItemPosition]

                    return oldItem == newItem
                }
            }

            val diffResult = DiffUtil.calculateDiff(diffCallback)
            itemsList = newItemsList
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseGradeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.item_grade_course -> GradeViewHolder(view)
            R.layout.header_grade_course -> HeaderViewHolder(view)
            else -> throw IllegalStateException("Unknown viewType $viewType")
        }
    }

    override fun getItemCount() = itemsList.count()

    override fun getItemViewType(position: Int): Int {
        return when (itemsList[position]) {
            is String -> R.layout.header_grade_course
            is Cours -> R.layout.item_grade_course
            else -> throw IllegalStateException("Unknown view type at position $position")
        }
    }

    override fun onBindViewHolder(holder: CourseGradeViewHolder, position: Int) {
        when (holder) {
            is GradeViewHolder -> {
                with(itemsList[position] as Cours) {
                    holder.tvCourseGrade.apply {
                        text = when {
                            !this@with.cote.isNullOrEmpty() -> this@with.cote
                            !this@with.noteSur100.isNullOrEmpty() -> {
                                String.format(
                                        context.getString(R.string.text_grade_in_percentage),
                                        this@with.noteSur100
                                )
                            }
                            else -> context.getString(R.string.abbreviation_not_available)
                        }
                    }

                    holder.tvCourseSigle.text = this.sigle

                    ViewCompat.setTransitionName(holder.tvCourseGrade, this.sigle)

                    holder.itemView.setOnClickListener { onCourseClickListener.onCourseClick(this@with, holder) }
                }
            }
            is HeaderViewHolder -> {
                holder.tvSessionGrades.text = itemsList[position] as String
            }
        }
    }

    sealed class CourseGradeViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        class GradeViewHolder(override val containerView: View) : CourseGradeViewHolder(containerView)

        class HeaderViewHolder(override val containerView: View) : CourseGradeViewHolder(containerView)
    }

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
        fun onCourseClick(cours: Cours, holder: CourseGradeViewHolder)
    }
}