package ca.etsmtl.etsmobile.presentation.infoetudiant

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.etsmtl.etsmobile.R
import ca.etsmtl.repos.data.model.signets.Etudiant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_info_etudiant.textViewInfoEtudiantItemLabel
import kotlinx.android.synthetic.main.item_info_etudiant.textViewInfoEtudiantItemValue

/**
 * Created by Sonphil on 02-05-18.
 */
class InfoEtudiantAdapter : RecyclerView.Adapter<InfoEtudiantAdapter.ViewHolder>() {

    private var etudiant: Etudiant? = null

    enum class INFORMATION {
        NAME, PERMANENT_CODE, BALANCE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_info_etudiant, parent, false)
    )

    override fun getItemCount(): Int = INFORMATION.values().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        etudiant?.let { etudiant ->
            when (position) {
                INFORMATION.NAME.ordinal -> setInfo(holder, R.string.label_name_info_etudiant, etudiant.prenom + " " + etudiant.nom)
                INFORMATION.PERMANENT_CODE.ordinal -> setInfo(holder, R.string.label_permanent_code_info_etudiant, etudiant.codePerm)
                INFORMATION.BALANCE.ordinal -> setInfo(holder, R.string.label_balance_info_etudiant, etudiant.soldeTotal
                        ?: "")
            }
        }
    }

    fun setEtudiant(etudiant: Etudiant) {
        this.etudiant = etudiant
        notifyDataSetChanged()
    }

    private fun setInfo(holder: ViewHolder, @StringRes labelId: Int, info: String) {
        holder.labelTextView.setText(labelId)
        holder.infoTextView.text = info
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val infoTextView: TextView = textViewInfoEtudiantItemValue
        val labelTextView: TextView = textViewInfoEtudiantItemLabel
    }
}