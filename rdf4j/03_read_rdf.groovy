#!/usr/bin/env groovy

@Grab(group='org.eclipse.rdf4j', module='rdf4j-model', version='4.0.0-M2')
@Grab(group='org.eclipse.rdf4j', module='rdf4j-rio-turtle', version='4.0.0-M2')

import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio

def model = null;

new File("tmp/02_write.ttl").withReader('utf-8') {
    reader ->
        model = Rio.parse(reader,"",RDFFormat.TURTLE)
}

model.each( st -> {
    println(st)
})