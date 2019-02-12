package ca.etsmtl.applets.etsmobile.presentation.dashboard

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.etsmtl.applets.etsmobile.util.Event
import data.domain.DashboardCardsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.DashboardCard
import java.util.Collections
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val dashboardCardsUseCase: DashboardCardsUseCase
) : ViewModel(), LifecycleObserver {
    private val visibleCards = MutableLiveData<MutableList<DashboardCard>>()
    private val hiddenCards = MutableLiveData<MutableList<DashboardCard>>()
    val cards: LiveData<out List<DashboardCard>> = visibleCards
    private val lastRemovedCardPosition = MutableLiveData<Int?>()
    private val _showUndoCardRemove = MutableLiveData<Event<Unit>>()
    val showUndoCardRemove: LiveData<Event<Unit>> = _showUndoCardRemove

    private fun MutableLiveData<MutableList<DashboardCard>>.notifyChange() {
        value = value
    }

    fun moveCard(fromPosition: Int, toPosition: Int) {
        Collections.swap(visibleCards.value, fromPosition, toPosition)
        visibleCards.notifyChange()
    }

    fun removeCard(position: Int) {
        visibleCards.value?.removeAt(position)?.let { removedCard ->
            removedCard.visible = false
            hiddenCards.value?.add(removedCard)
            lastRemovedCardPosition.value = position
            visibleCards.notifyChange()
            _showUndoCardRemove.value = Event(Unit)
        }
    }

    fun undoLastRemove() {
        val removedCard = hiddenCards.value?.run {
            removeAt(size - 1)
        }
        val position = lastRemovedCardPosition.value

        lastRemovedCardPosition.value = null

        if (removedCard != null && position != null) {
            removedCard.visible = true
            visibleCards.value?.add(position, removedCard)
            visibleCards.notifyChange()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun load() = viewModelScope.launch {
        val cards = dashboardCardsUseCase
            .fetch()
            .receive()
            .partition { card ->
                card.visible
            }

        delay(200)
        visibleCards.postValue(cards.first.toMutableList())
        hiddenCards.postValue(cards.second.toMutableList())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun save() {
        dashboardCardsUseCase.save(
            visibleCards.value ?: emptyList(),
            hiddenCards.value ?: emptyList()
        )
    }

    fun restore() {
        dashboardCardsUseCase.restore()
        load()
    }
}