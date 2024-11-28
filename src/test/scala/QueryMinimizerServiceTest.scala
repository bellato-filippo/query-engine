import Services.*
class QueryMinimizerServiceTest extends munit.FunSuite:
    test("Lab4 E3 a"):
        val q = QueryParserService.parse("Answer(x) :- R(x, y), S(z)")
        assertEquals(QueryMinimizerService.isMinimal(q), true)

    // test("Lab4 E3 b"):
    //     val q = QueryParserService.parse("Answer(x) :- R1(x, y, w, z), R1(x, w, x, y), R1(x, y, w, s), R2(u, v), R2(s, t), R2(v, u)")
    //     assertEquals(QueryMinimizerService.isMinimal(q), false)

    // test("Lab4 E3 c"):
    //     val q = QueryParserService.parse("Answer(x, y) :- R(u, v, w), R(x, y, z), R(u, y, z), S(u, v), S(r, s)")
    //     assertEquals(QueryMinimizerService.isMinimal(q), false)

    test("Lab4 E3 f"):
        val q = QueryParserService.parse("Answer(x) :- R(x, y), S(z)")
        assertEquals(QueryMinimizerService.isMinimal(q), true)

    test("Lab4 E3 h"):
        val q = QueryParserService.parse("Answer(x, y) :- R(x, z), R(w, y)")
        assertEquals(QueryMinimizerService.isMinimal(q), true)