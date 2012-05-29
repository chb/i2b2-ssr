i2b2-ssr
====================

#What is i2b2-ssr?
The i2b2-based self-scaling registry platform (i2b2-ssr) is a self-scaling, interoperable platform for
collaborative data sharing based upon the widely adopted SHRINE project, utilizing the Informatics for Integrating
Biology & the Bedside (i2b2) software as federated data source. The i2b2-ssr platform has been established as the
principal research data warehousing infrastructure for the 56-site Childhood Arthritis & Rheumatism Research Alliance
(CARRA) Registry of pediatric rheumatic diseases and Harvard Inflammatory Bowel Disease Longitudinal Data Repository.

i2b2-ssr has customized/extended SHRINE specifically for querying and aggregating patient data and patients counts from
large scale flat topology networks using a centralized aggregation strategy. This is accomplished in a
backwards-compatible fashion, whereby existing i2b2 clients should work without modification with i2b2-ssr
based datastores.


#Licensing
##Software License

The i2b2-SSR project code is copyright Children's Hospital Boston (2010-2012) and made available as open source software,
licensed under LGPL version 3 for i2b2-SSR components; constituent components and dependencies are available under their
respective open source licenses (repository and links at [https://open.med.harvard.edu/display/CARRANET]). The Webserver
package is copyright Cincinnati Children's Hospital Medical Center and
available at [https://bmi.cchmc.org/svn/i2b2/i2b2/public]

##Documentation License
The i2b2-SSR documentation is made available under the terms of the Creative Commons Attribution Share-Alike
version 3.0 unported license ([http://creativecommons.org/licenses/by-sa/3.0])

#Funding
We gratefully acknowledge the support that has made the i2b2-SSR project possible. Funding support was received from
the National Institute of Arthritis and Musculoskeletal and Skin Diseases (RC2AR058934), the National Library of
Medicine (1R01LM011185-01 and T15LM007092), Friends of CARRA, the Arthritis Foundation, the RasmussenFund, the National
Institute of Diabetes and Digestive and Kidney Diseases (NIH Loan Repayment Program), and the Duke Clinical Research
Institute.

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