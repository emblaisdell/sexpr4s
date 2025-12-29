//> using dep "org.scalameta::munit::1.1.1"

package com.emblaisdell.sexpr4s

class SExprParserTest extends munit.FunSuite:

  test("simple list") {
    val input = "(define x 42)"
    val result = SExpr.parse(input)
    println(result)
    assertEquals(result.toOption.get.toString, "L(List(S(define), S(x), N(42.0)))")
  }

  test("quoted string") {
    val input = """("hello world")"""
    val result = SExpr.parse(input)
    println(result)
    assertEquals(result.toOption.get.toString, "L(List(Q(hello world)))")
  }

  test("print roundtrip") {
    val input = """(define x 42.0 ("hello" "wo rld"))"""
    val result = SExpr.parse(input)
    println(result)
    assertEquals(result.toOption.get.print, input)
  }