package ca.etsmtl.applets.etsmobile.presentation.dashboard

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.util.Event
import data.domain.FetchDashboardCardsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import model.DashboardCard
import java.util.Collections
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val fetchDashboardCardsUseCase: FetchDashboardCardsUseCase) : ViewModel(), LifecycleObserver {
    private var cardList: MutableList<DashboardCard> = mutableListOf()

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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun load() {
        CoroutineScope(Dispatchers.Main).launch {
            fetchDashboardCardsUseCase().consumeEach {
                cardList = it.toMutableList()
                _cards.value = cardList
            }
        }
    }

    fun save() {
        // TODO
    }
}