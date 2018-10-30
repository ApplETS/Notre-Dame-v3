package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.repository.data.model.Seance
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_schedule.*

/**
 * Created by mykaelll87 on 2018-10-28
 */
class ScheduleAdapter(): RecyclerView.Adapter<ScheduleAdapter.SeanceViewHolder>(){

    private val differ = AsyncListDiffer<Seance>(this, object :DiffUtil.ItemCallback<Seance>(){
        override fun areContentsTheSame(oldItem: Seance, newItem: Seance): Boolean = oldItem==newItem

        override fun areItemsTheSame(oldItem: Seance, newItem: Seance): Boolean {
            return oldItem.sigleCours == newItem.sigleCours&&
                    oldItem.session == newItem.session &&
                    oldItem.dateDebut == newItem.dateDebut
        }

    })

    var items: List<Seance> = emptyList()
        set(value){
            field = value
            differ.submitList(mutableListOf<Seance>().apply {
                this.addAll(value)
            })
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeanceViewHolder = SeanceViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent,false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SeanceViewHolder, position: Int) {
        with(differ.currentList[position]){
            holder.titreTextView.text = this.libelleCours
            holder.sigleTextView.text = this.sigleCours
            holder.localTextView.text = this.local
            holder.startTextView.text = this.dateDebut.toString()
            holder.endTextView.text = this.dateFin.toString()
            holder.groupeTextView.text = "Allo"
        }
    }

    class SeanceViewHolder(override val containerView:View): RecyclerView.ViewHolder(containerView), LayoutContainer{
        val titreTextView = textViewScheduleTitreCours
        val sigleTextView = textViewScheduleSigle
        val groupeTextView = textViewScheduleGroupe
        val localTextView = textViewScheduleLocal
        val startTextView = textViewScheduleStartTime
        val endTextView = textViewScheduleEndTime
    }
}