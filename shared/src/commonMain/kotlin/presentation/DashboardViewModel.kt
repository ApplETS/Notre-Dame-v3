package presentation

import data.domain.DashboardCardsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import model.DashboardCard

/**
 * Created by Sonphil on 12-02-19.
 */

class DashboardViewModel(private val dashboardCardsUseCase: DashboardCardsUseCase) : ViewModel() {
    val cardsChannel = Channel<List<DashboardCard>>()
    private var visibleCards: MutableList<DashboardCard> = mutableListOf()
    private var hiddenCards: MutableList<DashboardCard> = mutableListOf()
    private var lastRemovedCardPosition = -1
    val showUndoRemoveChannel = Channel<Boolean>()

    fun load() = scope.launch {
        if (visibleCards.isNullOrEmpty()) {
            val cards = dashboardCardsUseCase
                .fetch()
                .receive()
                .partition { card ->
                    card.visible
                }

            visibleCards = cards.first.toMutableList()
            hiddenCards = cards.second.toMutableList()
        }

        this@DashboardViewModel.cardsChannel.send(visibleCards.toList())
    }

    fun onCardMoved(fromPosition: Int, toPosition: Int) {
        visibleCards[fromPosition] = visibleCards.set(toPosition, visibleCards[fromPosition])
        cardsChannel.offer(visibleCards.toList())
    }

    fun onCardRemoved(position: Int) {
        val removedCard = visibleCards.removeAt(position)

        removedCard.visible = false

        hiddenCards.add(removedCard)
        lastRemovedCardPosition = position

        cardsChannel.offer(visibleCards.toList())

        showUndoRemoveChannel.offer(true)
    }

    fun undoLastRemove() {
        val removedCard = hiddenCards.run {
            removeAt(size - 1)
        }

        removedCard.visible = true
        visibleCards.add(lastRemovedCardPosition, removedCard)
        cardsChannel.offer(visibleCards.toList())
    }

    fun save() = dashboardCardsUseCase.save(
        visibleCards,
        hiddenCards
    )

    fun restore() {
        dashboardCardsUseCase.restore()
        load()
    }
}