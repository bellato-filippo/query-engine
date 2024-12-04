package Services

import Models.*

object QueryParserService:
    def parse(s: String): Query =
        // Split the query string on ":-"
        val parts = s.split(":-").map(_.trim)

        // Parse the head and body
        val head = parseHead(parts(0))
        val body = parseBody(parts(1))

        // Create the Query instance
        new Query(0, head, body)

    def parseHead(headStr: String): Head = 
        // Extract the terms part by removing "Answer(" and ")"
        val termsStr = headStr.stripPrefix("Answer(").stripSuffix(")")
        
        if (termsStr.isEmpty)
            return new Head(List())  // Head without terms

        // Split terms on commas, then classify each term
        val terms = termsStr.split(",").map(_.trim).map { term =>
            if (term.startsWith("'") && term.endsWith("'")) {
            new Constant(term.stripPrefix("'").stripSuffix("'"))  // Constant without the single quotes
            } else {
            new Variable(term)  // Variable as-is
            }
        }.toList

        // Create the Head instance
        new Head(terms)

    def parseBody(bodyStr: String): Set[Atom] =
        // Split on "),", keeping the ")" but removing the ","
        val atomStrs = bodyStr.split("(?<=\\)),").map(_.trim)

        // Map each atom string to an Atom instance
        atomStrs.map { atomStr =>
            // Extract the atom name and terms
            val name = atomStr.takeWhile(_ != '(')
            val termsStr = atomStr.drop(name.length + 1).dropRight(1) // Remove "name(" at the start and ")" at the end

            // Split terms by commas and classify each term
            val terms = termsStr.split(",").map(_.trim).map { term =>
            if (term.startsWith("'") && term.endsWith("'")) {
                new Constant(term.stripPrefix("'").stripSuffix("'"))
            } else {
                new Variable(term)
            }
            }.toList

            // Create the Atom instance
            new Atom(name, terms)
        }.toSet
