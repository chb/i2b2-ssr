i2b2-ssr Modules Overview
===================================

#Introduction
The architecture of i2b2-ssr is designed as a modular extension of SHRINE network as well as a suite of tools
to help manage i2b2-ssr and networks modeled after i2b2-ssr.  I2b2-ssr networks are made up of i2b2 datastores
connected by two components, a central broadcasting component and a small adapter that must be placed
on the same machine as the i2b2 data source.



#Components
![Alt text](images/Architecture.svg)

##Broadcaster
The broadcaster includes everything needed to start up an i2b2-ssr network including a simple
administrative interface for configuring the network and managing users and running the broadcaster
side of the i2b2-ssr network.

##Node Component
This war sits next to an existing i2b2 installation.  It is the adapter which allows access to the SHRINE
network.  This software is given an account in an existing i2b2 datasource and use this account to provide
data back to the central broadcaster.

#Differences from SHRINE
The current version of CARRAnet is built on a hub and spoke model with
