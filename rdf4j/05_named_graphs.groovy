#!/usr/bin/env groovy

@Grab(group='org.eclipse.rdf4j', module='rdf4j-model', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-rio-ntriples', version='4.0.0-M2')

import static org.eclipse.rdf4j.model.util.Values.*

import org.eclipse.rdf4j.model.util.ModelBuilder
import org.eclipse.rdf4j.model.impl.*
import org.eclipse.rdf4j.model.vocabulary.*

import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio

def builder = new ModelBuilder()

builder.setNamespace("ex","http://example.org/")

builder.namedGraph("ex:namedGraph1")
       .subject("ex:Picasso")
          .add(RDF.TYPE, "ex:Artist")
          .add(FOAF.FIRST_NAME,"Pablo")

builder.namedGraph("ex:namedGraph2")
       .subject("ex:VanGogh")
           .add(RDF.TYPE, "ex:Artist")
           .add(FOAF.FIRST_NAME, "Vincent")

def model = builder.build()

model.contexts().each {
    context -> {
        println("Named graph ${context} contains: ")
        Rio.write(model.filter(null,null,null,context), System.out, RDFFormat.NTRIPLES)
    }
}