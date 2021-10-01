package br.gov.ma.ctamanagerservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class CtaManagerServiceApplication

@Bean
@Primary
fun restTemplate(
	jacksonHttpMessageConverter: MappingJackson2HttpMessageConverter
): RestTemplate =
	RestTemplateBuilder()
	.messageConverters(jacksonHttpMessageConverter)
		.additionalMessageConverters(FormHttpMessageConverter())
		.build()

fun main(args: Array<String>) {
	runApplication<CtaManagerServiceApplication>(*args)
}
