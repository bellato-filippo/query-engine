package Models

import scala.compiletime.ops.double

class Homomorphism(val source: Set[Term], val destination: Set[Term], val map: Map[Term, Term]):
    def isMapValidForSource(): Boolean = 
        map.keys.forall(source.contains)

    def isMapValidForDestination(): Boolean = 
        map.values.forall(destination.contains)
    
    def isActiveDomainVariablesMapped(): Boolean = 
        source.filter(u => u.termType == TermType.Var).forall(map.contains)

    def isActiveDomainConstantMapped(): Boolean = 
        var allMapped = true

        source.filter(u => u.termType == TermType.Cons).foreach { item =>
            if (!map.get(item).contains(item)) then
                allMapped = false
        }
        allMapped

    def isValid(): Boolean = 
        isMapValidForSource()
        && isMapValidForDestination()
        && isActiveDomainVariablesMapped()
        && isActiveDomainConstantMapped()