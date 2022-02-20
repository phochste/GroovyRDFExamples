#!/usr/bin/env groovy

@Grab(group='org.eclipse.rdf4j', module='rdf4j-model', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-rio-turtle', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-sail-memory', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-repository-sail', version='4.0.0-M2')

import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio
import org.eclipse.rdf4j.repository.sail.SailRepository
import org.eclipse.rdf4j.sail.memory.MemoryStore

// First create a model and then load it in the store
def example1() {
    def model = null;

    new File("example.ttl").withReader('utf-8') {
        reader ->
            model = Rio.parse(reader,"urn:example.ttl",RDFFormat.TURTLE)
    }

    def db = new SailRepository(new MemoryStore())

    try( def conn = db.getConnection() ) {
        conn.add(model)

        try( def result = conn.getStatements(null,null,null)) {
            result.each {
                st -> println(st)
            }
        }
    } finally {
        db.shutDown()
    }
}

// Directly load into store
def example2() {
    def db = new SailRepository(new MemoryStore())

    try( def conn = db.getConnection() ) {
        new File("example.ttl").withReader('utf-8') {
            reader ->
                conn.add(reader,"urn:example.ttl",RDFFormat.TURTLE)
        }

        try( def result = conn.getStatements(null,null,null)) {
            result.each {
                st -> println(st)
            }
        }
    } finally {
        db.shutDown()
    }
}

println "example1:"
example1()

println "example2:"
example2()