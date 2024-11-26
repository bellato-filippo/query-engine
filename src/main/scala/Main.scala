import upickle.default.write
import Services.QueryParserService
import Models.*


object Main extends App:
	val query1: Query = QueryParserService.parse("Answer(x) :- R(x), S('sium')")
	val query2: Query = QueryParserService.parse("Answer(u) :- R(v), S(t)")
	
	println(query1 isContainedIn query2)