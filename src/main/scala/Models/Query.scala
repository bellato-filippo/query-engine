package Models

import upickle.default._

class Query(val queryId: Int, val head: Head, val body: Set[Atom]):
    override def equals(that: Any): Boolean = that match
        case q: Query => this.queryId == q.queryId && this.head == q.head && this.body.toSet == q.body.toSet
        case _ => false

    infix def isContainedIn(query: Query): Boolean = 
        Services.ContainmentService.isContainedIn(this, query)
    
    def isMinimal(): Boolean = 
        var sonar = true
        for (atom <- body) {
            val smallerQuery: Query = Query(queryId, head, body - atom)
            if (Services.ContainmentService.isContainedIn(this, smallerQuery) && Services.ContainmentService.isContainedIn(smallerQuery, this))
                sonar = false
        }
        sonar
    
    override def toString(): String = 
        s"${head.toString()} :- ${body.map(_.toString).mkString(",")}"