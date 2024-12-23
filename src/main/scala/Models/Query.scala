package Models

import java.io._
import Services.AcyclicityService

class Query(var queryId: Int, val head: Head, val body: Set[Atom]):
    override def equals(that: Any): Boolean = that match
        case q: Query => this.queryId == q.queryId && this.head == q.head && this.body.toSet == q.body.toSet
        case _ => false

    override def hashCode(): Int =
        val prime = 31
        val headHash = if (head != null) head.hashCode else 0
        val bodyHash = if (body != null) body.hashCode else 0
        prime * (prime * queryId + headHash) + bodyHash

    infix def isContainedIn(query: Query): Boolean = 
        Services.ContainmentService.isContainedIn(this, query)
    
    def isMinimal(): Boolean = 
        Services.QueryMinimizerService.isMinimal(this)

    def isBoolean(): Boolean = 
        head.terms.isEmpty
    
    override def toString(): String = 
        s"${head.toString()} :- ${body.map(_.toString).mkString(",")}"
        
    def isAcyclic: Boolean =
        AcyclicityService.isAcyclic(this)