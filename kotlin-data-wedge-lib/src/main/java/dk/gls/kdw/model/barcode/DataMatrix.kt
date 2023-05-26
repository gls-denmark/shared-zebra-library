package dk.gls.kdw.model.barcode

import android.os.Bundle
import dk.gls.kdw.bundler.Bundler
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataMatrix(
    @SerialName("decoder_datamatrix")
    val enabled: Boolean = true
) : Bundleable {
    override fun toBundle(): Bundle {
        return Bundler.bundle(serializer(), this)
    }
}