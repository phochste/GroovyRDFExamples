#!/usr/bin/env groovy

@Grab(group='org.eclipse.rdf4j', module='rdf4j-model', version='4.0.0-M2')

import static org.eclipse.rdf4j.model.util.Values.iri
import static org.eclipse.rdf4j.model.util.Values.literal
import static org.eclipse.rdf4j.model.util.Values.bnode

import org.eclipse.rdf4j.model.*
import org.eclipse.rdf4j.model.impl.*
import org.eclipse.rdf4j.model.vocabulary.*

def ex      = "http://example.org/"
def picasso = iri(ex, "Picasso")
def artist  = iri(ex, "Artist")

def model = new TreeModel()

model.add(picasso, RDF.TYPE, artist)
model.add(picasso, FOAF.FIRST_NAME, literal("Pablo"))

model.each( st -> {
    println(st)
})