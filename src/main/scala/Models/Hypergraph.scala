package Models

class Hypergraph(val q: Query):
    val vertices: Set[Term] = q.body.flatMap(_.terms)
    var edges: Set[Set[Term]] = q.body.map(atom => atom.terms.toSet)

    //controllo se ci sono vertici contenuti in altri edges e ottengo il set di term contenuti in altri edge
    def containedVertices(edge: Set[Term]): Set[Term] = {
        val containedverts = scala.collection.mutable.Set[Term]()   //set mutabile per inserire i vertici contenuti in altri edge
        for (v <- edge) {   //devo vedere se il vertice v è contenuto in altri edge quindi prende vertice per vertice e per ognuno di essi controllo se è contenuto in uno degli edge
            for (e <- edges if e != edge) {
                if (e.contains(v)) {
                    containedverts.add(v)   //se il vertice è contenuto lo aggiungo al set
                }
            }
        }
        containedverts.toSet
    }

    def isEar(edge: Set[Term]): Boolean =
        val containedverts: Set[Term] = containedVertices(edge)  //controllo la prima condizione (se i vertici sono tutti esclusivi)
        if(containedverts.isEmpty)
            return true
        //ora abbiamo un set con tutti i vertici dell'edge contenuti in altri edge, quindi basta vedere se c'è un edge che li contenga tutti
        edges.exists(e =>
            e != edge && containedverts.subsetOf(e))

    override def toString: String =
        s"Hypergraph(\n Vertices:  ${vertices.mkString(", ")};\n Edges:  ${edges.map(_.mkString("{",", ","}")).mkString("; ")})"