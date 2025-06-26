package com.yuyakaido.alembic.domain

import android.net.Uri

data class User(
    override val id: Long,
    override val name: String,
    override val icon: Uri,
) : UserType
