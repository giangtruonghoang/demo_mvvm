package net.dirox.c4p.di.module

import net.dirox.c4p.data.local.LocalDataSource
import net.dirox.c4p.data.local.LocalDataSourceImp
import net.dirox.c4p.data.remote.RemoteDataSource
import net.dirox.c4p.data.remote.RemoteDataSourceImp
import net.dirox.c4p.data.repository.*
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.experimental.builder.single

val repositoryModule = module {
    single<LocalDataSourceImp>() bind LocalDataSource::class
    single<RemoteDataSourceImp>() bind RemoteDataSource::class
    single<RepositoryImp>() bind Repository::class
}