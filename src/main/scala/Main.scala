import upickle.default.write
import Services.*
import Models.*


object Main extends App {
  val q1 = QueryParserService.parse("Answer(x2) :- R1(x1, x2, x3), R2(x2, 'a'), R3(x3, 'b', 'c')")
  val q2 = QueryParserService.parse("Answer(y2) :- R2(y1, y4), R1(y1, y2, y3)")
  q1.queryId = 411
  q2.queryId = 412
  ContainmentService.isContainedIn(q1, q2, true)
  // assertEquals(ContainmentService.isContainedIn(q2, q1, true), false)
}