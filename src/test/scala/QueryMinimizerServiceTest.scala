import Services.*
import scala.concurrent.duration.Duration
class QueryMinimizerServiceTest extends munit.FunSuite:
    override def munitTimeout: Duration = Duration(120, "s")

    test("Lab4 E3 a"):
        val q = QueryParserService.parse("Answer(x) :- R(x, y), S(z)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)

    test("Lab4 E3 b"):
        val q = QueryParserService.parse("Answer(x) :- R1(x, y, w, z), R1(x, w, x, y), R1(x, y, w, s), R2(u, v), R2(s, t), R2(v, u)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), false)

    test("Lab4 E3 c"):
        val q = QueryParserService.parse("Answer(x, y) :- R(u, v, w), R(x, y, z), R(u, y, z), S(u, v), S(r, s)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), false)
        
    test("Lab4 E3 d"):
        val q = QueryParserService.parse("Answer(x, y, z) :- R(x, y, z), R(u, v, z), R(u, y, r), S(x, y), S(u, v)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), false)

    test("Lab4 E3 e"):
        val q = QueryParserService.parse("Answer(y) :- R1(x, y), R1(v, u), R1(v, y), R2(v, x, u, y), R2(u, y, v, x)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)

    test("Lab4 E3 f"):
        val q = QueryParserService.parse("Answer(x) :- R(x, y), S(z)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)

    test("Lab4 E3 g"):
        val q = QueryParserService.parse("Answer(y, z) :- R(x, y), R(y, z), R(z, x), R(x, u), S(u, y), S(y, u)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)

    test("Lab4 E3 h"):
        val q = QueryParserService.parse("Answer(x, y) :- R(x, z), R(w, y)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)

    test("Lab4 E3 i"):
        val q = QueryParserService.parse("Answer(z, x, u) :- R(x, y, u), S(y, u, x), R(u, y, x), S(z, x, y), R(s, y, x)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), false)

    test("Lab4 E3 j"):
        val q = QueryParserService.parse("Answer(u, v) :- S1(u, v, w, x, y), S1(r, s, t, y, x), S1(u, s, w, x, x), S2(v, w), S2(x, y)")
        assertEquals(QueryMinimizerService.isMinimal(q, false), true)