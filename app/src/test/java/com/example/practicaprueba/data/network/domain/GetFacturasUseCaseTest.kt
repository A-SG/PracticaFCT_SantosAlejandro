package com.example.practicaprueba.data.network.domain


import com.example.practicaprueba.data.FacturaRepository
import com.example.practicaprueba.data.model.toDatabase
import com.example.practicaprueba.data.network.domain.model.Factura
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
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
        getFacturasUseCase()

        //Then
        coVerify (exactly = 1) { repository.getFacturasDatabase() }
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        val lista = listOf((Factura("Pagada", 25.14,"05/02/2019")))
        //Given
        coEvery { repository.getFacturasApi() } returns  lista

        //When
        val response = getFacturasUseCase()

        //Then
        coVerify (exactly = 1) { repository.clearFacturas() }
        coVerify (exactly = 1) { repository.insertFacturas(any()) }
        //Con el 0 se comprueba que la función no es llamada
        coVerify (exactly = 0) { repository.getFacturasDatabase() }
        assert(lista==response)
    }
}