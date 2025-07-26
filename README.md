# `sexpr4s` S-Expression Parser

A minimal S-expression parser in Scala 3 using Scala CLI. Parses strings like:

```lisp
(define "x" 42 (list foo "bar"))
```

Into a structured representation using an `enum`.

---

## Features

- Parses nested S-expressions
- Supports:
  - Lists `(a b c)`
  - Symbols: `define`, `foo`, etc.
  - Quoted strings: `"hello world"`, supports `\"`, `\\`, `\n`, `\t`, etc.
  - Numbers: integers and floats

---

## Usage

### `SExprParser.parse(input: String): Either[String, SExpr]`

Parses a string into an `SExpr` or returns an error message.

### Example

```scala
val input = """(define "x" 42 (list foo "bar"))"""
val result = SExprParser.parse(input)

result match
  case Right(expr) => println(expr)
  case Left(error) => println(s"Error: $error")
```

### Output

```
L(List(S(define), Q(x), N(42.0), L(List(S(list), S(foo), Q(bar)))))
```

---

## 📄 `SExpr` AST

The `SExpr` enum variants:

```scala
enum SExpr:
  case L(values: List[SExpr])  // List
  case S(value: String)        // Symbol
  case Q(value: String)        // Quoted string
  case N(value: Double)        // Number
```

---

## Example Inputs

```lisp
(define x 42)
("hello\nworld" "quote: \"" "backslash: \\")
(list 1 2 (nested (deeper "string")))
```

---

## Not Supported (yet)

- Annotations like `@tag value`
- Comments (`; this is a comment`)
- Custom number formats (hex, scientific)
