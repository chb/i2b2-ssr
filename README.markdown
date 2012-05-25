i2b2-ssr 1.2
====================

#What is i2b2-ssr?
i2b2-ssr is project built upon the [SHRINE](https://open.med.harvard.edu/display/SHRINE/Concept) project specifically
for the CARRAnet registry project at Children's Hospital Boston.  I2b2-ssr has customized/extended SHRINE specifically
for querying and aggregating patient data and patients counts from large scale flat topology networks using a
centralized aggregation strategy.

All of this is done in a backwards compatible way such that existing i2b2 clients should work without modification
with i2b2-ssr based datastores


#Concepts
## Authorization
i2b2-ssr provides an authorization cell similar to the i2b2 Project management cell but using digitally signed tokens
to facilitate federated queries in a secure way.

##Overlay
i2b2-ssr provides a way to allow users of the system access to subsets of the entire i2b2-ssr network.

##Adapter software
The adapter software must be installed alongside a local i2b2 node.  This exposes the node to the i2b2-ssr network.

##Broadcaster/Aggregator
The broadcaster aggregator is responsible for broadcasting queries to i2b2 services with the requried adapter software.



##Modules:
* Authorization/Overlay REST
* SHRINE broadcaster/aggregators
* Administrative user interface
