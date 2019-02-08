package ca.etsmtl.applets.etsmobile.presentation.dashboard

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCard
import ca.etsmtl.applets.etsmobile.presentation.dashboard.card.DashboardCardType
import ca.etsmtl.applets.etsmobile.util.Event
import java.util.Collections
import javax.inject.Inject

const val SHOW_CARD_PREF_KEY_PREFIX = "ShowCard"
const val POSITION_CARD_PREF_KEY_PREFIX = "PositionCard"

class DashboardViewModel @Inject constructor(private val prefs: SharedPreferences) : ViewModel() {
    private val cardList by lazy {
        DashboardCardType.values()
            .filter { type ->
                prefs.getBoolean(SHOW_CARD_PREF_KEY_PREFIX + type, true)
            }
            .map { type ->
                DashboardCard(type)
            }
            .sortedBy { card ->
                prefs.getInt(POSITION_CARD_PREF_KEY_PREFIX + card.type, card.type.ordinal)
            }
            .toMutableList()
    }

    private val _cards = MutableLiveData<List<DashboardCard>>().apply {
        value = cardList
    }
    val cards: LiveData<List<DashboardCard>> = _cards
    private val lastCardRemoved = MutableLiveData<DashboardCard?>()
    private val _showUndoCardRemove = MutableLiveData<Event<Unit>>()
    val showUndoCardRemove: LiveData<Event<Unit>> = _showUndoCardRemove

    fun moveCard(fromPosition: Int, toPosition: Int) {
        prefs.edit {
            putInt(POSITION_CARD_PREF_KEY_PREFIX + cardList[fromPosition].type, toPosition)
            putInt(POSITION_CARD_PREF_KEY_PREFIX + cardList[toPosition].type, fromPosition)
        }
        Collections.swap(cardList, fromPosition, toPosition)
    }

    fun removeCard(position: Int) {
        prefs.edit {
            putBoolean(SHOW_CARD_PREF_KEY_PREFIX + cardList[position].type, false)
            if (position < cardList.size - 1) {
                putInt(POSITION_CARD_PREF_KEY_PREFIX + cardList[position + 1].type, position)
            }
        }
        lastCardRemoved.value = cardList.removeAt(position)
        _showUndoCardRemove.value = Event(Unit)
    }

    fun undoLastRemove() {
        val removedCard = lastCardRemoved.value
        lastCardRemoved.value = null

        if (removedCard != null) {
            prefs.edit {
                putBoolean(SHOW_CARD_PREF_KEY_PREFIX + removedCard.type, true)
            }

            val position = prefs.getInt(
                POSITION_CARD_PREF_KEY_PREFIX + removedCard.type,
                removedCard.type.ordinal
            )

            cardList.add(position, removedCard)

            _cards.value = cardList
        }
    }
}