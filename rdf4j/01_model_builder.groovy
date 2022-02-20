#!/usr/bin/env groovy

@Grab(group='org.eclipse.rdf4j', module='rdf4j-model', version='4.0.0-M2')

import static org.eclipse.rdf4j.model.util.Values.iri
import static org.eclipse.rdf4j.model.util.Values.literal
import static org.eclipse.rdf4j.model.util.Values.bnode

import org.eclipse.rdf4j.model.*
import org.eclipse.rdf4j.model.impl.*
import org.eclipse.rdf4j.model.vocabulary.*

import org.eclipse.rdf4j.model.util.ModelBuilder
import java.time.LocalDate

def builder = new ModelBuilder()
def model  = builder.setNamespace("ex","https://example.org/")
                     .subject("ex:Picasso")
                     .add(RDF.TYPE,"ex:Artist")
                     .add(FOAF.FIRST_NAME,"Pablo")
                     .add("ex:creationDate", LocalDate.parse("1885-04-01"))
                     .add("ex:title",literal("De weg","nl"))
                     .add("ex:brol",bnode())
                     .build()

model.each( st -> {
    println(st)
})