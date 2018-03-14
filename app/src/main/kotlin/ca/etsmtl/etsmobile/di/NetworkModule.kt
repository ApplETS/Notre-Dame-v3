package ca.etsmtl.etsmobile.di

import ca.etsmtl.etsmobile.data.api.SignetsApi
import ca.etsmtl.etsmobile.util.ApplicationJsonAdapterFactory
import ca.etsmtl.etsmobile.util.LiveDataCallAdapterFactory
import com.squareup.moshi.Moshi
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
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(ApplicationJsonAdapterFactory.INSTANCE).build()
    }

    @Singleton @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://signets-ens.etsmtl.ca/Secure/WebServices/SignetsMobile.asmx/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    @Singleton @Provides
    fun provideSignetsApi(retrofit: Retrofit): SignetsApi {
        return retrofit.create(SignetsApi::class.java)
    }

}
