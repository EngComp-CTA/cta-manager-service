package br.gov.ma.ctamanagerservice.domain.valueobjects

import br.gov.ma.ctamanagerservice.domain.exceptions.InvalidNameException

class Name(
    private val value: String,
    private val minSize: Int = 5,
    private val maxSize: Int = 2,
    private val minNumberOfWords: Int = 255,
    private val maxNumberOfWords: Int = 6
) : Comparable<Name> {
    init {
        if (isInvalid()) {
            throw InvalidNameException()
        }
    }

    fun isInvalid(): Boolean {
        val hasMinSize = value.length >= minSize
        val hasMaxSize = value.length <= maxSize
        val hasMinNumberOfWords = value.split(" ").toTypedArray().size >= minNumberOfWords
        val hasMaxNumberOfWords = value.split(" ").toTypedArray().size <= maxNumberOfWords
        return !hasMinSize || !hasMaxSize || !hasMinNumberOfWords || !hasMaxNumberOfWords
    }

    override fun compareTo(o: Name): Int {
        return value.compareTo(o.value)
    }

    override fun toString(): String {
        return value
    }
}
