package com.example.codechallenge.data

import com.example.codechallenge.data.datasource.RemoteCharacterDataSource
import com.example.codechallenge.data.repository.CharacterRepositoryImpl
import com.example.codechallenge.model.Character
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryTest {

    @Mock
    private lateinit var remoteCharacterDataSource: RemoteCharacterDataSource

    private lateinit var characterRepository: CharacterRepositoryImpl

    @Before
    fun setup() {
        characterRepository =
            CharacterRepositoryImpl(remoteCharacterDataSource)
    }

    @Test
    fun `getAllCharacters returns an expected list of characters`() {
        val expectedResult = listOf(mockedCharacter.copy(id = 1))
        given(remoteCharacterDataSource.getAllCharacters(any())).willReturn(
            Single.just(
                expectedResult
            )
        )
        characterRepository.getAllCharacters(1).test().assertComplete().assertNoErrors()
            .assertValue(expectedResult)
    }

    @Test
    fun `getCharacterBy returns an expected character`() {
        val expectedResult = mockedCharacter.copy(id = 1)
        given(remoteCharacterDataSource.getCharacterById(any())).willReturn(
            Single.just(
                expectedResult
            )
        )
        characterRepository.getCharacterById(1).test().assertComplete().assertNoErrors()
            .assertValue(expectedResult)
    }

    private val mockedCharacter = Character(
        id = 0,
        name = "",
        image = "",
        description = ""
    )
}