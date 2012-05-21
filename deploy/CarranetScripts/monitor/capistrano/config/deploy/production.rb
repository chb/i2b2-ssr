# TOMCAT SERVERS
role :core, "carra-core-production"
role(:jboss) {
  hosts = (1..60).map { |x|
    x == 1 ? "carra-node-production" : "carra-node-#{x}-production"
  }
  hosts

}