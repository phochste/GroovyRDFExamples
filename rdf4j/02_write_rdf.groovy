#!/usr/bin/env groovy

@Grab(group='org.eclipse.rdf4j', module='rdf4j-model', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-rio-turtle', version='4.0.0-M2')

import static org.eclipse.rdf4j.model.util.Values.iri
import static org.eclipse.rdf4j.model.util.Values.literal
import static org.eclipse.rdf4j.model.util.Values.bnode

import org.eclipse.rdf4j.model.*
import org.eclipse.rdf4j.model.impl.*
import org.eclipse.rdf4j.model.vocabulary.*

import org.eclipse.rdf4j.model.util.ModelBuilder
import java.time.LocalDate

import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio

def builder = new ModelBuilder()
def model  = builder.setNamespace("ex","https://example.org/")
                     .subject("ex:Picasso")
                     .add(RDF.TYPE,"ex:Artist")
                     .add(FOAF.FIRST_NAME,"Pablo")
                     .add("ex:creationDate", LocalDate.parse("1885-04-01"))
                     .add("ex:title",literal("De weg","nl"))
                     .add("ex:brol",bnode())
                     .build()

new File("tmp/02_write.ttl").withWriter('utf-8') {
    writer ->
        Rio.write(model, writer, RDFFormat.TURTLE)
}