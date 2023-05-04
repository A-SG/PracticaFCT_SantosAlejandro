package com.example.practicaprueba.data.network.domain


import com.example.practicaprueba.data.FacturaRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetFacturasUseCaseTest{

    @RelaxedMockK
    private lateinit var  repository : FacturaRepository
    lateinit var  getFacturasUseCase: GetFacturasUseCase


    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getFacturasUseCase = GetFacturasUseCase(repository)
    }

    @Test
    fun `when the api doesnt return anything the get values from database`() = runBlocking {

        //Given
        coEvery { repository.getFacturasApi() } returns  emptyList()

        //When
        getFacturasUseCase

        //Then
        coVerify (exactly = 1) { repository.getFacturasDatabase() }
    }
}