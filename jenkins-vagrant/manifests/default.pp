import "jenkins.pp"

include jenkins

package { "git":
  ensure  => "installed",
  require => Exec [ "apt_update_once" ]
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

file { "/etc/default/jenkins":
  ensure  => "file",
  owner   => "root",
  group   => "root",
  source  => "/vagrant/files/jenkins",
  require => Exec [ "install_jenkins" ],
  notify  => Service [ "jenkins" ]
}

define jenkins_plugin ($plugin) {
  file { "/var/lib/jenkins/plugins/$plugin.hpi":
    ensure  => "file",
    owner   => "jenkins",
    group   => "nogroup",
    source  => "/vagrant/files/$plugin.hpi",
    require => Exec [ "install_jenkins" ],
    notify  => Service [ "jenkins" ]
  }
}

jenkins_plugin { "scm-api":
  plugin => "scm-api"
}

jenkins_plugin { "git-client":
  plugin => "git-client"
}

jenkins_plugin { "git":
  plugin => "git"
}
