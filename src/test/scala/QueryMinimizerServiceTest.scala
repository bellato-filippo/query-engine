import Services.*
import scala.concurrent.duration.Duration
class QueryMinimizerServiceTest extends munit.FunSuite:
    override def munitTimeout: Duration = Duration(120, "s")

    test("Lab4 E3 a"):
        val q = QueryParserService.parse("Answer(x) :- R(x, y), S(z)")
        q.queryId = 431
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)
        
    test("Lab4 E3 d"):
        val q = QueryParserService.parse("Answer(x, y, z) :- R(x, y, z), R(u, v, z), R(u, y, r), S(x, y), S(u, v)")
        q.queryId = 432
        assertEquals(QueryMinimizerService.isMinimal(q, false), false)

    test("Lab4 E3 e"):
        val q = QueryParserService.parse("Answer(y) :- R1(x, y), R1(v, u), R1(v, y), R2(v, x, u, y), R2(u, y, v, x)")
        q.queryId = 433
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)

    test("Lab4 E3 f"):
        val q = QueryParserService.parse("Answer(x) :- R(x, y), S(z)")
        q.queryId = 434
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)

    test("Lab4 E3 g"):
        val q = QueryParserService.parse("Answer(y, z) :- R(x, y), R(y, z), R(z, x), R(x, u), S(u, y), S(y, u)")
        q.queryId = 435
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)

    test("Lab4 E3 h"):
        val q = QueryParserService.parse("Answer(x, y) :- R(x, z), R(w, y)")
        q.queryId = 436
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)

    test("Lab4 E3 i"):
        val q = QueryParserService.parse("Answer(z, x, u) :- R(x, y, u), S(y, u, x), R(u, y, x), S(z, x, y), R(s, y, x)")
        q.queryId = 437
        assertEquals(QueryMinimizerService.isMinimal(q, false), false)