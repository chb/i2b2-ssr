# TOMCAT SERVERS
role :core, "carra-core"
role(:jboss) {
  hosts = (1..3).map { |x| "carra-test-#{x}" }
  hosts
}
