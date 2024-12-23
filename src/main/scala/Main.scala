import upickle.default.write
import Services.*
import Models.*


object Main extends App:
  val q1 = QueryParserService.parse("Answer() :- A(x, y), B(y, z), C(z, w, u, 'V')")
  q1.queryId = 1

  val q2 = QueryParserService.parse("Answer() :- A(x, y), B(y, z), C(z, w, u, 'V'), D(w, y)")
  q2.queryId = 2

  val q3 = QueryParserService.parse("Answer() :- A(x, x), D(x, x)")
  q3.queryId = 3

  val q4 = QueryParserService.parse("Answer(x) :- E(x, x)")
  q4.queryId = 4

  val q5 = QueryParserService.parse("Answer(x) :- E(x, y), E(y, z)")
  q5.queryId = 5

  val q6 = QueryParserService.parse("Answer(w) :- E(w, w), E(w, u)")
  q6.queryId = 6

  val q7 = QueryParserService.parse("Answer(x) :- E(x, x), E(x, y), E(y, z)")
  q7.queryId = 7

  val q8 = QueryParserService.parse("Answer(y, z) :- E(y, z), E(z, z), E(z, w)")
  q8.queryId = 8

  val q9 = QueryParserService.parse("Answer(x, y) :- E(x, y), E(y, z), E(z, y), E(y, w)")
  q9.queryId = 9

  val q10 = QueryParserService.parse("Answer(z, z) :- E(z, y), E(y, z), E(y, w)")
  q10.queryId = 10

  val queries = List(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10)

  // main
  val mainFile = new java.io.PrintWriter(s"./src/test/results/main-output.csv")
  mainFile.println(s"queryId,isAcyclic,isMinimal")
  for (query <- queries) {
    mainFile.println(s"${query.queryId},${query.isAcyclic},${query.isMinimal}")
  }

  // containment
  val containmentFile = new java.io.PrintWriter(s"./src/test/results/containment-output.csv")
  containmentFile.println(s"queryId1,queryId2,isContainedIn")
  for (query1 <- queries) {
    for (query2 <- queries) {
      containmentFile.println(s"${query1.queryId},${query2.queryId},${query1 isContainedIn query2}")
    }
  }

  mainFile.close()
  containmentFile.close()