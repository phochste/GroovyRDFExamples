#!/usr/bin/env groovy

@Grab(group='org.eclipse.rdf4j', module='rdf4j-model', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-rio-turtle', version='4.0.0-M2')

import static org.eclipse.rdf4j.model.util.Values.*

import org.eclipse.rdf4j.model.util.Models
import org.eclipse.rdf4j.model.vocabulary.*
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio

def model = null;

new File("example.ttl").withReader('utf-8') {
    reader ->
        model = Rio.parse(reader,"urn:example.ttl",RDFFormat.TURTLE)
}

def bob      = iri("urn:example.ttl#bob")

def resModel = model.filter(bob, null, null)

resModel.each( st -> {
    def subject   = st.subject
    def predicate = st.predicate
    def object    = st.object

    println "+++"
    println subject
    println predicate

    if (object.isLiteral()) {
        println "literal: ${object}"
    }
    else if (object.isIRI()) {
        println "iri: ${object}"
    }
    else {
        println "blank: ${object}"
    }
    println "---"
})

def name = model.filter(bob, FOAF.NAME, null)

def bobsName = Models.objectString(name).orElse("(unknown)")

println "${bob}'s name is ${bobsName}"