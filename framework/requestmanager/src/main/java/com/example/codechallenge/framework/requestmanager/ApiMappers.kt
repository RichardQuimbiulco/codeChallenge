package com.example.codechallenge.framework.requestmanager

import com.example.codechallenge.domain.Character

fun CharacterResponseServer.toCharacterDomainList(): List<Character> = data.results.map {
    it.run {
        Character(
            id = id,
            name = name,
            image = it.thumbnail?.path.plus(".").plus(it.thumbnail?.extension),
            description = description
        )
    }
}

fun CharacterResponseServer.toCharacterDomain(): Character = Character(
    id = data.results[0].id,
    name = data.results[0].name,
    image = data.results[0].thumbnail?.path.plus(".").plus(data.results[0].thumbnail?.extension),
    description = data.results[0].description,
)
/*it.run {
    Character(
        id,
        name,
        description,
        it.thumbnail?.path.plus(".").plus(it.thumbnail?.extension)
    )
}
}*/

/*fun OriginServer.toOriginDomain() = OriginEntity(
    name,
    url
)

fun LocationServer.toLocationDomain() = LocationEntity(
    name,
    url
)*/

