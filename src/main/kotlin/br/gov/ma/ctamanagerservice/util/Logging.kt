package br.gov.ma.ctamanagerservice.util

import org.slf4j.LoggerFactory

interface Logging {
    fun logger() = LoggerFactory.getLogger(javaClass)
}

abstract class WithLogging : Logging {
    val LOG = logger()
}
