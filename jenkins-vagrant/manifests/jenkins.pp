import "apt.pp"

class jenkins {
  include apt

  package { "daemon"      : ensure => "installed", require => Exec [ "apt-get update" ] }
  package { "adduser"     : ensure => "installed", require => Exec [ "apt-get update" ] }
  package { "psmisc"      : ensure => "installed", require => Exec [ "apt-get update" ] }
  package { "default-jdk" : ensure => "installed", require => Exec [ "apt-get update" ] }

  exec { "install jenkins":
    command => "/usr/bin/dpkg -i /vagrant/files/jenkins_1.558_all.deb",
    creates => "/var/lib/jenkins/",
    user    => "root",
    require => [
      Package [ "daemon", "adduser", "psmisc", "default-jdk" ],
      Exec [ "apt-get update" ]
    ]
  }

  service { "jenkins":
    ensure  => running,
    enable  => true,
    require => Exec [ "install jenkins" ]
  }

  exec { "restart jenkins":
    command     => "/usr/bin/service jenkins restart",
    user        => "root",
    refreshonly => true,
    require     => Exec [ "install jenkins" ]
  }

  user { "jenkins":
    groups  => "vagrant",
    require => Exec [ "install jenkins" ]
  }

  $waitForJenkins = ""

  exec { "jenkins started":
    logoutput => "true",
    command   => "/usr/bin/wget --spider --tries 10 --retry-connrefused http://10.10.10.2:8080/api/json || sleep 2",
    require   => Service [ "jenkins" ]
  }

  define plugin ($plugin) {
    file { "/var/lib/jenkins/plugins/$plugin":
      ensure  => "file",
      owner   => "jenkins",
      group   => "nogroup",
      source  => "/vagrant/files/$plugin",
      require => Exec [ "jenkins started" ],
      notify  => Exec [ "restart jenkins" ]
    }
  }

  file { "/etc/default/jenkins":
    ensure  => "file",
    owner   => "root",
    group   => "root",
    source  => "/vagrant/files/jenkins",
    require => Exec [ "jenkins started" ]
  }

  define job ($name) {
    file { "/var/lib/jenkins/jobs/$name/":
      ensure  => "directory",
      group   => "nogroup",
      owner   => "jenkins",
      require => Exec [ "jenkins started" ],
      notify  => Exec [ "restart jenkins" ]
    }

    file { "/var/lib/jenkins/jobs/$name/config.xml":
      ensure  => "file",
      group   => "nogroup",
      owner   => "jenkins",
      source  => "/vagrant/files/$name.xml",
      require => File [ "/var/lib/jenkins/jobs/$name/" ]
    }
  }
}