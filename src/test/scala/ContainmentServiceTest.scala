import Models.*
import Services.*

class ContainmentServiceTest extends munit.FunSuite {
    test("Extract terms from query test"):
        val q1: Query = Query(0, Head(List(Variable("x"), Variable("y"), Variable("z"))), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("S", List(Variable("y"), Variable("z")))))
        val terms1: Set[Term] = ContainmentService.extractTermsFromQuery(q1)
        val terms2: Set[Term] = Set(Variable("x"), Variable("y"), Variable("z"))
        assertEquals(terms1, terms2)

    test("Extract terms from query test 2"):
        val q1: Query = Query(0, Head(List(Variable("x"))), Set(Atom("R", List(Variable("x"), Variable("y")))))
        val terms1: Set[Term] = ContainmentService.extractTermsFromQuery(q1)
        val terms2: Set[Term] = Set(Variable("x"), Variable("y"))
        assertEquals(terms1, terms2)

    test("Extract terms from query test 3"):
        val q1: Query = Query(0, Head(List(Variable("x"))), Set(Atom("R", List(Variable("x"))), Atom("S", List(Variable("y"), Constant("a")))))
        val terms1: Set[Term] = ContainmentService.extractTermsFromQuery(q1)
        val terms2: Set[Term] = Set(Variable("x"), Variable("y"), Constant("a"))
        assertEquals(terms1, terms2)

    test("Lab4 E1 a"):
        val q1 = QueryParserService.parse("Answer(x2) :- R1(x1, x2, x3), R2(x2, 'a'), R3(x3, 'b', 'c')")
        val q2 = QueryParserService.parse("Answer(y2) :- R2(y1, y4), R1(y1, y2, y3)")
        assertEquals(ContainmentService.isContainedIn(q1, q2, false), false)
        assertEquals(ContainmentService.isContainedIn(q2, q1, false), false)

    test("Lab4 E1 b"):
        val q1 = QueryParserService.parse("Answer(x3) :- R1(x1), S1(x1, x3), R2(x1, x4), S2(x5, x1)")
        val q2 = QueryParserService.parse("Answer() :- R1(x1), S1(x1, x3)")
        assertEquals(ContainmentService.isContainedIn(q1, q2, false), false)
        assertEquals(ContainmentService.isContainedIn(q2, q1, false), false)

    test("Lab4 E1 c"):
        val q1 = QueryParserService.parse("Answer(x1, x2, x3) :- S1(x1, x2), S2(x2, 'a'), S3(x3, 'b'), S4(x3, x1)")
        val q2 = QueryParserService.parse("Answer(y2, y3, y1) :- S4(y1, y2), S1(y2, y3), S2(y3, y4)")
        assertEquals(ContainmentService.isContainedIn(q1, q2, false), true)
        assertEquals(ContainmentService.isContainedIn(q2, q1, false), false)

    test("Lab4 E1 d"):
        val q1 = QueryParserService.parse("Answer(x1) :- R(x1, y1), S(y1, x2), R(x1, y2), S(y2, x2)")
        val q2 = QueryParserService.parse("Answer(u) :- R(u, v), S(v, w), R(x, v), R(x, y)")
        assertEquals(ContainmentService.isContainedIn(q1, q2, false), true)
        assertEquals(ContainmentService.isContainedIn(q2, q1, false), true)

    test("Lab4 E2 a"):
        val q1 = QueryParserService.parse("Answer(y1, y2, y3) :- R1(y1, y3), R6(y3, 'a'), R3(y2, y1), R5('b', y1), R2(y3, y2), R4(y2, 'a')")
        val q2 = QueryParserService.parse("Answer(z, y, x) :- R2(x, y), R3(y, z), R1(z, x)")
        assertEquals(ContainmentService.isContainedIn(q1, q2, false), true)
        assertEquals(ContainmentService.isContainedIn(q2, q1, false), false)

    test("Lab4 E2 b"):
        val q1 = QueryParserService.parse("Answer(x2, x3) :- R1(x1, x2, 'a'), R2(x2, x1, x3), R3(x3, 'c'), R4('d', x1)")
        val q2 = QueryParserService.parse("Answer(y2, y3) :- R2(y2, y1, 'e'), R1(y1, y2, y3)")
        assertEquals(ContainmentService.isContainedIn(q1, q2, false), false)
        assertEquals(ContainmentService.isContainedIn(q2, q1, false), false)

    test("Lab4 E2 c"):
        val q1 = QueryParserService.parse("Answer(y2, y3, y1) :- S4(y1, y2), S1(y2, y3), S2(y3, y4)")
        val q2 = QueryParserService.parse("Answer(x1, x2, x3) :- S1(x1, x2), S2(x2, 'a'), S3(x3, 'b'), S4(x3, x1)")
        assertEquals(ContainmentService.isContainedIn(q1, q2, false), false)
        assertEquals(ContainmentService.isContainedIn(q2, q1, false), true)

    test("Lab4 E2 d"):
        val q1 = QueryParserService.parse("Answer(x1) :- R(x1, y1), S(y1, x2), R(x1, y2), S(y2, x2)")
        val q2 = QueryParserService.parse("Answer(u) :- R(u, v), S(v, w), R(x, v), R(x, y)")
        assertEquals(ContainmentService.isContainedIn(q1, q2, false), true)
        assertEquals(ContainmentService.isContainedIn(q2, q1, false), true)
}