package br.gov.ma.ctamanagerservice.domain.valueobjects

import br.gov.ma.ctamanagerservice.domain.exceptions.InvalidEmailException
import java.util.regex.Pattern

class Email {
    var value: String? = null
        private set

    internal constructor() {}
    constructor(value: String?) {
        this.value = value ?: ""
        val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(value)
        if (!matcher.find()) {
            throw InvalidEmailException()
        }
    }

    override fun toString(): String {
        return value!!
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + if (value == null) 0 else value.hashCode()
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as Email
        if (value == null) {
            if (other.value != null) return false
        } else if (value != other.value) return false
        return true
    }

    companion object {
        val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
        )
    }
}