import upickle.default.write
import Services.*
import Models.*


object Main extends App:
	// val q1 = QueryParserService.parse("Answer(y1, y2, y3) :- R1(y1, y3), R6(y3, 'a'), R3(y2, y1), R5('b', y1), R2(y3, y2), R4(y2, 'a')")
	// val q2 = QueryParserService.parse("Answer(z, y, x) :- R2(x, y), R3(y, z), R1(z, x)")
	
	val q1 = QueryParserService.parse("Answer(x1) :- R(x1, y1), S(y1, x2), R(x1, y2), S(y2, x2)")
	val q2 = QueryParserService.parse("Answer(u) :- R(u, v), S(v, w), R(x, v), R(x, y)")

	// println(query1 isContainedIn query2)
	// println(query2 isContainedIn query1)

	// println(ContainmentService.extractTermsFromQuery(q2))

	val q = QueryParserService.parse("Answer(x) :- R1(x, y, w, z), R1(x, w, x, y), R1(x, y, w, s), R2(u, v), R2(s, t), R2(v, u)")
	QueryMinimizerService.isMinimal(q)

	// q1 isContainedIn q2
	// println()
	// q2 isContainedIn q1