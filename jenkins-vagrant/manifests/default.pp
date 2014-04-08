import "jenkins.pp"

include jenkins

package { "git":
  ensure  => "installed"
}

file { "/etc/default/jenkins":
  ensure => "file",
  owner  => "root",
  group  => "root",
  source => "/vagrant/files/jenkins",
  require => Package [ "jenkins" ]
}

exec { "/usr/bin/wget -O /var/lib/jenkins/plugins/git.hpi http://updates.jenkins-ci.org/latest/git.hpi":
  creates => "/var/lib/jenkins/plugins/git.hpi",
  user    => "jenkins",
  notify  => Service [ "jenkins" ],
  require => Exec [
    "/usr/bin/wget -O /var/lib/jenkins/plugins/git-client.hpi http://updates.jenkins-ci.org/latest/git-client.hpi",
    "/usr/bin/wget -O /var/lib/jenkins/plugins/scm-api.hpi http://updates.jenkins-ci.org/latest/scm-api.hpi"
  ]
}

exec { "/usr/bin/wget -O /var/lib/jenkins/plugins/git-client.hpi http://updates.jenkins-ci.org/latest/git-client.hpi":
  creates => "/var/lib/jenkins/plugins/git-client.hpi",
  user    => "jenkins",
  require => Package [
    "jenkins",
    "git"
  ]
}

exec { "/usr/bin/wget -O /var/lib/jenkins/plugins/scm-api.hpi http://updates.jenkins-ci.org/latest/scm-api.hpi":
  creates => "/var/lib/jenkins/plugins/scm-api.hpi",
  user    => "jenkins",
  require => Package [
    "jenkins",
    "git"
  ]
}
