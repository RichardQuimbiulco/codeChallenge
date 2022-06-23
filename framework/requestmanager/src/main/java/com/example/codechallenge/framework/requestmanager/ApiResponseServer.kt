package com.example.codechallenge.framework.requestmanager

data class CharacterResponseServer(
    val code: Int,
    val status: String,
    val copyright: String,
    val data: CharacterDataServer
)

data class CharacterDataServer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterServer>
)

data class CharacterServer(
    val id: Int,
    val name: String,
    val description: String?,
    val thumbnail: CharacterThumbnailResponseServer?
)

data class CharacterThumbnailResponseServer(
    val path: String,
    val extension: String
)