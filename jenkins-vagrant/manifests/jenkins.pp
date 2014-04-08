import "apt.pp"

class jenkins {
  include apt

  package { "daemon"              : ensure => "installed", require => Exec [ "apt-get update" ] }
  package { "adduser"             : ensure => "installed", require => Exec [ "apt-get update" ] }
  package { "psmisc"              : ensure => "installed", require => Exec [ "apt-get update" ] }
  package { "default-jre-headless": ensure => "installed", require => Exec [ "apt-get update" ] }

  exec { "install_jenkins":
    command => "/usr/bin/dpkg -i /vagrant/files/jenkins_1.558_all.deb",
    creates => "/var/lib/jenkins/",
    user    => "root",
    require => [
      Package [ "daemon", "adduser", "psmisc", "default-jre-headless" ],
      Exec [ "apt-get update" ]
    ]
  }

  service { "jenkins":
    ensure  => running,
    enable  => true,
    require => Exec [ "install_jenkins" ]
  }

  user { "jenkins":
    groups  => "vagrant",
    require => Exec [ "install_jenkins" ]
  }
}