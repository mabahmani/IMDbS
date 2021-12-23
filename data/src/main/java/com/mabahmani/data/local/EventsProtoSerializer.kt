package com.mabahmani.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object EventsProtoSerializer: Serializer<EventsProtoOuterClass.EventsProto> {
    override val defaultValue: EventsProtoOuterClass.EventsProto
        get() = EventsProtoOuterClass.EventsProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): EventsProtoOuterClass.EventsProto {
        try {
            return EventsProtoOuterClass.EventsProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: EventsProtoOuterClass.EventsProto, output: OutputStream) = t.writeTo(output)
}