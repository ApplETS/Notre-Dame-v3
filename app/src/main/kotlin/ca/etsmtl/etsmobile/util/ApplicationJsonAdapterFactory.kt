package ca.etsmtl.etsmobile.util

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

/**
 * Created by Sonphil on 13-03-18.
 */
@KotshiJsonAdapterFactory
abstract class ApplicationJsonAdapterFactory : JsonAdapter.Factory {
    companion object {
        val INSTANCE: ApplicationJsonAdapterFactory = KotshiApplicationJsonAdapterFactory()
    }
}