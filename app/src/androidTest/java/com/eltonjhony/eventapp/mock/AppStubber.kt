package com.eltonjhony.eventapp.mock

import com.eltonjhony.eventapp.data.remote.URLChangeInterceptor
import com.github.tomakehurst.wiremock.client.WireMock.*

class AppStubber {

    companion object {

        private const val MOCK_URL = "http://localhost:8080/events"

        fun stubTicketMasterResponseWithError(errorCode: Int) {
            URLChangeInterceptor.setInterceptor(MOCK_URL)
            stubFor(
                get(anyUrl())
                    .willReturn(
                        aResponse()
                            .withStatus(errorCode)
                    )
            )
        }

        fun stubTicketMasterResponseWithSuccess(jsonBody: String) {
            URLChangeInterceptor.setInterceptor(MOCK_URL)
            val json: String = AssetReaderUtil.asset(jsonBody)
            stubFor(
                get(anyUrl())
                    .willReturn(
                        aResponse()
                            .withStatus(200)
                            .withBody(json)
                    )
            )
        }

    }

}