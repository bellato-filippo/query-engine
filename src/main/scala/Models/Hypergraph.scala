package Models

class Hypergraph(val q: Query):
    val vertices: Set[Term] = q.body.flatMap(_.terms)
    var edges: Set[Set[Term]] = q.body.map(atom => atom.terms.toSet)

    def containedVertices(edge: Set[Term]): Set[Term] = {
        val containedverts = scala.collection.mutable.Set[Term]()
        for (v <- edge) {
            for (e <- edges if e != edge) {
                if (e.contains(v)) {
                    containedverts.add(v)
                }
            }
        }
        containedverts.toSet
    }

    def isEar(edge: Set[Term]): Boolean =
        val containedverts: Set[Term] = containedVertices(edge)
        if(containedverts.isEmpty)
            return true
        edges.exists(e =>
            e != edge && containedverts.subsetOf(e))

    override def toString: String =
        s"Hypergraph(\n Vertices:  ${vertices.mkString(", ")};\n Edges:  ${edges.map(_.mkString("{",", ","}")).mkString("; ")})"