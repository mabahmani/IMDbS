package com.mabahmani.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object TitlesProtoSerializer: Serializer<TitlesProtoOuterClass.TitlesProto> {
    override val defaultValue: TitlesProtoOuterClass.TitlesProto
        get() = TitlesProtoOuterClass.TitlesProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TitlesProtoOuterClass.TitlesProto {
        try {
            return TitlesProtoOuterClass.TitlesProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TitlesProtoOuterClass.TitlesProto, output: OutputStream) = t.writeTo(output)
}