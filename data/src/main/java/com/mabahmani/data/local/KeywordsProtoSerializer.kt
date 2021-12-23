package com.mabahmani.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object KeywordsProtoSerializer: Serializer<KeywordsProtoOuterClass.KeywordsProto> {
    override val defaultValue: KeywordsProtoOuterClass.KeywordsProto
        get() = KeywordsProtoOuterClass.KeywordsProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): KeywordsProtoOuterClass.KeywordsProto {
        try {
            return KeywordsProtoOuterClass.KeywordsProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: KeywordsProtoOuterClass.KeywordsProto, output: OutputStream) = t.writeTo(output)
}