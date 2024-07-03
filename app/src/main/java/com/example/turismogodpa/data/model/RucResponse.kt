import com.google.gson.annotations.SerializedName

data class RucResponse(
    @SerializedName("estado") val estado: String,
    @SerializedName("razonSocial") val razonSocial: String,
    @SerializedName("direccion") val direccion: String
)
