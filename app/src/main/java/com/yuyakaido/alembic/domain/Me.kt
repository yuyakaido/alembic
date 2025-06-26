package com.yuyakaido.alembic.domain

data class Me(
    override val id: Long,
    override val name: String,
) : UserType
