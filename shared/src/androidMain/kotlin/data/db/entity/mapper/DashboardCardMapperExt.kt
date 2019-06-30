package data.db.entity.mapper

import data.db.entity.DashboardCardEntity
import model.DashboardCard

/**
 * Created by Sonphil on 04-06-19.
 */

fun DashboardCardEntity.toDashboardCard() = DashboardCard(
    DashboardCard.Type.valueOf(type),
    visible,
    dismissible
)

fun List<DashboardCardEntity>.toDashboardCards() = map { it.toDashboardCard() }

fun DashboardCard.toDashboardCardEntity(position: Int) = DashboardCardEntity(
    type.name,
    visible,
    position,
    dismissible
)