package com.mabahmani.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object HomeExtraProtoSerializer: Serializer<HomeExtraProtoOuterClass.HomeExtraProto> {
    override val defaultValue: HomeExtraProtoOuterClass.HomeExtraProto
        get() = HomeExtraProtoOuterClass.HomeExtraProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): HomeExtraProtoOuterClass.HomeExtraProto {
        try {
            return HomeExtraProtoOuterClass.HomeExtraProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: HomeExtraProtoOuterClass.HomeExtraProto, output: OutputStream) = t.writeTo(output)
}