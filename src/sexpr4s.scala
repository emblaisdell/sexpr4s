package com.emblaisdell.sexpr4s

enum SExpr:
  case L(values: List[SExpr])     // List
  case S(value: String)           // Symbol
  case Q(value: String)           // Quoted string
  case N(value: Double)           // Number

import scala.util.{Try, Success, Failure}

object SExpr:

  def parse(input: String): Either[String, SExpr] =
    tokenize(input) match
      case Left(err) => Left(err)
      case Right(tokens) =>
        parseTokens(tokens) match
          case Some((expr, Nil)) => Right(expr)
          case Some((_, rest))   => Left(s"Unexpected tokens after end: $rest")
          case None              => Left("Failed to parse")

  private def tokenize(input: String): Either[String, List[String]] =
    val Token = raw"""([()]|"(?:[^"\\]|\\.)*"|[^\s()]+)""".r
    val tokens = Token.findAllIn(input).toList
    println(tokens)
    Right(tokens)

  private def parseTokens(tokens: List[String]): Option[(SExpr, List[String])] =
    tokens match
      case Nil => None
      case "(" :: rest =>
        parseList(rest, Nil).map { case (list, rem) => (SExpr.L(list), rem) }

      case tok :: rest if tok.startsWith("\"") && tok.endsWith("\"") =>
        val str = tok.substring(1, tok.length - 1)
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\")
                    .replace("\\n", "\n")
                    .replace("\\t", "\t")
                    
        Some((SExpr.Q(str), rest))

      case tok :: rest =>
        Try(tok.toDouble) match
          case Success(num) => Some((SExpr.N(num), rest))
          case Failure(_)   => Some((SExpr.S(tok), rest))

  private def parseList(tokens: List[String], acc: List[SExpr]): Option[(List[SExpr], List[String])] =
    tokens match
      case Nil => None
      case ")" :: rest => Some((acc.reverse, rest))
      case _ =>
        parseTokens(tokens) match
          case Some((expr, rem)) => parseList(rem, expr :: acc)
          case None              => None
