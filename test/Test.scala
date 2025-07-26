//> using dep "org.scalameta::munit::1.1.1"

class SExprParserTest extends munit.FunSuite:

  test("simple list") {
    val input = "(define x 42)"
    val result = SExprParser.parse(input)
    println(result)
    assertEquals(result.toOption.get.toString, "L(List(S(define), S(x), N(42.0)))")
  }

  test("quoted string") {
    val input = """("hello world")"""
    val result = SExprParser.parse(input)
    println(result)
    assertEquals(result.toOption.get.toString, "L(List(Q(hello world)))")
  }