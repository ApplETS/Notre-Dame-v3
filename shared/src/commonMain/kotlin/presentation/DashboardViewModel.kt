package presentation

import di.Inject
import domain.FetchDashboardCardsUseCase
import domain.RestoreDashboardCardsUseCase
import domain.SaveDashboardCardsUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import model.DashboardCard

/**
 * Created by Sonphil on 12-02-19.
 */

class DashboardViewModel @Inject constructor(
    private val fetchDashboardCardsUseCase: FetchDashboardCardsUseCase,
    private val restoreDashboardCardsUseCase: RestoreDashboardCardsUseCase,
    private val saveDashboardCardsUseCase: SaveDashboardCardsUseCase
) : ViewModel() {
    private val _cardsChannel = Channel<List<DashboardCard>>()
    val cardsChannel: ReceiveChannel<List<DashboardCard>> = _cardsChannel
    private val _showUndoRemoveChannel = Channel<Boolean>()
    val showUndoRemoveChannel: ReceiveChannel<Boolean> = _showUndoRemoveChannel

    private var visibleCards: MutableList<DashboardCard> = mutableListOf()
    private var hiddenCards: MutableList<DashboardCard> = mutableListOf()
    private var lastRemovedCardPosition = -1

    fun load() = vmScope.launch {
        for (cards in fetchDashboardCardsUseCase()) {
            val cards = cards.partition { card ->
                card.visible
            }

            visibleCards = cards.first.toMutableList()
            hiddenCards = cards.second.toMutableList()

            this@DashboardViewModel._cardsChannel.send(visibleCards.toList())
        }
    }

    fun onCardMoved(fromPosition: Int, toPosition: Int) {
        visibleCards[fromPosition] = visibleCards.set(toPosition, visibleCards[fromPosition])
        _cardsChannel.offer(visibleCards.toList())
    }

    fun onCardRemoved(position: Int) {
        val removedCard = visibleCards.removeAt(position)

        removedCard.visible = false

        hiddenCards.add(removedCard)
        lastRemovedCardPosition = position

        _cardsChannel.offer(visibleCards.toList())

        _showUndoRemoveChannel.offer(true)
    }

    fun undoLastRemove() {
        val removedCard = hiddenCards.run {
            removeAt(size - 1)
        }

        removedCard.visible = true
        visibleCards.add(lastRemovedCardPosition, removedCard)
        _cardsChannel.offer(visibleCards.toList())
    }

    fun save() = GlobalScope.launch {
        saveDashboardCardsUseCase(
            visibleCards,
            hiddenCards
        )
    }

    fun restore() = vmScope.launch {
        _showUndoRemoveChannel.offer(false)
        restoreDashboardCardsUseCase()
        load()
    }
}