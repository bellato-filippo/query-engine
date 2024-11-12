package Models

import upickle.default._
import scala.compiletime.ops.double
import scala.collection.BuildFrom.buildFromString


case class Homomorphism(val source: Set[Term], val destination: Set[Term], val map: Map[Term, Term]):
    override def toString(): String =
        var s: String = ""
        // s = "------------------------------------------------------------\n"

        // s += s"Source -> ${source.toString()}\n"
        // s += s"Destination -> ${destination.toString()}\n"
        // s += s"Mapping -> ${map.toString()}"
        s += s"(S: ${source.toString()}, D: ${destination.toString()}, M: ${map.toString()})"

        s


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

    

object Homomorphism:
    implicit val rw: ReadWriter[Homomorphism] = macroRW