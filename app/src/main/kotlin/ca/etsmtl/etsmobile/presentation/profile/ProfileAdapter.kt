package ca.etsmtl.etsmobile.presentation.profile

import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.etsmtl.etsmobile.R
import ca.etsmtl.repository.data.model.signets.Etudiant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_content_profile.textViewInfoProfileItemLabel
import kotlinx.android.synthetic.main.item_content_profile.textViewInfoProfileItemValue
import kotlinx.android.synthetic.main.item_profile.layoutProfileItemContent
import kotlinx.android.synthetic.main.item_profile.textViewTitleProfile

/**
 * Created by Sonphil on 02-05-18.
 */
class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    private var etudiant: Etudiant? = null

    enum class INFORMATION {
        TITLE_PERSONAL_INFO, FIRST_NAME, LAST_NAME, PERMANENT_CODE, BALANCE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_profile, parent, false)
    )

    override fun getItemCount(): Int = INFORMATION.values().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        etudiant?.let { etudiant ->
            when (position) {
                INFORMATION.TITLE_PERSONAL_INFO.ordinal -> setTitle(holder, R.string.title_personal_information_profile)
                INFORMATION.FIRST_NAME.ordinal -> setInfo(holder, R.string.label_first_name_profile, etudiant.prenom)
                INFORMATION.LAST_NAME.ordinal -> setInfo(holder, R.string.label_last_name_profile, etudiant.nom)
                INFORMATION.PERMANENT_CODE.ordinal -> setInfo(holder, R.string.label_permanent_code_profile, etudiant.codePerm)
                INFORMATION.BALANCE.ordinal -> setInfo(holder, R.string.label_balance_profile, etudiant.soldeTotal)
            }
        }
    }

    fun setEtudiant(etudiant: Etudiant) {
        this.etudiant = etudiant
        notifyDataSetChanged()
    }

    private fun setTitle(holder: ViewHolder, @StringRes titleId: Int) {
        holder.titleTextView.visibility = View.VISIBLE
        holder.itemContentLayout.visibility = View.GONE
        holder.titleTextView.setText(titleId)
    }

    private fun setInfo(holder: ViewHolder, @StringRes labelId: Int, info: String) {
        holder.titleTextView.visibility = View.GONE
        holder.itemContentLayout.visibility = View.VISIBLE
        holder.labelTextView.setText(labelId)
        holder.infoTextView.text = info
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        val titleTextView: TextView = textViewTitleProfile
        val itemContentLayout = layoutProfileItemContent
        val infoTextView: TextView = textViewInfoProfileItemLabel
        val labelTextView: TextView = textViewInfoProfileItemValue
    }
}