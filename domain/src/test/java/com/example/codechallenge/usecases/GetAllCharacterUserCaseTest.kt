package com.example.codechallenge.usecases

import com.example.codechallenge.model.Character
import com.example.codechallenge.repository.CharacterRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAllCharacterUserCaseTest {

    @Mock
    private lateinit var characterRepository: CharacterRepository

    private lateinit var getAllCharacterUseCase: GetAllCharacterUseCase

    @Before
    fun setup() {
        getAllCharacterUseCase = GetAllCharacterUseCase(characterRepository)
    }

    @Test
    fun `getAllCharacterUseCase returns a list of characters`() {
        val expectedResult = listOf(mockedCharacter.copy(id = 1))
        given(characterRepository.getAllCharacters(any())).willReturn(Single.just(expectedResult))
        getAllCharacterUseCase.invoke(1).test().assertComplete().assertNoErrors()
            .assertValue(expectedResult)
    }

    private val mockedCharacter = Character(
        id = 0,
        name = "",
        image = "",
        description = ""
    )
}