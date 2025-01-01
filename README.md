# Create a query
To create a query we can simply use the Query, Atom and Term classes to compose the query

```scala
// this code creates the following query Answer(x,y,z) :- R(x,y),S(y,z)
val q: Query = Query(1, Head(List(Variable("x"), Variable("y"), Variable("z"))), Set(Atom("R", List(Variable("x"), Variable("y"))), Atom("S", List(Variable("y"), Variable("z")))))
```

A faster way to create a query object is to use the query parser (parse method from QueryParserService) that takes a query in the string format and converts it into the object
```scala
// this code creates the following query Answer(x,y,z) :- R(x,y),S(y,z)
val q: Query = QueryParserService.parse("Answer(x,y,z) :- R(x,y),S(y,z)")
```
In order for it to work the string should be in the correct format:
* body and head must be separated by `:-`
* the head must contain one atom of the format `Answer(variable, 'constant')`. The list of terms is sparated by commas `,` and can either be a variable or a constant surrounded by a single quote `'`
* the body can contain many atoms of the form `Relation(variable, 'constant')`. The atoms must be separated by commas `,`. The list of terms for each relation is sparated by commas `,` and can either be a variable or a constant surrounded by a single quote `'`

The query parser sets the query id to `0` by default. If the query must have an id it should be set once the query object has been instantiated.

# Features
## Printing
The query overrides the `toString` method to print its content
```scala
val q: Query = QueryParserService.parse("Answer(x,y,z) :- R(x,y),S(y,z)")
val text = q.toString()
// text contains Answer(x,y,z) :- R(x,y),S(y,z)
```

## Acyclicity
To test if a query is acyclic we can invoke the `isAcyclic` method as follows
The query overrides the `toString` method to print its content
```scala
val q: Query = QueryParserService.parse("Answer(x,y,z) :- R(x,y),S(y,z)")
val isAcyclic = q.isAcyclic // true
```

## Containment
To test if a query is contained in another we invoke the `isContainedIn` method as follows
```scala
val q1: Query = QueryParserService.parse("Answer(x,y,z) :- R(x,y),S(y,z)")
val q2: Query = QueryParserService.parse("Answer(x,y,z) :- R(x,y),S(y,z)")
val isContained = q1 isContainedIn q2 // true
```

## Minimal
To test if a query is minimal we invoke the `isMinimal` method as follows
```scala
val q = QueryParserService.parse("Answer(x) :- R(x, y), S(z)")
val isMinimal = q.isMinimal // true
```