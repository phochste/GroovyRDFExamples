#!/usr/bin/env groovy

@Grab(group='org.eclipse.rdf4j', module='rdf4j-model', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-rio-turtle', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-sail-memory', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-repository-sail', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-queryresultio-text', version='4.0.0-M2')

import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio
import org.eclipse.rdf4j.repository.sail.SailRepository
import org.eclipse.rdf4j.sail.memory.MemoryStore
import org.eclipse.rdf4j.query.resultio.text.tsv.SPARQLResultsTSVWriter
import org.eclipse.rdf4j.query.QueryResults

def example1() {
    def db = new SailRepository(new MemoryStore())

    try( def conn = db.getConnection() ) {
        new File("example.ttl").withReader('utf-8') {
            reader ->
                conn.add(reader,"urn:example.ttl",RDFFormat.TURTLE)
        }

        def sparql = '''
PREFIX foaf: <http://xmlns.com/foaf/0.1/>

SELECT ?s WHERE {
    ?s a foaf:Person
}
'''

        def query = conn.prepareTupleQuery(sparql)

        println "Just in code:"
        try (def result = query.evaluate()) {
            result.each {
                binding -> {
                    println "s: ${binding.getValue("s")}"
                }
            }
        }

        println "Or as TSV:"
        def tsvWriter = new SPARQLResultsTSVWriter(System.out)

        query.evaluate(tsvWriter)

    } finally {
        db.shutDown()
    }
}

def example2() {
    def db = new SailRepository(new MemoryStore())

    try( def conn = db.getConnection() ) {
        new File("example.ttl").withReader('utf-8') {
            reader ->
                conn.add(reader,"urn:example.ttl",RDFFormat.TURTLE)
        }

        def sparql = '''
PREFIX foaf: <http://xmlns.com/foaf/0.1/>

CONSTRUCT
WHERE {
    ?s a foaf:Person;
       foaf:name ?n .
}
'''

        def query = conn.prepareGraphQuery(sparql)

        println "As plain result:"
        try (def result = query.evaluate()) { 
            result.each {
                st -> println st
            }
        }

        println "As a model:"
        def model = QueryResults.asModel(query.evaluate())

        model.each {
            st -> println st
        }
        
        println "As turtle:"

        def turtleWriter = Rio.createWriter(RDFFormat.TURTLE, System.out);
        query.evaluate(turtleWriter)

    } finally {
        db.shutDown()
    }
}

println "example1:"
example1()

println "example2:"
example2()