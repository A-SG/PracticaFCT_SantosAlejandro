package com.example.practicaprueba.data.network.domain

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.practicaprueba.data.model.NumFactura
import retrofit2.Response
import retrofit2.http.GET


interface RetromockService {
        @GET("/endpoint")
        @MockResponse(body = "{\n" +
                "  \"numFacturas\": 8,\n" +
                "  \"facturas\": [\n" +
                "    {\n" +
                "      \"descEstado\": \"Pendiente de pago\",\n" +
                "      \"importeOrdenacion\": 2.5600000000000001,\n" +
                "      \"fecha\": \"07/02/2019\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 25.140000000000001,\n" +
                "      \"fecha\": \"05/02/2019\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 22.690000000000001,\n" +
                "      \"fecha\": \"08/01/2019\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pendiente de pago\",\n" +
                "      \"importeOrdenacion\": 12.84,\n" +
                "      \"fecha\": \"07/12/2018\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 35.159999999999997,\n" +
                "      \"fecha\": \"16/11/2018\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 18.27,\n" +
                "      \"fecha\": \"05/10/2018\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pendiente de pago\",\n" +
                "      \"importeOrdenacion\": 61.170000000000002,\n" +
                "      \"fecha\": \"05/09/2018\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"descEstado\": \"Pagada\",\n" +
                "      \"importeOrdenacion\": 37.18,\n" +
                "      \"fecha\": \"07/08/2018\"\n" +
                "    }\n" +
                "  ]\n" +
                "  \n" +
                "}")
        @Mock
        suspend fun getFactura() : Response<NumFactura>
    }
