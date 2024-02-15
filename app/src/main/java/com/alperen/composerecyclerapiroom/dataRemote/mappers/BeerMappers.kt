package com.alperen.composerecyclerapiroom.dataRemote.mappers

import com.alperen.composerecyclerapiroom.dataRemote.BeerDto
import com.alperen.composerecyclerapiroom.domain.Beer
import com.alperen.composerecyclerapiroom.local.BeerEntity

fun BeerDto.toBeerEntity():BeerEntity {
    return BeerEntity(
        id = id,
        name = name,
        tagline= tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url
    )
}
fun BeerEntity.toBeer(): Beer {
    return Beer(
        id = id,
        name = name,
        tagline= tagline,
        description = description,
        firstBrewed = firstBrewed,
        imageUrl = imageUrl
    )
}
