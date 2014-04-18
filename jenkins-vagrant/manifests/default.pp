import "apt.pp"
import "jenkins.pp"
import "nodejs.pp"
import "maven.pp"
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

jenkins::job { "test-front":   name => "test-front"   }
jenkins::job { "build-front":  name => "build-front"  }
jenkins::job { "deploy-front": name => "deploy-front" }

jenkins::job { "build-back":      name => "build-back"      }
jenkins::job { "deploy-back":     name => "deploy-back"     }
jenkins::job { "switch-env-back": name => "switch-env-back" }

jenkins::job { "test-front-2":   name => "test-front-2"   }
jenkins::job { "build-front-2":  name => "build-front-2"  }
jenkins::job { "deploy-front-2": name => "deploy-front-2" }

package { "make":
  ensure  => "installed",
  require => Jenkins::Job [ "test-front" ]
}

package { "g++":
  ensure  => "installed",
  require => Jenkins::Job [ "test-front" ]
}

package { "curl":
  ensure => "installed"
}

package { "linux-image-generic-lts-raring":
  ensure => "installed",
  require => Exec [ "apt-get update" ]
}

package { "linux-headers-generic-lts-raring":
  ensure => "installed",
  require => Exec [ "apt-get update" ]
}

package { "virtualbox-guest-utils":
  ensure  => "installed",
  require => Package[ "linux-headers-generic-lts-raring" ]
}

jenkins::plugin { "scm-api":               plugin => "scm-api.hpi"               }
jenkins::plugin { "git-client":            plugin => "git-client.hpi"            }
jenkins::plugin { "git":                   plugin => "git.hpi"                   }
jenkins::plugin { "jquery":                plugin => "jquery.jpi"                }
jenkins::plugin { "parameterized-trigger": plugin => "parameterized-trigger.jpi" }
jenkins::plugin { "build-pipeline-plugin": plugin => "build-pipeline-plugin.jpi" }
jenkins::plugin { "claim":                 plugin => "claim.hpi"                 }
jenkins::plugin { "z-mon":                 plugin => "z-mon.hpi"                 }
jenkins::plugin { "copy-artifact":         plugin => "copyartifact.hpi"          }

