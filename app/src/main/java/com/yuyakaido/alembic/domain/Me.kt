package com.yuyakaido.alembic.domain

import android.net.Uri

data class Me(
    override val id: Long,
    override val name: String,
    override val icon: Uri,
) : UserType
