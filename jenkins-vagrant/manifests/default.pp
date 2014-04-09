import "apt.pp"
import "jenkins.pp"
import "nodejs.pp"

include apt
include jenkins
include nodejs

package { "git":
  ensure  => "installed",
  require => Exec [ "apt-get update" ]
}

file { "/var/lib/jenkins/jobs/build-front/":
  ensure  => "directory",
  group   => "nogroup",
  owner   => "jenkins",
  require => Exec [ "install_jenkins" ],
  notify  => Service [ "jenkins" ]
}

file { "/var/lib/jenkins/jobs/build-front/config.xml":
  ensure  => "file",
  group   => "nogroup",
  owner   => "jenkins",
  source  => "/vagrant/files/build-front.xml",
  require => File [ "/var/lib/jenkins/jobs/build-front/" ],
  notify  => Service [ "jenkins" ]
}

package { "make":
  ensure  => "installed",
  require => File [ "/var/lib/jenkins/jobs/build-front/config.xml" ]
}

package { "g++":
  ensure  => "installed",
  require => File [ "/var/lib/jenkins/jobs/build-front/config.xml" ]
}

file { "/etc/default/jenkins":
  ensure  => "file",
  owner   => "root",
  group   => "root",
  source  => "/vagrant/files/jenkins",
  require => Exec [ "install_jenkins" ],
  notify  => Service [ "jenkins" ]
}

define jenkins_plugin ($plugin) {
  file { "/var/lib/jenkins/plugins/$plugin":
    ensure  => "file",
    owner   => "jenkins",
    group   => "nogroup",
    source  => "/vagrant/files/$plugin",
    require => Exec [ "install_jenkins" ],
    notify  => Service [ "jenkins" ]
  }
}

jenkins_plugin { "scm-api":
  plugin => "scm-api.hpi"
}

jenkins_plugin { "git-client":
  plugin => "git-client.hpi"
}

jenkins_plugin { "git":
  plugin => "git.hpi"
}

jenkins_plugin { "jquery":
  plugin => "jquery.jpi"
}

jenkins_plugin { "parameterized-trigger":
  plugin => "parameterized-trigger.jpi"
}

jenkins_plugin { "build-pipeline-plugin":
  plugin => "build-pipeline-plugin.jpi"
}
