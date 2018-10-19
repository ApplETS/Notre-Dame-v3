package ca.etsmtl.applets.etsmobile.presentation.ets

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.etsmtl.applets.etsmobile.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_ets.appBarLayoutEts
import kotlinx.android.synthetic.main.fragment_ets.iVETSLogo
import kotlinx.android.synthetic.main.fragment_ets.ivEtsAppBar

/**
 * This fragment shows information related to the university.
 *
 * Created by Sonphil on 28-06-18.
 */

class EtsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolBar()
    }

    private fun setUpToolBar() {
        Glide.with(this)
                .load("https://etsmtl.ca/ETS/media/Prive/Accueil/slideshow/entete1-1.jpg")
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        appBarLayoutEts.setExpanded(false)

                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable>, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        appBarLayoutEts.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                            val maxScroll = appBarLayout.totalScrollRange
                            val ratio = Math.abs(verticalOffset) / maxScroll.toFloat()

                            iVETSLogo.alpha = when {
                                ratio >= MIN_VERTICAL_OFFSET_BEFORE_SHOWING_LOGO -> {
                                    (ratio - MIN_VERTICAL_OFFSET_BEFORE_SHOWING_LOGO) / (1 - MIN_VERTICAL_OFFSET_BEFORE_SHOWING_LOGO)
                                }
                                else -> 0f
                            }
                        })

                        return false
                    }
                })
                .into(ivEtsAppBar)
    }

    companion object {
        private const val TAG = "EtsFragment"
        private const val MIN_VERTICAL_OFFSET_BEFORE_SHOWING_LOGO = 0.3f
        fun newInstance() = EtsFragment()
    }
}
