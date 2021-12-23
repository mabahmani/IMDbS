package com.mabahmani.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object TrailersProtoSerializer: Serializer<TrailersProtoOuterClass.TrailersProto> {
    override val defaultValue: TrailersProtoOuterClass.TrailersProto
        get() = TrailersProtoOuterClass.TrailersProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TrailersProtoOuterClass.TrailersProto {
        try {
            return TrailersProtoOuterClass.TrailersProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TrailersProtoOuterClass.TrailersProto, output: OutputStream) = t.writeTo(output)
}