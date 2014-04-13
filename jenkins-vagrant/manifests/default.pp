import "apt.pp"
import "jenkins.pp"
import "nodejs.pp"
import "git.pp"

include apt
include jenkins
include nodejs

package { "git":
  ensure  => "installed",
  require => Exec [ "apt-get update" ]
}

file { "/var/lib/jenkins/config.xml":
  ensure  => "file",
  group   => "nogroup",
  owner   => "jenkins",
  source  => "/vagrant/files/config.xml",
  require => Exec [ "jenkins started" ],
  notify  => Exec [ "restart jenkins" ]
}

jenkins::job { "build-front":  name => "build-front"  }
jenkins::job { "deploy-front": name => "deploy-front" }
jenkins::job { "build-back":   name => "build-back"   }

package { "make":
  ensure  => "installed",
  require => Jenkins::Job [ "build-front" ]
}

package { "g++":
  ensure  => "installed",
  require => Jenkins::Job [ "build-front" ]
}

jenkins::plugin { "scm-api":               plugin => "scm-api.hpi"               }
jenkins::plugin { "git-client":            plugin => "git-client.hpi"            }
jenkins::plugin { "git":                   plugin => "git.hpi"                   }
jenkins::plugin { "jquery":                plugin => "jquery.jpi"                }
jenkins::plugin { "parameterized-trigger": plugin => "parameterized-trigger.jpi" }
jenkins::plugin { "build-pipeline-plugin": plugin => "build-pipeline-plugin.jpi" }

