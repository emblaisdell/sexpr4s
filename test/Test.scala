//> using dep "org.scalameta::munit::1.2.4"

package com.emblaisdell.sexpr4s

class SExprParserTest extends munit.FunSuite:

  test("simple list") {
    val input = "(define x 42)"
    val result = SExpr.parse(input)
    println(result)
    assertEquals(result.toOption.get.toString, "L(List(S(define), S(x), N(42)))")
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

  test("complex parse") {
    val input = """(Str "Parse a Phoenicia file string and return an s-expression of rewrites.  The function expects one or more `calc \"path\" { ... }` blocks, each containing zero or more declarations of the form `name : Type* -> Type;`, optional `@` annotations on the line(s) immediately before a declaration, and `//` line comments immediately above a declaration for documentation.")"""
    val result = SExpr.parse(input)
    println(result)
    assertEquals(result.toOption.get.print, input)
  }
