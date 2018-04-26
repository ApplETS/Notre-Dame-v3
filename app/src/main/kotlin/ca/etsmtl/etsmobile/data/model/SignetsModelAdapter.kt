package ca.etsmtl.etsmobile.data.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * Created by Sonphil on 26-04-18.
 */

class SignetsModelAdapter<T>(private val adapter: JsonAdapter<T>) : JsonAdapter<SignetsModel<out SignetsData>>() {

    override fun fromJson(reader: JsonReader): SignetsModel<SignetsData>? {
        var signetsData: SignetsData? = null

        reader.beginObject()
        if (reader.hasNext()) {
            if (reader.nextName() == "d") {
                signetsData = readData(reader) as SignetsData?
            }
        }
        reader.endObject()

        return if (signetsData != null) {
            val signetsModel = SignetsModel<SignetsData>()
            signetsModel.data = signetsData
            signetsModel
        } else {
            null
        }
    }

    private fun readData(reader: JsonReader): T? {
        return adapter.fromJson(reader)
    }

    override fun toJson(writer: JsonWriter, value: SignetsModel<out SignetsData>?) {
        TODO("not implemented yet")
    }
}