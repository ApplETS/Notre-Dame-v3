package ca.etsmtl.applets.repository.di

import android.content.Context
import ca.etsmtl.applets.repository.R
import ca.etsmtl.applets.repository.data.api.SignetsApi
import ca.etsmtl.applets.repository.util.LiveDataCallAdapterFactory
import ca.etsmtl.applets.repository.util.SignetsTrust
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by Sonphil on 28-02-18.
 */
@Module
internal open class NetworkModule {

    companion object {
        val instance = NetworkModule()
    }

    @Singleton @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return SignetsTrust(context).client
    }

    @Singleton @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton @Provides
    fun provideRetrofit(context: Context, moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(context.getString(R.string.url_signets_webservice))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
    }

    @Singleton @Provides
    fun provideSignetsApi(retrofit: Retrofit): SignetsApi {
        return retrofit.create(SignetsApi::class.java)
    }
}
