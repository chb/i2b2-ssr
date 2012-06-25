i2b2-ssr Modules Overview
===================================

#Introduction
The architecture of i2b2-ssr is designed as a modular extension of SHRINE network as well as a suite of tools
to help manage i2b2-ssr and networks modeled after i2b2-ssr.  I2b2-ssr networks are made up of i2b2 datastores
connected by two components, a central broadcasting component and a small adapter that must be placed
on the same machine as the i2b2 data source.



#Components
![Architecture](https://raw.github.com/chb/i2b2-ssr/master/Documentation/images/architecture.jpg)

##Broadcaster
The broadcaster includes everything needed to start up an i2b2-ssr network including a simple
administrative interface for configuring the network and managing users and running the broadcaster
side of the i2b2-ssr network.  As of version 1.2 of i2b2-ssr the Authenticator, Overlay Service and broadcaster have
been bundled into a single, easy to deploy war file.

The broadcaster must be installed on Tomcat 6 or higher.

##Node Component
The node component is the i2b2 connector portion of i2b2-ssr.  Data providers who care to contribute to an existing
i2b2-ssr network must install this connector on the local machine running i2b2.  This component requires Tomcat6
or higher.

#Differences from SHRINE
I2b2-ssr heavily draws on code and previous work of the SHRINE project.  SHRINE is designed to suport multiple network
topologies including fully meshed peer-to-peer networks and more traditional hub and spoke networks with a single fixed
aggregation point.  In order to meet the needs of CARRAnet registry use case i2b2-ssr has focused on making the hub and
spoke single aggregation use case as painless as possible while adding a features which might otherwise be difficult
to implement in a p2p SHRINE network

![Peergroups](https://raw.github.com/chb/i2b2-ssr/master/Documentation/images/peergroups.jpg)

The hub and spoke topology allows i2b2-ssr to implement:

- Sophsticated access control via standard permissioning system and peergroups
- User dependant data tagging
- Centralized auditing
- Centralized certificate provisioning









