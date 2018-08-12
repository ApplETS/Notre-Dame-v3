package ca.etsmtl.etsmobile.presentation.about

import android.os.Bundle
import android.view.MenuItem
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.BaseActivity
import kotlinx.android.synthetic.main.include_toolbar.toolbar

/**
 * Created by Sonphil on 01-07-18.
 */

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)

        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setTitle(R.string.more_item_label_about_applets)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
