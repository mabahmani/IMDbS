package com.mabahmani.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object CelebsProtoSerializer: Serializer<CelebsProtoOuterClass.CelebsProto> {
    override val defaultValue: CelebsProtoOuterClass.CelebsProto
        get() = CelebsProtoOuterClass.CelebsProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CelebsProtoOuterClass.CelebsProto {
        try {
            return CelebsProtoOuterClass.CelebsProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: CelebsProtoOuterClass.CelebsProto, output: OutputStream) = t.writeTo(output)
}