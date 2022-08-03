package net.dirox.c4p.common.extension

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }