package ca.etsmtl.applets.etsmobile.presentation.security

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_security.*

class SecurityAdapter(private val securityList: List<String>) : RecyclerView.Adapter<SecurityAdapter.SecurityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SecurityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_security, parent, false)
    )

    override fun getItemCount(): Int = securityList.size
    //TODO Completer le clickListener pour les differentes type d'urgence
    override fun onBindViewHolder(holder: SecurityViewHolder, position: Int) {
            holder.titleTypeUrgence.text=securityList[position]

//            holder.containerView.setOnClickListener {
//            }

    }

    class SecurityViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

}