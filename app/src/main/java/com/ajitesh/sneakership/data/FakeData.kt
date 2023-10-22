package com.ajitesh.sneakership.data

import com.ajitesh.sneakership.domain.data.Sneaker
import java.util.UUID

object FakeData {
    const val image = "https://pngimg.com/uploads/running_shoes/running_shoes_PNG5805.png"

    fun getFakeSneakerList(): List<Sneaker> {
        val list = mutableListOf<Sneaker>()
        val sneaker = Sneaker(
            id = UUID.randomUUID().toString(),
            price = 200,
            title = "Puma RS-X",
            image = image,
            brand = "Puma",
            yearOfRelease = "2022"
        )
        list.add(sneaker)
        repeat(33) {
            val fakeList = listOf(
                Sneaker(
                    id = UUID.randomUUID().toString(),
                    price = 120,
                    title = "Nike Air Jordan",
                    image = image,
                    brand = "Nike",
                    yearOfRelease = "2020"
                ),
                Sneaker(
                    id = UUID.randomUUID().toString(),
                    price = 150,
                    title = "Nike Air Max",
                    image = image,
                    brand = "Nike",
                    yearOfRelease = "2023"
                ),
                Sneaker(
                    id = UUID.randomUUID().toString(),
                    price = 200,
                    title = "Adidas Yeezy",
                    image = image,
                    brand = "Adidas",
                    yearOfRelease = "2023"
                )
            )
            list.addAll(fakeList)
        }
        return list
    }

}