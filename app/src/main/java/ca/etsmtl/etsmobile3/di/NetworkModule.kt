package ca.etsmtl.etsmobile3.di

import ca.etsmtl.etsmobile3.data.api.SignetsApi
import ca.etsmtl.etsmobile3.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by Sonphil on 28-02-18.
 */
@Module
open class NetworkModule {

    companion object {
        val instance = NetworkModule()
    }

    @Singleton @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://signets-ens.etsmtl.ca/Secure/WebServices/SignetsMobile.asmx/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    @Singleton @Provides
    fun provideSignetsApi(retrofit: Retrofit): SignetsApi {
        return retrofit.create(SignetsApi::class.java)
    }

}
