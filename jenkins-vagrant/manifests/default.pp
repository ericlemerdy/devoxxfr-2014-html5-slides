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

exec { "/usr/bin/wget -O /var/lib/jenkins/plugins/git.jpi http://updates.jenkins-ci.org/latest/git.jpi":
  creates => "/var/lib/jenkins/plugins/git.jpi",
  user    => "root",
  require => Package [
    "jenkins",
    "git"
  ]
}
