package com.example.codechallenge.usecases

import com.example.codechallenge.data.CharacterRepository
import com.example.codechallenge.domain.Character
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetDetailCharacterUseCaseTest {

    @Mock
    private lateinit var characterRepository: CharacterRepository

    private lateinit var getDetailCharacterUseCase: GetDetailCharacterUseCase

    @Before
    fun setup() {
        getDetailCharacterUseCase = GetDetailCharacterUseCase(characterRepository)
    }

    @Test
    fun `getDetailCharacterUseCase returns a single characters`() {
        val expectedResult = mockedCharacter.copy(id = 1)
        given(characterRepository.getCharacterById(any())).willReturn(Single.just(expectedResult))
        getDetailCharacterUseCase.invoke(1).test().assertComplete().assertNoErrors()
            .assertValue(expectedResult)
    }

    private val mockedCharacter = Character(
        id = 0,
        name = "",
        image = "",
        description = ""
    )
}