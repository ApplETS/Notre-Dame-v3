package ca.etsmtl.applets.etsmobile.presentation.security

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_security.*

class SecurityAdapter(val securityList: ArrayList<SecurityModel>) : RecyclerView.Adapter<SecurityAdapter.SecurityViewHolder>() {
    //TODO Ajouter clickListener pour les differentes type d'urgence
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SecurityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_security, parent, false)
    )

    override fun getItemCount(): Int = securityList.size

    override fun onBindViewHolder(holder: SecurityViewHolder, position: Int) {
        with(securityList[position]) {
            holder.titleTypeUrgence.text = securityType
        }
    }

    class SecurityViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

}