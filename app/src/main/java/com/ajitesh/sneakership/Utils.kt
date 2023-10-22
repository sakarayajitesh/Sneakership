package com.ajitesh.sneakership

import com.ajitesh.sneakership.data.datasource.local.entity.SneakerEntity
import com.ajitesh.sneakership.domain.data.Sneaker

fun List<Sneaker>.toSneakerEntityList() =
    map {
        it.toSneakerEntity()
    }

fun List<SneakerEntity>.toSneakerList() =
    map {
        it.toSneaker()
    }

fun SneakerEntity.toSneaker(): Sneaker {
    return Sneaker(
        id = this.id,
        title = this.title,
        price = this.price,
        image = this.image,
        brand = this.brand,
        yearOfRelease = this.yearOfRelease
    )
}

fun Sneaker.toSneakerEntity(): SneakerEntity {
    return SneakerEntity(
        id = this.id,
        title = this.title,
        price = this.price,
        image = this.image,
        brand = this.brand,
        yearOfRelease = this.yearOfRelease
    )
}

fun List<Sneaker>.getTotalPrice() = this.sumOf { it.price }

fun Int.asPrice() = "$${this}"

val TAXANDCHARGES = 40