package ca.etsmtl.applets.etsmobile.presentation.dashboard

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCard
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCardType
import java.util.Collections
import javax.inject.Inject

const val SHOW_CARD_PREF_KEY_PREFIX = "ShowCard"

class DashboardViewModel @Inject constructor(private val prefs: SharedPreferences) : ViewModel() {
    private val cardList by lazy {
        DashboardCardType.values()
            .filter { type ->
                prefs.getBoolean(SHOW_CARD_PREF_KEY_PREFIX + type, true)
            }
            .map { type ->
                DashboardCard(type)
            }
            .toMutableList()
    }

    private val _cards = MutableLiveData<List<DashboardCard>>().apply {
        value = cardList
    }
    val cards: LiveData<List<DashboardCard>> = _cards

    fun moveCard(fromPosition: Int, toPosition: Int) {
        Collections.swap(cardList, fromPosition, toPosition)
    }

    fun removeCard(position: Int) {
        prefs.edit {
            putBoolean(SHOW_CARD_PREF_KEY_PREFIX + cardList[position].type, false)
        }
        cardList.removeAt(position)
    }
}