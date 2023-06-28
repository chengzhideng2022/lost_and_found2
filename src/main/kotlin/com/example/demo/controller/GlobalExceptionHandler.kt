package com.example.demo.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
@CrossOrigin(origins = ["http://localhost:8081"], allowCredentials = "true")
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleIllegalArgumentException(req: HttpServletRequest, e: IllegalArgumentException): ModelAndView {
        println("Caught IllegalArgumentException: ${e.message}") // 添加这一行
        val mav = ModelAndView()
        mav.addObject("exception", e)
        mav.addObject("url", req.requestURL)
        mav.viewName = "error"
        return mav
    }
}


