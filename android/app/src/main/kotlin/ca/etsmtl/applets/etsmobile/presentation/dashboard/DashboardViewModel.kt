package ca.etsmtl.applets.etsmobile.presentation.dashboard

import android.content.SharedPreferences
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
    private val lastCardRemovedPosition = MutableLiveData<Int?>()
    private val _showUndoCardRemove = MutableLiveData<Event<Unit>>()
    val showUndoCardRemove: LiveData<Event<Unit>> = _showUndoCardRemove

    fun moveCard(fromPosition: Int, toPosition: Int) {
        Collections.swap(cardList, fromPosition, toPosition)
        _cards.value = cardList
    }

    fun removeCard(position: Int) {
        lastCardRemoved.value = cardList.removeAt(position)
        lastCardRemovedPosition.value = position
        _cards.value = cardList
        _showUndoCardRemove.value = Event(Unit)
    }

    fun undoLastRemove() {
        val removedCard = lastCardRemoved.value
        val position = lastCardRemovedPosition.value

        lastCardRemoved.value = null
        lastCardRemovedPosition.value = null

        if (removedCard != null && position != null) {
            cardList.add(position, removedCard)

            _cards.value = cardList
        }
    }

    fun save() {
        // TODO
    }
}