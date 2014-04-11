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

file { "/var/lib/jenkins/jobs/build-front/":
  ensure  => "directory",
  group   => "nogroup",
  owner   => "jenkins",
  require => Exec [ "jenkins started" ],
  notify  => Exec [ "restart jenkins" ]
}

file { "/var/lib/jenkins/jobs/build-front/config.xml":
  ensure  => "file",
  group   => "nogroup",
  owner   => "jenkins",
  source  => "/vagrant/files/build-front.xml",
  require => File [ "/var/lib/jenkins/jobs/build-front/" ]
}

file { "/var/lib/jenkins/jobs/deploy-front/":
  ensure  => "directory",
  group   => "nogroup",
  owner   => "jenkins",
  require => Exec [ "jenkins started" ],
  notify  => Exec [ "restart jenkins" ]
}

file { "/var/lib/jenkins/jobs/deploy-front/config.xml":
  ensure  => "file",
  group   => "nogroup",
  owner   => "jenkins",
  source  => "/vagrant/files/deploy-front.xml",
  require => File [ "/var/lib/jenkins/jobs/deploy-front/" ],
  notify  => Exec [ "restart jenkins" ]
}

package { "make":
  ensure  => "installed",
  require => File [ "/var/lib/jenkins/jobs/build-front/config.xml" ]
}

package { "g++":
  ensure  => "installed",
  require => File [ "/var/lib/jenkins/jobs/build-front/config.xml" ]
}

jenkins::plugin { "scm-api":
  plugin => "scm-api.hpi"
}

jenkins::plugin { "git-client":
  plugin => "git-client.hpi"
}

jenkins::plugin { "git":
  plugin => "git.hpi"
}

jenkins::plugin { "jquery":
  plugin => "jquery.jpi"
}

jenkins::plugin { "parameterized-trigger":
  plugin => "parameterized-trigger.jpi"
}

jenkins::plugin { "build-pipeline-plugin":
  plugin => "build-pipeline-plugin.jpi"
}
