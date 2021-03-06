package ca.etsmtl.applets.etsmobile.presentation.security

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ca.etsmtl.applets.etsmobile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_security.titleTypeUrgence

class SecurityAdapter(
    private val securityList: Array<String>,
    private val findNavController: NavController
) : RecyclerView.Adapter<SecurityAdapter.SecurityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SecurityViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_security, parent, false)
    ).apply {
        itemView.setOnClickListener {
            val action = SecurityFragmentDirections.actionSecurityFragmentToSecurityDetailFragment(
                securityList[adapterPosition]
            )
            findNavController.navigate(action)
        }
    }

    override fun getItemCount() = securityList.size

    override fun onBindViewHolder(holder: SecurityViewHolder, position: Int) {
        holder.titleTypeUrgence.text = securityList[position]
    }

    class SecurityViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer
}