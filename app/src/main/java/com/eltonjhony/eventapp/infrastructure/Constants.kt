package com.eltonjhony.eventapp.infrastructure

class Constants {

    interface DatePattern {
        companion object {
            const val MONTH_FORMAT = "MMM"
            const val DAY_OF_WEEK_FORMAT = "EEE"
            const val TIME_FORMAT = "HH:mm a"
            const val DATE_TIME_FORMAT = "yyy-MM-dd'T'HH:mm:ss'Z'"
            const val DATE_AND_TIME_FORMAT = "yyyy, MMM dd - HH:mm a"
        }
    }

    interface SearchFilter {
        companion object {
            const val PAGE_SIZE = 20
            const val NO = "no"
            const val COUNTRY_CODE = "DE"
        }
    }

    interface Configs {
        companion object {
            const val BASE_URL = "https://app.ticketmaster.com/discovery/v2/"
            const val API_KEY_PARAM = "apikey"
            const val API_KEY_VALUE = "CmqFKledVWHtMyAq85u6zk5KZUZmq8cw"
        }
    }

}