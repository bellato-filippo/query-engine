package Services

import Models.*

object QueryParserService:
    def parse(s: String): Query =
        val parts = s.split(":-").map(_.trim)

        val head = parseHead(parts(0))
        val body = parseBody(parts(1))

        new Query(0, head, body)

    def parseHead(headStr: String): Head = 
        val termsStr = headStr.stripPrefix("Answer(").stripSuffix(")")
        
        if (termsStr.isEmpty)
            return new Head(List())

        val terms = termsStr.split(",").map(_.trim).map { term =>
            if (term.startsWith("'") && term.endsWith("'")) {
            new Constant(term.stripPrefix("'").stripSuffix("'"))
            } else {
            new Variable(term)
            }
        }.toList

        new Head(terms)

    def parseBody(bodyStr: String): Set[Atom] =
        val atomStrs = bodyStr.split("(?<=\\)),").map(_.trim)

        atomStrs.map { atomStr =>
            val name = atomStr.takeWhile(_ != '(')
            val termsStr = atomStr.drop(name.length + 1).dropRight(1)

            val terms = termsStr.split(",").map(_.trim).map { term =>
            if (term.startsWith("'") && term.endsWith("'")) {
                new Constant(term.stripPrefix("'").stripSuffix("'"))
            } else {
                new Variable(term)
            }
            }.toList

            new Atom(name, terms)
        }.toSet
