package com.yuyakaido.alembic.data.local

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): UserPreferences = UserPreferences.parseFrom(input)
    override suspend fun writeTo(t: UserPreferences, output: OutputStream) = t.writeTo(output)
}
