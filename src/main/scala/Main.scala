import upickle.default.write
import Services.QueryParserService
import Models.*


object Main extends App:
	val query1: Query = QueryParserService.parse("Answer(x, y) :- R(x), S('sium'), T(y)")
	val query2: Query = QueryParserService.parse("Answer(v, w) :- R(v), S('ses'), T(w)")
	
	// println(query1 isContainedIn query2)
	// println(query2 isContainedIn query1)

	query1 isContainedIn query2
	println()
	query2 isContainedIn query1