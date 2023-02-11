package io.sharing.server.api

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.aopalliance.intercept.MethodInvocation
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils
import org.springframework.web.bind.annotation.RequestBody

@Aspect
@Component
class ApiLoggerAspect(
    private val request: HttpServletRequest,
    private val objectMapper: ObjectMapper
) {
    private val logger = KotlinLogging.logger { }

    @After("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    fun logGet(joinPoint: JoinPoint) {
        logger.info { "[GET] ${request.requestURI}, ${request.parameterMap}" }
    }

    @After("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    fun logPost(joinPoint: JoinPoint) {
        val result = joinPoint as MethodInvocationProceedingJoinPoint

        val field = MethodInvocationProceedingJoinPoint::class.java.declaredFields
            .first { it.name == "methodInvocation" }
            .apply { this.isAccessible = true }

        val methodInvocation: MethodInvocation = ReflectionUtils.getField(field, result) as MethodInvocation

        val argIndexes = List(
            methodInvocation.method.parameters.filter { it -> it.declaredAnnotations.any { it is RequestBody } }.size
        ) { index -> index }

        val body = objectMapper.writeValueAsString(argIndexes.map { result.args[it] })

        logger.info { "[POST] ${request.requestURI}, $body" }
    }
}
