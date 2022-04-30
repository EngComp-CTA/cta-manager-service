import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest

@ImportAutoConfiguration
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest
annotation class IntegrationTests
