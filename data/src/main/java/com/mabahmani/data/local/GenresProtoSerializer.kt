package com.mabahmani.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object GenresProtoSerializer: Serializer<GenresProtoOuterClass.GenresProto> {
    override val defaultValue: GenresProtoOuterClass.GenresProto
        get() = GenresProtoOuterClass.GenresProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): GenresProtoOuterClass.GenresProto {
        try {
            return GenresProtoOuterClass.GenresProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: GenresProtoOuterClass.GenresProto, output: OutputStream) = t.writeTo(output)
}