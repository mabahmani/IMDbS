package com.mabahmani.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object HomeProtoSerializer: Serializer<HomeProtoOuterClass.HomeProto> {
    override val defaultValue: HomeProtoOuterClass.HomeProto
        get() = HomeProtoOuterClass.HomeProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): HomeProtoOuterClass.HomeProto {
        try {
            return HomeProtoOuterClass.HomeProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: HomeProtoOuterClass.HomeProto, output: OutputStream) = t.writeTo(output)
}