package com.eltonjhony.eventapp.infrastructure.di

import com.eltonjhony.eventapp.data.local.AppDatabase
import com.eltonjhony.eventapp.data.local.EventDao
import com.eltonjhony.eventapp.data.remote.EventService
import com.eltonjhony.eventapp.data.remote.NetworkHandler
import com.eltonjhony.eventapp.data.remote.ServiceFactory
import com.eltonjhony.eventapp.data.repository.EventsDataRepository
import com.eltonjhony.eventapp.data.repository.WishListDataRepository
import com.eltonjhony.eventapp.data.repository.datasources.CloudEventDataSource
import com.eltonjhony.eventapp.data.repository.datasources.LocalWishListDataSource
import com.eltonjhony.eventapp.domain.interactor.*
import com.eltonjhony.eventapp.domain.repository.EventsRepository
import com.eltonjhony.eventapp.domain.repository.WishListRepository
import com.eltonjhony.eventapp.presentation.features.listevents.ListEventsViewModel
import com.eltonjhony.eventapp.presentation.features.wishlist.WishListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val presentationModule = module {
    viewModel { ListEventsViewModel(get()) }
    viewModel { WishListViewModel(get(), get(), get(), get()) }
}

val domainModule = module {
    single { GetEventsUseCase(get()) }
    single { AddToWishListUseCase(get()) }
    single { RemoveToWishListUseCase(get()) }
    single { FetchWishListUseCase(get()) }
    single { GetWishListRowCountUseCase(get()) }
}

val dataModule = module {
    factory<EventsRepository> { EventsDataRepository(get(), get()) }
    factory<WishListRepository> { WishListDataRepository(get()) }

    factory { CloudEventDataSource(get(), get()) }
    factory { LocalWishListDataSource(get()) }

    single { AppDatabase.getDatabase(androidContext()) }
    single { AppDatabase.getDatabase(androidContext()).eventDao() } bind EventDao::class

}

val cloudModule = module {
    single { NetworkHandler(androidContext()) }
    single<EventService> {
        ServiceFactory.getRetrofit().create(EventService::class.java)
    }
}

val appModules =
    listOf(presentationModule, domainModule, dataModule, cloudModule)