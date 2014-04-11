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

  exec { "wait for jenkins":
    require => Service [ "jenkins" ],
    logoutput   => "on_failure",
    command => "/usr/bin/wget --spider --tries 10 --retry-connrefused http://10.10.10.2:8080/api/json",
  }

  define plugin ($plugin) {
    file { "/var/lib/jenkins/plugins/$plugin":
      ensure  => "file",
      owner   => "jenkins",
      group   => "nogroup",
      source  => "/vagrant/files/$plugin",
      require => Exec [ "wait for jenkins" ]
    }
  }

  file { "/etc/default/jenkins":
    ensure  => "file",
    owner   => "root",
    group   => "root",
    source  => "/vagrant/files/jenkins",
    require => Exec [ "wait for jenkins" ]
  }
}