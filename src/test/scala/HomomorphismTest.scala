import Models.*
class HomomorphismTest extends munit.FunSuite {
  // Sample Terms for testing
  val varX = new Term("x", TermType.Var)
  val varY = new Term("y", TermType.Var)

  val consA = new Term("a", TermType.Cons)
  val consB = new Term("b", TermType.Cons)

  test("isMapValidForSource should return true when all keys are present in the source") {
    val source = Set(varX, varY)
    val destination = Set(consA, consB)
    val mapping = Map(varX -> consA, varY -> consB)

    val homomorphism = Homomorphism(source, destination, mapping)
    assert(homomorphism.isMapValidForSource())
  }

  test("isMapValidForSource should return false when some keys are not present in the source") {
    val source = Set(varX)
    val destination = Set(consA, consB)
    val mapping = Map(varX -> consA, varY -> consB)

    val homomorphism = Homomorphism(source, destination, mapping)
    assert(!homomorphism.isMapValidForSource())
  }

  test("isMapValidForDestination should return true when all values are present in the destination") {
    val source = Set(varX, varY)
    val destination = Set(consA, consB)
    val mapping = Map(varX -> consA, varY -> consB)

    val homomorphism = Homomorphism(source, destination, mapping)
    assert(homomorphism.isMapValidForDestination())
  }

  test("isMapValidForDestination should return false when some values are not present in the destination") {
    val source = Set(varX, varY)
    val destination = Set(consA)
    val mapping = Map(varX -> consA, varY -> consB)

    val homomorphism = Homomorphism(source, destination, mapping)
    assert(!homomorphism.isMapValidForDestination())
  }

  test("isActiveDomainVariablesMapped should return true when all variable terms in the source are mapped") {
    val source = Set(varX, varY)
    val destination = Set(consA, consB)
    val mapping = Map(varX -> consA, varY -> consB)

    val homomorphism = Homomorphism(source, destination, mapping)
    assert(homomorphism.isActiveDomainVariablesMapped())
  }

  test("isActiveDomainVariablesMapped should return false when any variable term in the source is not mapped") {
    val source = Set(varX, varY)
    val destination = Set(consA, consB)
    val mapping = Map(varX -> consA)

    val homomorphism = Homomorphism(source, destination, mapping)
    assert(!homomorphism.isActiveDomainVariablesMapped())
  }

  test("isActiveDomainConstantMapped should return true when all constant terms map to themselves") {
    val source = Set(consA, consB)
    val destination = Set(consA, consB)
    val mapping = Map(consA -> consA, consB -> consB)

    val homomorphism = Homomorphism(source, destination, mapping)
    assert(homomorphism.isActiveDomainConstantMapped())
  }

  test("isActiveDomainConstantMapped should return false when any constant term does not map to itself") {
    val source = Set(consA, consB)
    val destination = Set(consA, consB)
    val mapping = Map(consA -> consB)

    val homomorphism = new Homomorphism(source, destination, mapping)
    assert(!homomorphism.isActiveDomainConstantMapped())
  }
}