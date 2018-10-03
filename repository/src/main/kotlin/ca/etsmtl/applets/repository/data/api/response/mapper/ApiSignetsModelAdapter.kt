package ca.etsmtl.applets.repository.data.api.response.mapper

import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsData
import ca.etsmtl.applets.repository.data.api.response.signets.ApiSignetsModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * Created by Sonphil on 26-04-18.
 */

class ApiSignetsModelAdapter<T>(private val adapter: JsonAdapter<T>) : JsonAdapter<ApiSignetsModel<out ApiSignetsData>>() {

    override fun fromJson(reader: JsonReader): ApiSignetsModel<ApiSignetsData>? {
        var apiSignetsData: ApiSignetsData? = null

        reader.beginObject()
        if (reader.hasNext()) {
            if (reader.nextName() == "d") {
                apiSignetsData = readData(reader) as ApiSignetsData?
            }
        }
        reader.endObject()

        return if (apiSignetsData != null) {
            val signetsModel = ApiSignetsModel<ApiSignetsData>()
            signetsModel.data = apiSignetsData
            signetsModel
        } else {
            null
        }
    }

    private fun readData(reader: JsonReader): T? {
        return adapter.fromJson(reader)
    }

    override fun toJson(writer: JsonWriter, value: ApiSignetsModel<out ApiSignetsData>?) {
        TODO("not implemented yet")
    }
}