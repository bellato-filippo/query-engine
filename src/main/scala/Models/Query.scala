package Models

class Query(val queryId: Int, val head: Head, val body: Set[Atom]):
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
    
    def GyoAlgorithm(hypergraph: Hypergraph): Hypergraph =
        //per ogni edge guardo se è un orecchio
        if (hypergraph.edges.isEmpty)
          return hypergraph

        hypergraph.edges.foreach(e => {
            if (hypergraph.isEar(e)) {
                hypergraph.edges = hypergraph.edges - e
                writer.println("We remove the ear: " + e)
                writer.println("Current hypergraph edges are: " + hypergraph.edges.map(_.mkString("{",", ","}")).mkString("; ") )
                return GyoAlgorithm(hypergraph)
            }
        })
        hypergraph
        
    def isAcyclic: Boolean =
        writer.println("GYO for query: " + this.toString())
        val h: Hypergraph = new Hypergraph(this)
        val acyclic: Hypergraph = GyoAlgorithm(h)
        if(acyclic.edges.nonEmpty)
          writer.println("no more ears")
        else writer.println("Algorithm terminate with an empty query")
        writer.close()
        acyclic.edges.isEmpty
            //se l'edge e è un ear allora lo rimuovo
            //richiamo la funzione GYOAlghoritm


      //nell'homomorphismo posso mappare solo variabili della stessa relazione
      //1 step: mappare le variabili nell'header